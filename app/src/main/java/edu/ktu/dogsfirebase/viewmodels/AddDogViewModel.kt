package edu.ktu.dogsfirebase.viewmodels

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddDogViewModel : ViewModel() {

    fun addDog(name: String, age: Int, breed: String) {
        FirebaseAuth.getInstance().currentUser?.let {user ->
            Firebase.firestore.collection("dogs").add(mapOf(
                "name" to name,
                "age" to age,
                "breed" to breed,
                "owner" to user.uid,
            ))
        }
    }

}