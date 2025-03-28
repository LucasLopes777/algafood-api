package com.algaworks.algafood;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.util.DatabaseCleaner;
import com.algaworks.algafood.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

//10.3. Criando e rodando um teste de integração com Spring Boot, JUnit e AssertJ
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) //10.8. Implementando Testes de API com REST Assured e validando o código de status HTTP
@TestPropertySource("/application-test.properties")
public class CadastroCozinhaIntegrationIT {

	//****************Testes de API****************
	
	private static final int COZINHA_ID_INEXISTENTE = 100;
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	private Cozinha cozinhaAmericana;
	private int quantidadeCozinhasCadastradas;
	private String jsonCorretoCozinhaChinesa;
	
	//10.12. Voltando o estado inicial do banco de dados para cada execução de teste com callback do Flyway
//	@Autowired
//	private Flyway flyway;
	
	//10.10. Criando um método para fazer setup dos testes
	@Before
	public void setUp() {
		
		jsonCorretoCozinhaChinesa = ResourceUtils.getContentFromResource(
				"/json/correto/cozinha-chinesa.json");
		
		//habilita log's em casos de erro
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.basePath = "/cozinhas";
		RestAssured.port = port;
		
		databaseCleaner.clearTables();
		
		preparaDados();

//		flyway.migrate();
	}

	@Test
	public void shouldRetornarStatus200_WhenConsultarCozinhas() {
		
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void shouldConter4Cozinhas_WhenConsultarCozinhas() {
		
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("", Matchers.hasSize(4))
			.body("nome", Matchers.hasItems("Indiana", "Tailandesa"));
	}
	
	@Test
	public void testRetornaStatus200_whenCadastrarCozinha() {
		given()
			.body("{\"nome\": \"Chinesa\"}")
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void deveRetornarStatus201_QuandoCadastrarCozinha() {
	    given()
	        .body(jsonCorretoCozinhaChinesa)
	        .contentType(ContentType.JSON)
	        .accept(ContentType.JSON)
	    .when()
	        .post()
	    .then()
	        .statusCode(HttpStatus.CREATED.value());
	}

	@Test
	public void deveRetornarQuantidadeCorretaDeCozinhas_QuandoConsultarCozinhas() {
	    given()
	        .accept(ContentType.JSON)
	    .when()
	        .get()
	    .then()
	        .body("", hasSize(quantidadeCozinhasCadastradas));
	}
	
	@Test
	public void deveRetornarRespostaEStatusCorretos_QuandoConsultarCozinhaExistente() {
		given()
			.pathParam("cozinhaId", cozinhaAmericana.getId())
			.accept(ContentType.JSON)
		.when()
			.get("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("nome", equalTo(cozinhaAmericana.getNome()));
	}
	
	@Test	
	public void deveRetornarStatus404_QuandoConsultarCozinhaInexistente() {
	    given()
	        .pathParam("cozinhaId", COZINHA_ID_INEXISTENTE)
	        .accept(ContentType.JSON)
	    .when()
	        .get("/{cozinhaId}")
	    .then()
	        .statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	private void preparaDados() {

		Cozinha cozinha1 = new Cozinha();
		cozinha1.setNome("Tailandesa");
		cozinhaRepository.save(cozinha1);
		
		Cozinha cozinha2 = new Cozinha();
		cozinha2.setNome("Americana");
		cozinhaRepository.save(cozinha2);
		
		quantidadeCozinhasCadastradas = (int) cozinhaRepository.count();
	}

	
	/*
	//****************Testes de Integração****************
	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	@Test
	public void shouldAtribuidId_WhenCadastrarCozinhaComDadosCorretos() {
		// Cenário
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome("Chinesa");

		// Ação
		novaCozinha = cadastroCozinha.salvar(novaCozinha);

		// Validação
		assertThat(novaCozinha).isNotNull();
		assertThat(novaCozinha.getId()).isNotNull();

	}

	@Test(expected = ConstraintViolationException.class)
	public void shouldFalhar_WhenCadastrarCozinhaSemNome() {
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome(null);

		novaCozinha = cadastroCozinha.salvar(novaCozinha);
	}

	@Test(expected = EntidadeEmUsoException.class)
	public void shouldFalhar_WhenExcluirCozinhaEmUso() {
		cadastroCozinha.excluir(1L);
	}

	@Test(expected = CozinhaNaoEncontradaException.class)
	public void shouldFalhar_WhenExcluirCozinhaInexistente() {
		cadastroCozinha.excluir(100L);
	}
	*/
	
}
