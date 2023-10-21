package com.example.trivia.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.trivia.util.AppColors

@Preview(showBackground = true)
@Composable
fun ShowProgress(score: Float = 0f) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp)
            .padding(4.dp)
            .border(
                width = 4.dp,
                brush = Brush.linearGradient(
                    colors = listOf(
                        AppColors.mLightPurple,
                        AppColors.mLightPurple
                    )
                ),
                shape = RoundedCornerShape(34.dp)
            )
            .clip(shape = RoundedCornerShape(50))
            .background(color = Color.Transparent)
    ) {
        Button(
            onClick = { },
            contentPadding = PaddingValues(1.dp),
            modifier = Modifier
                .fillMaxWidth(score)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            AppColors.progressGradiant1,
                            AppColors.progressGradiant2
                        )
                    )
                ),
            enabled = false,
            elevation = null,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent,
                disabledBackgroundColor = Color.Transparent
            )
        ) {
            
        }
        
    }
}