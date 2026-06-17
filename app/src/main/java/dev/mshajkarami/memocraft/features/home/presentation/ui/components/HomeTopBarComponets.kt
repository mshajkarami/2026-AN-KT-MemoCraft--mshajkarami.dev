package dev.mshajkarami.memocraft.features.home.presentation.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dev.mshajkarami.memocraft.core.presentation.ui.components.BaseTopBar
import dev.mshajkarami.memocraft.core.presentation.ui.components.TopBarSearchField
import dev.mshajkarami.memocraft.core.presentation.ui.theme.MemoCraftAppTheme
import dev.mshajkarami.memocraft.core.presentation.ui.theme.MemoCraftTheme

@Composable
fun HomeTopBar(
    searchQuery: String,
    isSearchActive: Boolean,
    onSearchQueryChange: (String) -> Unit,
    onSearchClick: () -> Unit,
    onSearchClose: () -> Unit,
    onSearchSubmit: () -> Unit = {}
) {
    AnimatedContent(
        targetState = isSearchActive,
        transitionSpec = {
            fadeIn() togetherWith fadeOut() using SizeTransform(clip = false)
        },
        label = "HomeTopBarAnimatedContent"
    ) { active ->
        if (active) {
            BaseTopBar(
                content = {
                    TopBarSearchField(
                        query = searchQuery,
                        placeholder = "Search tasks, notes...",
                        onQueryChange = onSearchQueryChange,
                        onCloseClick = {
                            if (searchQuery.isNotBlank()) {
                                onSearchQueryChange("")
                            } else {
                                onSearchClose()
                            }
                        },
                        onSearchSubmit = onSearchSubmit
                    )
                }
            )
        } else {
            BaseTopBar(
                actions = {
                    IconButton(onClick = onSearchClick) {
                        Icon(
                            imageVector = Icons.Outlined.Search,
                            contentDescription = "Search",
                            tint = MemoCraftTheme.colors.topBarActionIconColor
                        )
                    }
                }
            )
        }
    }
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
            searchQuery = "",
            isSearchActive = false,
            onSearchQueryChange = {},
            onSearchClick = {},
            onSearchClose = {}
        )
    }
}

@Preview(
    name = "HomeTopBar Search - Light",
    showBackground = true,
    backgroundColor = 0xFFF8F8FF,
    widthDp = 430,
    heightDp = 88
)
@Composable
private fun HomeTopBarSearchLightPreview() {
    MemoCraftAppTheme(darkTheme = false) {
        HomeTopBar(
            searchQuery = "Backend API",
            isSearchActive = true,
            onSearchQueryChange = {},
            onSearchClick = {},
            onSearchClose = {}
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
            searchQuery = "",
            isSearchActive = false,
            onSearchQueryChange = {},
            onSearchClick = {},
            onSearchClose = {}
        )
    }
}

@Preview(
    name = "HomeTopBar Search - Dark",
    showBackground = true,
    backgroundColor = 0xFF0F1226,
    widthDp = 430,
    heightDp = 88
)
@Composable
private fun HomeTopBarSearchDarkPreview() {
    MemoCraftAppTheme(darkTheme = true) {
        HomeTopBar(
            searchQuery = "Project Proposal",
            isSearchActive = true,
            onSearchQueryChange = {},
            onSearchClick = {},
            onSearchClose = {}
        )
    }
}
