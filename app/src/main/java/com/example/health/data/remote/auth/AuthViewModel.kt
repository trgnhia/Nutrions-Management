package com.example.health.data.remote.auth

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

sealed class AuthState {
    object Unauthenticated : AuthState()
    object AuthenticatedButNotRegistered : AuthState()
    object Authenticated : AuthState()
}

class AuthViewModel(
    application: Application,
    private val accountRepository: AccountRepository
) : AndroidViewModel(application) {

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
            .requestIdToken(application.getString(R.string.web_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(application, gso)
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
                val acc = Account(
                    Uid = account.id ?: "",
                    Name = account.displayName ?: "",
                    Email = account.email ?: "",
                    Status = "incomplete"
                )
                accountRepository.insertAccount(acc)
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
            // Nếu tài khoản chưa tồn tại, tạo tài khoản mới và đánh dấu "incomplete"
            val account = Account(Uid = uid, Name = name, Email = email, Status = "incomplete")
            accountRepository.insertAccount(account)
            _authState.value = AuthState.AuthenticatedButNotRegistered
        } else {
            // Nếu tài khoản tồn tại, lấy thông tin của tài khoản
            val status = doc.getString("status")
            _authState.value = if (status == "completed") AuthState.Authenticated
            else AuthState.AuthenticatedButNotRegistered

    // Nếu tài khoản đã hoàn thành, đồng bộ dữ liệu
            if (status == "completed") {
                syncAllDataFromFirestore(uid)
           }
        }
    }

    // ✅ Đồng bộ Firestore về Room khi Room chưa có dữ liệu
    private suspend fun syncAllDataFromFirestore(uid: String) {
        try {
            accountRepository.fetchFromRemote(uid)
            delay(500)
            // Nếu bạn không dùng DI: tạo tạm repo trong đây
            val app = getApplication<Application>()
            val db = AppDatabase.getDatabase(app)
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

            // 🧹 Xoá dữ liệu local
            healthMetricViewModel.deleteAllHealthMetrics()
            accountViewModel.deleteAccount()
            baseInfoViewModel.deleteBaseInfo()

            _authState.value = AuthState.Unauthenticated
        }
    }

}