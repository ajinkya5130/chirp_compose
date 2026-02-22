package com.plcoding.core.designsystem.components.avatar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.plcoding.core.designsystem.theme.ChirpTheme
import org.jetbrains.compose.ui.tooling.preview.Preview


/**
 * A composable that displays a horizontal row of overlapping avatars with an optional overflow indicator.
 *
 * This component is commonly used to show multiple chat participants in a compact space,
 * with avatars overlapping each other. If there are more avatars than [maxVisible],
 * an additional avatar is shown displaying the count of remaining participants.
 *
 * @param avatars The list of chat participants to display as avatars.
 * @param modifier The modifier to be applied to the root Row container.
 * @param size The size of each avatar. Defaults to [ChirpAvatarSize.SMALL].
 * @param maxVisible The maximum number of avatars to display before showing an overflow indicator.
 *                   Defaults to 2. Must be a positive integer.
 * @param overlapPercentage The percentage of overlap between adjacent avatars, expressed as a decimal
 *                          between 0.0 and 1.0. For example, 0.4 means 40% overlap. Defaults to 0.4f.
 *
 * @see ChirpChatAvatarPhoto
 * @see ChatParticipantUi
 * @see ChirpAvatarSize
 */
@Composable
fun ChirpStackedAvatars(
    avatars: List<ChatParticipantUi>,
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

/**
 * Preview function for [ChirpStackedAvatars] showing a stacked avatar layout with 4 participants.
 *
 * Demonstrates the component with 4 chat participants where only 2 are visible,
 * and the remaining 2 are indicated by a "+2" overflow avatar.
 */
@Composable
@Preview
fun ChirpStackedAvatarsPreview() {
    ChirpTheme {
        ChirpStackedAvatars(
            avatars = listOf(
                ChatParticipantUi(
                    id = "1",
                    username = "Philipp",
                    initials = "PH",
                ),
                ChatParticipantUi(
                    id = "2",
                    username = "John",
                    initials = "JO",
                ),
                ChatParticipantUi(
                    id = "3",
                    username = "Sabrina",
                    initials = "SA",
                ),
                ChatParticipantUi(
                    id = "4",
                    username = "Cinderella",
                    initials = "CI",
                ),
            )
        )
    }
}