package com.stephens.diceroller.data

import com.stephens.diceroller.api.RandomApi
import javax.inject.Inject
import kotlin.random.Random

class RandomRepository @Inject constructor(
    private val api: RandomApi
) {

    /**
     * Where the call to get a random number takes place
     * @param valuesToReturn the number of separate values that will be returned
     * @param min the minimum random number
     * @param max the maximum random number
     */
    suspend fun rollDice(
        valuesToReturn: Int,
        min: Int,
        max: Int
    ): Int {
        var result: Int
        try {
            val response = api.getRandomNumber(
                num = valuesToReturn.toString(),
                min = min.toString(),
                max = max.toString()
            )
            result = if (response.isSuccessful) {
                response.body() ?: 0
            } else {
                0
            }
        } catch (e: Exception) {
            result = 0
        }
        return if (result == 0) Random.nextInt(from = min, until = max) else result
    }
}