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

    private By botaoAdicionarProduto = By.cssSelector("[data-test='add-to-cart-sauce-labs-backpack']");
    private By tituloPagina = By.className("title");
    private By badgeCarrinho = By.cssSelector(".shopping_cart_badge");

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public String obterTituloPagina() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(tituloPagina));
        return driver.findElement(tituloPagina).getText();
    }

    public void adicionarProdutoAoCarrinho() {
        wait.until(ExpectedConditions.elementToBeClickable(botaoAdicionarProduto));
        driver.findElement(botaoAdicionarProduto).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(badgeCarrinho));
    }

    // Navega diretamente para o carrinho via URL
    public void irParaCarrinho() {
        driver.get("https://www.saucedemo.com/cart.html");
        wait.until(ExpectedConditions.urlContains("cart"));
    }
}