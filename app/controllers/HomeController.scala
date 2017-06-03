package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.libs.json._
import model.Flight
import model.SearchFlight
import model.Reserve
import play.api.db._
import play.api.db.Databases
import scala.collection.mutable.ListBuffer

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject() extends Controller {

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action {
    Ok("Bienvenidos a Chan-Airlines")
  }

  implicit class RichResult (result: Result) {
  def enableCors =  result.withHeaders(
    "Access-Control-Allow-Origin" -> "*"
    , "Access-Control-Allow-Methods" -> "OPTIONS, GET, POST, PUT, DELETE, HEAD"   // OPTIONS for pre-flight
    , "Access-Control-Allow-Headers" -> "Accept, Content-Type, Origin, X-Json, X-Prototype-Version, X-Requested-With" //, "X-My-NonStd-Option"
    , "Access-Control-Allow-Credentials" -> "true"
  )
}

  def search = Action { implicit request =>

    val json = Json.toJson(request.body.asJson)
    val searchFlight = json.validate[SearchFlight].get

    var flights = ListBuffer[Flight]()
    val db = Databases(
      driver = "org.postgresql.Driver",
      url = "jdbc:postgresql://ec2-54-83-49-44.compute-1.amazonaws.com:5432/d4knfn1f5q4rac?user=lfvvtprsytbqlr&password=22ed3b05700ea24f010b53fb45211e9cd6d943f0a0550f3f03aeab57fdae3cbb&sslmode=require"

    )
    val conn = db.getConnection()

    try {
      val stmt = conn.createStatement
      val rs = stmt.executeQuery("SELECT * FROM flight WHERE origin = '"+searchFlight.origin+"' AND destination = '"+searchFlight.destination+"' AND departure_date = '"+searchFlight.departureDate+"' AND passengers >= "+searchFlight.passengers)

      while (rs.next()) {
        var flight = Flight(rs.getString("id"),rs.getString("origin"),rs.getString("destination"),rs.getString("price"), rs.getString("currency"), rs.getString("hour") +" "+ rs.getString("departure_date"), rs.getString("arrival_date"), rs.getString("passengers"))
        flights += flight
      }
    } finally {
      conn.close()
    }
    val jsonNormal =  Json.obj("airline"->Airline("1117","chan","xxx"),
                                "result"->flights)
    Ok(jsonNormal).enableCors

  }



   def reserve = Action { implicit request =>

    var message = "R"
    val json = Json.toJson(request.body.asJson)
    val createReserve = json.validate[Reserve].get
    val db = Databases(
      driver = "org.postgresql.Driver",
      url = "jdbc:postgresql://ec2-54-83-49-44.compute-1.amazonaws.com:5432/d4knfn1f5q4rac?user=lfvvtprsytbqlr&password=22ed3b05700ea24f010b53fb45211e9cd6d943f0a0550f3f03aeab57fdae3cbb&sslmode=require"

    )
    val conn = db.getConnection()
    val stmt = conn.createStatement
    try {
      val rs = stmt.executeQuery("SELECT * FROM flight WHERE id = '"+createReserve.flightCode+"'")
       if (rs.next()){

        if(rs.getString("passengers").toInt < createReserve.passengers ){
          message = "I"
        }else{
          val queryFlight = stmt.executeUpdate("INSERT INTO reserve(user_name,flightCode,passengers) VALUES('"+createReserve.username+"','"+createReserve.flightCode+"',"+createReserve.passengers+");")
        }
       }else{
           message = "NF"
       }
    } finally {
      conn.close()
    }
    Ok(Json.obj("message"->message)).enableCors

  }
  //Servicio para ver las reservas hechas por un user
    def seeReserve(token: String)= Action { 

    var flights = ListBuffer[Flight]()
    val db = Databases(
      driver = "org.postgresql.Driver",
      url = "jdbc:postgresql://ec2-54-83-49-44.compute-1.amazonaws.com:5432/d4knfn1f5q4rac?user=lfvvtprsytbqlr&password=22ed3b05700ea24f010b53fb45211e9cd6d943f0a0550f3f03aeab57fdae3cbb&sslmode=require"

    )
    val conn = db.getConnection()

    try {
      val stmt = conn.createStatement
      val rs = stmt.executeQuery("SELECT * FROM flight f, reserve r WHERE f.id = r.flightcode AND r.user_name ='"+token+"';")

      while (rs.next()) {
        var flight = Flight(rs.getString("flightcode"),rs.getString("origin"),rs.getString("destination"),rs.getString("price"), rs.getString("currency"), rs.getString("hour") +" "+ rs.getString("departure_date"), rs.getString("arrival_date"), rs.getString("passengers"))
        flights += flight
        
      }
      conn.close()
    } finally {
      conn.close()
    }
    //conn.close()
    val jsonNormal =  Json.obj("airline"->Airline("1117","chan","xxx"),
                                "result"->flights)
    Ok(jsonNormal).enableCors

  }


}
