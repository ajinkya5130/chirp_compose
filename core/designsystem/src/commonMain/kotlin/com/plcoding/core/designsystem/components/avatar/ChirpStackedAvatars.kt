package com.plcoding.core.designsystem.components.avatar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.plcoding.core.designsystem.theme.ChirpTheme
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun ChirpStackedAvatars(
    avatars: List<ChirpAvatarUi>,
    modifier: Modifier = Modifier,
    size: ChirpAvatarSize = ChirpAvatarSize.SMALL,
    maxVisible: Int = 2,
    overlapPercentage: Float = 0.4f,
) {
    val overlapOffset = -(size.dp * overlapPercentage)

    val visibleAvatars = avatars.take(maxVisible)
    val remainingCount = (avatars.size - maxVisible).coerceAtLeast(0)

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(overlapOffset),
        verticalAlignment = Alignment.CenterVertically
    ) {
        visibleAvatars.forEach { chirpAvatarUi ->
            ChirpChatAvatarPhoto(
                displayText = chirpAvatarUi.initials,
                size = size,
                imageUrl = chirpAvatarUi.imageUrl
            )
        }

        if (remainingCount > 0) {
            ChirpChatAvatarPhoto(
                displayText = "$remainingCount+",
                size = size,
                textColor = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
@Preview
fun ChirpStackedAvatarsPreview() {
    ChirpTheme {
        ChirpStackedAvatars(
            avatars = listOf(
                ChirpAvatarUi(
                    id = "1",
                    username = "Philipp",
                    initials = "PH",
                ),
                ChirpAvatarUi(
                    id = "2",
                    username = "John",
                    initials = "JO",
                ),
                ChirpAvatarUi(
                    id = "3",
                    username = "Sabrina",
                    initials = "SA",
                ),
                ChirpAvatarUi(
                    id = "4",
                    username = "Cinderella",
                    initials = "CI",
                ),
            )
        )
    }
}