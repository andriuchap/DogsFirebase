package edu.ktu.dogsfirebase.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.ktu.dogsfirebase.models.Dog

class DogListViewModel : ViewModel() {

    private val currentUser = FirebaseAuth.getInstance().currentUser
    private var dogListenerRegistration: ListenerRegistration? = null

    private val _dogs = MutableLiveData<List<Dog>>()
    val dogs: LiveData<List<Dog>> = _dogs

    init {
        currentUser?.let {
            dogListenerRegistration = Firebase.firestore.collection("dogs").whereEqualTo("owner", currentUser.uid)
                .addSnapshotListener { snapshot, e ->
                    e?.let {
                        Log.w("TAG", "listen:error", e)
                        return@addSnapshotListener
                    }

                    if (snapshot != null && !snapshot.isEmpty) {
                        _dogs.value = snapshot.documents.map {
                            Dog(
                                it.id, it["name"] as String, (it["age"] as Long).toInt(),
                                it["breed"] as String
                            )
                        }
                    }
                }
        }
    }

    fun deleteDog(dog: Dog)
    {
        currentUser?.let {
            Firebase.firestore.collection("dogs").document(dog.uid).delete()
        }
    }

    fun signOut()
    {
        currentUser?.let {
            dogListenerRegistration?.remove()
            FirebaseAuth.getInstance().signOut()
        }
    }
}