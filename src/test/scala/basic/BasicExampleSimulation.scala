package basic

import io.gatling.core.Predef._
import io.gatling.core.feeder.BatchableFeederBuilder
import io.gatling.core.scenario.Simulation
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class BasicExampleSimulation extends Simulation {

	val httpProtocol: HttpProtocolBuilder = http
		.baseUrl("http://excilys-bank-web.cloudfoundry.com")
		.acceptCharsetHeader("ISO-8859-1,utf-8;q=0.7,*;q=0.7")
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("fr,fr-fr;q=0.8,en-us;q=0.5,en;q=0.3")
		.disableFollowRedirect

	val headers_3 = Map(
		"Keep-Alive" -> "115",
		"Content-Type" -> "application/x-www-form-urlencoded")
	val WaterTracker_csv: BatchableFeederBuilder[String]#F = csv("user_information.csv").random

	val scn: ScenarioBuilder = scenario("Scenario_name")
	.feed(WaterTracker_csv)
	.exec(http("request3")
		.post("/login")
		.headers(headers_3)
		.formParam("password", "${password}")
		.formParam("username", "${username}")

	)
		setUp(scn.inject(rampUsers(10) during  10)).protocols(httpProtocol)

}
