package dev.tanakornsss.luminality.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices.PIXEL_9
import androidx.compose.ui.tooling.preview.Preview
import dev.tanakornsss.luminality.BuildConfig
import dev.tanakornsss.luminality.ui.APP_SIDE_PADDING
import dev.tanakornsss.luminality.ui.screen.intoduction.IntroductionTemplate
import dev.tanakornsss.luminality.ui.theme.LuminalityTheme

@Composable
fun WhatsNewScreen(onClose: () -> Unit) {
    IntroductionTemplate(title = "What's new") {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text("Version: ${BuildConfig.VERSION_NAME}")
                Spacer(modifier = Modifier.height(APP_SIDE_PADDING))
                Text(
                    text = "Changelog",
                    style = MaterialTheme.typography.titleLarge
                )
                Text("Add newly designed setting page")
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(onClick = { onClose() }) {
                    Text("OK")
                }
            }
        }
    }
}

@Preview(showSystemUi = true, device = PIXEL_9)
@Composable
fun WhatsNewScreenPreview() {
    LuminalityTheme {
        WhatsNewScreen { }
    }
}
