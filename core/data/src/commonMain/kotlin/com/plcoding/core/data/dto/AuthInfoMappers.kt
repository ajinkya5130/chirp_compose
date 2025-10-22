package com.plcoding.core.data.dto

import com.plcoding.core.domain.auth.AuthInfo
import com.plcoding.core.domain.auth.UserInfo

/**
 * Converts a serializable [AuthInfoSerializable] to domain model [AuthInfo].
 *
 * This mapper function transforms the data transfer object received from the network
 * into the domain model used throughout the application.
 *
 * @return The domain model [AuthInfo] with all authentication data
 */
fun AuthInfoSerializable.toAuthInfo(): AuthInfo {
    return AuthInfo(
        accessToken = accessToken,
        refreshToken = refreshToken,
        user = user.toUserInfo()
    )
}

/**
 * Converts a serializable [UserInfoSerializable] to domain model [UserInfo].
 *
 * This mapper function transforms the user data transfer object received from the network
 * into the domain model used throughout the application.
 *
 * @return The domain model [UserInfo] with all user data
 */
fun UserInfoSerializable.toUserInfo(): UserInfo {
    return UserInfo(
        id = id,
        email = email,
        username = username,
        isEmailVerified = isEmailVerified,
        profilePictureUrl = profilePictureUrl,
    )
}

/**
 * Converts a domain model [AuthInfo] to serializable [AuthInfoSerializable].
 *
 * This mapper function transforms the domain model into a data transfer object
 * that can be serialized for storage or network transmission.
 *
 * @return The serializable [AuthInfoSerializable] with all authentication data
 */
fun AuthInfo.toSerializableAuth(): AuthInfoSerializable {
    return AuthInfoSerializable(
        accessToken = accessToken,
        refreshToken = refreshToken,
        user = user.toSerializableUserInfo()
    )
}

/**
 * Converts a domain model [UserInfo] to serializable [UserInfoSerializable].
 *
 * This mapper function transforms the domain model into a data transfer object
 * that can be serialized for storage or network transmission.
 *
 * @return The serializable [UserInfoSerializable] with all user data
 */
fun UserInfo.toSerializableUserInfo(): UserInfoSerializable {
    return UserInfoSerializable(
        id = id,
        email = email,
        username = username,
        isEmailVerified = isEmailVerified,
        profilePictureUrl = profilePictureUrl,
    )
}
