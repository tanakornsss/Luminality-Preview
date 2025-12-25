package dev.tanakornsss.luminality.ui.component

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

private const val LIST_HEIGHT = 50

@Composable
fun CustomListItem(
    modifier: Modifier = Modifier,
    @StringRes label: Int,
    enabled: Boolean = true,
    onClick: () -> Unit = { },
    trailingContent: @Composable () -> Unit = { }
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(LIST_HEIGHT.dp)
            .clickable { if (enabled) onClick() }
        ,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(label),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .alpha(if (enabled) 1f else 0.6f)
        )
        trailingContent()
    }
}
