package com.authentication.authentication.utils

import com.authentication.authentication.entity.Token
import com.authentication.authentication.entity.TokenEntity
import com.authentication.authentication.entity.UserEntity
import com.authentication.authentication.model.UserModel
import java.time.OffsetDateTime

class MockDataUtil {
    private val mockUserName = "john.doe"
    private val mockPassword = "12345"

    fun createMockUserModel() = UserModel(mockUserName, mockPassword)
    fun createMockUserEntity() = UserEntity(id = 1, mockUserName, mockPassword, OffsetDateTime.now())
    fun createMockTokenEntity(token: Token) = TokenEntity(id = 1, username = mockUserName, token = token, OffsetDateTime.now())
}