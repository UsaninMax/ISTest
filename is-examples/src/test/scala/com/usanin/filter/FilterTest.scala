package com.usanin.filter

import com.usanin.model.Product
import org.joda.time.DateTime
import org.junit.Assert.assertEquals
import org.scalatest.{FlatSpec, Suite}

class FilterTest extends FlatSpec with Suite {

  it should "show data with price more 39$" in {
    assertEquals(3, products.count(moreExpensive))
  }

  it should "show data have Word Player In Tittle" in {
    assertEquals(1, products.count(haveWordPlayerInTittle))
  }

  it should "show data have EANlist Digits More Than 15" in {
    assertEquals(1, products.count(haveEANlistDigitsMoreThan15))
  }

  it should "show data have only Viewtv in label" in {
    assertEquals(1, products.count(onlyLabelViewtv))
  }

  it should "show data have added in last 10 days" in {
    assertEquals(6, products.count(addedByLast10Days))
  }

  val products: Seq[Product] = Seq(
    Product(DateTime.now(), Map("ListPrice" -> "22", "Title" -> "player dsad ", "EANList" -> "2353532453453453255325345345")),
    Product(DateTime.now(), Map("ListPrice" -> "45", "EANList" -> "0")),
    Product(DateTime.now(), Map("ListPrice" -> "33")),
    Product(DateTime.now(), Map("ListPrice" -> "456.88", "Label" -> "werwe Viewtv")),
    Product(DateTime.now(), Map("ListPrice" -> "56.99", "Label" -> " Viewtv")),
    Product(DateTime.now(), Map()),
    Product(DateTime.now().minusMonths(5), Map("ListPrice" -> "5"))

  )
}
