package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.model.Bill
import com.example.data.repository.BillRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddEditBillViewModel(private val repository: BillRepository = BillRepository()) : ViewModel() {
    
    private val _bill = MutableStateFlow(Bill())
    val bill: StateFlow<Bill> = _bill.asStateFlow()
    
    private val _isSaving = MutableStateFlow(false)
    val isSaving: StateFlow<Boolean> = _isSaving.asStateFlow()
    
    private val _saveSuccess = MutableStateFlow(false)
    val saveSuccess: StateFlow<Boolean> = _saveSuccess.asStateFlow()
    
    fun loadBill(billId: String) {
        if (billId == "new") return
        viewModelScope.launch {
            repository.getBill(billId).onSuccess { loadedBill ->
                loadedBill?.let { _bill.value = it }
            }
        }
    }
    
    fun updateBill(updatedBill: Bill) {
        _bill.value = updatedBill
    }
    
    fun saveBill() {
        _isSaving.value = true
        viewModelScope.launch {
            val result = if (_bill.value.id.isEmpty()) {
                repository.createBill(_bill.value)
            } else {
                repository.updateBill(_bill.value)
            }
            _isSaving.value = false
            if (result.isSuccess) {
                _saveSuccess.value = true
            }
        }
    }
}

class AddEditBillViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddEditBillViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddEditBillViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
