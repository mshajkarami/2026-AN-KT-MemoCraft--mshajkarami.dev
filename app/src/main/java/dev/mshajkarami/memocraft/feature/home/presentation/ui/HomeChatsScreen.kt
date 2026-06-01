package dev.mshajkarami.memocraft.feature.home.presentation.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.NotificationsNone
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.TaskAlt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


data class TaskItem(
    val title: String,
    val subtitle: String,
    val time: String,
    val priority: String,
    val priorityColor: Color,
    val iconBg: Color,
    val iconColor: Color,
    val trailingText: String? = null,
    val trailingChecked: Boolean = false
)

@Composable
fun MemoCraftScreen() {
    val tasks = listOf(
        TaskItem(
            title = "Project Proposal",
            subtitle = "Design the proposal deck",
            time = "10:30 AM",
            priority = "High Priority",
            priorityColor = Color(0xFF6EDC8A),
            iconBg = Color(0xFFE9FBF0),
            iconColor = Color(0xFF2BC56B),
            trailingChecked = true
        ),
        TaskItem(
            title = "Backend API Review",
            subtitle = "Review endpoints & docs",
            time = "1:00 PM",
            priority = "Urgent",
            priorityColor = Color(0xFFFFC7C7),
            iconBg = Color(0xFFFFEEF0),
            iconColor = Color(0xFFFF5A6E),
            trailingText = "50%"
        ),
        TaskItem(
            title = "Team Standup",
            subtitle = "Daily sync with team",
            time = "3:00 PM",
            priority = "Medium Priority",
            priorityColor = Color(0xFFD9F0FF),
            iconBg = Color(0xFFEDF6FF),
            iconColor = Color(0xFF4A90E2)
        )
    )

    Scaffold(
        containerColor = Color(0xFFF8F8FC),
        bottomBar = {
            BottomNavBar()
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            item { Spacer(modifier = Modifier.height(8.dp)) }
            item { TopBar() }
            item { GreetingSection() }
            item { ProgressCard() }
            item { SectionHeader("Upcoming Tasks", "See all") }
            items(tasks.size) {
                TaskCard(task = tasks[it])
            }
            item { SectionHeader("AI Insights", "") }
            item { AiInsightCard() }
            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Composable
fun TopBar() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {}) {
            Icon(
                imageVector = Icons.Outlined.Menu,
                contentDescription = "Menu",
                tint = Color(0xFF1A1A1A)
            )
        }

        Text(
            text = "MemoCraft",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = Color.Black
        )

        Box {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Outlined.NotificationsNone,
                    contentDescription = "Notifications",
                    tint = Color(0xFF1A1A1A)
                )
            }
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF8B5CF6))
                    .align(Alignment.TopEnd)
            )
        }
    }
}

@Composable
fun GreetingSection() {
    Column {
        Text(
            text = "Good morning,",
            fontSize = 18.sp,
            color = Color(0xFF222222)
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Arjun!",
                fontSize = 40.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF111111)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "\uD83D\uDC4B",
                fontSize = 28.sp
            )
        }
        Text(
            text = "Let's make today productive.",
            fontSize = 16.sp,
            color = Color(0xFF777777)
        )
    }
}

@Composable
fun ProgressCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF7B5CFF),
                        Color(0xFF4E54F7)
                    )
                )
            )
            .padding(18.dp)
    ) {
        Column {
            Text(
                text = "Today's Progress",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ProgressRing(
                    percentage = 72,
                    modifier = Modifier.size(140.dp)
                )

                Spacer(modifier = Modifier.width(20.dp))

                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ProgressLegendItem("Completed", "8", Color(0xFF34D399))
                    ProgressLegendItem("In Progress", "4", Color(0xFF60A5FA))
                    ProgressLegendItem("Pending", "5", Color(0xFFFB7185))

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Total Tasks",
                        color = Color(0xFFDAD7FF),
                        fontSize = 14.sp
                    )
                    Text(
                        text = "17",
                        color = Color.White,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun ProgressRing(
    percentage: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 16.dp.toPx()
            val sweep = 360f * (percentage / 100f)

            drawArc(
                color = Color.White.copy(alpha = 0.25f),
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )

            drawArc(
                brush = Brush.sweepGradient(
                    listOf(
                        Color.White,
                        Color(0xFFEDE9FE),
                        Color.White
                    )
                ),
                startAngle = -90f,
                sweepAngle = sweep,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "$percentage%",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = "Completed",
                color = Color(0xFFE9E7FF),
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun ProgressLegendItem(title: String, value: String, dotColor: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(dotColor)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = title,
            color = Color.White.copy(alpha = 0.95f),
            fontSize = 14.sp,
            modifier = Modifier.width(90.dp)
        )
        Text(
            text = value,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }
}

@Composable
fun SectionHeader(title: String, action: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            color = Color(0xFF151515),
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp
        )

        if (action.isNotEmpty()) {
            Text(
                text = action,
                color = Color(0xFF6D5DF6),
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp
            )
        }
    }
}

@Composable
fun TaskCard(task: TaskItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
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
                    color = Color(0xFF191919),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = task.subtitle,
                    fontSize = 14.sp,
                    color = Color(0xFF6E6E6E)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(30.dp))
                        .background(task.priorityColor.copy(alpha = 0.35f))
                        .padding(horizontal = 12.dp, vertical = 5.dp)
                ) {
                    Text(
                        text = task.priority,
                        color = when (task.priority) {
                            "Urgent" -> Color(0xFFE05252)
                            "High Priority" -> Color(0xFF2FAF61)
                            else -> Color(0xFF4C8FD9)
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
                    color = if (task.time == "1:00 PM") Color(0xFFE25D5D) else Color(0xFF353535),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )

                when {
                    task.trailingChecked -> {
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF22C55E)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "✓",
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    task.trailingText != null -> {
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .border(3.dp, Color(0xFFE8E6FF), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = task.trailingText,
                                color = Color(0xFF5B4CF0),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    else -> {
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .border(2.dp, Color(0xFFE8E8E8), CircleShape)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AiInsightCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF2ECFF))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "✨ You're on track! 🎯",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF26204A)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Focus on 2 high-priority tasks\nto finish your day strong.",
                    fontSize = 14.sp,
                    color = Color(0xFF5D5D74),
                    lineHeight = 20.sp
                )
            }

            Text(
                text = "\uD83E\uDD16",
                fontSize = 54.sp
            )
        }
    }
}

@Composable
fun BottomNavBar() {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 10.dp
    ) {
        NavigationBarItem(
            selected = true,
            onClick = {},
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Home,
                    contentDescription = "Home"
                )
            },
            label = { Text("Home") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF6D5DF6),
                selectedTextColor = Color(0xFF6D5DF6),
                indicatorColor = Color.Transparent,
                unselectedIconColor = Color(0xFF7B7B89),
                unselectedTextColor = Color(0xFF7B7B89)
            )
        )

        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = {
                Icon(
                    imageVector = Icons.Outlined.TaskAlt,
                    contentDescription = "Tasks"
                )
            },
            label = { Text("Tasks") }
        )

        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = {
                Icon(
                    imageVector = Icons.Outlined.CalendarToday,
                    contentDescription = "Planner"
                )
            },
            label = { Text("Planner") }
        )

        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = {
                Icon(
                    imageVector = Icons.Outlined.PersonOutline,
                    contentDescription = "Profile"
                )
            },
            label = { Text("Profile") }
        )
    }
}