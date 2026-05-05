package com.testes.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

// Representa a página de login do SauceDemo
public class LoginPage {

    private WebDriver driver;

    private By campoUsuario = By.id("user-name");
    private By campoSenha = By.id("password");
    private By botaoLogin = By.id("login-button");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void abrirPagina() {
        driver.get("https://www.saucedemo.com/");
    }

    public void fazerLogin(String usuario, String senha) {
        driver.findElement(campoUsuario).sendKeys(usuario);
        driver.findElement(ca