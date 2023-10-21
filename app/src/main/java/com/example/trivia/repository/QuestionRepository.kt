package com.example.trivia.repository

import com.example.trivia.data.TriviaDataState
import com.example.trivia.model.QuestionItem
import com.example.trivia.network.QuestionWebService
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class QuestionRepository @Inject constructor(
    private val questionWebService: QuestionWebService
) {

    private val triviaDataState = TriviaDataState<ArrayList<QuestionItem>, Boolean, Exception>()

    suspend fun getAllQuestions(): TriviaDataState<ArrayList<QuestionItem>, Boolean, Exception> {
        try {
            triviaDataState.loading = true
            val response = questionWebService.getQuestions()
            if(response.isSuccessful){
                triviaDataState.data = response.body()
                triviaDataState.loading = false
            } else {
                triviaDataState.error = CancellationException(response.message())
                triviaDataState.loading = false
            }


        } catch (e: Exception) {
            triviaDataState.error = e
            triviaDataState.loading = false
        }

        return triviaDataState
    }

}