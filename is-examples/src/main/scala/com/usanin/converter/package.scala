package com.usanin

package object converter {

  //example 24600USD$246.00
  def priceConverter(s: String): String = {
    s.replaceFirst("^[^\\$]*\\$", "").replaceAll(",","")
  }

}
