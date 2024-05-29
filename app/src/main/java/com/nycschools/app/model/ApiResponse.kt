package com.nycschools.app.model

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("dbn") val dbn: String? = null,
    @SerializedName("school_name") val schoolName: String? = null,
    @SerializedName("overview_paragraph") val overviewParagraph: String? = null,
    @SerializedName("website") val websiteUrl: String? = null
)

data class ApiSATResponse(
    @SerializedName("dbn") var dbn: String? = null,
    @SerializedName("school_name") val schoolName: String? = null,
    @SerializedName("num_of_sat_test_takers") val num0fSatTestTakers: String? = null,
    @SerializedName("sat_critical_reading_avg_score") val satCriticalReadingAvgScore: String? = null,
    @SerializedName("sat_math_avg_score") val satMathAvgScore: String? = null,
    @SerializedName("sat_writing_avg_score") val satWritingAvgScore: String? = null
)

data class Response(
    @SerializedName("status") val status : Int,
    @SerializedName("message") val message : String,
    @SerializedName("data") val data : List<LogDataResponse>
){
    data class LogDataResponse(
        val name : String,
        val school : String
    )
}

