package com.wema.test.app;


import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class MobileTests {

    private AndroidDriver driver;

    @BeforeClass
    public void setUp() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("deviceName", "emulator-5554");
        caps.setCapability("appPackage", "com.example.app");
        caps.setCapability("appActivity", ".MainActivity");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), caps);
    }

    @Test
    public void testLoginWithValidCredentials() {
        WebElement usernameField = driver.findElement(By.id("com.qaapp:id/username"));
        WebElement passwordField = driver.findElement(By.id("com.qaapp:id/password"));
        WebElement loginButton = driver.findElement(By.id("com.qaapp:id/loginButton"));

        usernameField.sendKeys("test@user1.com");
        passwordField.sendKeys("Password1.");
        loginButton.click();

        WebElement successMessage = driver.findElement(By.id("com.qaapp:id/successMessage"));
        Assert.assertEquals(successMessage.getText(), "Login successful");
    }

    @Test
    public void testLoginWithInvalidCredentials() {
        WebElement usernameField = driver.findElement(By.id("com.qaapp:id/username"));
        WebElement passwordField = driver.findElement(By.id("com.qaapp:id/password"));
        WebElement loginButton = driver.findElement(By.id("com.qaapp:id/loginButton"));

        usernameField.sendKeys("invalid@user.com");
        passwordField.sendKeys("wrongpassword");
        loginButton.click();

        WebElement errorMessage = driver.findElement(By.id("com.qaapp:id/errorMessage"));
        Assert.assertEquals(errorMessage.getText(), "Invalid credentials");
    }

    @Test
    public void testForgotPassword() {
        WebElement forgotPasswordLink = driver.findElement(By.id("com.qaapp:id/forgotPassword"));
        forgotPasswordLink.click();

        WebElement emailField = driver.findElement(By.id("com.qaapp:id/email"));
        WebElement submitButton = driver.findElement(By.id("com.qaapp:id/submitButton"));

        emailField.sendKeys("test@user1.com");
        submitButton.click();

        WebElement confirmationMessage = driver.findElement(By.id("com.qaapp:id/confirmationMessage"));
        Assert.assertEquals(confirmationMessage.getText(), "Password reset email sent");
    }

    @Test
    public void testLogoutFunctionality() {
        testLoginWithValidCredentials(); // Reuse the login test

        WebElement logoutButton = driver.findElement(By.id("com.qaapp:id/logoutButton"));
        logoutButton.click();

        WebElement loginButton = driver.findElement(By.id("com.qaapp:id/loginButton"));
        Assert.assertTrue(loginButton.isDisplayed());
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}