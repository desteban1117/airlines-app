package controllers

import play.api.libs.json._
import play.api.libs.functional.syntax._

object Airline{
  implicit val airlineWrites: Writes[Airline] = (
    (JsPath \ "code").write[String] and
    (JsPath \ "name").write[String] and
    (JsPath \ "thumbnail").write[String] 
  )(unlift(Airline.unapply))

  implicit val airlineReads: Reads[Airline] = (
  	(JsPath \ "code").read[String] and
  	(JsPath \ "name").read[String] and 
  	(JsPath \ "thumbnail").read[String]  
  	)(Airline.apply _)


}


case class Airline(code: String,name: String,thumbnail: String)