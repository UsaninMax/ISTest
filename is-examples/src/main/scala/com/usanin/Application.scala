package com.usanin

import com.usanin.controller.{BaseController, DataPresenterController}
import com.usanin.rest.SparkRestConfig
import spark.Spark

import scala.concurrent.Future

object Application extends App {
  SparkRestConfig
  BaseController
  DataPresenterController

  Future {
    Spark.init()
    Spark.awaitInitialization()
  }(scala.concurrent.ExecutionContext.global)
}