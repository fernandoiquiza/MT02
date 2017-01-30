package org.funjala.automation.web.mach2.steps.widget.mt;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import org.funjala.automation.web.common.drivers.Driver;
import org.funjala.automation.web.common.utilities.Log;
import org.funjala.automation.web.model.erp.search.ModelSearch;
import org.funjala.automation.web.pages.erp.home.OEHomePage;
import org.funjala.automation.web.pages.erp.login.OELoginPage;
import org.funjala.automation.web.pages.erp.search.OESearch;
import org.funjala.automation.web.pages.mach2.dashboard.MyDashboard;
import org.funjala.automation.web.pages.mach2.menu.TopMenuPage;
import org.funjala.automation.web.pages.mach2.widget.WidgetPage;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertEquals;

/**
 * Created by Administrator on 1/30/2017.
 */
public class mtTableEmPeInByDivisionSteps {

  @And("^I select \"([^\"]*)\" on \"([^\"]*)\" combobox$")
  public void iFillJobTitle( String value, String tag) {
    Log log = Log.getInstance();
    WebDriver driver = Driver.getDriver().getWebDriver();
    log.info("Step", "I select" + tag + " with " +value, "select" + tag + " with " +value);
    WidgetPage widget = new WidgetPage(driver);
    widget.clickOnSpecificComboBox(tag, value);
  }

  @Then("^I should see the table of EPI by \"([^\"]*)\" Division and the number of employees of the table should be the same number of employees that have OPEN ERP in this division.$")
  public void iShouldSeeTheTableOfEPIByDivisionAndTheNumberOfEmployeesOfTheTableShouldBeTheSameNumberOfEmployeesThatHaveOPENERPInThisDivision(String name) throws IOException, InterruptedException {
    Log log = Log.getInstance();
    WebDriver driver = Driver.getDriver().getWebDriver();
    log.info("Step", "The number of the table displaying EPI by Division", "is completed");
    WidgetPage widget = new WidgetPage(driver);
    boolean mach2 = widget.verifyList(name);
    Thread.sleep(5000);
    MyDashboard dashboard = new MyDashboard(driver);
    dashboard.deleteBoard();

    //Logout Mach2
    TopMenuPage topMenuPage = new TopMenuPage(driver);
    topMenuPage.clickOnLogOut();
    assertEquals(openERPVerification(name), mach2);
  }

  private boolean openERPVerification(String name) throws IOException, InterruptedException {
    WebDriver driver;
    driver = Driver.getDriver().openBrowser(Driver.OpenERP);
    OELoginPage loginERP = new OELoginPage(driver);
    loginERP.setUserName("jose4");
    loginERP.setPassword("jose4");
    OEHomePage homeERP = loginERP.clickBtnSubmit();

    //Go to Human Resources

    homeERP.clickHumanResources();
    OESearch searchERP = homeERP.oeSearch();

    //Go to Search
    searchERP.clickSearchArrow();
    searchERP.clickAdvancedSearch();
    searchERP.foundAndClickAdvancedFilterOptions("Division", "contains", "Outsourcing");
    searchERP.clickApplySearch();
    searchERP.clickSwitchList();
    searchERP.clickNumberElement();
    searchERP.clickQuantityButton();
    searchERP.clickUnlimitedOption();
    boolean result = false;
    List<ModelSearch> list = searchERP.getResultOfSearch();
    if (list.get(0).getName().equals(name)) {
      result = true;
    }
    homeERP.clickUserAccount();
    homeERP.clickLogOut();
    return result;
  }
}
