package com.example.data.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

enum class BillStatus { DRAFT, PENDING, PARTIALLY_PAID, PAID, OVERDUE, CANCELLED }

data class BillItem(
    val productService: String = "",
    val description: String = "",
    val quantity: Int = 1,
    val unitPrice: Double = 0.0,
    val discountPercent: Double = 0.0,
    val taxPercent: Double = 0.0
) {
    val itemTotal: Double
        get() = quantity * unitPrice
        
    val discount: Double
        get() = itemTotal * (discountPercent / 100.0)
        
    val taxableAmount: Double
        get() = itemTotal - discount
        
    val tax: Double
        get() = taxableAmount * (taxPercent / 100.0)
        
    val grandTotal: Double
        get() = taxableAmount + tax
}

data class Bill(
    @DocumentId val id: String = "",
    val ownerId: String = "",
    val billNumber: String = "",
    val customerName: String = "",
    val customerEmail: String = "",
    val customerPhone: String = "",
    val customerAddress: String = "",
    val issueDateMillis: Long = System.currentTimeMillis(),
    val dueDateMillis: Long = System.currentTimeMillis() + 7L * 24 * 60 * 60 * 1000,
    val items: List<BillItem> = emptyList(),
    val status: String = BillStatus.DRAFT.name,
    val notes: String = "",
    @ServerTimestamp val createdAt: Date? = null,
    @ServerTimestamp val updatedAt: Date? = null
) {
    val subtotal: Double
        get() = items.sumOf { it.itemTotal }
        
    val discount: Double
        get() = items.sumOf { it.discount }
        
    val tax: Double
        get() = items.sumOf { it.tax }
        
    val total: Double
        get() = items.sumOf { it.grandTotal }
}

data class UserProfile(
    @DocumentId val uid: String = "",
    val fullName: String = "",
    val email: String = "",
    val phone: String = "",
    val bio: String = "",
    val country: String = "",
    val currency: String = "₹",
    @ServerTimestamp val createdAt: Date? = null
)
