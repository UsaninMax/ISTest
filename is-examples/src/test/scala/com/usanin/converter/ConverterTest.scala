package com.usanin.converter

import com.usanin.model.Product
import org.joda.time.DateTime
import org.junit.Assert.assertEquals
import org.scalatest.{FlatSpec, Suite}

class ConverterTest extends FlatSpec with Suite {

  it should "convert price 24600USD$246.00 to 246.00" in {
    assertEquals("246.00", priceConverter("24600USD$246.00"))
  }

  it should "convert price 246,000.00USD$246,000.00 to 24600.00" in {
    assertEquals("246000.00", priceConverter("246,000.00USD$246,000.00"))
  }

}
