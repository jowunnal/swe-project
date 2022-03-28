package org.jinhostudy.swproject

import com.google.gson.annotations.SerializedName

data class FoodDTO( //식품영양성분 api 요청해서 나온 메타데이터를 beautifyJson 해서 코틀린 json to object 플러그인으로 DTO 생성 후 모두모아줌
    @SerializedName("body")
    val body: Body?,
    @SerializedName("header")
    val header: Header?
)

data class Header(
    @SerializedName("resultCode")
    val resultCode: String?,
    @SerializedName("resultMsg")
    val resultMsg: String?
)

data class Body(
    @SerializedName("items")
    val items: List<Item>?,
    @SerializedName("numOfRows")
    val numOfRows: Int?,
    @SerializedName("pageNo")
    val pageNo: Int?,
    @SerializedName("totalCount")
    val totalCount: Int?
)

data class Item(
    @SerializedName("ANIMAL_PLANT")
    val aNIMALPLANT: String?,
    @SerializedName("BGN_YEAR")
    val bGNYEAR: String?,
    @SerializedName("DESC_KOR")
    val dESCKOR: String?,
    @SerializedName("NUTR_CONT1")
    val nUTRCONT1: String?,
    @SerializedName("NUTR_CONT2")
    val nUTRCONT2: String?,
    @SerializedName("NUTR_CONT3")
    val nUTRCONT3: String?,
    @SerializedName("NUTR_CONT4")
    val nUTRCONT4: String?,
    @SerializedName("NUTR_CONT5")
    val nUTRCONT5: String?,
    @SerializedName("NUTR_CONT6")
    val nUTRCONT6: String?,
    @SerializedName("NUTR_CONT7")
    val nUTRCONT7: String?,
    @SerializedName("NUTR_CONT8")
    val nUTRCONT8: String?,
    @SerializedName("NUTR_CONT9")
    val nUTRCONT9: String?,
    @SerializedName("SERVING_WT")
    val sERVINGWT: String?
)