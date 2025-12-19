package com.example.questapi_236.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.questapi_236.modeldata.DetailSiswa
import com.example.questapi_236.modeldata.UIStateSiswa
import com.example.questapi_236.modeldata.toDataSiswa
import com.example.questapi_236.modeldata.toDetailSiswa
import com.example.questapi_236.repositori.RepositoryDataSiswa
import kotlinx.coroutines.launch

class EditViewModel(
    private val repositoryDataSiswa: RepositoryDataSiswa
) : ViewModel() {
    var updateUiState by mutableStateOf(UIStateSiswa())
        private set

    fun loadSiswa(id: Int) {
        viewModelScope.launch {
            try {
                val siswa = repositoryDataSiswa.getSiswaById(id)
                updateUiState =
                    UIStateSiswa(detailSiswa = siswa.toDetailSiswa(), isEntryValid = true)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateUiState(detailSiswa: DetailSiswa) {
        updateUiState = UIStateSiswa(
            detailSiswa = detailSiswa,
            isEntryValid = validasiInput(detailSiswa)
        )
    }

    private fun validasiInput(uiState: DetailSiswa): Boolean {
        return with(uiState) {
            nama.isNotBlank() && alamat.isNotBlank() && telpon.isNotBlank()
        }
    }

    // Di dalam UpdateViewModel
    suspend fun updateSiswa(id: Int) {
        if (validasiInput(updateUiState.detailSiswa)) {
            try {
                // Mengirimkan ID siswa dan data yang sudah diubah (Susan)
                repositoryDataSiswa.updateSiswa(id, updateUiState.detailSiswa.toDataSiswa())
                println("Update Berhasil di ViewModel")
            } catch (e: Exception) {
                println("Error Update: ${e.message}")
            }
        }
    }
}