package com.example.health.data.remote.auth

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.health.R
import com.example.health.data.local.appdatabase.AppDatabase
import com.example.health.data.local.entities.Account
import com.example.health.data.local.repostories.AccountRepository
import com.example.health.data.local.repostories.BaseInfoRepository
import com.example.health.data.local.repostories.HealthMetricRepository
import com.example.health.data.local.viewmodel.AccountViewModel
import com.example.health.data.local.viewmodel.BaseInfoViewModel
import com.example.health.data.local.viewmodel.HealthMetricViewModel
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

sealed class AuthState {
    object Unauthenticated : AuthState()
    object AuthenticatedButNotRegistered : AuthState()
    object Authenticated : AuthState()
}

class AuthViewModel(
    private val context: Context,
    private val accountRepository: AccountRepository
) : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    private val _authState = MutableStateFlow<AuthState>(AuthState.Unauthenticated)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    private val _isProcessing = MutableStateFlow(false)
    val isProcessing: StateFlow<Boolean> = _isProcessing.asStateFlow()

    val currentUser: FirebaseUser? get() = auth.currentUser

    private val googleSignInClient: GoogleSignInClient

    init {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.web_id)) // ✅ lấy context thay vì application
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(context, gso)
        checkLoginStatus()
    }

    fun getSignInIntent(): Intent = googleSignInClient.signInIntent

    fun signInWithGoogle(data: Intent?, onError: (Exception) -> Unit) {
        viewModelScope.launch {
            _isProcessing.value = true
            try {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
            } catch (e: Exception) {
                onError(e)
            } finally {
                _isProcessing.value = false
            }
        }
    }

    private suspend fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        val result = auth.signInWithCredential(credential).await()
        val user = result.user ?: return
        checkAccountStatus(user.uid, user.displayName ?: "", user.email ?: "")
    }

    private fun checkLoginStatus() {
        val user = auth.currentUser ?: run {
            _authState.value = AuthState.Unauthenticated
            return
        }
        viewModelScope.launch {
            checkAccountStatus(user.uid, user.displayName ?: "", user.email ?: "")
        }
    }

    private suspend fun checkAccountStatus(uid: String, name: String, email: String) {
        val doc = firestore.collection("accounts").document(uid).get().await()

        if (!doc.exists()) {
            val account = Account(Uid = uid, Name = name, Email = email, Status = "incomplete")
            accountRepository.insertAccount(account)
            _authState.value = AuthState.AuthenticatedButNotRegistered
        } else {
            val status = doc.getString("status")
            _authState.value = if (status == "completed")
                AuthState.Authenticated
            else
                AuthState.AuthenticatedButNotRegistered

            if (status == "completed") {
                syncAllDataFromFirestore(uid)
            }
        }
    }

    private suspend fun syncAllDataFromFirestore(uid: String) {
        try {
            accountRepository.fetchFromRemote(uid)
            delay(500)

            val db = AppDatabase.getDatabase(context)
            val baseInfoRepo = BaseInfoRepository(db.baseInfoDao(), db.pendingActionDao(), firestore)
            val healthRepo = HealthMetricRepository(db.healMetricDao(), db.pendingActionDao(), firestore)

            baseInfoRepo.fetchFromRemote(uid)
            healthRepo.fetchAllFromRemote(uid)
        } catch (_: Exception) {
        }
    }

    fun updateStatus(uid: String, status: String) {
        viewModelScope.launch {
            accountRepository.updateStatus(uid, status)
            _authState.value = if (status == "completed") AuthState.Authenticated else AuthState.AuthenticatedButNotRegistered
        }
    }

    fun signOut(
        accountViewModel: AccountViewModel,
        baseInfoViewModel: BaseInfoViewModel,
        healthMetricViewModel: HealthMetricViewModel
    ) {
        viewModelScope.launch {
            auth.signOut()
            googleSignInClient.signOut().await()

            healthMetricViewModel.deleteAllHealthMetrics()
            accountViewModel.deleteAccount()
            baseInfoViewModel.deleteBaseInfo()

            _authState.value = AuthState.Unauthenticated
        }
    }
}
