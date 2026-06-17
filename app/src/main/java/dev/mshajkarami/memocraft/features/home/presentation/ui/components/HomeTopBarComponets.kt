package dev.mshajkarami.memocraft.features.home.presentation.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dev.mshajkarami.memocraft.core.presentation.ui.components.BaseTopBar
import dev.mshajkarami.memocraft.core.presentation.ui.theme.MemoCraftAppTheme
import dev.mshajkarami.memocraft.core.presentation.ui.theme.MemoCraftTheme

@Composable
fun HomeTopBar(
    onSearchClick: () -> Unit
) {
    BaseTopBar(
        actions = {
            IconButton(onClick = onSearchClick) {
                Icon(Icons.Outlined.Search, "Search", tint = MemoCraftTheme.colors.topBarActionIconColor)
            }
        }
    )
}

@Preview(
    name = "HomeTopBar - Light",
    showBackground = true,
    backgroundColor = 0xFFF8F8FF,
    widthDp = 430,
    heightDp = 88
)
@Composable
private fun HomeTopBarLightPreview() {
    MemoCraftAppTheme(darkTheme = false) {
        HomeTopBar(
            onSearchClick = {}
        )
    }
}

@Preview(
    name = "HomeTopBar - Dark",
    showBackground = true,
    backgroundColor = 0xFF0F1226,
    widthDp = 430,
    heightDp = 88
)
@Composable
private fun HomeTopBarDarkPreview() {
    MemoCraftAppTheme(darkTheme = true) {
        HomeTopBar(
            onSearchClick = {}
        )
    }
}
