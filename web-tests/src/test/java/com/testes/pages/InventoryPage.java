package com.testes.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

// Representa a página de produtos do SauceDemo
public class InventoryPage {

    private WebDriver driver;

    private By botaoAdicionarPrimeiroProduto = By.id("add-to-cart-sauce-labs-backpack");
    private By iconeCarrinho = By.className("shopping_cart_link");

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
    }

    public String obterTituloPagina() {
        return driver.findElement(By.className("title")).getText();
    }

    public void adicionarProdutoAoCarrinho() {
        driver.findElement(botaoAdicionarPrimeiroProduto).click();
    }

    public void irParaCarrinho() {
        driver.findElement(iconeCarrinho).click();
    }
}