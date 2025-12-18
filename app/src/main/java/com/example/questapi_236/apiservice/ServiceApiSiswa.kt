package com.example.questapi_236.apiservice

import com.example.questapi_236.modeldata.DataSiswa
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ServiceApiSiswa {
    @GET("bacaTeman.php")
    suspend fun getSiswa(): List<DataSiswa>

    @POST("insertTM.php")
    suspend fun postSiswa(
        @Body dataSiswa: DataSiswa
    ): Response<Void>
}