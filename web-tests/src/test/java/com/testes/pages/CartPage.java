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

    private By botaoCheckout = By.id("checkout");
    private By itensCarrinho = By.className("cart_item");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public int obterQuantidadeItens() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(itensCarrinho));
        return driver.findElements(itensCarrinho).size();
    }

    public void irParaCheckout() {
        wait.until(ExpectedConditions.elementToBeClickable(botaoCheckout));
        driver.findElement(botaoCheckout).click();
    }
}