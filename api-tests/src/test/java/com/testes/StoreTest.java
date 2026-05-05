package com.testes;

import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StoreTest {

    private static final int ORDER_ID = 1;

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }

    @Test
    @Order(1)
    @DisplayName("Deve criar um pedido na loja")
    void deveCriarPedido() {
        String body = """
                {
                    "id": 1,
                    "petId": 999999,
                    "quantity": 1,
                    "status": "placed",
                    "complete": true
                }
                """;

        given()
            .contentType(ContentType.JSON)
            .body(body)
        .when()
            .post("/store/order")
        .then()
            .statusCode(200)
            .body("id", equalTo(ORDER_ID))
            .body("status", equalTo("placed"));
    }

    @Test
    @Order(2)
    @DisplayName("Deve buscar o pedido pelo ID")
    void deveBuscarPedido() {
        given()
        .when()
            .get("/store/order/" + ORDER_ID)
        .then()
            .statusCode(200)
            .body("id", equalTo(ORDER_ID));
    }

    @Test
    @Order(3)
    @DisplayName("Deve consultar o inventário da loja")
    void deveConsultarInventario() {
        given()
        .when()
            .get("/store/inventory")
        .then()
            .statusCode(200);
    }

    @Test
    @Order(4)
    @DisplayName("Deve deletar o pedido")
    void deveDeletarPedido() {
        given()
        .when()
            .delete("/store/order/" + ORDER_ID)
        .then()
            .statusCode(200);
    }
}