package edu.ktu.dogsfirebase.viewmodels

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser
import edu.ktu.dogsfirebase.utils.AuthError
import edu.ktu.dogsfirebase.utils.PASSWORD

class SignUpViewModel : ViewModel() {

    private val _signUpError = MutableLiveData(AuthError.NoError)
    val signUpError: LiveData<AuthError> = _signUpError

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _currentUser = MutableLiveData<FirebaseUser>(null)
    val currentUser: LiveData<FirebaseUser> = _currentUser

    fun signUp(email: String, password: String) {
        if (email.isNullOrEmpty()) {
            _signUpError.value = AuthError.NoEmailProvided
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _signUpError.value = AuthError.MalformedEmail
            return
        }
        if (password.isNullOrEmpty()) {
            _signUpError.value = AuthError.NoPasswordProvided
            return
        }
        if (!PASSWORD.matcher(password).matches()) {
            _signUpError.value = AuthError.WeakPassword
            return
        }

        _isLoading.value = true
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                _isLoading.value = false
                if (task.isSuccessful) {
                    _signUpError.value = AuthError.NoError
                    _currentUser.value = task.result.user
                } else {
                    task.exception?.let {exception ->
                        _signUpError.value = when (exception) {
                            is FirebaseAuthWeakPasswordException ->
                                AuthError.WeakPassword
                            is FirebaseAuthInvalidCredentialsException ->
                                AuthError.MalformedEmail
                            is FirebaseAuthUserCollisionException ->
                                AuthError.UserExists
                            else -> AuthError.NoError
                        }
                    }
                }
            }
    }
}