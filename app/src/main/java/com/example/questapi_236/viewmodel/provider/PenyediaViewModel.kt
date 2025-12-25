package com.questapi_236.viewmodel.provider

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.questapi_236.repositori.AplikasiDataSiswa
import com.questapi_236.viewmodel.DetailViewModel
import com.questapi_236.viewmodel.EditViewModel
import com.questapi_236.viewmodel.EntryViewModel
import com.questapi_236.viewmodel.HomeViewModel

fun CreationExtras.aplikasiDataSiswa(): AplikasiDataSiswa = (
        this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AplikasiDataSiswa
        )

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(aplikasiDataSiswa().container.repositoryDataSiswa)
        }
        initializer {
            EntryViewModel(aplikasiDataSiswa().container.repositoryDataSiswa)
        }
        initializer {
            DetailViewModel(
                this.createSavedStateHandle(),
                aplikasiDataSiswa().container.repositoryDataSiswa
            )
        }
        initializer {
            EditViewModel(
                this.createSavedStateHandle(),
                aplikasiDataSiswa().container.repositoryDataSiswa
            )
        }
    }
}