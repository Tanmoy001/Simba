package com.example.demo.service

import org.springframework.stereotype.Service

@Service
class ScalaService {

  def createGreeting(name: String): String = {
    s"Hello, $name! (Greeting from Scala)"
  }

  def addNumbers(a: Int, b: Int): Int = {
    a + b
  }
}