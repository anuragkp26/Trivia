package com.example.trivia.network

import com.example.trivia.model.QuestionItem
import retrofit2.Response
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface QuestionWebService {

    @GET("world.json")
    suspend fun getQuestions() : Response<ArrayList<QuestionItem>>
}