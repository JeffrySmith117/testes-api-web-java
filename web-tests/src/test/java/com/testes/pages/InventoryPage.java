package com.testes.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.List;

// Representa a página de produtos do SauceDemo
public class InventoryPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By tituloPagina = By.className("title");

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public String obterTituloPagina() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(tituloPagina));
        return driver.findElement(tituloPagina).getText();
    }

    public void adicionarProdutoAoCarrinho() {
        // Espera a página carregar completamente
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("inventory_list")));
        // Pega todos os botões e clica no primeiro via JavaScript
        List<WebElement> botoes = driver.findElements(By.tagName("button"));
        for (WebElement botao : botoes) {
            String texto = botao.getText().toLowerCase();
            if (texto.contains("add to cart") || texto.contains("add")) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", botao);
                break;
            }
        }
    }

    public void irParaCarrinho() {
        driver.get("https://www.saucedemo.com/cart.html");
    }
}