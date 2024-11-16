import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Testing extends parameters {

    // **Setup Method**: Initializes the driver and navigates to the base URL.
    @BeforeTest
    public void setup() {
        mySetup();
    }

    // **Test Case 1: Verify Website Language**
    @Test(priority = 1)
    public void Language() {
        String ActualLang = driver.findElement(By.tagName("html")).getAttribute("lang");
        Assert.assertEquals(ActualLang, ExpectedLang);
    }

    // **Test Case 2: Verify Default Currency**
    @Test(priority = 2)
    public void currency() {
        String ActualCurrency = driver.findElement(By.xpath("//button[@data-testid='Header__CurrencySelector']")).getText();
        Assert.assertEquals(ActualCurrency, ExpectedCurrency);
    }

    // **Test Case 3: Verify Contact Number**
    @Test(priority = 3)
    public void ContactNum() {
        String ActualNum = driver.findElement(By.cssSelector(".sc-hUfwpO.bWcsTG")).getText();
        Assert.assertEquals(ActualNum, ExpectedNum);
    }

    // **Test Case 4: Verify Qitaf Logo Visibility in Footer**
    @Test(priority = 4)
    public void QitafLogo() {
        WebElement Footer = driver.findElement(By.tagName("footer"));
        boolean ActualRes = Footer.findElement(By.cssSelector(".sc-bdVaJa.bxRSiR.sc-ciodno.lkfeIG")).isDisplayed();
        Assert.assertEquals(ActualRes, ExpectedResQitaf);
    }

    // **Test Case 5: Verify Hotel Tab Is Not Selected**
    @Test(priority = 5)
    public void HotelTabNotSelected() throws IOException {
        WebElement HotelTab = driver.findElement(By.id("uncontrolled-tab-example-tab-hotels"));
        String ActualRes = HotelTab.getAttribute("aria-selected");
        Assert.assertEquals(ActualRes, ExpectedResHotelNotSelected);
    }

    // **Test Case 6: Verify Departure Time**
    @Test(priority = 6)
    public void DepartureTime() {
        String ActualDate = driver.findElement(By.cssSelector("div[class='sc-OxbzP sc-lnrBVv gKbptE'] span[class='sc-fvLVrH hNjEjT']")).getText();
        Assert.assertEquals(ActualDate, ExpectedDepartureTime);
    }

    // **Test Case 7: Verify Return Time**
    @Test(priority = 7)
    public void ReturnTime() {
        String ActualDate = driver.findElement(By.cssSelector("div[class='sc-OxbzP sc-bYnzgO bojUIa'] span[class='sc-fvLVrH hNjEjT']")).getText();
        Assert.assertEquals(ActualDate, ExpectedReturnTime);
    }

    // **Test Case 8: Verify Language and Input Random Search Location**
    @Test(priority = 8)
    public void RandLang() {
        RandomlyEnterWebsite();
        WebElement SearchBar = driver.findElement(By.cssSelector(".sc-phbroq-2.uQFRS.AutoComplete__Input"));
        CheckWebsiteLang(SearchBar);
        RandomNumOfVistors();
    }

    // **Test Case 9: Verify Search Results Completion**
    @Test(priority = 9)
    public void CheckifFinished() throws InterruptedException {
        Thread.sleep(23000);
        WebElement FoundRes = driver.findElement(By.xpath("//span[@data-testid='srp_properties_found']"));
        boolean AcutalRes = FoundRes.getText().contains("found") || FoundRes.getText().contains("مكان");
        Assert.assertEquals(AcutalRes, ExpectedFinishingRes);
    }

    // **Test Case 10: Verify Sort Functionality by Lowest Price**
    @Test(priority = 10)
    public void CheckTheSortOption() throws InterruptedException {
        WebElement LowestPriceButton = driver.findElement(By.xpath("//div[@data-testid='srp_sort_LOWEST_PRICE']"));
        LowestPriceButton.click();
        checkSort();
    }
}
