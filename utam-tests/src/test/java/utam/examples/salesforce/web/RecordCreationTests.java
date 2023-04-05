/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: MIT
 * For full license text, see the LICENSE file in the repo root
 * or https://opensource.org/licenses/MIT
 */
package utam.examples.salesforce.web;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utam.aura.pageobjects.ContainerManager;
import utam.core.element.Element;
import utam.core.framework.base.PageObject;
import utam.core.framework.base.RootPageObject;
import utam.core.selenium.element.LocatorBy;
import utam.force.pageobjects.Component;
import utam.force.pageobjects.LightningModalBody;
import utam.force.pageobjects.ListViewManagerHeader;
import utam.force.pageobjects.impl.ComponentImpl;
import utam.global.pageobjects.ConsoleObjectHome;
import utam.global.pageobjects.RecordActionWrapper;
import utam.global.pageobjects.RecordHomeFlexipage2;
import utam.lightning.pageobjects.AccordionSection;
import utam.lightning.pageobjects.BaseCombobox;
import utam.lightning.pageobjects.Input;
import utam.lightning.pageobjects.impl.AccordionImpl;
import utam.lightning.pageobjects.impl.AccordionSectionImpl;
import utam.records.pageobjects.BaseRecordForm;
import utam.records.pageobjects.LwcRecordLayout;
import utam.records.pageobjects.RecordLayoutItem;

import utam.tests.pageobjects.ForceAloha;
import utam.tests.pageobjects.LeadsCreate;
import utam.tests.pageobjects.LeadsInut;
import utam.tests.pageobjects.UtamChild;
import utam.tests.pageobjects.impl.LeadsCreateImpl;
import utam.utils.salesforce.RecordType;
import utam.utils.salesforce.TestEnvironment;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

/**
 * IMPORTANT: Page objects and tests for Salesforce UI are compatible with the application version
 * mentioned in published page objects Test environment is private SF sandbox, not available for
 * external users and has DEFAULT org setup
 *
 * @author Salesforce
 * @since Dec 2021
 */
public class RecordCreationTests extends SalesforceWebTestBase {

  private final TestEnvironment testEnvironment = getTestEnvironment("sandbox");

  @BeforeTest
  public void setup() {
    setupChrome();
    login(testEnvironment, "home");
  }

  /**
   * navigate to object home via URL and click New
   *
   * @param recordType record type affects navigation url
   */
   private void openRecordModal(RecordType recordType) {

    log("Navigate to an Object Home for " + recordType.name());
    getDriver().get(recordType.getObjectHomeUrl(testEnvironment.getRedirectUrl()));

    log("Load Lead Object Home page");
    ConsoleObjectHome objectHome = from(ConsoleObjectHome.class);
    ListViewManagerHeader listViewHeader = objectHome.getListView().getHeader();

    log("List view header: click button 'New'");
    listViewHeader.waitForAction("Nuevo").click();

  }

  @Test
  public void testAccountRecordCreation() {

      openRecordModal(RecordType.Lead);
      LeadsCreate leadsCreate = from(LeadsCreate.class);
     ForceAloha ventasFrame =getDomDocument().enterFrameAndLoad(leadsCreate.getPreviewFrame(),ForceAloha.class);

     ventasFrame.waitForPageNewLead();










      //List<LeadsInut> leadsCreateInputName =leadsCreate.getSlotWithUnknownContent("flowruntime-base-section.slds-wrap>flowruntime-screen-field",LeadsInut.class);
      //leadsCreate.waitForPageNewLead();
      //leadsCreate.setName("mary");


  }
    private <T extends CompatibilityTests.MyExternalCompatiblePageObject, S extends PageObject>
    S getUtamInsideCompatible(T externalPageObject, Class<S> utamPageObject, String cssStr) {
        return loader.create(externalPageObject, utamPageObject, LocatorBy.byCss(cssStr));
    }


  @AfterTest
  public final void tearDown() {
    quitDriver();
  }
}
