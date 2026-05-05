package com.testes;

import com.testes.pages.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CompraE2ETest {

    private static WebDriver driver;
    private static LoginPage loginPage;
    private static InventoryPage inventoryPage;
    private static CartPage cartPage;
    private static CheckoutPage checkoutPage;

    @BeforeAll
    static void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);
        cartPage = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);
    }

    @Test
    @Order(1)
    @DisplayName("Deve fazer login com sucesso")
    void deveLogar() {
        loginPage.abrirPagina();
        loginPage.fazerLogin("standard_user", "secret_sauce");
        assertEquals("Products", inventoryPage.obterTituloPagina());
    }

    @Test
    @Order(2)
    @DisplayName("Deve adicionar produto ao carrinho")
    void deveAdicionarProduto() {
        inventoryPage.adicionarProdutoAoCarrinho();
        inventoryPage.irParaCarrinho();
        assertEquals(1, cartPage.obterQuantidadeItens());
    }

    @Test
    @Order(3)
    @DisplayName("Deve finalizar a compra com sucesso")
    void deveFinalizarCompra() {
        cartPage.irParaCheckout();
        checkoutPage.preencherDados("Teste", "Usuario", "12345");
        checkoutPage.finalizarCompra();
        assertEquals("Thank you for your order!", checkoutPage.obterMensagemSucesso());
    }

    @AfterAll
    static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}