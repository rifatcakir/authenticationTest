package com.authentication.authentication.service

import com.authentication.authentication.entity.Token
import com.authentication.authentication.entity.TokenEntity
import com.authentication.authentication.repository.TokenRepository
import org.springframework.stereotype.Service
import java.time.OffsetDateTime

@Service
class TokenService(private val tokenRepository: TokenRepository) {
    fun createUserToken(username: String) =
        tokenRepository.save(username.toTokenEntity()).token

    fun getUserNameByToken(userToken: Token) =
        tokenRepository.findByToken(userToken)

}

private fun String.toTokenEntity() =
    TokenEntity(username = this, token = getRandomString(15), expireTime = OffsetDateTime.now().plusDays(1))

fun getRandomString(length: Int): String {
    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")
}