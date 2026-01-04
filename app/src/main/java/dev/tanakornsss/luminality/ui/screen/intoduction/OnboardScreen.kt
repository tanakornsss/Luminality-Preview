package dev.tanakornsss.luminality.ui.screen.intoduction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices.PIXEL_9
import androidx.compose.ui.tooling.preview.Preview
import dev.tanakornsss.luminality.R
import dev.tanakornsss.luminality.ui.theme.LuminalityTheme

@Composable
fun OnboardScreen(onClose: () -> Unit) {
    IntroductionTemplate(stringResource(R.string.welcome)) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(stringResource(R.string.welcome_to_luminality_app))
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(onClick = { onClose() }) {
                    Text(stringResource(R.string.ok))
                }
            }
        }
    }
}

@Preview(showSystemUi = true, device = PIXEL_9)
@Composable
fun OnboardScreenPreview() {
    LuminalityTheme {
        OnboardScreen { }
    }
}
