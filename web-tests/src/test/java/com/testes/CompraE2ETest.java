package com.testes;

import com.testes.pages.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.*;

public class CompraE2ETest {

    private WebDriver driver;
    private LoginPage loginPage;
    private InventoryPage inventoryPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;

    @BeforeEach
    void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");
        driver = new ChromeDriver(options);
        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);
        cartPage = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);
    }

    @Test
    @DisplayName("Deve realizar fluxo completo de compra")
    void deveRealizarCompraCompleta() {
        // Login
        loginPage.abrirPagina();
        loginPage.fazerLogin("standard_user", "secret_sauce");
        assertEquals("Products", inventoryPage.obterTituloPagina());

        // Adicionar produto ao carrinho
        inventoryPage.adicionarProdutoAoCarrinho();

        // Ir para carrinho e verificar
        inventoryPage.irParaCarrinho();
        assertTrue(cartPage.temItens(), "Carrinho deve ter pelo menos 1 item");

        // Finalizar compra
        cartPage.irParaCheckout();
        checkoutPage.preencherDados("Teste", "Usuario", "12345");
        checkoutPage.finalizarCompra();
        assertEquals("Thank you for your order!", checkoutPage.obterMensagemSucesso());
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}