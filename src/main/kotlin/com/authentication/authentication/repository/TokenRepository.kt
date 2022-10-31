package com.authentication.authentication.repository

import com.authentication.authentication.entity.Token
import com.authentication.authentication.entity.TokenEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TokenRepository : JpaRepository<TokenEntity, Long> {
    fun findByToken(userToken: Token): TokenEntity?
}