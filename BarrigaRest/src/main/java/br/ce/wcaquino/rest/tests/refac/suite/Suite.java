package br.ce.wcaquino.rest.tests.refac.suite;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;


import br.ce.wcaquino.rest.core.BaseTest;
import br.ce.wcaquino.rest.tests.refac.AuthTest;
import br.ce.wcaquino.rest.tests.refac.ContasTest;
import br.ce.wcaquino.rest.tests.refac.MovimentacaoTest;
import br.ce.wcaquino.rest.tests.refac.SaldoTest;
import io.restassured.RestAssured;


//@ExtendWith({ ContasTest.class, MovimentacaoTest.class, SaldoTest.class, AuthTest.class })
//@Select({ ContasTest.class, MovimentacaoTest.class, SaldoTest.class, AuthTest.class })
public class Suite extends BaseTest {
	
	@BeforeAll
	public static void login() {
		Map<String, String> login = new HashMap<>();
		login.put("email", "zeotti@gmail.com");
		login.put("senha", "123456");
		
		//obter token do login
		String TOKEN = given()
			.body(login)
		.when()
			.post("/signin")
		.then()
			.statusCode(200)
			.extract().path("token");
		
		RestAssured.requestSpecification.header("Authorization", "JWT " + TOKEN);		// envia o token no given().header em todos os testes
		
		RestAssured.get("/reset").then().statusCode(200);
	}
}
