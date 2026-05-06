package com.testes;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @Test
    @DisplayName("Deve realizar fluxo completo de compra")
    void deveRealizarCompraCompleta() {
        // 1. Login
        driver.get("https://www.saucedemo.com/");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        wait.until(ExpectedConditions.urlContains("inventory"));
        assertTrue(driver.getCurrentUrl().contains("inventory"));

        // 2. Adiciona produto clicando no botão
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("inventory_list")));
        WebElement botao = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[contains(@class,'btn_inventory')]")));
        js.executeScript("arguments[0].scrollIntoView(true);", botao);
        js.executeScript("arguments[0].click();", botao);

        // 3. Verifica badge do carrinho
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("shopping_cart_badge")));
        String badge = driver.findElement(By.className("shopping_cart_badge")).getText();
        assertEquals("1", badge, "Badge deve mostrar 1 item");

        // 4. Vai para carrinho clicando no link
        js.executeScript("window.location.href='https://www.saucedemo.com/cart.html'");
        wait.until(ExpectedConditions.urlContains("cart"));

        // 5. Verifica item no carrinho
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("cart_item")));
        assertTrue(!driver.findElements(By.className("cart_item")).isEmpty());

        // 6. Checkout
        wait.until(ExpectedConditions.elementToBeClickable(By.id("checkout")));
        driver.findElement(By.id("checkout")).click();

        // 7. Preenche dados
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("first-name")));
        driver.findElement(By.id("first-name")).sendKeys("Teste");
        driver.findElement(By.id("last-name")).sendKeys("Usuario");
        driver.findElement(By.id("postal-code")).sendKeys("12345");
        driver.findElement(By.id("continue")).click();

        // 8. Finaliza
        wait.until(ExpectedConditions.elementToBeClickable(By.id("finish")));
        driver.findElement(By.id("finish")).click();

        // 9. Verifica sucesso
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("complete-header")));
        assertEquals("Thank you for your order!",
            driver.findElement(By.className("complete-header")).getText());
    }

    @AfterEach
    void tearDown() {
        if (driver != null) driver.quit();
    }
}