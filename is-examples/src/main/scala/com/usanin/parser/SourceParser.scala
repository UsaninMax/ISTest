package com.usanin.parser

import com.usanin.converter
import com.usanin.data.SourceProviderComponent
import com.usanin.model.Product
import org.joda.time.{DateTime, DateTimeZone}

trait SourceParserComponent {

  val sourceParser: SourceParser

  trait SourceParser {
    def parsedData(): Seq[Product]
  }
}

trait SourceParserComponentImpl extends SourceParserComponent {
  self: SourceProviderComponent =>

  override val sourceParser = new SourceParserImpl

  class SourceParserImpl extends SourceParser {
    lazy val data: Seq[Product] = parse()

    override def parsedData(): Seq[Product] = data

    def parse(): Seq[Product] = {
      sourceProvider.get().split("ITEM \\d+")
        .map(s => s
          .split("\\n")
          .filter(x => x.nonEmpty && x.contains("="))
          .map(m => m
            .split("=", 2) match {
            case Array(x, y) if x == "ListPrice" => (x, converter.priceConverter(y))
            case Array(x, y) => (x, y)
          }).toMap
        ).filter(_.nonEmpty).map(f => Product(generateRandomDate, f)).toSeq
    }

    def generateRandomDate: DateTime = {
      val now = DateTime.now().getMillis
      val before = DateTime.now().minusMonths(3).getMillis
      new DateTime((now + Math.random * (before - now)).asInstanceOf[Long], DateTimeZone.UTC)
    }
  }
}
