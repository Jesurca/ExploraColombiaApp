package me.jesusurbinez.exploracolombiaapp.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import me.jesusurbinez.exploracolombiaapp.data.Place

class AddPlaceViewModel : ViewModel() {
    // Estado de los campos
    var placeName by mutableStateOf("")
    var department by mutableStateOf("")
    var city by mutableStateOf("")
    var description by mutableStateOf("")
    
    var isLoading by mutableStateOf(false)
        private set

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    // Lógica para validar y guardar
    fun onPublishClick(onSuccess: () -> Unit, onError: (String) -> Unit) {
        if (placeName.isBlank() || department.isBlank() || city.isBlank() || description.isBlank()) {
            onError("Por favor, completa todos los campos.")
            return
        }

        val currentUser = auth.currentUser
        if (currentUser == null) {
            onError("Debes iniciar sesión para publicar.")
            return
        }

        isLoading = true
        val place = Place(
            name = placeName,
            department = department,
            city = city,
            description = description,
            userId = currentUser.uid
        )

        firestore.collection("places")
            .add(place)
            .addOnSuccessListener {
                isLoading = false
                onSuccess()
            }
            .addOnFailureListener { e ->
                isLoading = false
                onError(e.message ?: "Error al guardar el lugar")
            }
    }
}
