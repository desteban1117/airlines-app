package model

import play.api.libs.json._

case class SearchFlight(departureDate: String,arrivalDate: String,origin: String,destination: String,passengers: Int,roundTrip: Boolean)

object SearchFlight{
  implicit val SearchFlighthWrite = Json.writes[SearchFlight]
  implicit val SearchFlightRead = Json.reads[SearchFlight]
}
