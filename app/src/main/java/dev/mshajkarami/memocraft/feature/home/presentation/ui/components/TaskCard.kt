package dev.mshajkarami.memocraft.feature.home.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.mshajkarami.memocraft.core.presentation.ui.theme.MemoCraftTheme
import dev.mshajkarami.memocraft.feature.home.presentation.ui.TaskItem

@Composable
fun TaskCard(task: TaskItem) {
    val colors = MemoCraftTheme.colors

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = colors.taskCardContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(task.iconBg),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = when (task.title) {
                        "Project Proposal" -> "✓"
                        "Backend API Review" -> "</>"
                        else -> "👥"
                    },
                    color = task.iconColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = task.title,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = colors.taskCardTitle,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = task.subtitle,
                    fontSize = 14.sp,
                    color = colors.taskCardSubtitle,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(30.dp))
                        .background(task.priorityColor.copy(alpha = 0.18f))
                        .padding(horizontal = 12.dp, vertical = 5.dp)
                ) {
                    Text(
                        text = task.priority,
                        color = when (task.priority) {
                            "Urgent" -> colors.taskPriorityUrgentContent
                            "High Priority" -> colors.taskPriorityHighContent
                            else -> colors.taskPriorityNormalContent
                        },
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.height(72.dp)
            ) {
                Text(
                    text = task.time,
                    color = if (task.time == "1:00 PM") {
                        colors.taskCardTimeHighlighted
                    } else {
                        colors.taskCardTimeDefault
                    },
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )

                when {
                    task.trailingChecked -> {
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .clip(CircleShape)
                                .background(colors.taskCompletedContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "✓",
                                color = colors.taskCompletedContent,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    task.trailingText != null -> {
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .border(
                                    width = 3.dp,
                                    color = colors.taskProgressBorder,
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = task.trailingText,
                                color = colors.taskProgressContent,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    else -> {
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .border(
                                    width = 2.dp,
                                    color = colors.taskUncheckedBorder,
                                    shape = CircleShape
                                )
                        )
                    }
                }
            }
        }
    }
}
