package com.testes.pages;

import org.openqa.selenium.By;
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
    private By botoesAdicionarProduto = By.className("btn_inventory");

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public String obterTituloPagina() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(tituloPagina));
        return driver.findElement(tituloPagina).getText();
    }

    public void adicionarProdutoAoCarrinho() {
        // Espera os botões aparecerem e clica no primeiro
        wait.until(ExpectedConditions.visibilityOfElementLocated(botoesAdicionarProduto));
        List<WebElement> botoes = driver.findElements(botoesAdicionarProduto);
        botoes.get(0).click();
    }

    public void irParaCarrinho() {
        driver.get("https://www.saucedemo.com/cart.html");
    }
}