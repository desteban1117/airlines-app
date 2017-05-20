package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.libs.json._
import model.Flight
import model.SearchFlight
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
    Ok("hola muchachos 2")
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
        var flight = Flight(rs.getString("id"),rs.getString("origin"),rs.getString("destination"),rs.getString("price"), rs.getString("currency"), rs.getString("departure_date"), rs.getString("arrival_date"), rs.getString("passengers"), rs.getString("hour") )


        flights += flight
      }
    } finally {
      conn.close()
    }
    val jsonAnswer = Json.arr(
    	Json.obj("airline"->Airline("1117","chan","xxx")),
    	Json.obj("result"->flights)
    	) 
    val jsonflights = Json.toJson(flights)
    Ok(jsonAnswer)

  }

}
