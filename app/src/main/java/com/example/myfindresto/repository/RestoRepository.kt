package com.example.myfindresto.repository

import com.example.myfindresto.model.RestoModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class RestoRepository {
    private val db = FirebaseFirestore.getInstance()
    suspend fun fetchRestaurants(): List<RestoModel> {
        val snapshot = db.collection("restaurants").get().await()
        return snapshot.documents.mapNotNull { it.toObject(RestoModel::class.java) }
    }
}