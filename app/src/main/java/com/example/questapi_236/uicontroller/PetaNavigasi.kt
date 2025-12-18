package com.example.questapi_236.uicontroller

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.questapi_236.uicontroller.route.DestinasiEntry
import com.example.questapi_236.uicontroller.route.DestinasiHome
import com.example.questapi_236.view.EntrySiswaScreen
import com.example.questapi_236.view.HomeScreen

@Composable
fun DataSiswaApp(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    HostNavigasi(navController = navController)
}

@Composable
fun HostNavigasi(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = modifier
    ) {
        // Rute untuk Halaman Utama (Home)
        composable(DestinasiHome.route) {
            HomeScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntry.route) },
                navigateToItemUpdate = {
                    // Navigasi ke detail/update jika sudah diimplementasikan
                    // navController.navigate("${DestinasiDetail.route}/${it}")
                }
            )
        }

        // Rute untuk Halaman Tambah Siswa (Entry)
        composable(DestinasiEntry.route) {
            EntrySiswaScreen(
                navigateBack = {
                    navController.navigate(DestinasiHome.route) {
                        // Menghapus tumpukan backstack agar kembali ke home dengan bersih
                        popUpTo(DestinasiHome.route) { inclusive = true }
                    }
                }
            )
        }
    }
}