package utam.examples.salesforce.web;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utam.tests.pageobjects.AppVentas;
import utam.utils.salesforce.TestEnvironment;

public class AppVentasTest extends SalesforceWebTestBase {
    private final TestEnvironment testEnvironment = getTestEnvironment("sandbox");

    @BeforeTest
    public void setup() {
        setupChrome();
        login(testEnvironment, "home");
    }
    @Test
    public void validateNameAppSales() {
        AppVentas appVentas = from(AppVentas.class);
        Assert.assertEquals(appVentas.getVentas().getText(),"Ventas");
    }

    @AfterTest
    public final void tearDown() {
        quitDriver();
    }
}
