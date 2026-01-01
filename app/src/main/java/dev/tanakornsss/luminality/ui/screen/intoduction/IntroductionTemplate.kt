package dev.tanakornsss.luminality.ui.screen.intoduction

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.tanakornsss.luminality.ui.APP_SIDE_PADDING

@Composable
fun IntroductionTemplate(
    title: String,
    contentBody: @Composable () -> Unit,
) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = APP_SIDE_PADDING)
                .fillMaxSize()
        ) {
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineLarge
                )
                Spacer(modifier = Modifier.height(APP_SIDE_PADDING))
                contentBody()
            }
        }
    }
}