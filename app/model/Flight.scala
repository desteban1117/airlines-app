package model

import play.api.libs.json._

case class Flight(flightCode: String ,origin: String ,destination: String ,price: String ,currency: String , departure_date: String, arrival_date: String, passengers: String, hour: String)

object Flight{
  implicit val FligthWrite = Json.writes[Flight]
  implicit val FligthRead = Json.reads[Flight]
}
