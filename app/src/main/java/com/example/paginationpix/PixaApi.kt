package com.example.paginationpix

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PixaApi {
    //https://pixabay.com/api/
    @GET("api/")
    fun getImages(@Query("key") key:String = "43174290-6cd29d2ac3a15c781219f64ef",
                  @Query("q") searchWord: String,
                  @Query("page") page:Int,
                  @Query("per_page") perPage: Int = 3
    ): Call<PixaModel>
}