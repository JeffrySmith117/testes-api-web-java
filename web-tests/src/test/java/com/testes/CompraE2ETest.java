package com.testes;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CompraE2ETest {

    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    @BeforeEach
    void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-gpu");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        js = (JavascriptExecutor) driver;
    }

    private void preencherCampo(String id, String valor) {
        WebElement campo = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));
        campo.clear();
        campo.sendKeys(valor);
    }

    @Test
    @DisplayName("Deve realizar fluxo completo de compra")
    void deveRealizarCompraCompleta() {
        // 1. Login
        driver.get("https://www.saucedemo.com/");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));
        preencherCampo("user-name", "standard_user");
        preencherCampo("password", "secret_sauce");
        driver.findElement(By.id("login-button")).click();
        wait.until(ExpectedConditions.urlContains("inventory"));
        assertTrue(driver.getCurrentUrl().contains("inventory"));

        // 2. Adiciona produto
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("inventory_list")));
        WebElement botaoAdd = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[contains(@class,'btn_inventory')]")));
        js.executeScript("arguments[0].click();", botaoAdd);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("shopping_cart_badge")));

        // 3. Vai para carrinho
        js.executeScript("arguments[0].click();",
            driver.findElement(By.className("shopping_cart_link")));
        wait.until(ExpectedConditions.urlContains("cart"));
        assertFalse(driver.findElements(By.className("cart_item")).isEmpty());

        // 4. Checkout
        js.executeScript("arguments[0].click();",
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("checkout"))));
        wait.until(ExpectedConditions.urlContains("checkout-step-one"));

        // 5. Preenche dados usando sendKeys
        preencherCampo("first-name", "Teste");
        preencherCampo("last-name", "Usuario");
        preencherCampo("postal-code", "12345");
        driver.findElement(By.id("continue")).click();
        wait.until(ExpectedConditions.urlContains("checkout-step-two"));

        // 6. Finaliza
        js.executeScript("arguments[0].click();",
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("finish"))));

        // 7. Verifica sucesso
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("complete-header")));
        assertEquals("Thank you for your order!",
            driver.findElement(By.className("complete-header")).getText());
    }

    @AfterEach
    void tearDown() {
        if (driver != null) driver.quit();
    }
}