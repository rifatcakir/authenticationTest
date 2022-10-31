package com.authentication.authentication.controller

import com.authentication.authentication.entity.Token
import com.authentication.authentication.model.UserModel
import com.authentication.authentication.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController(private val userService: UserService) {

    @PostMapping("/create")
    fun createUser(@RequestBody userModel: UserModel) = userService.createUser(userModel)

    @PostMapping("/login")
    fun loginUser(@RequestBody userModel: UserModel) = userService.loginUser(userModel)

    @GetMapping("/profile/{token}")
    fun getProfile(@PathVariable token: Token) = userService.getUserInfoByToken(token)
}