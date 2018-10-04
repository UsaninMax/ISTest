package com.usanin

import com.usanin.model.Product
import org.joda.time.{DateTime, DateTimeZone}

package object filter {
  def moreExpensive(product: Product): Boolean = {
    product.fields.get("ListPrice") match {
      case Some(v) => v.toDouble > 39
      case None => false
    }
  }

  def haveWordPlayerInTittle(product: Product): Boolean = {
    product.fields.get("Title") match {
      case Some(v) => v.contains("player")
      case None => false
    }
  }
  def haveEANlistDigitsMoreThan15(product: Product): Boolean = {
    product.fields.get("EANList") match {
      case Some(v) => v.length > 15
      case None => false
    }
  }

  def onlyLabelViewtv(product: Product): Boolean = {
    product.fields.get("Label") match {
      case Some(v) => v.replaceAll(" ", "") == "Viewtv"
      case None => false
    }
  }

  def addedByLast10Days(product: Product): Boolean = {
    product.product_added.isAfter(DateTime.now(DateTimeZone.UTC).minusDays(10))
  }
}
