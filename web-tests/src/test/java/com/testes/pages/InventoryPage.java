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
    private JavascriptExecutor js;

    private By tituloPagina = By.className("title");
    private By listaInventario = By.className("inventory_list");
    private By iconeCarrinho = By.id("shopping_cart_container");

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.js = (JavascriptExecutor) driver;
    }

    public String obterTituloPagina() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(tituloPagina));
        return driver.findElement(tituloPagina).getText();
    }

    public void adicionarProdutoAoCarrinho() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(listaInventario));
        List<WebElement> botoes = driver.findElements(By.tagName("button"));
        for (WebElement botao : botoes) {
            String texto = botao.getText().toLowerCase();
            if (texto.contains("add")) {
                js.executeScript("arguments[0].click();", botao);
                // Espera o badge aparecer
                wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("#shopping_cart_container .shopping_cart_badge")));
                break;
            }
        }
    }

    public void irParaCarrinho() {
        WebElement carrinho = driver.findElement(iconeCarrinho);
        js.executeScript("arguments[0].click();", carrinho);
        wait.until(ExpectedConditions.urlContains("cart"));
    }
}