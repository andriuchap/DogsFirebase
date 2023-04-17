package edu.ktu.dogsfirebase.utils

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("authEmailError")
fun TextInputLayout.setAuthEmailError(authError: AuthError)
{
    error = when(authError){
        AuthError.NoEmailProvided -> "No email provided"
        AuthError.MalformedEmail -> "Email is invalid"
        AuthError.UserExists -> "User with such email already exists"
        else -> null
    }
}

@BindingAdapter("authPasswordError")
fun TextInputLayout.setAuthPasswordError(authError: AuthError)
{
    error = when(authError){
        AuthError.NoPasswordProvided-> "No password provided"
        AuthError.WrongCredentials-> "Email or password is incorrect"
        AuthError.WeakPassword-> "Password has to be atleast 8 characters long, have one lower case, one upper case letter and a special symbol (!@#$%^&*()\\-_+.) or digit"
        else->null
    }
}