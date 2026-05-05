package com.testes.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

// Representa a página do carrinho do SauceDemo
public class CartPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By itensCarrinho = By.className("cart_item");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public int obterQuantidadeItens() {
        wait.until(ExpectedConditions.urlContains("cart"));
        return driver.findElements(itensCarrinho).size();
    }

    // Navega diretamente para o checkout via URL
    public void irParaCheckout() {
        driver.get("https://www.saucedemo.com/checkout-step-one.html");
        wait.until(ExpectedConditions.urlContains("checkout-step-one"));
    }
}