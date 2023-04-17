package edu.ktu.dogsfirebase.utils

enum class SignUpError
{
    NoError,
    NoEmailProvided,
    MalformedEmail,
    UserExists,
    WeakPassword,
}