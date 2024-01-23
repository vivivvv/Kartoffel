package com.app.mybase

import com.app.mybase.helper.ApisResponse
import com.app.mybase.helper.AppConstants
import com.app.mybase.model.ApiResponseData
import com.app.mybase.network.ApiStories
import javax.inject.Inject

class MainRepository @Inject constructor(var apiStories: ApiStories) {

    suspend fun getResponseData(catId: String): ApisResponse<ApiResponseData> {
        return try {
            val callApi = apiStories.getResponseData(catId)
            ApisResponse.Success(callApi)
        } catch (e: Exception) {
            ApisResponse.Error(e.message ?: AppConstants.SOMETHING_WENT_WRONG)
        }
    }

}