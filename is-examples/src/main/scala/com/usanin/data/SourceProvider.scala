package com.usanin.data

import java.nio.charset.CodingErrorAction

import scala.io.{Codec, Source}

trait SourceProviderComponent {
  val sourceProvider: SourceProvider

  trait SourceProvider {
    def get(): String
  }
}

trait FileDataProvider extends SourceProviderComponent {
  override val sourceProvider = new SourceProviderImpl

  class SourceProviderImpl extends SourceProvider {
    val filePath: String = "/amazondata_Electronics_14200_142.txt"
    implicit val codec: Codec = Codec.UTF8
    codec.onMalformedInput(CodingErrorAction.IGNORE)

    override def get(): String = {
      Source.fromInputStream(getClass.getResourceAsStream(filePath)).mkString
    }
  }
}