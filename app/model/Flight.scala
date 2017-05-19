package model

import play.api.libs.json._

case class Flight(flightCode: String ,origin: String ,destination: String ,price: Int ,currency: String , date: String)

object Flight{
  implicit val FligthWrite = Json.writes[Flight]
  implicit val FligthRead = Json.reads[Flight]
}
