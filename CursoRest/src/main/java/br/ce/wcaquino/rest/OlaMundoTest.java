package br.ce.wcaquino.rest;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class OlaMundoTest {
	
	@Test
	public void testOlaMundo() {
		
		Response response = request(Method.GET, "http://restapi.wcaquino.me:80/ola");
		assertTrue(response.getBody().asString().equals("Ola Mundo!"));
		assertTrue(response.statusCode() == 200);
		assertEquals(200, response.statusCode());
		
		ValidatableResponse validacao = response.then();
		validacao.statusCode(200);
	}
	
	@Test
	public void devoConhecerOutrasFormasRestAssured() {
		
		Response response = request(Method.GET, "http://restapi.wcaquino.me/ola");
		ValidatableResponse validacao = response.then();
		validacao.statusCode(200);
		
		get("http://restapi.wcaquino.me/ola").then().statusCode(200);
		
		given() //Pré condicoes
		.when() //Ação
			.get("http://restapi.wcaquino.me/ola")
		.then() //Assertivas
			.statusCode(200);
	}
	
	@Test
	public void devoConhecerMatchersHamcrest() {
		assertThat("Maria", is("Maria"));
		assertThat(128, is(128));
		assertThat(128, Matchers.isA(Integer.class));
		assertThat(128d, Matchers.isA(Double.class));
		assertThat(128d, Matchers.greaterThan(120d)); //128 é maior que 120?
		assertThat(128d, Matchers.lessThan(130d)); //128 é menos que 130?
		
		List<Integer> impares = Arrays.asList(1,3,5,7,9);
		assertThat(impares, hasSize(5)); // lista tem tamanho 5?
		assertThat(impares, contains(1,3,5,7,9)); //contem esses numeros
		assertThat(impares, containsInAnyOrder(1,5,3,9,7)); //nao importa a ordem
		assertThat(impares, hasItem(1)); //possui o numero 1 na lista
		assertThat(impares, hasItems(1, 5)); //possui os numeros 1 e 5 na lista
		
		assertThat("Maria", is(not("Joao"))); //Maria nao é Joao
		assertThat("Maria", not("Joao")); // Pode usar sem o is()
		assertThat("Joaquina", anyOf(is("Maria"), is("Joaquina"))); //Joaquina é Maria ou Joaquina?
		assertThat("Joaquina", allOf(startsWith("Joa"), endsWith("ina"), containsString("qui")));
	}

	@Test
	public void devoValidarBody() {
		given()
		.when()
			.get("http://restapi.wcaquino.me/ola")
		.then()
			.statusCode(200)
			.body(is("Ola Mundo!"))
			.body(containsString("Mundo"))
			.body(is(not(nullValue())));
	}
}
