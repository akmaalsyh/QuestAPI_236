package com.example.questapi_236.repositori

import android.app.Application
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.questapi_236.apiservice.ServiceApiSiswa
import com.questapi_236.repositori.JaringanRepositoryDataSiswa
import com.questapi_236.repositori.RepositoryDataSiswa
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

interface ContainerApp {
    val repositoryDataSiswa: RepositoryDataSiswa
}

class DefaultContainerApp : ContainerApp {
    private val baseUrl = "http://10.0.2.2/umyTI/" // Samakan penulisan variabel

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val klien = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    private val json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
        isLenient = true
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl) // Menggunakan baseUrl yang sudah diperbaiki
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .client(klien)
        .build()

    private val retrofitService: ServiceApiSiswa by lazy {
        retrofit.create(ServiceApiSiswa::class.java)
    }

    override val repositoryDataSiswa: RepositoryDataSiswa by lazy {
        JaringanRepositoryDataSiswa(retrofitService) // Sesuaikan nama Class Repository Anda
    }
}

class AplikasiDataSiswa : Application() {
    lateinit var container: ContainerApp
    override fun onCreate() {
        super.onCreate()
        container = DefaultContainerApp()
    }
}