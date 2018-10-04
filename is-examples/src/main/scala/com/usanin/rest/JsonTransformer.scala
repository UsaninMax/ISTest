package com.usanin.rest

import spark.ResponseTransformer

object JsonTransformer extends ResponseTransformer{
  override def render(model: scala.Any): String = model match {
    case str: String => str
    case _=> JacksonSupport.mapper.writeValueAsString(model)
  }
}
