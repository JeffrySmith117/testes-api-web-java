package com.testes.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

// Representa a página do carrinho do SauceDemo
public class CartPage {

    private WebDriver driver;

    private By botaoCheckout = By.id("checkout");

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public int obterQuantidadeItens() {
        return driver.findElements(By.className("cart_item")).size();
    }

    public void irParaCheckout() {
        driver.findElement(botaoCheckout).click();
    }
}