package com.testes;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PetTest {

    // ID fixo para usar em todos os testes
    private static final long PET_ID = 999999L;

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }

    @Test
    @Order(1)
    @DisplayName("Deve criar um pet com sucesso")
    void deveCriarPet() {
        String body = """
                {
                    "id": 999999,
                    "name": "Rex",
                    "status": "available"
                }
                """;

        given()
            .contentType(ContentType.JSON)
            .body(body)
        .when()
            .post("/pet")
        .then()
            .statusCode(200)
            .body("id", equalTo(999999))
            .body("name", equalTo("Rex"));
    }

    @Test
    @Order(2)
    @DisplayName("Deve buscar o pet pelo ID")
    void deveBuscarPetPorId() {
        given()
        .when()
            .get("/pet/" + PET_ID)
        .then()
            .statusCode(200)
            .body("id", equalTo(999999))
            .body("name", equalTo("Rex"));
    }

    @Test
    @Order(3)
    @DisplayName("Deve atualizar o status do pet")
    void deveAtualizarPet() {
        String body = """
                {
                    "id": 999999,
                    "name": "Rex",
                    "status": "sold"
                }
                """;

        given()
            .contentType(ContentType.JSON)
            .body(body)
        .when()
            .put("/pet")
        .then()
            .statusCode(200)
            .body("status", equalTo("sold"));
    }

    @Test
    @Order(4)
    @DisplayName("Deve deletar o pet")
    void deveDeletarPet() {
        given()
        .when()
            .delete("/pet/" + PET_ID)
        .then()
            .statusCode(200);
    }
}