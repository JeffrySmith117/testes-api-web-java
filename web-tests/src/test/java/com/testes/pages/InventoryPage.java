package com.testes.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

// Representa a página de produtos do SauceDemo
public class InventoryPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By botaoAdicionarPrimeiroProduto = By.id("add-to-cart-sauce-labs-backpack");
    private By iconeCarrinho = By.className("shopping_cart_link");
    private By tituloPagina = By.className("title");

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String obterTituloPagina() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(tituloPagina));
        return driver.findElement(tituloPagina).getText();
    }

    public void adicionarProdutoAoCarrinho() {
        wait.until(ExpectedConditions.elementToBeClickable(botaoAdicionarPrimeiroProduto));
        driver.findElement(botaoAdicionarPrimeiroProduto).click();
    }

    public void irParaCarrinho() {
        wait.until(ExpectedConditions.elementToBeClickable(iconeCarrinho));
        driver.findElement(iconeCarrinho).click();
    }
}