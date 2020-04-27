package controllers

import org.scalatest.{MustMatchers, WordSpec}
import org.scalatestplus.mockito.MockitoSugar
import org.scalatestplus.play.guice.GuiceOneAppPerSuite

trait PlaySpecBase extends WordSpec with MustMatchers with GuiceOneAppPerSuite with MockitoSugar {}
