package com.usanin.controller

import com.usanin.data.FileDataProvider
import com.usanin.filter._
import com.usanin.parser.{SourceParserComponent, SourceParserComponentImpl}
import com.usanin.rest.JsonTransformer
import spark.Spark._
import spark.{Request, Response}


trait DataPresenterControllerImpl {
  self: SourceParserComponent =>
  get("/data/show", (reg: Request, resp: Response) => {
    reg.queryParams("filterId") match {
      case "1" => sourceParser.parsedData().filter(moreExpensive)
      case "2" => sourceParser.parsedData().filter(haveWordPlayerInTittle)
      case "3" => sourceParser.parsedData().filter(haveEANlistDigitsMoreThan15)
      case "4" => sourceParser.parsedData()
        .sortBy(r => r.fields.getOrElse("ListPrice", "0"))
        .sortBy(r => r.fields.getOrElse("EAN", "0"))(Ordering[String].reverse)
        .take(10)
      case "5" => sourceParser.parsedData().filter(onlyLabelViewtv)
        .sortBy(r => r.fields.getOrElse("ListPrice", "0"))(Ordering[String].reverse)
      case "6" => sourceParser.parsedData().filter(addedByLast10Days)
    }

  }, JsonTransformer)
}

object DataPresenterController extends DataPresenterControllerImpl
  with SourceParserComponentImpl
  with FileDataProvider