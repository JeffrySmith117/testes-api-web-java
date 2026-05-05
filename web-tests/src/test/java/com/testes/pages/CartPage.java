package com.testes.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.List;

// Representa a página do carrinho do SauceDemo
public class CartPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By botaoCheckout = By.id("checkout");
    private By itensCarrinho = By.className("cart_item");
    private By badgeCarrinho = By.className("shopping_cart_badge");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public int obterQuantidadeItens() {
        // Espera a URL conter 'cart' antes de contar itens
        wait.until(ExpectedConditions.urlContains("cart"));
        List itens = driver.findElements(itensCarrinho);
        return itens.size();
    }

    public void irParaCheckout() {
        wait.until(ExpectedConditions.elementToBeClickable(botaoCheckout));
        driver.findElement(botaoCheckout).click();
    }
}