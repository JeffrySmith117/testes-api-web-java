package com.testes;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserTest {

    private static final String USERNAME = "testeuser123";

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }

    @Test
    @Order(1)
    @DisplayName("Deve criar um usuário com sucesso")
    void deveCriarUsuario() {
        String body = """
                {
                    "id": 1,
                    "username": "testeuser123",
                    "firstName": "Teste",
                    "lastName": "User",
                    "email": "teste@email.com",
                    "password": "senha123",
                    "phone": "11999999999",
                    "userStatus": 1
                }
                """;

        given()
            .contentType(ContentType.JSON)
            .body(body)
        .when()
            .post("/user")
        .then()
            .statusCode(200);
    }

    @Test
    @Order(2)
    @DisplayName("Deve buscar o usuário pelo username")
    void deveBuscarUsuario() {
        given()
        .when()
            .get("/user/" + USERNAME)
        .then()
            .statusCode(200)
            .body("username", equalTo(USERNAME));
    }

    @Test
    @Order(3)
    @DisplayName("Deve fazer login com o usuário")
    void deveLogarUsuario() {
        given()
            .queryParam("username", USERNAME)
            .queryParam("password", "senha123")
        .when()
            .get("/user/login")
        .then()
            .statusCode(200);
    }

    @Test
    @Order(4)
    @DisplayName("Deve deletar o usuário")
    void deveDeletarUsuario() {
        given()
        .when()
            .delete("/user/" + USERNAME)
        .then()
            .statusCode(200);
    }
}