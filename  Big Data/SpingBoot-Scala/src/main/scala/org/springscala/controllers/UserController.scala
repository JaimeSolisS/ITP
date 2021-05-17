package org.springscala.controllers

import java.lang.Iterable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController 
import org.springframework.http.HttpStatus
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PathVariable

@RestController
@RequestMapping(path = Array("/api"))
class UserController() {

  @GetMapping(path = Array("/users"))
  def getAllUsers(): ResponseEntity[String] = {
     new ResponseEntity("Hello world", new HttpHeaders, HttpStatus.CREATED)
  }
  
  @GetMapping(path = Array("/users/{id}"))
  def getUser(@PathVariable id: Long): ResponseEntity[String] = {
     new ResponseEntity("Hello world by user", new HttpHeaders, HttpStatus.CREATED)
  }
 
}