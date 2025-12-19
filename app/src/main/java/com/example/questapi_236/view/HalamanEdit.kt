package com.example.questapi_236.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.questapi_236.viewmodel.EditViewModel
import com.example.questapi_236.viewmodel.provider.PenyediaViewModel
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalMaterial3Api::class)
@Composable
fun UpdateSiswaScreen(
    id: Int,
    navigateBack: () -> Unit,
    viewModel: EditViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()

    // Ambil data lama saat pertama kali dibuka
    LaunchedEffect(id) {
        viewModel.loadSiswa(id)
    }

    Scaffold(
        topBar = {
            SiswaTopAppBar(title = "Edit Siswa", canNavigateBack = true, navigateUp = navigateBack)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            FormTambahSiswa(
                detailSiswa = viewModel.updateUiState.detailSiswa,
                onValueChange = viewModel::updateUiState,
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    coroutineScope.launch {
                        viewModel.updateSiswa(id)
                        navigateBack()
                    }
                },
                enabled = viewModel.updateUiState.isEntryValid,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Simpan Perubahan")
            }
        }
    }
}