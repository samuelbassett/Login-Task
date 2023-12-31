package com.tc.login_task.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import com.tc.login_task.data.LoginRepository
import com.tc.login_task.data.Result

import com.tc.login_task.R

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String) {
        // can be launched in a separate asynchronous job
        val result = loginRepository.login(username, password)

        if (result is Result.Success) {
            _loginResult.value =
                LoginResult(success = LoggedInUserView(displayName = result.data.displayName))
        } else {
            _loginResult.value = LoginResult(error = R.string.login_failed)
        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValidLength(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password_length)
        } else if (!isPasswordValidComplexity(password)){
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password_complexity)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else return false

    }

    // A placeholder password validation check
    private fun isPasswordValidLength(password: String): Boolean {
        return password.length > 8
    }

    private fun isPasswordValidComplexity(password: String): Boolean {
        val regex = Regex("[!@#\$%^&*()_+\\-=\\[\\]{};':\",.<>?]+")
        return password.contains(regex)
    }
}