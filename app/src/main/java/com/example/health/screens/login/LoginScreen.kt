package com.example.health.screens.login

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.health.R
import com.example.health.data.remote.auth.AuthState
import com.example.health.data.remote.auth.AuthViewModel

@Composable
fun LoginScreen(
    authViewModel: AuthViewModel,
    navController: NavController
) {
    val isProcessing by authViewModel.isProcessing.collectAsState()
    val authState by authViewModel.authState.collectAsState()
    var loginError by remember { mutableStateOf<String?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            authViewModel.signInWithGoogle(result.data) { error ->
                loginError = "Login failed, please try again"
            }
        }
    }

    // Điều hướng nếu đăng nhập thành công
    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Authenticated -> navController.navigate("home") {
                popUpTo("login") { inclusive = true }
            }
            is AuthState.AuthenticatedButNotRegistered -> navController.navigate("base_info") {
                popUpTo("login") { inclusive = true }
            }
            else -> Unit
        }
    }

    // Giao diện nền
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.login_background), // Ảnh nền bạn có sẵn
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Khối nội dung
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.6f),
                //elevation = CardDefaults.cardElevation(defaultElevation = 0.5.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.4f))

            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center

                ) {
                    // Logo
                    Image(
                        painter = painterResource(id = R.drawable.android_light_rd_na),
                        contentDescription = "Logo",
                        modifier = Modifier.size(64.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text ="Login",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Image(
                        painter = painterResource(id = R.drawable.android_light_rd_ctn),
                        contentDescription = "Sign in with Google",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .clickable {
                                val intent = authViewModel.getSignInIntent()
                                launcher.launch(intent)
                            }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        buildAnnotatedString {
                            append("By clicking continue with Google, you agree to our ")
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Terms of Service")
                            }
                            append(" and ")
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Privacy Policy")
                            }
                        },
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center
                    )


                    if (loginError != null) {
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            text = "Login failed, please try again",
                            color = Color(0xFFE53935), // Đỏ tươi
                            style = MaterialTheme.typography.bodyMedium,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }


                    if (isProcessing) {
                        Spacer(modifier = Modifier.height(8.dp))
                        CircularProgressIndicator(modifier = Modifier.size(50.dp))
                    }
                }
            }
        }
    }
}
