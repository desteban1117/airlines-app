
import org.scalatestplus.play._


import play.api.db._
import play.api.db.Databases



class ConectionSpec extends PlaySpec {

    "CONECTION TEST" should {
      "should be valid" in {

        var outString = ""
        val db = Databases(
          driver = "org.postgresql.Driver",
          url = "jdbc:postgresql://ec2-54-83-49-44.compute-1.amazonaws.com:5432/d4knfn1f5q4rac?user=lfvvtprsytbqlr&password=22ed3b05700ea24f010b53fb45211e9cd6d943f0a0550f3f03aeab57fdae3cbb&sslmode=require"

        )
        val conn = db.getConnection()

        try {
          val stmt = conn.createStatement
          val rs = stmt.executeQuery("SELECT origin FROM flight WHERE flightCode = 1")

          while (rs.next()) {
            outString = rs.getString("origin")
          }
        } finally {
          conn.close()
        }

        outString mustBe "MDE"

      }
    }
}
