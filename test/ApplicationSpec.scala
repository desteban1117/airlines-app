import scala.concurrent.Future

import org.scalatestplus.play._

import play.api.mvc._
import play.api.test._
import play.api.test.Helpers._

class HomeController extends PlaySpec with Results {

  "Example Page#index" should {
    "should be valid" in {
      val controller = new HomeController()
      val result: Future[Result] = controller.index().apply(FakeRequest())
      val bodyText: String = contentAsString(result)
      bodyText mustBe "hola muchachos"
    }
  }
}
