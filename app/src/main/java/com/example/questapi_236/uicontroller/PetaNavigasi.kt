package com.example.questapi_236.uicontroller

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.questapi_236.uicontroller.route.DestinasiDetail
import com.example.questapi_236.uicontroller.route.DestinasiEntry
import com.example.questapi_236.uicontroller.route.DestinasiHome
import com.example.questapi_236.uicontroller.route.DestinasiUpdate
import com.example.questapi_236.view.DetailSiswaScreen
import com.example.questapi_236.view.EntrySiswaScreen
import com.example.questapi_236.view.HomeScreen
import com.example.questapi_236.view.UpdateSiswaScreen

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
                navigateToItemUpdate = { id -> // Pastikan menerima ID
                    // Hapus tanda komentar // di bawah ini
                    navController.navigate("${DestinasiDetail.route}/$id")
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

        // Di dalam HostNavigasi
        composable(
            route = "${DestinasiDetail.route}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            DetailSiswaScreen(
                id = id,
                navigateBack = { navController.popBackStack() },
                // TAMBAHKAN BARIS INI:
                onClickEdit = {
                    // Navigasi ke halaman edit/update (sesuaikan rutenya)
                    navController.navigate("${DestinasiUpdate.route}/$it")
                }
            )
        }

        composable(
            route = "${DestinasiUpdate.route}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            UpdateSiswaScreen(
                id = id,
                navigateBack = { navController.popBackStack() }
            )
        }
    }
}