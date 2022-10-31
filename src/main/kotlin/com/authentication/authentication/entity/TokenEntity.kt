package com.authentication.authentication.entity

import java.time.OffsetDateTime
import javax.persistence.*


@Entity
data class TokenEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val username: String,
    val token: Token,
    val expireTime: OffsetDateTime
)
typealias Token = String
