package com.usanin.rest

import spark.{Request, Response, Spark}

object SparkRestConfig {
  Spark.port(9000)
  Spark.staticFileLocation("/static")
  Spark.exception(classOf[Exception], ErrorHandler)

  Spark.after("/*", (req: Request, resp: Response) => {
    resp.header("Cache-Control", "no-catche,no-store,must-revalidate")
    resp.header("Pragma", "no-catche")
    resp.header("Expires", "-1")
  })

  Spark.before((request: Request, response: Response) => {
    response.header("Access-Control-Allow-Origin", "*")
  })
  Spark.options("/*", (request: Request, response: Response) => {
    val accessControlRequestHeaders = request.headers("Access-Control-Request-Headers")
    if (accessControlRequestHeaders != null) response.header("Access-Control-Allow-Headers", accessControlRequestHeaders)
    val accessControlRequestMethod = request.headers("Access-Control-Request-Method")
    if (accessControlRequestMethod != null) response.header("Access-Control-Allow-Methods", accessControlRequestMethod)
    "OK"
  })
}
