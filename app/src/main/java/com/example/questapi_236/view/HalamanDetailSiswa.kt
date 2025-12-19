package com.example.questapi_236.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.questapi_236.viewmodel.DetailUiState
import com.example.questapi_236.viewmodel.DetailViewModel
import com.example.questapi_236.viewmodel.provider.PenyediaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailSiswaScreen(
    id: Int,
    navigateBack: () -> Unit,
    onClickEdit: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val detailUiState = viewModel.detailUiState
    val coroutineScope = rememberCoroutineScope()

    // State untuk mengontrol tampilan Pop-up
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }

    LaunchedEffect(id) {
        viewModel.getSiswaById(id)
    }

    // --- Logic Pop-up Hapus ---
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Konfirmasi Hapus") },
            text = { Text("Apakah Anda yakin ingin menghapus data siswa ini?") },
            confirmButton = {
                TextButton(onClick = {
                    showDeleteDialog = false
                    coroutineScope.launch {
                        viewModel.deleteSiswa(id)
                        navigateBack()
                    }
                }) {
                    Text("Iya", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Tidak")
                }
            }
        )
    }

    // --- Logic Pop-up Edit ---
    if (showEditDialog) {
        AlertDialog(
            onDismissRequest = { showEditDialog = false },
            title = { Text("Konfirmasi Edit") },
            text = { Text("Apakah Anda ingin mengubah data siswa ini?") },
            confirmButton = {
                TextButton(onClick = {
                    showEditDialog = false
                    onClickEdit(id)
                }) {
                    Text("Iya")
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditDialog = false }) {
                    Text("Tidak")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            SiswaTopAppBar(
                title = "Detail Siswa",
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showEditDialog = true }, // Pemicu Pop-up Edit
                shape = MaterialTheme.shapes.medium,
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Siswa")
            }
        }
    ) { innerPadding ->
        DetailBody(
            detailUiState = detailUiState,
            onDeleteClick = { showDeleteDialog = true }, // Pemicu Pop-up Hapus
            onEditClick = { showEditDialog = true },   // Pemicu Pop-up Edit
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun DetailBody(
    detailUiState: DetailUiState,
    onDeleteClick: () -> Unit,
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (detailUiState) {
        is DetailUiState.Loading -> Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        is DetailUiState.Success -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFDDE6FF)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        ComponentDetail(label = "Nama Siswa", value = detailUiState.siswa.nama)
                        ComponentDetail(label = "Alamat Siswa", value = detailUiState.siswa.alamat)
                        ComponentDetail(label = "Telpon Siswa", value = detailUiState.siswa.telpon)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Tambahkan tombol Edit dan Hapus di sini
                Column{
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Button(
                            onClick = onEditClick,
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5A84E2)),
                            shape = MaterialTheme.shapes.small
                        ) {
                            Text("Edit Data")
                        }
                        Button(
                            onClick = onDeleteClick,
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD9534F)),
                            shape = MaterialTheme.shapes.small
                        ) {
                            Text("Hapus Data")
                        }
                    }
                }
            }
        }
        is DetailUiState.Error -> Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Gagal memuat data siswa.")
        }
    }
}

@Composable
fun ComponentDetail(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$label :",
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color(0xFF3F51B5),
                fontWeight = FontWeight.Medium
            )
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2C3E50)
            )
        )
    }
}