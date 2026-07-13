package dev.mshajkarami.memocraft.features.task.presentation.list.component

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
import dev.mshajkarami.memocraft.core.designsystem.component.BaseTopBar
import dev.mshajkarami.memocraft.core.designsystem.component.TopBarSearchField
import dev.mshajkarami.memocraft.core.designsystem.theme.MemoCraftTheme

@Composable
fun TasksTopBar(
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
        label = "TasksTopBarAnimatedContent"
    ) { active ->
        if (active) {
            BaseTopBar(
                content = {
                    TopBarSearchField(
                        query = searchQuery,
                        placeholder = "Search tasks...",
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
                title = "Tasks",
                subtitle = "Manage your daily work",
                actions = {
                    IconButton(onClick = onSearchClick) {
                        Icon(
                            imageVector = Icons.Outlined.Search,
                            contentDescription = "Search tasks",
                            tint = MemoCraftTheme.colors.topBarActionIconColor
                        )
                    }
                }
            )
        }
    }
}
