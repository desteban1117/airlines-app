package model

import play.api.libs.json._

case class Reserve(username: String,flightCode: String ,passengers: Int,token: String)

object Reserve{
  implicit val ReserveWrite = Json.writes[Reserve]
  implicit val ReserveRead = Json.reads[Reserve]
}
