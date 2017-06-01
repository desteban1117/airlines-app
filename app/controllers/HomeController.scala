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
import java.io.InputStream
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.database._
import java.io.FileInputStream

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
      conn.close()
    } finally {
      conn.close()
    }
    //conn.close()
    val jsonNormal =  Json.obj("airline"->Airline("1117","chan","xxx"),
                                "result"->flights)
    Ok(jsonNormal).enableCors

  }


  def reservar  = Action { implicit request =>

/*
    object Firebase {

      private val credentials : InpuStream = getClass.getResourceAsStream("/airline-chan-firebase-adminsdk-99fgo-6acba9f607.json")
      private val options = new FirebaseOptions.Builder()
        .setServiceAccount(credentials)
        .setDatabaseUrl("https://airline-chan.firebaseio.com")
        .build()
      FirebaseApp.initializeApp(options)*/
/*
      FileInputStream serviceAccount = new FileInputStream("path/to/serviceAccountKey.json");

      FirebaseOptions options = new FirebaseOptions.Builder()
        .setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
        .setDatabaseUrl("https://airline-chan.firebaseio.com")
        .build();

      FirebaseApp.initializeApp(options);
    }*/

    val json = Json.toJson(request.body.asJson)
    Ok((json \ "token").as[String])

  }

}
