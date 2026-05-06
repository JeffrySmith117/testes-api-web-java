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
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        js = (JavascriptExecutor) driver;
    }

    // Preenche campo React corretamente disparando eventos
    private void setReactValue(String id, String value) {
        js.executeScript(
            "var el = document.getElementById(arguments[0]);" +
            "var nativeInputValueSetter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype, 'value').set;" +
            "nativeInputValueSetter.call(el, arguments[1]);" +
            "el.dispatchEvent(new Event('input', { bubbles: true }));" +
            "el.dispatchEvent(new Event('change', { bubbles: true }));",
            id, value);
    }

    @Test
    @DisplayName("Fluxo completo: login, carrinho e compra")
    void deveRealizarCompraCompleta() {
        // Login
        driver.get("https://www.saucedemo.com");
        wait.until(ExpectedConditions.elementToBeClickable(By.id("user-name"))).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        js.executeScript("document.getElementById('login-button').click()");
        wait.until(ExpectedConditions.urlContains("inventory"));

        // Adiciona produto
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("inventory_list")));
        js.executeScript("document.getElementById('add-to-cart-sauce-labs-backpack').click()");
        wait.until(ExpectedConditions.textToBe(By.cssSelector(".shopping_cart_badge"), "1"));

        // Carrinho
        js.executeScript("document.querySelector('.shopping_cart_link').click()");
        wait.until(ExpectedConditions.urlContains("cart"));
        assertFalse(driver.findElements(By.className("cart_item")).isEmpty());

        // Checkout step 1
        js.executeScript("document.getElementById('checkout').click()");
        wait.until(ExpectedConditions.urlContains("checkout-step-one"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("first-name")));

        // Preenche campos via React
        setReactValue("first-name", "Teste");
        setReactValue("last-name", "Usuario");
        setReactValue("postal-code", "12345");

        js.executeScript("document.getElementById('continue').click()");
        wait.until(ExpectedConditions.urlContains("checkout-step-two"));

        // Finaliza
        js.executeScript("document.getElementById('finish').click()");

        // Confirmação
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("complete-header")));
        assertEquals("Thank you for your order!",
            driver.findElement(By.className("complete-header")).getText());
    }

    @AfterEach
    void tearDown() {
        if (driver != null) driver.quit();
    }
}