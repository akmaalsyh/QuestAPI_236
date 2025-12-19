package com.example.questapi_236.apiservice

import com.example.questapi_236.modeldata.DataSiswa
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ServiceApiSiswa {
    @GET("bacaTeman.php")
    suspend fun getSiswa(): List<DataSiswa>

    @POST("insertTM.php")
    suspend fun postSiswa(
        @Body dataSiswa: DataSiswa
    ): Response<Void>

    @GET("getSiswaById.php")
    suspend fun getSiswaById(@Query("id") id: Int): DataSiswa

    @DELETE("deleteTM.php") // Sesuaikan nama file PHP kamu
    suspend fun deleteSiswa(@Query("id") id: Int): retrofit2.Response<Void>

    @POST("updateTM.php")
    suspend fun updateSiswa(@Query("id") id: Int, @Body dataSiswa: DataSiswa): retrofit2.Response<Void>
}