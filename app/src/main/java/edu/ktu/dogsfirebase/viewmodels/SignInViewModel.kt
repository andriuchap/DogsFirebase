package edu.ktu.dogsfirebase.viewmodels

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseUser
import edu.ktu.dogsfirebase.utils.AuthError

class SignInViewModel : ViewModel() {

    private val _currentUser = MutableLiveData<FirebaseUser>(null)
    val currentUser: LiveData<FirebaseUser> = _currentUser

    private val _signInError = MutableLiveData(AuthError.NoError)
    val signInError: LiveData<AuthError> = _signInError

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        _currentUser.value = FirebaseAuth.getInstance().currentUser
    }

    fun signIn(email: String, password: String) {
        if (email.isNullOrEmpty()) {
            _signInError.value = AuthError.NoEmailProvided
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _signInError.value = AuthError.MalformedEmail
            return
        }
        if (password.isNullOrEmpty()) {
            _signInError.value = AuthError.NoPasswordProvided
            return
        }

        _isLoading.value = true
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                _isLoading.value = false
                if (it.isSuccessful) {
                    _currentUser.value = it.result.user
                    _signInError.value = AuthError.NoError
                } else {
                    it.exception?.let {
                        if (it is FirebaseAuthInvalidUserException || it is FirebaseAuthInvalidCredentialsException) {
                            _signInError.value = AuthError.WrongCredentials
                        }
                    }
                }
            }
    }
}