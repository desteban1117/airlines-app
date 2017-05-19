
import org.scalatestplus.play._

import model.Flight
import model.SearchFlight
import play.api.libs.json._


import play.api.db._

class ModelSpec extends PlaySpec {

  "Fligth test class to json" should {
    "should be valid" in {

      val jsonResult = Json.obj(
        "flightCode"-> "05",
        "origin"-> "medellin",
        "destination"-> "bogota",
        "price"-> 2000,
        "currency"-> "COP",
        "date"-> "01-02-2017"
      )

      val flight = Flight("05", "medellin", "bogota", 2000, "COP", "01-02-2017")
      val json = Json.toJson(flight)

      json mustBe jsonResult

    }
  }

  "Fligth test json to class" should {
    "should be valid" in {

      val json = Json.obj(
        "flightCode"-> "05",
        "origin"-> "medellin",
        "destination"-> "bogota",
        "price"-> 2000,
        "currency"-> "COP",
        "date"-> "01-02-2017"
      )

      val flightResult = Flight("05", "medellin", "bogota", 2000, "COP", "01-02-2017")

      val flight = json.validate[Flight].get

      flight mustBe flightResult

    }
  }

  "SearchFlight test json to class" should {
    "should be valid" in {

      val json = Json.obj(
        "departureDate"-> "12-15-2017",
        "arrivalDate"-> "",
        "origin"-> "bogota",
        "destination"-> "medellin",
        "passengers"-> 2,
        "roundTrip"-> false
      )

      val searchFlightResult = SearchFlight("12-15-2017", "", "bogota", "medellin", 2, false)

      val searchFlight = json.validate[SearchFlight].get

      searchFlight mustBe searchFlightResult

    }
  }

  "SearchFlight test class to json" should {
    "should be valid" in {

      val jsonResult = Json.obj(
        "departureDate"-> "12-15-2017",
        "arrivalDate"-> "",
        "origin"-> "bogota",
        "destination"-> "medellin",
        "passengers"-> 2,
        "roundTrip"-> false
      )

      val searchFlight = SearchFlight("12-15-2017", "", "bogota", "medellin", 2, false)
      val json = Json.toJson(searchFlight)

      json mustBe jsonResult

    }
  }

}

//case class SearchFlight(departureDate: String,arrivalDate: String,origin: String,destination: String,passengers: Int,roundTrip: String)
