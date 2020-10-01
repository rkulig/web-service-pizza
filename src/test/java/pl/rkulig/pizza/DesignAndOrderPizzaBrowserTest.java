package pl.rkulig.pizza;



import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class DesignAndOrderPizzaBrowserTest {

    private static HtmlUnitDriver browser;

    @LocalServerPort
    private int port;

    @Autowired
    TestRestTemplate rest;

    @BeforeClass
    public static void setup() {
        browser = new HtmlUnitDriver();
        browser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterClass
    public static void closeBrowser() {
        browser.quit();
    }

    @Test
    public void testDesignAPizzaPage_HappyPath() throws Exception {
        browser.get(homePageUrl());
        clickDesignAPizza();
        assertDesignPageElements();
        buildAndSubmitAPizza("Basic Pizza", "CIEN", "KURC", "PAPR", "KETC");
        clickBuidAnotherPizza();
        buildAndSubmitAPizza("Another pizza", "GRUB", "SALA", "PIEC", "CZOS");
        fillInAndSubmitOrderForm();
        assertEquals(homePageUrl(), browser.getCurrentUrl());
    }

    @Test
    public void testDesignAPizzaPage_EmptyOrderInfo() throws Exception {
        browser.get(homePageUrl());
        clickDesignAPizza();
        assertDesignPageElements();
        buildAndSubmitAPizza("Standardowa pizza", "CIEN", "KURC", "PAPR", "KETC");
        submitEmptyOrderForm();
        fillInAndSubmitOrderForm();
        assertEquals(homePageUrl(), browser.getCurrentUrl());
    }

    public void testDesignAPizza_InvalidOrderInfo() throws Exception {
        browser.get(homePageUrl());
        clickDesignAPizza();
        assertDesignPageElements();
        buildAndSubmitAPizza("Basic Pizza", "CIEN", "KURC", "PAPR", "KETC");
        submitInvalidOrderForm();
        assertEquals(homePageUrl(), browser.getCurrentUrl());
    }

    // Browser test action methods

    private void buildAndSubmitAPizza(String pizzaName, String... ingredients) {
        assertDesignPageElements();

        for (String ingredient : ingredients) {
            browser.findElementByCssSelector("input[value='" + ingredient + "']").click();
        }
        browser.findElementByCssSelector("input#pizzaName").sendKeys(pizzaName);
        browser.findElementByCssSelector("form").submit();
    }

    private void assertDesignPageElements() {
        assertEquals(designPageUrl(), browser.getCurrentUrl());
        List<WebElement> ingredientGroups = browser.findElementsByClassName("ingredient-group");
        assertEquals(4, ingredientGroups.size());

        WebElement doughGroup = browser.findElementByCssSelector("div.ingredient-group#doughs");
        List<WebElement> doughs = doughGroup.findElements(By.tagName("div"));
        assertEquals(2, doughs.size());
        assertIngredient(doughGroup, 0, "CIEN", "cienkie");
        assertIngredient(doughGroup, 1, "GRUB", "grube");

        WebElement proteinGroup = browser.findElementByCssSelector("div.ingredient-group#proteins");
        List<WebElement> proteins = proteinGroup.findElements(By.tagName("div"));
        assertEquals(2, proteins.size());
        assertIngredient(proteinGroup, 0, "KURC", "kurczak");
        assertIngredient(proteinGroup, 1, "SALA", "salami");

        WebElement veggieGroup = browser.findElementByCssSelector("div.ingredient-group#veggies");
        List<WebElement> veggies = proteinGroup.findElements(By.tagName("div"));
        assertEquals(2, veggies.size());
        assertIngredient(veggieGroup, 0, "PAPR", "papryka");
        assertIngredient(veggieGroup, 1, "PIEC", "pieczarki");

        WebElement sauceGroup = browser.findElementByCssSelector("div.ingredient-group#sauces");
        List<WebElement> sauces = proteinGroup.findElements(By.tagName("div"));
        assertEquals(2, sauces.size());
        assertIngredient(sauceGroup, 0, "CZOS", "czosnkowy");
        assertIngredient(sauceGroup, 1, "KETC", "ketchup");
    }

    private void fillInAndSubmitOrderForm(){
        assertTrue(browser.getCurrentUrl().startsWith(orderDetailsPageUrl()));
        fillField("input#name","żarełko");
        fillField("input#street","Mickiewicza 16b");
        fillField("input#city","Warszawa");
        fillField("input#zip","00-110");
        fillField("input#ccNumber","4056217749709544");
        fillField("input#ccExpiration","10/22");
        fillField("input#ccCVV","123");
        browser.findElementByCssSelector("form").submit();
    }

    private void submitEmptyOrderForm(){
        assertEquals(currentOrderDetailsPageUrl(),browser.getCurrentUrl());
        browser.findElementByCssSelector("form").submit();

        assertEquals(orderDetailsPageUrl(), browser.getCurrentUrl());

        List<String> validationErrors = getValidationErrorTexts();
        assertEquals(8, validationErrors.size());
        assertTrue(validationErrors.contains("Usuń wymienione problemy i spróbuj ponownie"));
        assertTrue(validationErrors.contains("Podanie imienia i nazwiska jest obowiązkowe"));
        assertTrue(validationErrors.contains("Podanie ulicy jest obowiązkowe"));
        assertTrue(validationErrors.contains("Podanie miejscowości jest obowiązkowe"));
        assertTrue(validationErrors.contains("Podanie kodu pocztowego jest obowiązkowe"));
        assertTrue(validationErrors.contains("To nie jest prawidłowy numer karty kredytowej"));
        assertTrue(validationErrors.contains("Data musi być w formacie MM/RR"));
        assertTrue(validationErrors.contains("Nieprawidłowy kod ccCVV"));
    }

    private List<String> getValidationErrorTexts(){
        List<WebElement> validationErrorElements = browser.findElementsByClassName("validationError");
        List<String> validationErrors = validationErrorElements.stream()
                .map(el -> el.getText())
                .collect(Collectors.toList());
        return validationErrors;
    }

    private void submitInvalidOrderForm() {
        assertTrue(browser.getCurrentUrl().startsWith(orderDetailsPageUrl()));
        fillField("input#name", "I");
        fillField("input#street", "1");
        fillField("input#city", "F");
        fillField("input#zip", "8");
        fillField("input#ccNumber", "1234432112344322");
        fillField("input#ccExpiration", "14/91");
        fillField("input#ccCVV", "1234");
        browser.findElementByCssSelector("form").submit();

        assertEquals(orderDetailsPageUrl(), browser.getCurrentUrl());

        List<String> validationErrors = getValidationErrorTexts();
        assertEquals(4, validationErrors.size());
        assertTrue(validationErrors.contains("Proszę popraw wskazany problem i wyślij jeszcze raz"));
        assertTrue(validationErrors.contains("To nie jest prawidłowy numer karty kredytowej"));
        assertTrue(validationErrors.contains("Data musi być w formacie MM/RR"));
        assertTrue(validationErrors.contains("Nieprawidłowy kod ccCVV"));


    }

    private void fillField(String fieldName, String value) {
        WebElement field = browser.findElementByCssSelector(fieldName);
        field.clear();
        field.sendKeys(value);
    }

    private void assertIngredient(WebElement ingredientGroup, int ingredientIdx, String id, String name) {
        List<WebElement> proteins = ingredientGroup.findElements(By.tagName("div"));
        WebElement ingredient = proteins.get(ingredientIdx);
        assertEquals(id, ingredient.findElement(By.tagName("input")).getAttribute("value"));
        assertEquals(name, ingredient.findElement(By.tagName("span")).getText());
    }

    private void clickDesignAPizza() {
        assertEquals(homePageUrl(), browser.getCurrentUrl());
        browser.findElementByCssSelector("a[id='design']").click();
    }

private void clickBuidAnotherPizza(){
        assertTrue(browser.getCurrentUrl().startsWith(orderDetailsPageUrl()));
        browser.findElementByCssSelector("a[id='another']").click();
}
    // URL helper methods
    private String designPageUrl() {
        return homePageUrl() + "design";
    }

    private String homePageUrl() {
        return "http://localhost:" + port + "/";
    }

    private String orderDetailsPageUrl() {
        return homePageUrl() + "orders";
    }

    private String currentOrderDetailsPageUrl() {
        return homePageUrl() + "orders/current";
    }
}
