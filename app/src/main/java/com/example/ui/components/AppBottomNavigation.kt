package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.ui.navigation.Routes

val ElectricIndigo = Color(0xFF635BFF)
val TextMuted = Color(0xFF64748B)

@Composable
fun AppBottomNavigation(navController: NavController) {
    val navBackStackEntry = navController.currentBackStackEntryAsState().value
    val currentRoute = navBackStackEntry?.destination?.route

    // Define the top-level routes that should show the bottom bar
    val showBottomBar = currentRoute in listOf(
        Routes.DASHBOARD,
        Routes.BILLS_MANAGER,
        Routes.PROFILE,
        Routes.SETTINGS
    )

    if (showBottomBar) {
        NavigationBar(
            containerColor = Color.White,
            contentColor = ElectricIndigo,
            tonalElevation = 8.dp
        ) {
            NavigationBarItem(
                icon = { Icon(if (currentRoute == Routes.DASHBOARD) Icons.Filled.Home else Icons.Outlined.Home, contentDescription = "Home") },
                label = { Text("Home", fontWeight = FontWeight.Bold) },
                selected = currentRoute == Routes.DASHBOARD,
                onClick = {
                    navController.navigate(Routes.DASHBOARD) {
                        popUpTo(Routes.DASHBOARD) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = ElectricIndigo,
                    unselectedIconColor = TextMuted,
                    selectedTextColor = ElectricIndigo,
                    unselectedTextColor = TextMuted,
                    indicatorColor = Color.Transparent
                )
            )
            NavigationBarItem(
                icon = { Icon(if (currentRoute == Routes.BILLS_MANAGER) Icons.AutoMirrored.Filled.List else Icons.AutoMirrored.Outlined.List, contentDescription = "Bills") },
                label = { Text("Bills", fontWeight = FontWeight.Bold) },
                selected = currentRoute == Routes.BILLS_MANAGER,
                onClick = {
                    navController.navigate(Routes.BILLS_MANAGER) {
                        popUpTo(Routes.DASHBOARD) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = ElectricIndigo,
                    unselectedIconColor = TextMuted,
                    selectedTextColor = ElectricIndigo,
                    unselectedTextColor = TextMuted,
                    indicatorColor = Color.Transparent
                )
            )
            // Central Add Button
            NavigationBarItem(
                icon = { 
                    Icon(
                        imageVector = Icons.Filled.Add, 
                        contentDescription = "Add",
                        modifier = Modifier
                            .offset(y = (-12).dp)
                            .shadow(8.dp, RoundedCornerShape(16.dp))
                            .clip(RoundedCornerShape(16.dp))
                            .background(ElectricIndigo)
                            .padding(12.dp)
                            .size(32.dp),
                        tint = Color.White
                    ) 
                },
                label = null,
                selected = false,
                onClick = {
                    navController.navigate(Routes.addEditBillRoute())
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent
                )
            )
            NavigationBarItem(
                icon = { Icon(Icons.AutoMirrored.Outlined.List, contentDescription = "Receipts") }, // Assuming no Receipts screen yet
                label = { Text("Receipts", fontWeight = FontWeight.Bold) },
                selected = false,
                onClick = { /* TODO */ },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = ElectricIndigo,
                    unselectedIconColor = TextMuted,
                    selectedTextColor = ElectricIndigo,
                    unselectedTextColor = TextMuted,
                    indicatorColor = Color.Transparent
                )
            )
            NavigationBarItem(
                icon = { Icon(if (currentRoute == Routes.PROFILE) Icons.Filled.Person else Icons.Outlined.Person, contentDescription = "Profile") },
                label = { Text("Profile", fontWeight = FontWeight.Bold) },
                selected = currentRoute == Routes.PROFILE,
                onClick = {
                    navController.navigate(Routes.PROFILE) {
                        popUpTo(Routes.DASHBOARD) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = ElectricIndigo,
                    unselectedIconColor = TextMuted,
                    selectedTextColor = ElectricIndigo,
                    unselectedTextColor = TextMuted,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}
