package com.testes.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

// Representa a página de checkout do SauceDemo
public class CheckoutPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By campoNome = By.id("first-name");
    private By campoSobrenome = By.id("last-name");
    private By campoCep = By.id("postal-code");
    private By botaoContinuar = By.id("continue");
    private By botaoFinalizar = By.id("finish");
    private By mensagemSucesso = By.className("complete-header");

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void preencherDados(String nome, String sobrenome, String cep) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(campoNome));
        driver.findElement(campoNome).sendKeys(nome);
        driver.findElement(campoSobrenome).sendKeys(sobrenome);
        driver.findElement(campoCep).sendKeys(cep);
        driver.findElement(botaoContinuar).click();
    }

    public void finalizarCompra() {
        wait.until(ExpectedConditions.elementToBeClickable(botaoFinalizar));
        driver.findElement(botaoFinalizar).click();
    }

    public String obterMensagemSucesso() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(mensagemSucesso));
        return driver.findElement(mensagemSucesso).getText();
    }
}