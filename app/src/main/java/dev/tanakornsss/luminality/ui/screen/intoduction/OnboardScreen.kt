package dev.tanakornsss.luminality.ui.screen.intoduction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import dev.tanakornsss.luminality.R
import dev.tanakornsss.luminality.setting.SettingViewModel
import dev.tanakornsss.luminality.ui.component.CustomAlertDialog

@Composable
fun OnboardScreen(
    settingViewModel: SettingViewModel,
    onReturn: () -> Unit
) {
    val settingValue = settingViewModel.setting.value
    val analyticsValue = settingValue.telemetryState

    var showDialog by remember { mutableStateOf(false) }
    if (showDialog) {
        AnalyticsConsentDialog(
            onDismiss = {
                @Suppress("AssignedValueIsNeverRead")
                showDialog = false
                settingViewModel.updateTelemetry(false)
                onReturn()
            },
            onConfirm = {
                @Suppress("AssignedValueIsNeverRead")
                showDialog = false
                settingViewModel.updateTelemetry(true)
                onReturn()
            }
        )
    }

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
                Button(onClick = {
                    @Suppress("AssignedValueIsNeverRead")
                    if (analyticsValue) onReturn() else showDialog = true
                }
                ) {
                    Text(stringResource(R.string.ok))
                }
            }
        }
    }
}

@Composable
fun AnalyticsConsentDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    CustomAlertDialog(
        dialogTitle = stringResource(R.string.analytics_dialog_header),
        dialogText = stringResource(R.string.analytics_disclaimer),
        dismissText = stringResource(R.string.not_now),
        onDismissRequest = { onDismiss() },
        confirmText = stringResource(R.string.enable_analytics),
        onConfirmation = { onConfirm() }
    )
}
