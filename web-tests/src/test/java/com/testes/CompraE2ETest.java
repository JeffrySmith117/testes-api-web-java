package com.testes;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

    @Test
    @DisplayName("Deve realizar fluxo completo de compra")
    void deveRealizarCompraCompleta() {
        // 1. Login
        driver.get("https://www.saucedemo.com/");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        js.executeScript("arguments[0].click();", driver.findElement(By.id("login-button")));
        wait.until(ExpectedConditions.urlContains("inventory"));

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

        // 4. Clica em checkout via JS
        WebElement btnCheckout = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.id("checkout")));
        js.executeScript("arguments[0].click();", btnCheckout);
        wait.until(ExpectedConditions.urlContains("checkout-step-one"));

        // 5. Preenche dados via JS
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("first-name")));
        js.executeScript("document.getElementById('first-name').value='Teste'");
        js.executeScript("document.getElementById('last-name').value='Usuario'");
        js.executeScript("document.getElementById('postal-code').value='12345'");
        js.executeScript("arguments[0].click();", driver.findElement(By.id("continue")));
        wait.until(ExpectedConditions.urlContains("checkout-step-two"));

        // 6. Finaliza
        WebElement btnFinish = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.id("finish")));
        js.executeScript("arguments[0].click();", btnFinish);

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