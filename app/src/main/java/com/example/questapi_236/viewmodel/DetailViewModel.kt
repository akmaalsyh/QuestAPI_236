package com.example.questapi_236.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.questapi_236.modeldata.DataSiswa
import com.example.questapi_236.repositori.RepositoryDataSiswa
import kotlinx.coroutines.launch

sealed interface DetailUiState {
    data class Success(val siswa: DataSiswa) : DetailUiState
    object Error : DetailUiState
    object Loading : DetailUiState
}

class DetailViewModel(private val repositoryDataSiswa: RepositoryDataSiswa) : ViewModel() {
    var detailUiState: DetailUiState by mutableStateOf(DetailUiState.Loading)
        private set

    fun getSiswaById(id: Int) {
        viewModelScope.launch {
            detailUiState = DetailUiState.Loading
            try {
                val response = repositoryDataSiswa.getSiswaById(id)
                // Tambahkan validasi jika data yang datang null/kosong
                if (response != null) {
                    detailUiState = DetailUiState.Success(response)
                } else {
                    detailUiState = DetailUiState.Error
                }
            } catch (e: Exception) {
                detailUiState = DetailUiState.Error
            }
        }
    }

    fun deleteSiswa(id: Int) {
        viewModelScope.launch {
            try {
                repositoryDataSiswa.deleteSiswa(id)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}