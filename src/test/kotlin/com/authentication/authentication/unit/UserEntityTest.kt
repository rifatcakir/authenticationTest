package com.authentication.authentication.unit

import com.authentication.authentication.utils.UserServiceAssert
import com.authentication.authentication.entity.UserEntity
import com.authentication.authentication.repository.TokenRepository
import com.authentication.authentication.repository.UserRepository
import com.authentication.authentication.service.TokenService
import com.authentication.authentication.service.UserService
import com.authentication.authentication.utils.MockDataUtil
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test

class UserEntityTest {


    private val userRepository: UserRepository = mockk()
    private val mockDataUtil = MockDataUtil()

    @Test
    fun whenCreateUser_thenValidateEntityModel() {
        every { userRepository.findUserByUsername(any()) } returns mockDataUtil.createMockUserEntity()

        val user = userRepository.findUserByUsername("John")!!

        asserUserEntity(user).hasValidUserName().hasValidPassword().hasValidExpireDate()

    }

    private fun asserUserEntity(entity: UserEntity): UserServiceAssert {
        return UserServiceAssert(entity, UserServiceAssert::class.java)
    }
}