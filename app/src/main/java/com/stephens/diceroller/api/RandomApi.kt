package com.stephens.diceroller.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface RandomApi {
    @GET("integers/")
    suspend fun getRandomNumber(
        @Query("num") num: String,
        @Query("min") min: String,
        @Query("max") max: String,
        @Query("col") col: String = "1",
        @Query("base") base: String = "10",
        @Query("format") format: String = "plain",
        @Query("rnd") rnd: String = "new"
    ): Response<Int>
}
