package com.authentication.authentication.utils

import com.authentication.authentication.entity.UserEntity
import org.assertj.core.api.AbstractAssert
import org.assertj.core.api.Assertions.assertThat
import java.time.OffsetDateTime

class UserServiceAssert(actual: UserEntity, selfType: Class<*>?) :
    AbstractAssert<UserServiceAssert, UserEntity>(actual, selfType) {

    fun hasValidUserName(): UserServiceAssert {
        val nameLength = actual.username.length
        assertThat(nameLength).isBetween(3, 10)
        return this
    }

    fun hasValidPassword(): UserServiceAssert {
        val passwordLength = actual.password.length
        assertThat(passwordLength).isBetween(5, 10)
        return this
    }

    fun hasValidExpireDate(): UserServiceAssert {
        val lastLoginDate = actual.lastLoginTime
        assertThat(lastLoginDate).isBefore(OffsetDateTime.now())
        return this
    }
}