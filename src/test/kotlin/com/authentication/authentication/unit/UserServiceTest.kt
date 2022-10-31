package com.authentication.authentication.unit

import com.authentication.authentication.model.UserModel
import com.authentication.authentication.repository.TokenRepository
import com.authentication.authentication.repository.UserRepository
import com.authentication.authentication.service.TokenService
import com.authentication.authentication.service.UserService
import com.authentication.authentication.utils.MockDataUtil
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class UserServiceTest {

    private val userRepository: UserRepository = mockk()
    private val tokenRepository: TokenRepository = mockk()
    private val tokenService: TokenService = TokenService(tokenRepository)
    private val userService: UserService = UserService(userRepository, tokenService)

    private val mockDataUtil = MockDataUtil()

    @Test
    fun whenCreateUser_thenReturnCreatedUser() {
        val mockUser = mockDataUtil.createMockUserModel()
        every { userRepository.findUserByUsername(mockUser.username) } returns null
        every { userRepository.save(any()) } returns mockDataUtil.createMockUserEntity()

        val user = userService.createUser(UserModel(mockUser.username, mockUser.password))
        assertEquals(user.username, mockUser.username)
        assertEquals(user.password, mockUser.password)
    }

    @Test
    fun whenLoginUser_thenReturnLoginToken() {
        val mockUser = mockDataUtil.createMockUserModel()
        val token = "asdfgwe1235rfsf"
        every {
            userRepository.findUserByUsernameAndPassword(
                mockUser.username,
                mockUser.password
            )
        } returns mockDataUtil.createMockUserEntity()

        every { tokenRepository.save(any()) } returns mockDataUtil.createMockTokenEntity(token)

        val userToken = userService.loginUser(mockDataUtil.createMockUserModel())

        assertEquals(userToken, token)
    }

    @Test
    fun whenUserPreviewInfoWithToken_thenReturnUserInfo() {
        val mockUser = mockDataUtil.createMockUserModel()
        val token = "asdfgwe1235rfsf"
        every { tokenRepository.findByToken(any()) } returns mockDataUtil.createMockTokenEntity(token)
        every { userRepository.findUserByUsername(mockUser.username) } returns mockDataUtil.createMockUserEntity()

        val user = userService.getUserInfoByToken(token)
        assertEquals(user.username, mockUser.username)
        assertEquals(user.password, mockUser.password)
    }


}