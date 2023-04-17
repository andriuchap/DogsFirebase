package edu.ktu.dogsfirebase.utils

import java.util.regex.Pattern

    val PASSWORD: Pattern
        get() = Pattern.compile(
            "(?=(.*[A-Z])+)" +
                    "(?=(.*[A-Z])+)" +
                    "(?=(.*[!@#\$%^&*()\\-__+.\\d])+)" +
                    ".{8,}"
        )