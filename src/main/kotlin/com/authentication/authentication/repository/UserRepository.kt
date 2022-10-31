package com.authentication.authentication.repository

import com.authentication.authentication.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<UserEntity, Long> {
    fun findUserByUsername(userName: String): UserEntity?

    fun findUserByUsernameAndPassword(username: String, password: String): UserEntity?
}