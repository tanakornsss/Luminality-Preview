package dev.tanakornsss.luminality.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

private const val LIST_HEIGHT = 50

@Composable
fun CustomListItem(
    modifier: Modifier = Modifier,
    label: String,
    trailingContent: @Composable () -> Unit = { }
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(LIST_HEIGHT.dp)
        ,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label)
        trailingContent()
    }
}

@Composable
fun OverlayingListItem(
    modifier: Modifier = Modifier,
    label: String,
    trailingContent: @Composable () -> Unit = { }
) {

}
