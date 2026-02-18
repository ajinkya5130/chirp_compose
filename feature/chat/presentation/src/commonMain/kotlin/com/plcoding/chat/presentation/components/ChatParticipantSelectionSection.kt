package com.plcoding.chat.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.plcoding.core.designsystem.components.avatar.ChatParticipantUi
import com.plcoding.core.designsystem.components.avatar.ChirpAvatarSize
import com.plcoding.core.designsystem.components.avatar.ChirpChatAvatarPhoto
import com.plcoding.core.designsystem.dimesions.LocalDim
import com.plcoding.core.designsystem.theme.ChirpTheme
import com.plcoding.core.designsystem.theme.extended
import com.plcoding.core.presentation.utils.DeviceConfiguration
import com.plcoding.core.presentation.utils.rememberDeviceConfiguration
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Composable that displays the chat participant selection section.
 *
 * This section shows either the current search result or the list of selected participants
 * in a scrollable list. The height adapts based on device configuration - using fixed
 * constraints on tablets/desktop and flexible height on mobile devices.
 *
 * @receiver ColumnScope Required to use weight modifier for flexible sizing.
 * @param modifier Modifier to be applied to the root container.
 * @param selectedParticipants List of participants that have been selected for the chat.
 * @param searchResult The current search result participant, if any. When present, only this
 *                     participant is displayed instead of the selected participants list.
 */
@Composable
fun ColumnScope.ChatParticipantSelectionSection(
    modifier: Modifier = Modifier,
    selectedParticipants: List<ChatParticipantUi> = emptyList(),
    searchResult: ChatParticipantUi? = null,
) {
    val deviceConfig = rememberDeviceConfiguration()
    val heightModifier = when (deviceConfig) {
        DeviceConfiguration.TABLET_PORTRAIT,
        DeviceConfiguration.TABLET_LANDSCAPE,
        DeviceConfiguration.DESKTOP,
            -> {
            Modifier.animateContentSize()
                .heightIn(min = LocalDim.current.dimen200dp, max = LocalDim.current.dimen300dp)
        }

        else -> Modifier.weight(1f)
    }
    Box(
        modifier = heightModifier.then(modifier),
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            searchResult?.let {
                item {
                    ChatParticipantListItem(model = it)
                }
            }
            if (selectedParticipants.isNotEmpty() && searchResult == null) {
                items(
                    items = selectedParticipants,
                    key = {
                        it.id
                    }
                ) {
                    ChatParticipantListItem(
                        model = it
                    )
                }
            }
        }
    }
}

/**
 * Composable that displays a single chat participant list item.
 *
 * Shows the participant's avatar (with initials or profile picture) and username
 * in a horizontal layout. The username is truncated with ellipsis if too long.
 *
 * @param modifier Modifier to be applied to the root container.
 * @param model The chat participant data to display.
 */
@Composable
fun ChatParticipantListItem(
    modifier: Modifier = Modifier,
    model: ChatParticipantUi,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(LocalDim.current.dimen16dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(LocalDim.current.dimen12dp)
    ) {
        ChirpChatAvatarPhoto(
            displayText = model.initials,
            size = ChirpAvatarSize.SMALL,
            imageUrl = model.imageUrl
        )
        Text(
            text = model.username,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.extended.textPrimary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }

}

/**
 * Preview composable for the Chat Participant Selection section.
 *
 * Displays a preview of the participant selection section with default empty state.
 */
@Composable
@Preview
fun ChatParticipantSelectionSectionPreview() {
    ChirpTheme {
        Column {
            ChatParticipantSelectionSection()
        }
    }
}