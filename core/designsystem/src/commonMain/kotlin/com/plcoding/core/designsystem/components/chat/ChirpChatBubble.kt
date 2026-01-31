package com.plcoding.core.designsystem.components.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.plcoding.core.designsystem.dimesions.LocalDim
import com.plcoding.core.designsystem.theme.ChirpTheme
import com.plcoding.core.designsystem.theme.extended
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChirpChatBubble(
    modifier: Modifier = Modifier,
    messageContent: String,
    sender: String,
    formattedDate: String,
    trianglePosition: TrianglePosition,
    color: Color = MaterialTheme.colorScheme.extended.surfaceHigher,
    messageStatus: @Composable (() -> Unit)? = null,
    triangleSize: Dp = 10.dp,
    cornerRadius: Dp = 8.dp,
    onLongPress: (() -> Unit)? = null,
) {
    val dimen = LocalDim.current
    val padding = dimen.dimen12dp
    Column(
        modifier = modifier.then(
            if (onLongPress != null) {
                Modifier
                    .combinedClickable(
                        onClick = {},
                        onLongClick = onLongPress,
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(
                            color = MaterialTheme.colorScheme.extended.surfaceOutline
                        )
                    )
            } else {
                Modifier
            }
        )
            .background(
                color = color,
                shape =
                    ChatBubbleShape(
                        triangleSize = triangleSize,
                        cornerRadius = cornerRadius,
                        trianglePos = trianglePosition
                    )
            )
            .padding(
                start = if (trianglePosition == TrianglePosition.LEFT) {
                    padding + triangleSize
                } else {
                    padding
                },
                end = if (trianglePosition == TrianglePosition.RIGHT) {
                    padding + triangleSize
                } else {
                    padding
                },
                top = padding,
                bottom = padding
            ),
        verticalArrangement = Arrangement.spacedBy(padding)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth().padding(
                    start = if (trianglePosition == TrianglePosition.RIGHT) {
                        padding
                    } else {
                        0.dp
                    }
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = sender,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.extended.textSecondary,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(2.dp))
            Text(
                text = formattedDate,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.extended.textSecondary,
            )
        }
        Text(
            text = messageContent,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.extended.textPrimary,
            modifier = Modifier
                .fillMaxWidth().padding(
                    start = if (trianglePosition == TrianglePosition.RIGHT) {
                        padding
                    } else {
                        0.dp
                    }
                )
        )
        messageStatus?.invoke()
    }

}

@Composable
@Preview
fun ChirpChatBubbleLeftPreview() {
    ChirpTheme {
        ChirpChatBubble(
            messageContent = "Hello World Long message need to be shown to user to test the chat bubble, " +
                    "please provide a long message to test the chat bubble. ",
            sender = "John",
            formattedDate = "12:00",
            trianglePosition = TrianglePosition.LEFT,
            color = MaterialTheme.colorScheme.extended.accentGreen
        )
    }
}

@Composable
@Preview
fun ChirpChatBubbleRightPreview() {
    ChirpTheme {
        ChirpChatBubble(
            messageContent = "Hello World Long message need to be shown to user to test the chat bubble, " +
                    "please provide a long message to test the chat bubble. ",
            sender = "John",
            formattedDate = "12:00",
            trianglePosition = TrianglePosition.RIGHT
        )
    }
}

@Composable
@Preview
fun ChirpChatBubbleLeftDarkPreview() {
    ChirpTheme(darkTheme = true) {
        ChirpChatBubble(
            messageContent = "Hello World Long message need to be shown to user to test the chat bubble, " +
                    "please provide a long message to test the chat bubble. ",
            sender = "John",
            formattedDate = "12:00",
            trianglePosition = TrianglePosition.LEFT,
        )
    }
}

@Composable
@Preview
fun ChirpChatBubbleRightDarkPreview() {
    ChirpTheme(darkTheme = true) {
        ChirpChatBubble(
            messageContent = "Hello World Long message need to be shown to user to test the chat bubble, " +
                    "please provide a long message to test the chat bubble. ",
            sender = "John",
            formattedDate = "12:00",
            trianglePosition = TrianglePosition.RIGHT
        )
    }
}