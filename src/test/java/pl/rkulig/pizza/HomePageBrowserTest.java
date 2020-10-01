package pl.rkulig.pizza;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HomePageBrowserTest {

    @LocalServerPort
    private int port;

    private static HtmlUnitDriver browser;

    @BeforeClass
    public static void setup (){
        browser = new HtmlUnitDriver();

        browser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterClass
    public static void tearDown(){
        browser.quit();
    }

    @Test
    public void testHomePage(){
        String homePage = "http://localhost:" + port;
        browser.get(homePage);

        String titleText = browser.getTitle();
        Assert.assertEquals("PIZZA", titleText);

        String h1Text = browser.findElementByTagName("h1").getText();
        Assert.assertEquals("Witaj w świecie Pizzy!", h1Text);

        String imgSrc = browser.findElementByTagName("img").getAttribute("src");
        Assert.assertEquals(homePage + "/images/pizza.png", imgSrc);
    }


}
