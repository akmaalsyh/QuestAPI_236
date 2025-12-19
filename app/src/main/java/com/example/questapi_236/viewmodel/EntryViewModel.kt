package com.example.questapi_236.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.questapi_236.modeldata.DetailSiswa
import com.example.questapi_236.modeldata.UIStateSiswa
import com.example.questapi_236.modeldata.toDataSiswa
import com.example.questapi_236.repositori.RepositoryDataSiswa
import kotlinx.coroutines.launch
import retrofit2.Response

class EntryViewModel(private val repositoryDataSiswa: RepositoryDataSiswa) : ViewModel() {
    var uiStateSiswa by mutableStateOf(UIStateSiswa())
        private set

    /* Fungsi untuk memvalidasi input */
    private fun validasiInput(uiState: DetailSiswa = uiStateSiswa.detailSiswa): Boolean {
        return with(uiState) {
            nama.isNotBlank() && alamat.isNotBlank() && telpon.isNotBlank()
        }
    }

    // Fungsi untuk menangani saat ada perubahan pada text input
    fun updateUiState(detailSiswa: DetailSiswa) {
        uiStateSiswa = UIStateSiswa(
            detailSiswa = detailSiswa,
            isEntryValid = validasiInput(detailSiswa)
        )
    }

    /* Fungsi untuk menyimpan data yang di-entry */
    suspend fun addSiswa() {
        if (validasiInput()) {
            try {
                // Konversi detailSiswa ke DataSiswa untuk dikirim ke API
                val response: Response<Void> = repositoryDataSiswa.postDataSiswa(
                    uiStateSiswa.detailSiswa.toDataSiswa()
                )

                if (response.isSuccessful) {
                    println("Sukses Tambah Data")
                } else {
                    println("Gagal tambah data: ${response.message()}")
                }
            } catch (e: Exception) {
                // Menangkap error jaringan agar aplikasi tidak crash
                println("Error Jaringan: ${e.message}")
            }
        }
    }
}