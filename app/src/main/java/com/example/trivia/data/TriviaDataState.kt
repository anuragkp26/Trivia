package com.example.trivia.data

data class TriviaDataState<T, Boolean, E: Exception>(
    var data : T? = null,
    var loading: Boolean? = null,
    var error: E? = null
)
