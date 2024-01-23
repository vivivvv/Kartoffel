package com.app.mybase.network

import com.app.mybase.model.ApiResponseData
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiStories {

    @GET("get_Particular_Category_Products")
    suspend fun getResponseData(@Query("cat_id") catId: String): ApiResponseData

}