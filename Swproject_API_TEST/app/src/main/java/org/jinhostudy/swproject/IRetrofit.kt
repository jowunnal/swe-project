package org.jinhostudy.swproject

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface IRetrofit {
    @GET("getFoodNtrItdntList1") // 엔드포인트부분
    suspend fun getData(@Query("ServiceKey") key:String , @Query("desc_kor") name:String,
        @Query("type") type:String) : Response<FoodDTO> // 요청할때 parameter로 키값,식품이름,응답받을 타입지정
}