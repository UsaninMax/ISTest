package com.usanin.controller

import com.usanin.model.Product
import com.usanin.parser.SourceParserComponent
import com.usanin.rest.ErrorHandler
import org.joda.time.DateTime
import org.junit.Assert.assertEquals
import org.mockito.Mockito.{reset, _}
import org.scalatest.{BeforeAndAfter, BeforeAndAfterAll, FlatSpec, Suite}
import scalaj.http.{Http, HttpOptions}
import spark.{Request, Response, Spark}

import scala.concurrent.Future

class DataPresenterControllerTest extends FlatSpec
  with BeforeAndAfter
  with Suite
  with BeforeAndAfterAll {

  override def beforeAll(): Unit = {
    Spark.port(9000)
    Spark.staticFileLocation("/static")
    Spark.exception(classOf[Exception], ErrorHandler)

    Spark.after("/*", (req: Request, resp: Response) => {
      resp.header("Cache-Control", "no-catche,no-store,must-revalidate")
      resp.header("Pragma", "no-catche")
      resp.header("Expires", "-1")
    })

    BaseController
    DataPresenterControllerTest

    Future {
      Spark.init()
      Spark.awaitInitialization()
    }(scala.concurrent.ExecutionContext.global)
  }

  before {
    reset(DataPresenterControllerTest.sourceParser)
  }

  object DataPresenterControllerTest
    extends DataPresenterControllerImpl
      with SourceParserComponentMock


  trait SourceParserComponentMock extends SourceParserComponent {
    override val sourceParser: SourceParser = mock(classOf[SourceParser])
  }

  it should "show data with price more 39$" in {
    when(DataPresenterControllerTest.sourceParser.parsedData()).thenReturn(products)
    val result = Http("http://localhost:9000/data/show")
      .method("get")
      .param("filterId", "1")
      .header("Content-Type", "application/json")
      .header("Charset", "UTF-8")
      .option(HttpOptions.readTimeout(10000)).asString

    assertEquals(200, result.code)
    println(result.body)
  }

  val products: Seq[Product] = Seq(
    Product(DateTime.now(), Map("ListPrice" -> "22")),
    Product(DateTime.now(), Map("ListPrice" -> "45")),
    Product(DateTime.now(), Map("ListPrice" -> "33")),
    Product(DateTime.now(), Map("ListPrice" -> "456.88")),
    Product(DateTime.now(), Map("ListPrice" -> "56.99")),
    Product(DateTime.now(), Map()),
    Product(DateTime.now(), Map("ListPrice" -> "5"))
  )

}
