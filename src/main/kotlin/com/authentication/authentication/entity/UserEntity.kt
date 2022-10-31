package com.authentication.authentication.entity

import java.time.OffsetDateTime
import javax.persistence.*


@Entity
data class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(unique = true)
    val username: String,
    val password: String,
    val lastLoginTime: OffsetDateTime = OffsetDateTime.now()
)
