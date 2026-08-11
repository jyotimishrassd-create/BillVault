package com.example.ui.screens.bills

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.ui.navigation.Routes
import com.example.ui.screens.dashboard.BillCard
import com.example.viewmodel.BillViewModel
import com.example.viewmodel.BillViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BillsManagerScreen(
    navController: NavController,
    viewModel: BillViewModel = viewModel(factory = BillViewModelFactory())
) {
    val bills by viewModel.bills.collectAsState()

    Scaffold(
        containerColor = Color(0xFFF8FAFC),
        topBar = {
            TopAppBar(
                title = { Text("Bills", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFF8FAFC)
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { Spacer(modifier = Modifier.height(8.dp)) }
            
            items(bills) { bill ->
                BillCard(bill = bill, onClick = { navController.navigate(Routes.billDetailsRoute(bill.id)) })
            }
            
            item { Spacer(modifier = Modifier.height(80.dp)) }
        }
    }
}
