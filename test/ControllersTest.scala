
import scala.concurrent.Future

import org.scalatestplus.play._

import play.api.mvc._
import play.api.test._
import play.api.test.Helpers._
import controllers.HomeController
import play.api.libs.json._

class HomeControllerSpec extends PlaySpec with Results {

  "Example Page#index" should {
    "should be valid" in {
      val controller = new HomeController()
      val result: Future[Result] = controller.index().apply(FakeRequest())
      val bodyText: String = contentAsString(result)
      bodyText mustBe "hola muchachos 2"
    }
  }
/*
  "json text" should {
    "should be valid" in {
      val jsonResult = Json.obj(
        "id"-> "06",
        "origin"-> "medellin",
        "destination"-> "bogota",
        "price"-> 2000,
        "currency"-> "COP",
        "departure_date"-> "01-02-2017",
        "arrival_date"-> "01-02-2017",
        "passengers" -> "",
        "hour" -> ""
      )
      val controller = new HomeController()
      val result: Future[Result] = controller.search.apply(FakeRequest(POST, "/").withJsonBody(jsonResult))
      val json  = contentAsJson(result)
      json mustBe jsonResult
    }
  }*/
}
