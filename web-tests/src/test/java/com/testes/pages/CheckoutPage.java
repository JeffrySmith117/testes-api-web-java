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