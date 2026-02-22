package com.plcoding.chat.presentation.chat_details_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.group_chat
import chirp.feature.chat.presentation.generated.resources.you
import com.plcoding.chat.models.ChatMessage
import com.plcoding.chat.presentation.model.ChatUi
import com.plcoding.core.designsystem.components.avatar.ChatParticipantUi
import com.plcoding.core.designsystem.components.avatar.ChirpStackedAvatars
import com.plcoding.core.designsystem.dimesions.LocalDim
import com.plcoding.core.designsystem.theme.ChirpTheme
import com.plcoding.core.designsystem.theme.extended
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.time.Clock

/**
 * Composable that displays a single chat item in a list.
 *
 * This component shows chat information including participant avatars, names,
 * and the last message. It supports both individual and group chats, with
 * different visual representations for each. The item can be in a selected state,
 * indicated by a different background color and a primary-colored indicator bar.
 *
 * Features:
 * - Stacked avatars for participants
 * - Group chat indicator with participant names
 * - Last message preview with sender name
 * - Selection state with visual feedback
 * - Adaptive background color based on selection
 *
 * @param modifier Modifier to be applied to the root container.
 * @param chat The chat data to display.
 * @param isSelected Whether this chat item is currently selected.
 * @param onClick Callback invoked when the chat item is clicked.
 */
@Composable
fun ChatListItemUi(
    modifier: Modifier = Modifier,
    chat: ChatUi,
    isSelected: Boolean = false,
    onClick: () -> Unit = {},
) {
    val dimen = LocalDim.current

    val isGroupChat = remember(chat.otherParticipants) {
        chat.otherParticipants.size > 1
    }

    val userName =
        if (isGroupChat) {
            stringResource(Res.string.group_chat)
        } else {
            chat.otherParticipants.first().username
        }

    val backgroundColor = if (isSelected) {
        MaterialTheme.colorScheme.surface
    } else {
        MaterialTheme.colorScheme.extended.surfaceLower
    }
    Row(
        modifier = Modifier
            .height(IntrinsicSize.Min)
            .background(color = backgroundColor)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(dimen.dimen16dp),
            verticalArrangement = Arrangement.spacedBy(dimen.dimen16dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(dimen.dimen12dp)
            ) {
                ChirpStackedAvatars(
                    avatars = chat.otherParticipants
                )
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(dimen.dimen6dp)
                ) {
                    Text(
                        text = userName,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.extended.textPrimary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth()
                    )

                    if (isGroupChat) {
                        val youString = stringResource(Res.string.you)
                        val formattedName = remember(chat.otherParticipants) {
                            "$youString, " + chat.otherParticipants.joinToString {
                                it.username
                            }
                        }
                        Text(
                            text = formattedName,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.extended.textPlaceholder,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.fillMaxWidth()
                        )

                    }

                }

            }
            chat.lastMessage?.let {
                val lastMsg = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.extended.textSecondary
                        )
                    ) {
                        append(chat.lastMessageSenderUserName)
                        append(": ")
                    }
                    append(chat.lastMessage.content)
                }
                Text(
                    text = lastMsg,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.extended.textSecondary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()
                )
            }

        }

        val visibleAlphaValue = if (isSelected) 1f else 0f
        Box(
            modifier = Modifier
                .alpha(visibleAlphaValue)
                .width(dimen.dimen4dp)
                .background(MaterialTheme.colorScheme.primary)
                .fillMaxHeight()
        )
    }

}

/**
 * Preview composable for [ChatListItemUi] in light theme.
 *
 * Displays a selected chat item with multiple participants and a long message preview.
 */
@Composable
@Preview
fun ChatListItemUiPreview() {
    ChirpTheme {
        val model = ChatUi(
            localParticipants = ChatParticipantUi(
                id = "1",
                username = "John",
                initials = "JO",
            ),
            otherParticipants = listOf(
                ChatParticipantUi(
                    id = "2",
                    username = "Jane",
                    initials = "JA",
                ),
                ChatParticipantUi(
                    id = "3",
                    username = "Done",
                    initials = "DO",
                ),
                ChatParticipantUi(
                    id = "4",
                    username = "Doe",
                    initials = "DO",
                )
            ),
            lastMessage = ChatMessage(
                id = "1",
                chatId = "1",
                content = "Hello World, local long message need to show to user Hello World, local long message need to show to user ",
                createdAt = Clock.System.now(),
                senderId = "1"
            ),
            id = "1",
            lastMessageSenderUserName = "John",
        )
        ChatListItemUi(
            chat = model,
            isSelected = true,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

/**
 * Preview composable for [ChatListItemUi] in dark theme.
 *
 * Displays a selected chat item with multiple participants and a long message preview
 * in dark mode to verify color contrast and theming.
 */
@Composable
@Preview()
fun ChatListItemUiPreviewDark() {
    ChirpTheme(darkTheme = true) {
        val model = ChatUi(
            localParticipants = ChatParticipantUi(
                id = "1",
                username = "John",
                initials = "JO",
            ),
            otherParticipants = listOf(
                ChatParticipantUi(
                    id = "2",
                    username = "Jane",
                    initials = "JA",
                ),
                ChatParticipantUi(
                    id = "3",
                    username = "Done",
                    initials = "DO",
                ),
                ChatParticipantUi(
                    id = "4",
                    username = "Doe",
                    initials = "DO",
                )
            ),
            lastMessage = ChatMessage(
                id = "1",
                chatId = "1",
                content = "Hello World, local long message need to show to user Hello World, local long message need to show to user ",
                createdAt = Clock.System.now(),
                senderId = "1"
            ),
            id = "1",
            lastMessageSenderUserName = "John",
        )
        ChatListItemUi(
            chat = model,
            isSelected = true,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
