package utam.examples.salesforce.web;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utam.action.pageobjects.ActionsRibbon;
import utam.aura.pageobjects.VirtualDataTable;
import utam.core.element.BasicElement;
import utam.force.pageobjects.ListViewManagerGrid;
import utam.force.pageobjects.ListViewManagerHeader;
import utam.force.pageobjects.ModalContainer;
import utam.force.pageobjects.ObjectHome;
import utam.global.pageobjects.ConsoleObjectHome;
import utam.global.pageobjects.impl.RecordHomeTemplateDesktopImpl;
import utam.tests.pageobjects.LeadsList;
import utam.tests.pageobjects.ModalEnviarParaAprobacion;
import utam.utils.salesforce.RecordType;
import utam.utils.salesforce.TestEnvironment;

import java.util.ArrayList;
import java.util.List;


public class StagesLeadTest extends SalesforceWebTestBase {
    private final TestEnvironment testEnvironment = getTestEnvironment("sandbox");

    private void openRecordModal(RecordType recordType) {

        log("Navigate to an Object Home for " + recordType.name());
        getDriver().get(recordType.getObjectHomeUrl(testEnvironment.getRedirectUrl()));

        log("Load Lead Object Home page");
        ConsoleObjectHome objectHome = from(ConsoleObjectHome.class);
        ListViewManagerHeader listViewHeader = objectHome.getListView().getHeader();

    }

    @BeforeTest
    public void setup() {
        setupChrome();
        login(testEnvironment, "home");
    }
    @Test
    public void submitForApproval() {
        openRecordModal(RecordType.Lead);
        LeadsList leadsList = from(LeadsList.class);
        leadsList.waitForRecords();

        List<BasicElement> rowCells; // campos de una fila
        List<String> nameLeads = new ArrayList<>();

        for (int i = 1; i < 15; i++) {
            rowCells = leadsList.getRowCells(i);//obtener los campos de una fila
            leadsList.getTableRows().get(i-1).moveTo();

            if (rowCells.get(7).getText().equals("Prospección")) {
                // agregar los links de los registros a una lista
                nameLeads.add(leadsList.getRowHeader(i).getAttribute("href"));
            }
        }

        for (String link : nameLeads) {

            getDriver().get(link);

            ActionsRibbon actionsMenu = loader.load(RecordHomeTemplateDesktopImpl.class).getHighlights().getActions();
            actionsMenu.getDropdownButton().clickButton();

            //buttonMenu.getMenuItemByText("Convertir").getLink().click();
            //loader.load(ActionsRibbonParent.class).getActionsRibbon().getButton();
            actionsMenu.getActionRendererWithTitle("Enviar para aprobación").getRibbonMenuItem().clickLinkItem();
            ModalContainer modal = loader.load(ModalContainer.class);
            modal.getHeaderText();
            ModalEnviarParaAprobacion modalEnviarParaAprobacion = from(ModalEnviarParaAprobacion.class);
            modalEnviarParaAprobacion.setComentario("comentario, prueba 2");
            modal.getModalFooter().getModalActionButton("Enviar").click();

        }
    }

    @Test
    public void convertLead() {
        openRecordModal(RecordType.Lead);
        LeadsList leadsList = from(LeadsList.class);
        leadsList.waitForRecords();

        List<BasicElement> rowCells; // campos de una fila
        List<String> nameLeads = new ArrayList<>();

        for (int i = 1; i < 18; i++) {
            rowCells = leadsList.getRowCells(i);//obtener los campos de una fila
            leadsList.getTableRows().get(i-1).moveTo();

            if (rowCells.get(7).getText().equals("Aprobado")) {
                // agregar los links de los registros a una lista
                nameLeads.add(leadsList.getRowHeader(i).getAttribute("href"));
            }
        }

        for (String link : nameLeads) {

            getDriver().get(link);

            ActionsRibbon actionsMenu = loader.load(RecordHomeTemplateDesktopImpl.class).getHighlights().getActions();
            actionsMenu.getDropdownButton().clickButton();

            //buttonMenu.getMenuItemByText("Convertir").getLink().click();
            //loader.load(ActionsRibbonParent.class).getActionsRibbon().getButton();
            actionsMenu.getActionRendererWithTitle("Convertir").getRibbonMenuItem().clickLinkItem();
            ModalContainer modal = loader.load(ModalContainer.class);
            modal.getHeaderText();
            loader.load(ModalContainer.class)
                    .getModalFooter()
                    .getModalActionButton("Convertir")
                    .click();


        }

    }


    @AfterTest
    public final void tearDown() {
        //quitDriver();
    }
}

