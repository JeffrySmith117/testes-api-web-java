package com.testes;

import com.testes.pages.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

        // 2. Verifica login
        wait.until(ExpectedConditions.urlContains("inventory"));
        assertTrue(driver.getCurrentUrl().contains("inventory"));

        // 3. Adiciona produto
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("inventory_list")));
        List<WebElement> botoes = driver.findElements(By.tagName("button"));
        for (WebElement b : botoes) {
            if (b.getText().toLowerCase().contains("add")) {
                b.click();
                break;
            }
        }

        // 4. Vai para carrinho via URL
        driver.get("https://www.saucedemo.com/cart.html");
        wait.until(ExpectedConditions.urlContains("cart"));

        // 5. Verifica item no carrinho
        List<WebElement> itens = driver.findElements(By.className("cart_item"));
        assertTrue(itens.size() > 0, "Carrinho deve ter itens");

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
        String mensagem = driver.findElement(By.className("complete-header")).getText();
        assertEquals("Thank you for your order!", mensagem);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) driver.quit();
    }
}