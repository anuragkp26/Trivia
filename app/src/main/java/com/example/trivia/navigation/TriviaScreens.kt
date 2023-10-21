package com.example.trivia.navigation

enum class TriviaScreens {
    QuestionScreen;
    companion object {
        fun fromRoute( route : String?) : TriviaScreens =
            when(route?.substringBefore("/")){
                QuestionScreen.name -> QuestionScreen
                null -> QuestionScreen
                else -> throw java.lang.IllegalArgumentException("Route $route is not recognised")
            }
    }
}