package utam.examples.salesforce.web;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utam.core.element.BasicElement;
import utam.tests.pageobjects.ItemsAppVentas;
import utam.utils.salesforce.TestEnvironment;
import java.util.ArrayList;
import java.util.List;

public class ItemsAppVentasTest extends SalesforceWebTestBase {
    private final TestEnvironment testEnvironment = getTestEnvironment("sandbox");

    @BeforeTest
    public void setup() {
        setupChrome();
        login(testEnvironment, "home");
    }
    @Test
    public void validateCantItemsAppSales() {
        ItemsAppVentas itemsAppVentas = from(ItemsAppVentas.class);

        Assert.assertEquals(itemsAppVentas.getItemsVisibles().size(),6);
        List<BasicElement> listItemsVisibles = itemsAppVentas.getItemsVisibles();
        List<BasicElement> listItemsNoVisibles = itemsAppVentas.getItemsNoVisibles();

        List<String> itemsName = new ArrayList<String>();
        itemsName.add("Inicio");
        itemsName.add("Candidatos");
        itemsName.add("Cuentas");
        itemsName.add("Contactos");
        itemsName.add("Generación de Demandas");
        itemsName.add("Bodegas");
        itemsName.add("Zonas asignadas");
        itemsName.add("Productos Oferta Valor");
        itemsName.add("Bodegas-Zonas");
        itemsName.add("Calendario");
        itemsName.add("Rutas");

        List<String> listItemsVisiblesName = new ArrayList<>();
        List<String> listItemsNoVisiblesName = new ArrayList<>();

        for (BasicElement s: listItemsVisibles){
            listItemsVisiblesName.add(s.getAttribute("title"));
        }
        for (BasicElement s: listItemsNoVisibles){
            listItemsNoVisiblesName.add(s.getAttribute("title"));
        }

        listItemsVisiblesName.addAll(listItemsNoVisiblesName);
        Assert.assertEquals(listItemsVisiblesName,itemsName);

    }

    @AfterTest
    public final void tearDown() {
        quitDriver();
    }
}
