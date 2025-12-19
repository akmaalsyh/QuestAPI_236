package com.example.questapi_236.repositori

import com.example.questapi_236.apiservice.ServiceApiSiswa
import com.example.questapi_236.modeldata.DataSiswa

interface RepositoryDataSiswa {
    suspend fun getDataSiswa(): List<DataSiswa>
    suspend fun postDataSiswa(dataSiswa: DataSiswa) :retrofit2.Response<Void>

    // Di Interface
    suspend fun getSiswaById(id: Int): DataSiswa
    suspend fun deleteSiswa(id: Int)

    suspend fun updateSiswa(id: Int, dataSiswa: DataSiswa)
}

class JaringanRepositoryDataSiswa(
    private val serviceApiSiswa: ServiceApiSiswa
) : RepositoryDataSiswa{
    override suspend fun getDataSiswa(): List<DataSiswa> = serviceApiSiswa.getSiswa()
    override suspend fun postDataSiswa(dataSiswa: DataSiswa): retrofit2.Response<Void> = serviceApiSiswa.postSiswa(dataSiswa)
    // Di JaringanRepositoryDataSiswa
    override suspend fun getSiswaById(id: Int): DataSiswa {
        return serviceApiSiswa.getSiswaById(id)
    }

    override suspend fun deleteSiswa(id: Int) {
        try {
            val response = serviceApiSiswa.deleteSiswa(id)
            if (!response.isSuccessful) {
                throw Exception("Gagal menghapus data di server")
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun updateSiswa(id: Int, dataSiswa: DataSiswa) {
        serviceApiSiswa.updateSiswa(id, dataSiswa)
    }
}