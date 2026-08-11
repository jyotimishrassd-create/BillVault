package com.example.ui.screens.dashboard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.data.model.Bill
import com.example.data.model.BillStatus
import com.example.ui.navigation.Routes
import com.example.viewmodel.BillViewModel
import com.example.viewmodel.BillViewModelFactory
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

// Colors directly from design system
val ElectricIndigo = Color(0xFF635BFF)
val SuccessGreen = Color(0xFF22C55E)
val SuccessGreenBg = Color(0xFFF0FDF4)
val WarningYellow = Color(0xFFF59E0B)
val WarningYellowBg = Color(0xFFFFFBEB)
val SurfaceLight = Color(0xFFFFFFFF)
val TextPrimary = Color(0xFF0F172A)
val TextMuted = Color(0xFF64748B)
val BorderLight = Color(0xFFF1F5F9)
val IndigoLightBg = Color(0xFFEEF2FF)

@Composable
fun DashboardScreen(
    navController: NavController,
    viewModel: BillViewModel = viewModel(factory = BillViewModelFactory())
) {
    val bills by viewModel.bills.collectAsState()
    val format = NumberFormat.getCurrencyInstance(Locale.Builder().setLanguage("en").setRegion("IN").build())
    format.currency = Currency.getInstance("INR")

    val totalAmount = bills.sumOf { it.total }
    val pendingAmount = bills.filter { it.status == BillStatus.PENDING.name }.sumOf { it.total }
    val paidAmount = bills.filter { it.status == BillStatus.PAID.name }.sumOf { it.total }

    Scaffold(
        containerColor = Color(0xFFF8FAFC)
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Good morning 👋", style = MaterialTheme.typography.bodySmall, color = TextMuted, fontWeight = FontWeight.Medium)
                        Text("Shashwat Mishra", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = TextPrimary)
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(SurfaceLight, shape = CircleShape)
                                .border(1.dp, BorderLight, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Outlined.Notifications, contentDescription = "Notifications", tint = TextMuted, modifier = Modifier.size(20.dp))
                        }
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(ElectricIndigo, shape = CircleShape)
                                .border(2.dp, SurfaceLight, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("SM", color = SurfaceLight, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelMedium)
                        }
                    }
                }
            }
            
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(32.dp))
                        .background(ElectricIndigo)
                        .padding(24.dp)
                ) {
                    Column(modifier = Modifier.zIndex(1f)) {
                        Text("Total Amount", color = Color.White.copy(alpha = 0.8f), style = MaterialTheme.typography.labelMedium)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(format.format(totalAmount), color = Color.White, style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)
                        
                        Spacer(modifier = Modifier.height(24.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text("TOTAL BILLS", color = Color.White.copy(alpha = 0.7f), style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.SemiBold, letterSpacing = 1.sp)
                                Text(bills.size.toString(), color = Color.White, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                            }
                            Box(
                                modifier = Modifier
                                    .background(Color.White.copy(alpha = 0.2f), RoundedCornerShape(16.dp))
                                    .border(1.dp, Color.White.copy(alpha = 0.3f), RoundedCornerShape(16.dp))
                                    .padding(horizontal = 12.dp, vertical = 4.dp)
                            ) {
                                Text("+12% this month", color = Color.White, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Medium)
                            }
                        }
                    }
                    Box(
                        modifier = Modifier
                            .size(160.dp)
                            .offset(x = 60.dp, y = 60.dp)
                            .background(Color.White.copy(alpha = 0.1f), CircleShape)
                            .align(Alignment.BottomEnd)
                    )
                }
            }
            
            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    StatGridCard(
                        modifier = Modifier.weight(1f),
                        title = "Paid",
                        value = format.format(paidAmount),
                        iconColor = SuccessGreen,
                        iconBgColor = SuccessGreenBg,
                        icon = Icons.Filled.CheckCircle
                    )
                    StatGridCard(
                        modifier = Modifier.weight(1f),
                        title = "Pending",
                        value = format.format(pendingAmount),
                        iconColor = WarningYellow,
                        iconBgColor = WarningYellowBg,
                        icon = Icons.Filled.Warning
                    )
                }
            }
            
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("RECENT BILLS", style = MaterialTheme.typography.labelMedium, color = TextPrimary, fontWeight = FontWeight.Bold, letterSpacing = 0.5.sp)
                    Text("View All", style = MaterialTheme.typography.labelMedium, color = ElectricIndigo, fontWeight = FontWeight.Bold)
                }
            }
            
            if (bills.isEmpty()) {
                item {
                    Box(modifier = Modifier.fillMaxWidth().height(150.dp), contentAlignment = Alignment.Center) {
                        Text("No bills yet. Create one!", color = TextMuted)
                    }
                }
            } else {
                items(bills.take(5)) { bill ->
                    BillCard(bill = bill, onClick = { navController.navigate(Routes.billDetailsRoute(bill.id)) })
                }
            }
            
            item { Spacer(modifier = Modifier.height(20.dp)) }
        }
    }
}

@Composable
fun StatGridCard(modifier: Modifier, title: String, value: String, iconColor: Color, iconBgColor: Color, icon: ImageVector) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceLight),
        border = BorderStroke(1.dp, BorderLight),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(iconBgColor, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(16.dp))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(title, style = MaterialTheme.typography.labelMedium, color = TextMuted, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(2.dp))
            Text(value, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = TextPrimary)
        }
    }
}

@Composable
fun BillCard(bill: Bill, onClick: () -> Unit) {
    val format = NumberFormat.getCurrencyInstance(Locale.Builder().setLanguage("en").setRegion("IN").build())
    format.currency = Currency.getInstance("INR")
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceLight),
        border = BorderStroke(1.dp, BorderLight),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val isPaid = bill.status == BillStatus.PAID.name
            val iconColor = if (isPaid) SuccessGreen else ElectricIndigo
            val iconBg = if (isPaid) SuccessGreenBg else IndigoLightBg
            
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(iconBg, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(bill.billNumber.ifEmpty { "NEW" }.take(5), style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = iconColor)
            }
            
            Column(modifier = Modifier.weight(1f)) {
                Text(bill.customerName.ifEmpty { "Unknown Customer" }, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = TextPrimary)
                Text(if (isPaid) "Yesterday, 9:30 AM" else "Due in 2 days", style = MaterialTheme.typography.labelSmall, color = TextMuted, fontWeight = FontWeight.SemiBold)
            }
            
            Column(horizontalAlignment = Alignment.End) {
                Text(format.format(bill.total), style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = TextPrimary)
                
                val statusColor = if (isPaid) SuccessGreen else WarningYellow
                val statusBg = if (isPaid) SuccessGreenBg else WarningYellowBg
                Box(
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .background(statusBg, RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(bill.status, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = statusColor)
                }
            }
        }
    }
}
