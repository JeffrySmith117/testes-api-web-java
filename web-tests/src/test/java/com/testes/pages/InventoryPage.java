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

    // Pega o primeiro botão "Add to cart" da página
    private By botaoAdicionarProduto = By.cssSelector(".btn_primary.btn_inventory");
    private By iconeCarrinho = By.cssSelector("a.shopping_cart_link");
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

    public void irParaCarrinho() {
        wait.until(ExpectedConditions.elementToBeClickable(iconeCarrinho));
        driver.findElement(iconeCarrinho).click();
        wait.until(ExpectedConditions.urlContains("cart"));
    }
}