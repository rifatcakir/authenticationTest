package com.authentication.authentication.it

import com.authentication.authentication.entity.Token
import com.authentication.authentication.entity.UserEntity
import com.authentication.authentication.model.UserModel
import com.authentication.authentication.repository.TokenRepository
import com.authentication.authentication.repository.UserRepository
import com.authentication.authentication.utils.MockDataUtil
import com.authentication.authentication.utils.UserServiceAssert
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.test.annotation.DirtiesContext

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserServiceIT {

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var tokenRepository: TokenRepository

    private val mockDataUtil = MockDataUtil()

    @Test
    @DirtiesContext
    fun testCreatingUserIsValid() {
        val mockUserModel = mockDataUtil.createMockUserModel()

        val result = restTemplate.postForEntity("/user/create", mockUserModel, UserModel::class.java)

        assertNotNull(result)
        assertEquals(HttpStatus.OK, result?.statusCode)
        assertEquals(mockUserModel.username, result.body?.username)

        val userInDB = userRepository.findUserByUsername(mockUserModel.username)
        assertNotNull(userInDB)
        asserUserEntity(userInDB!!).hasValidUserName().hasValidPassword().hasValidExpireDate()

    }

    @Test
    @DirtiesContext
    fun whenLoginUser_thenReturnLoginToken() {
        val mockUserModel = mockDataUtil.createMockUserModel()
        val user = userRepository.save(mockDataUtil.createMockUserEntity())

        val result = restTemplate.postForEntity("/user/login", mockUserModel, Token::class.java)

        assertNotNull(result)
        assertEquals(HttpStatus.OK, result?.statusCode)

        val tokenEntity = tokenRepository.findAll().first { it.username == user.username }

        assertEquals(tokenEntity.token, result.body)
    }

    @Test
    fun whenUserPreviewInfoWithToken_thenReturnUserInfo() {
        val mockUserModel = mockDataUtil.createMockUserModel()
        val token = "teuhnekmf1235gkdfdf"
        val userEntity = userRepository.save(mockDataUtil.createMockUserEntity())
        val tokenEntity = tokenRepository.save(mockDataUtil.createMockTokenEntity(token))

        val result = restTemplate.getForEntity("/user/profile/${tokenEntity.token}", UserModel::class.java)
        assertNotNull(result)
        assertEquals(HttpStatus.OK, result?.statusCode)
        assertEquals(userEntity.username, result.body?.username)
    }

    private fun asserUserEntity(entity: UserEntity): UserServiceAssert {
        return UserServiceAssert(entity, UserServiceAssert::class.java)
    }
}