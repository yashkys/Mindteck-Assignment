package com.kys.mindteck.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kys.mindteck.ui.features.home.HomeScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = HomeScreen) {

        composable<HomeScreen>() {
            HomeScreen(navController)
        }

    }
}