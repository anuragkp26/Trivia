package com.example.trivia.screens.question

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.trivia.components.CustomButton
import com.example.trivia.components.DrawLineComponent
import com.example.trivia.components.ShowProgress
import com.example.trivia.model.QuestionItem
import com.example.trivia.util.AppColors

@Composable
fun QuestionScreen(navController: NavHostController) {
    QuestionContent()
}

@Composable
fun QuestionContent(questionViewModel: QuestionViewModel = hiltViewModel()) {

    val questions = questionViewModel.data.value.data?.toMutableList()
    Log.e("QUEST::", "questions size: ${questions?.size}")


    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        if (questionViewModel.data.value.loading == true) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            if (!questions.isNullOrEmpty()) {
                QuestionDisplay(
                    questions.subList(fromIndex = 0, toIndex = 5),
                    questionViewModel,
                )
            }
        }
    }
}

//@Preview(showBackground = true)
@Composable
fun QuestionDisplay(
    questions: MutableList<QuestionItem>,
    viewModel: QuestionViewModel,
) {

    val context = LocalContext.current
    var counterState by remember {
        mutableStateOf(0)
    }
    var scoreState by remember {
        mutableStateOf(0)
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(modifier = Modifier.padding(all = 18.dp)) {
            ShowProgress(score = (scoreState.toFloat()/questions.size))
            QuestionCount(counterState + 1, questions.size)
            DrawLineComponent(
                pathEffect = PathEffect.dashPathEffect(
                    intervals = floatArrayOf(
                        10f,
                        10f
                    ), phase = 0f
                )
            )
            QuestionAndOptions(questions[counterState], counterState == questions.size - 1,) {answerStatus ->
                if (counterState + 1 < questions.size)
                    counterState += 1
                    if(answerStatus) scoreState += 1
                else
                    Toast.makeText(context, "Finished", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@Composable
fun QuestionCount(counter: Int, totalCount: Int) {
    Text(text = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = AppColors.mLightGray
            )
        ) {
            append("Question $counter/")
        }
        withStyle(
            style = SpanStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Light,
                color = AppColors.mLightGray
            )
        ) {
            append(totalCount.toString())
        }
    }, modifier = Modifier.padding(all = 18.dp))
}

@Composable
fun QuestionAndOptions(
    questionItem: QuestionItem,
    isLastQuestion: Boolean,
    onNextClicked: (Boolean) -> Unit
) {

    val context = LocalContext.current
    /* val optionsState = remember(questionItem) {
         questionItem.choices.toMutableList()
     }*/
    val answerState = remember(questionItem) {
        mutableStateOf<Int?>(null)
    }

    val correctAnswerState = remember(questionItem) {
        mutableStateOf<Boolean?>(null)
    }

    val updateAnswer: (Int) -> Unit = remember(questionItem) {
        {
            answerState.value = it
            correctAnswerState.value =
                questionItem.answer == questionItem.choices[it]//optionsState[it]
        }
    }

    Text(
        text = questionItem!!.question,
        modifier = Modifier
            .padding(vertical = 24.dp)
            .fillMaxHeight(.3f),
        fontSize = 18.sp,
        lineHeight = 20.sp,
        style = MaterialTheme.typography.subtitle1
    )

    /*optionsState*/questionItem.choices.forEachIndexed { index, option ->
        Row(
            modifier = Modifier
                .padding(all = 4.dp)
                .fillMaxWidth()
                .height(45.dp)
                .border(
                    width = 4.dp,
                    brush = Brush.linearGradient(
                        listOf(
                            AppColors.mLightPurple,
                            AppColors.mOffDarkPurple
                        )
                    ),
                    shape = RoundedCornerShape(16.dp)
                )
                .clip(RoundedCornerShape(percent = 50))
                .background(Color.Transparent)
                .clickable {
                    //if (answerState.value == null)
                        updateAnswer(index)
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = answerState.value == index,
                onClick = {
                    if (answerState.value == null)
                        updateAnswer(index)
                },
                modifier = Modifier.padding(start = 16.dp),
                colors = RadioButtonDefaults.colors(
                    selectedColor = if (correctAnswerState.value == true)
                        Color.Green.copy(alpha = 0.5f)
                    else Color.Red.copy(alpha = 0.5f)
                )
            )
            Text(
                text = option,
                color = if (correctAnswerState.value == true && answerState.value == index)
                    Color.Green
                else if (correctAnswerState.value != true && answerState.value == index) Color.Red
                else Color.White
            )
        }
    }
    if (!isLastQuestion) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp), contentAlignment = Alignment.Center
        ) {
            CustomButton(text = "Next", onClick = {
                if (answerState.value != null)
                    onNextClicked(correctAnswerState.value!!)
                else Toast.makeText(context, "Choose one option", Toast.LENGTH_SHORT).show()
            })
        }
    } else {
        Text(
            text = "Finished :)",
            modifier = Modifier
                .padding(vertical = 34.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.Green
        )
    }
}


@Preview(showBackground = true)
@Composable
fun QuestionPreview() {
    QuestionContent()
}