package com.example.trivia.screens.question

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trivia.data.TriviaDataState
import com.example.trivia.model.QuestionItem
import com.example.trivia.repository.QuestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject constructor(
    private val repository: QuestionRepository
): ViewModel() {

    val data: MutableState<TriviaDataState<ArrayList<QuestionItem>, Boolean, Exception>>
        = mutableStateOf(TriviaDataState(null, true, Exception("")))

    init {
        getAllQuestion()
    }


    private fun getAllQuestion(){
        viewModelScope.launch {
            data.value.loading = true
            data.value = repository.getAllQuestions()
            if(data.value.data.toString().isNotEmpty()){
                data.value.loading = false
            }

        }
    }
}