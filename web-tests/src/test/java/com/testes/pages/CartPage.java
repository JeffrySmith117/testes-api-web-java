package com.testes.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

// Representa a página do carrinho do SauceDemo
public class CartPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By itensCarrinho = By.className("cart_item");
    private By botaoCheckout = By.id("checkout");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public boolean temItens() {
        return !driver.findElements(itensCarrinho).isEmpty();
    }

    public void irParaCheckout() {
        wait.until(ExpectedConditions.elementToBeClickable(botaoCheckout));
        WebElement btn = driver.findElement(botaoCheckout);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
        wait.until(ExpectedConditions.urlContains("checkout"));
    }
}