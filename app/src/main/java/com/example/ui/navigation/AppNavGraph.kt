package com.example.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ui.screens.auth.LoginScreen
import com.example.ui.screens.auth.SignupScreen
import com.example.ui.screens.dashboard.DashboardScreen
import com.example.ui.screens.bills.BillsManagerScreen
import com.example.ui.screens.bills.AddEditBillScreen
import com.example.ui.screens.bills.BillDetailsScreen
import com.example.ui.screens.profile.ProfileScreen
import com.example.ui.screens.settings.SettingsScreen

object Routes {
    const val LOGIN = "login"
    const val SIGNUP = "signup"
    const val DASHBOARD = "dashboard"
    const val BILLS_MANAGER = "bills_manager"
    const val ADD_EDIT_BILL = "add_edit_bill/{billId}"
    const val BILL_DETAILS = "bill_details/{billId}"
    const val PROFILE = "profile"
    const val SETTINGS = "settings"
    
    fun addEditBillRoute(billId: String = "new") = "add_edit_bill/$billId"
    fun billDetailsRoute(billId: String) = "bill_details/$billId"
}

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Routes.LOGIN
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Routes.LOGIN) {
            LoginScreen(
                onNavigateToSignup = { navController.navigate(Routes.SIGNUP) },
                onLoginSuccess = {
                    navController.navigate(Routes.DASHBOARD) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.SIGNUP) {
            SignupScreen(
                onNavigateToLogin = { navController.popBackStack() },
                onSignupSuccess = {
                    navController.navigate(Routes.DASHBOARD) {
                        popUpTo(Routes.SIGNUP) { inclusive = true }
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.DASHBOARD) {
            DashboardScreen(navController = navController)
        }
        composable(Routes.BILLS_MANAGER) {
            BillsManagerScreen(navController = navController)
        }
        composable(Routes.ADD_EDIT_BILL) { backStackEntry ->
            val billId = backStackEntry.arguments?.getString("billId") ?: "new"
            AddEditBillScreen(navController = navController, billId = billId)
        }
        composable(Routes.BILL_DETAILS) { backStackEntry ->
            val billId = backStackEntry.arguments?.getString("billId") ?: return@composable
            BillDetailsScreen(navController = navController, billId = billId)
        }
        composable(Routes.PROFILE) {
            ProfileScreen(navController = navController)
        }
        composable(Routes.SETTINGS) {
            SettingsScreen(navController = navController)
        }
    }
}
