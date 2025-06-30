package com.example.cryptodashboard.auth.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cryptodashboard.auth.presentation.components.UiTextField
import com.example.cryptodashboard.ui.theme.CryptoDashboardTheme

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewmodel: AuthViewmodel = hiltViewModel<AuthViewmodel>(),
    onNavigateToSignUp: () -> Unit = {},
    onLoginSuccess: () -> Unit = {}
) {
    val contentColor = if (isSystemInDarkTheme()) Color.White else Color.Black
    val user = viewmodel.user

    // Navigate to dashboard when login is successful
    LaunchedEffect(user) {
        if (user != null) {
            onLoginSuccess()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = modifier
                .size(500.dp)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val email by viewmodel.email.collectAsState()
            val password by viewmodel.password.collectAsState()
            var isVisible by rememberSaveable { mutableStateOf(false) }

            Text(
                text = "Login",
                fontSize = 24.sp,
                color = contentColor
            )

            UiTextField(
                modifier = Modifier.fillMaxWidth(),
                value = email,
                onValueChange = viewmodel::onEmailChange,
                placeholder = "Email",
                leadingIcon = Icons.Default.AccountCircle,
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    keyboardType = KeyboardType.Email
                )
            )

            UiTextField(
                modifier = Modifier.fillMaxWidth(),
                value = password,
                onValueChange = viewmodel::onPasswordChange,
                placeholder = "Password",
                leadingIcon = Icons.Default.Password,
                trailingIcon = {
                    IconButton(
                        onClick = { isVisible = !isVisible }
                    ) {
                        Icon(
                            imageVector = if (isVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = null,
                            tint = contentColor
                        )
                    }
                },
                visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    keyboardType = KeyboardType.Password
                )
            )

            Button(
                onClick = { viewmodel.login(email, password) },
                modifier = Modifier.fillMaxWidth(.5f)
            ) {
                Text(
                    text = "Login",
                    fontSize = 16.sp,
                    color = contentColor
                )
            }

            val text = "Don't have an account? Sign Up"
            val startIndex = text.indexOf("Sign Up")
            val endIndex = startIndex + "Sign Up".length
            val annotatedString = buildAnnotatedString {
                append(text)
                addStyle(
                    style = SpanStyle(color = Color.Blue),
                    start = startIndex,
                    end = endIndex
                )
            }

            ClickableText(
                text = annotatedString,
                style = TextStyle(color = contentColor),
                onClick = { offset ->
                    if (offset in startIndex until endIndex) {
                        onNavigateToSignUp()
                    }
                }
            )
        }
    }
}
@PreviewLightDark
@Composable
private fun LoginScreenPreview() {
    CryptoDashboardTheme {
        LoginScreen()
    }
}