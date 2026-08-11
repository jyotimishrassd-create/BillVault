package com.example.data.repository

import com.example.data.model.Bill
import com.example.data.model.UserProfile
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class BillRepository(private val authRepository: AuthRepository = AuthRepository()) {
    private val db by lazy { FirebaseFirestore.getInstance() }
    
    private val userId: String
        get() = authRepository.getCurrentUserId() ?: throw IllegalStateException("User not logged in")

    private val billsCollection get() = db.collection("users").document(userId).collection("bills")
    private val userProfileCollection get() = db.collection("users")

    suspend fun createBill(bill: Bill): Result<String> {
        return try {
            val docRef = billsCollection.document()
            val newBill = bill.copy(id = docRef.id, ownerId = userId)
            docRef.set(newBill).await()
            Result.success(docRef.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateBill(bill: Bill): Result<Unit> {
        return try {
            billsCollection.document(bill.id).set(bill).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteBill(billId: String): Result<Unit> {
        return try {
            billsCollection.document(billId).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getBill(billId: String): Result<Bill?> {
        return try {
            val snapshot = billsCollection.document(billId).get().await()
            Result.success(snapshot.toObject(Bill::class.java))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getBillsFlow(): Flow<List<Bill>> = callbackFlow {
        val listenerRegistration = billsCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }
            val bills = snapshot?.toObjects(Bill::class.java) ?: emptyList()
            trySend(bills)
        }
        awaitClose { listenerRegistration.remove() }
    }
    
    suspend fun getUserProfile(): Result<UserProfile?> {
        return try {
            val snapshot = userProfileCollection.document(userId).get().await()
            Result.success(snapshot.toObject(UserProfile::class.java))
        } catch(e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun saveUserProfile(profile: UserProfile): Result<Unit> {
        return try {
            userProfileCollection.document(userId).set(profile.copy(uid = userId)).await()
            Result.success(Unit)
        } catch(e: Exception) {
            Result.failure(e)
        }
    }
}
