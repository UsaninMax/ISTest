package com.usanin.controller

import com.usanin.rest.JsonTransformer
import spark.utils.IOUtils
import spark.{Request, Response, Spark}
import spark.Spark._

object BaseController {
  post("/", (reg: Request, resp: Response) => {
    IOUtils.toString(classOf[Spark].getResourceAsStream("/static/Index.html"))
  }, JsonTransformer)
}
