package com.example.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.ui.navigation.Routes
import com.example.viewmodel.AuthViewModel
import com.example.viewmodel.AuthViewModelFactory
import com.example.viewmodel.AuthState

val ElectricIndigo = Color(0xFF635BFF)
val TextPrimary = Color(0xFF0F172A)
val TextMuted = Color(0xFF64748B)
val BorderLight = Color(0xFFF1F5F9)
val SurfaceLight = Color(0xFFFFFFFF)
val DangerRed = Color(0xFFEF4444)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory())
) {
    val authState by authViewModel.authState.collectAsState()

    LaunchedEffect(authState) {
        if (authState is AuthState.Unauthenticated) {
            navController.navigate(Routes.LOGIN) {
                popUpTo(0)
            }
        }
    }

    Scaffold(
        containerColor = Color(0xFFF8FAFC),
        topBar = {
            TopAppBar(
                title = { Text("Profile", fontWeight = FontWeight.Bold) },
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
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(ElectricIndigo, CircleShape)
                            .border(4.dp, SurfaceLight, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "SM",
                            color = SurfaceLight,
                            style = MaterialTheme.typography.displaySmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "Shashwat Mishra",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        "shashwat@example.com",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextMuted
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Member since August 2026",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextMuted,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    OutlinedButton(
                        onClick = { /* Edit Profile */ },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = ElectricIndigo
                        ),
                        border = androidx.compose.foundation.BorderStroke(1.dp, ElectricIndigo)
                    ) {
                        Icon(Icons.Outlined.Edit, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Edit Profile", fontWeight = FontWeight.Bold)
                    }
                }
            }
            
            item {
                Text(
                    "RECENT ACTIVITY",
                    style = MaterialTheme.typography.labelMedium,
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                )
            }
            
            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceLight),
                    border = androidx.compose.foundation.BorderStroke(1.dp, BorderLight),
                    elevation = CardDefaults.cardElevation(0.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        ActivityItem(
                            icon = Icons.Outlined.CheckCircle,
                            iconTint = Color(0xFF22C55E),
                            iconBg = Color(0xFFF0FDF4),
                            title = "Created Bill #BL-1024",
                            time = "Today, 10:42 AM"
                        )
                        HorizontalDivider(color = BorderLight)
                        ActivityItem(
                            icon = Icons.Outlined.Edit,
                            iconTint = ElectricIndigo,
                            iconBg = Color(0xFFEEF2FF),
                            title = "Updated Bill #BL-1022",
                            time = "Today, 9:30 AM"
                        )
                        HorizontalDivider(color = BorderLight)
                        ActivityItem(
                            icon = Icons.Outlined.CheckCircle,
                            iconTint = Color(0xFF22C55E),
                            iconBg = Color(0xFFF0FDF4),
                            title = "Marked Bill #BL-1019 as Paid",
                            time = "Yesterday, 6:21 PM"
                        )
                        HorizontalDivider(color = BorderLight)
                        ActivityItem(
                            icon = Icons.Outlined.Delete,
                            iconTint = DangerRed,
                            iconBg = Color(0xFFFEF2F2),
                            title = "Deleted Bill #BL-1015",
                            time = "Yesterday, 4:12 PM"
                        )
                    }
                }
            }
            
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { authViewModel.signOut() },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFEF2F2),
                        contentColor = DangerRed
                    ),
                    shape = RoundedCornerShape(16.dp),
                    elevation = ButtonDefaults.buttonElevation(0.dp)
                ) {
                    Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Sign Out", fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

@Composable
fun ActivityItem(icon: ImageVector, iconTint: Color, iconBg: Color, title: String, time: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(iconBg, RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(20.dp))
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(title, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold, color = TextPrimary)
            Text(time, style = MaterialTheme.typography.labelSmall, color = TextMuted)
        }
    }
}
