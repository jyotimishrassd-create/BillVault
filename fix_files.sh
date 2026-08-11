#!/bin/bash
cat << 'INNER_EOF' > app/src/main/java/com/example/ui/screens/bills/AddEditBillScreen.kt
package com.example.ui.screens.bills

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.viewmodel.AddEditBillViewModel
import com.example.viewmodel.AddEditBillViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditBillScreen(
    navController: NavController,
    billId: String,
    viewModel: AddEditBillViewModel = viewModel(factory = AddEditBillViewModelFactory())
) {
    val bill by viewModel.bill.collectAsState()
    val isSaving by viewModel.isSaving.collectAsState()
    val saveSuccess by viewModel.saveSuccess.collectAsState()

    LaunchedEffect(billId) {
        viewModel.loadBill(billId)
    }

    LaunchedEffect(saveSuccess) {
        if (saveSuccess) {
            navController.popBackStack()
        }
    }

    Scaffold(
        containerColor = Color(0xFFF8FAFC),
        topBar = {
            TopAppBar(
                title = { Text(if (billId == "new") "Create Bill" else "Edit Bill") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = bill.customerName,
                onValueChange = { viewModel.updateBill(bill.copy(customerName = it)) },
                label = { Text("Customer Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = bill.billNumber,
                onValueChange = { viewModel.updateBill(bill.copy(billNumber = it)) },
                label = { Text("Bill Number") },
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.weight(1f))
            
            Button(
                onClick = { viewModel.saveBill() },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isSaving
            ) {
                if (isSaving) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                } else {
                    Text("Save Bill")
                }
            }
        }
    }
}
INNER_EOF

cat << 'INNER_EOF' > app/src/main/java/com/example/ui/screens/bills/BillDetailsScreen.kt
package com.example.ui.screens.bills

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BillDetailsScreen(
    navController: NavController,
    billId: String
) {
    Scaffold(
        containerColor = Color(0xFFF8FAFC),
        topBar = {
            TopAppBar(
                title = { Text("Bill Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Details for bill: $billId", style = MaterialTheme.typography.headlineMedium)
            // Implementation for bill details
        }
    }
}
INNER_EOF
