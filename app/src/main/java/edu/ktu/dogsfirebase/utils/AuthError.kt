package edu.ktu.dogsfirebase.utils

enum class AuthError
{
    NoError,
    NoEmailProvided,
    MalformedEmail,
    NoPasswordProvided,
    WrongCredentials,
    UserExists,
    WeakPassword,
}