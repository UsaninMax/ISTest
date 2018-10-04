package com.usanin.parser

import java.nio.charset.CodingErrorAction

import com.usanin.data.SourceProviderComponent
import org.junit.Assert.assertEquals
import org.mockito.Mockito.{mock, reset, when}
import org.scalatest.{BeforeAndAfter, FlatSpec, Suite}

import scala.io.{Codec, Source}

class SourceParserComponentTest
  extends FlatSpec
    with BeforeAndAfter
    with Suite {

  before {
    reset(SourceParserComponentTest.sourceProvider)
  }

  object SourceParserComponentTest
    extends SourceParserComponentImpl
      with SourceProviderComponentMock


  trait SourceProviderComponentMock extends SourceProviderComponent {
    override val sourceProvider: SourceProvider = mock(classOf[SourceProvider])
  }

  it should "show return empty list if data empty" in {
    when(SourceParserComponentTest.sourceProvider.get()).thenReturn("")
    assertEquals(0, SourceParserComponentTest.sourceParser.data.size)
  }

  it should "show return one product" in {
    when(SourceParserComponentTest.sourceProvider.get()).thenReturn(testData())
    assertEquals(0, SourceParserComponentTest.sourceParser.data.size)
    assertEquals(1, SourceParserComponentTest.sourceParser.parse().size)
  }

  def testData(): String = {
    val filePath: String = "/test.txt"
    implicit val codec: Codec = Codec.UTF8
    codec.onMalformedInput(CodingErrorAction.IGNORE)

    Source.fromInputStream(getClass.getResourceAsStream(filePath)).mkString
  }
}
