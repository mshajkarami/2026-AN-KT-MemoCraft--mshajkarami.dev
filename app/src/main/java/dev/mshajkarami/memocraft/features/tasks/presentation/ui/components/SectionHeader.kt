package dev.mshajkarami.memocraft.features.tasks.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import dev.mshajkarami.memocraft.core.presentation.ui.theme.MemoCraftTheme

@Composable
fun SectionHeader(
    title: String,
    action: String,
    modifier: Modifier = Modifier,
    onActionClick: () -> Unit = {}
) {
    val colors = MemoCraftTheme.colors

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            color = colors.sectionHeaderTitleColor,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp
        )

        if (action.isNotEmpty()) {
            Text(
                text = action,
                color = colors.sectionHeaderActionColor,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                modifier = Modifier.clickable(onClick = onActionClick)
            )
        }
    }
}
