package com.example.trivia.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.trivia.screens.question.QuestionScreen
import com.example.trivia.screens.question.QuestionViewModel


@Composable
fun TriviaNavigation(){

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = TriviaScreens.QuestionScreen.name) {

        composable(route = TriviaScreens.QuestionScreen.name) {
            //val questionViewModel = hiltViewModel<QuestionViewModel>()
            QuestionScreen(navController)
        }
    }
}