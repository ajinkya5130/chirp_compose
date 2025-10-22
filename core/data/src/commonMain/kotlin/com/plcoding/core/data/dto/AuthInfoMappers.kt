package com.plcoding.core.data.dto

import com.plcoding.core.domain.auth.AuthInfo
import com.plcoding.core.domain.auth.UserInfo


fun AuthInfoSerializable.toAuthInfo(): AuthInfo {
    return AuthInfo(
        accessToken = accessToken,
        refreshToken = refreshToken,
        user = user.toUserInfo()
    )
}

fun UserInfoSerializable.toUserInfo(): UserInfo {
    return UserInfo(
        id = id,
        email = email,
        username = username,
        isEmailVerified = isEmailVerified,
        profilePictureUrl = profilePictureUrl,
    )
}

fun AuthInfo.toSerializableAuth(): AuthInfoSerializable {
    return AuthInfoSerializable(
        accessToken = accessToken,
        refreshToken = refreshToken,
        user = user.toSerializableUserInfo()
    )
}

fun UserInfo.toSerializableUserInfo(): UserInfoSerializable {
    return UserInfoSerializable(
        id = id,
        email = email,
        username = username,
        isEmailVerified = isEmailVerified,
        profilePictureUrl = profilePictureUrl,
    )
}
