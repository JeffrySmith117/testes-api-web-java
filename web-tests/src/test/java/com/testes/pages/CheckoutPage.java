package com.testes.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

// Representa a página de checkout do SauceDemo
public class CheckoutPage {

    private WebDriver driver;

    private By campoNome = By.id("first-name");
    private By campoSobrenome = By.id("last-name");
    private By campoCep = By.id("postal-code");
    private By botaoContinuar = By.id("continue");
    private By botaoFinalizar = By.id("finish");
    private By mensagemSucesso = By.className("complete-header");

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
    }

    public void preencherDados(String nome, String sobrenome, String cep) {
        driver.findElement(campoNome).sendKeys(nome);
        driver.findElement(campoSobrenome).sendKeys(sobrenome);
        driver.findElement(campoCep).sendKeys(cep);
        driver.findElement(botaoContinuar).click();
    }

    public void finalizarCompra() {
        driver.findElement(botaoFinalizar).click();
    }

    public String obterMensagemSucesso() {
        return driver.findElement(mensagemSucesso).getText();
    }
}