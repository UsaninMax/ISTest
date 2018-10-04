package com.usanin.rest


import spark.{ExceptionHandler, Request, Response}

object ErrorHandler extends ExceptionHandler[Exception]{
  override def handle(exception: Exception, request: Request, response: Response): Unit = {
    response.header("Content-Type", "application/json")
    response.status(500)
    response.body("Sorry =( error happen")
  }
}
