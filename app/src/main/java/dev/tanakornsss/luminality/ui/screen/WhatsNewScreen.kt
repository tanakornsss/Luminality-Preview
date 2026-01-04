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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices.PIXEL_9
import androidx.compose.ui.tooling.preview.Preview
import dev.tanakornsss.luminality.BuildConfig
import dev.tanakornsss.luminality.R
import dev.tanakornsss.luminality.ui.APP_SIDE_PADDING
import dev.tanakornsss.luminality.ui.component.BulletedList
import dev.tanakornsss.luminality.ui.screen.intoduction.IntroductionTemplate
import dev.tanakornsss.luminality.ui.theme.LuminalityTheme

@Composable
fun WhatsNewScreen(onClose: () -> Unit) {
    IntroductionTemplate(title = stringResource(R.string.what_s_new)) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(stringResource(R.string.version, BuildConfig.VERSION_NAME))
                Spacer(modifier = Modifier.height(APP_SIDE_PADDING))
                Text(
                    text = stringResource(R.string.changelog),
                    style = MaterialTheme.typography.titleLarge
                )
                BulletedList(stringResource(R.string.changelog_content))
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
fun WhatsNewScreenPreview() {
    LuminalityTheme {
        WhatsNewScreen { }
    }
}
