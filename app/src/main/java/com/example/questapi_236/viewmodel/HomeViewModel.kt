package com.example.questapi_236.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.questapi_236.modeldata.DataSiswa
import com.example.questapi_236.repositori.RepositoryDataSiswa
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface StatusUiSiswa {
    data class Success(val siswa: List<DataSiswa> = listOf()) : StatusUiSiswa
    object Error : StatusUiSiswa
    object Loading : StatusUiSiswa
}

class HomeViewModel(private val repositoryDataSiswa: RepositoryDataSiswa) : ViewModel() {
    // State untuk menampung status UI dan data siswa
    var listSiswa: StatusUiSiswa by mutableStateOf(StatusUiSiswa.Loading)
        private set

    init {
        loadSiswa()
    }

    // Fungsi untuk mengambil data dari repositori
    fun loadSiswa() {
        viewModelScope.launch {
            listSiswa = StatusUiSiswa.Loading
            listSiswa = try {
                // Berhasil mengambil data
                StatusUiSiswa.Success(repositoryDataSiswa.getDataSiswa())
            } catch (e: IOException) {
                // Kesalahan jaringan
                StatusUiSiswa.Error
            } catch (e: HttpException) {
                // Kesalahan respon server
                StatusUiSiswa.Error
            }
        }
    }
}