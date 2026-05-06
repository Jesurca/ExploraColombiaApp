package me.jesusurbinez.exploracolombiaapp.data

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class Place(
    val name: String = "",
    val department: String = "",
    val city: String = "",
    val description: String = "",
    val userId: String = "",
    @ServerTimestamp val createdAt: Date? = null
)
