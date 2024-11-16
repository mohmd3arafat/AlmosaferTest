import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Random;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

public class parameters {

    // **Driver and Test Variables Initialization**
    WebDriver driver = new ChromeDriver();
    Random rand = new Random();
    Date myDate = new Date();
    String MyWebsite = "https://www.almosafer.com/en";
    String ExpectedLang = "en";
    String ExpectedCurrency = "SAR";
    String ExpectedNum = "+966554400000";
    boolean ExpectedResQitaf = true;
    String ExpectedResHotelNotSelected = "false";
    String ExpectedDepartureTime = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("dd"));
    String ExpectedReturnTime = LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern("dd"));
    String[] ArPlaces = {"جدة", "دبي"};
    String[] EnPlaces = {"Riyadh", "Dubai", "Jeddah"};
    String[] myWebsites = {"https://www.almosafer.com/ar", "https://www.almosafer.com/en"};
    int RandArCity = rand.nextInt(ArPlaces.length);
    int RandEnCity = rand.nextInt(EnPlaces.length);
    int RandIndex = rand.nextInt(myWebsites.length);
    boolean ExpectedFinishingRes = true;

    // **Method: Randomly Select Number of Visitors and Rooms**
    public void RandomNumOfVistors() {
        WebElement ResList = driver.findElement(By.cssSelector(".sc-phbroq-4.gGwzVo.AutoComplete__List"));
        ResList.findElements(By.tagName("li")).get(1).click();

        WebElement Rooms = driver.findElement(By.cssSelector(".sc-tln3e3-1.gvrkTi"));
        Select sel = new Select(Rooms);
        int RoomsNum = Rooms.findElements(By.tagName("option")).size();
        int RandRooms = rand.nextInt(RoomsNum);

        sel.selectByIndex(RandRooms);

        WebElement SearchButton = driver.findElement(By.xpath("//button[@data-testid='HotelSearchBox__SearchButton']"));
        SearchButton.click();
    }

    // **Method: Randomly Navigate to Website**
    public void RandomlyEnterWebsite() {
        driver.get(myWebsites[RandIndex]);
        WebElement HotelBar = driver.findElement(By.id("uncontrolled-tab-example-tab-hotels"));
        HotelBar.click();
    }

    // **Method: Initial Setup for Driver and Base Website**
    public void mySetup() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get(MyWebsite);
        driver.manage().window().maximize();
        driver.findElement(By.cssSelector(".sc-jTzLTM.hQpNle.cta__button.cta__saudi.btn.btn-primary")).click();
    }

    // **Method: Take a Screenshot**
    public void screenShot() throws IOException, InterruptedException {
    	Thread.sleep(3000);
        String nowDate = myDate.toString().replace(":", "-");
        TakesScreenshot screenshot = (TakesScreenshot) driver;
        File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
        File destFile = new File("./ScreenShots/" + nowDate + ".png");
        FileUtils.copyFile(srcFile, destFile);
    }

    // **Method: Verify Website Language**
    public void CheckWebsiteLang(WebElement SearchBar) {
        if (driver.getCurrentUrl().equals("https://www.almosafer.com/ar")) {
            String ActualLang = driver.findElement(By.tagName("html")).getAttribute("lang");
            String ExpectedLang = "ar";
            Assert.assertEquals(ActualLang, ExpectedLang);
            SearchBar.sendKeys(ArPlaces[RandArCity]);
        } else {
            String ActualLang = driver.findElement(By.tagName("html")).getAttribute("lang");
            String ExpectedLang = "en";
            Assert.assertEquals(ActualLang, ExpectedLang);
            SearchBar.sendKeys(EnPlaces[RandEnCity]);
        }
    }

    // **Method: Verify Sorting of Results**
    public void checkSort() {
        if (driver.getCurrentUrl().contains("https://www.almosafer.com/en")) {
            List<WebElement> allPrices = driver.findElements(By.cssSelector(".MuiTypography-root.MuiTypography-heading3SemBld.__ds__comp.undefined.muiltr-18vmb2l"));
            int LowestPrice = Integer.parseInt(allPrices.get(1).getText().replaceAll("[^0-9]", ""));
            int HighestPrice = Integer.parseInt(allPrices.get(allPrices.size() - 1).getText().replaceAll("[^0-9]", ""));
            boolean Actual = HighestPrice > LowestPrice;
            Assert.assertTrue(Actual);
        } else {
            List<WebElement> allPrices = driver.findElements(By.cssSelector(".MuiTypography-root.MuiTypography-heading3SemBld.__ds__comp.undefined.muirtl-1l5b3qq"));
            int LowestPrice = Integer.parseInt(allPrices.get(1).getText().replaceAll("[^0-9]", ""));
            int HighestPrice = Integer.parseInt(allPrices.get(allPrices.size() - 1).getText().replaceAll("[^0-9]", ""));
            boolean Actual = HighestPrice > LowestPrice;
            Assert.assertTrue(Actual);
        }
    }
}
