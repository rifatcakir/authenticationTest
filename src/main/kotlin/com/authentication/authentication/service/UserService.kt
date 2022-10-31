package com.authentication.authentication.service

import com.authentication.authentication.entity.Token
import com.authentication.authentication.entity.UserEntity
import com.authentication.authentication.model.UserModel
import com.authentication.authentication.repository.UserRepository
import com.authentication.authentication.utils.exception.CustomException
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class UserService(private val userRepository: UserRepository, private val tokenService: TokenService) {

    fun createUser(userModel: UserModel): UserModel {
        userRepository.findUserByUsername(userModel.username)?.let { throw CustomException("User already exists!") }
        return userRepository.save(userModel.toEntity()).toAPI()
    }

    @Transactional
    fun loginUser(userModel: UserModel): Token {
        val user = userRepository.findUserByUsernameAndPassword(userModel.username, userModel.password)
            ?: throw CustomException("Could not login!")
        return tokenService.createUserToken(user.username)
    }

    fun getUserInfoByToken(userToken: Token): UserModel {
        val userName = tokenService.getUserNameByToken(userToken)?.username ?: throw CustomException("Token not valid!")
        return userRepository.findUserByUsername(userName)!!.toAPI()
    }

}

private fun UserEntity.toAPI() =
    UserModel(username = this.username, password = this.password)

private fun UserModel.toEntity() =
    UserEntity(username = this.username, password = this.password)
