package WebFilmsTesting;

import java.util.regex.Pattern;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class TestWebFilmsEmail {
   private WebDriver driver;
   private boolean acceptNextAlert = true;
   private StringBuffer verificationErrors = new StringBuffer();

   @Before
   public void setUp() throws Exception {
      System.setProperty("webdriver.chrome.driver", //
    		  "lib\\win\\chromedriver.exe");
      driver = new ChromeDriver();
      driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
   }
   
   @Test
   public void testEmail() throws Exception {
	   // Select showing and time you would like to test seat selection on
	   String theaterName = "//input[@value='Oakview']";
	   String movieName = "//input[@value='Bee Movie']";
	   String time = "//input[@value='12:00 PM']";
	   String seat = "//input[@value='1A']";
	   
	   driver.get("http://ec2-52-14-138-145.us-east-2.compute.amazonaws.com:8080/webfilmsProject/Index.html");
	   
	   driver.findElement(By.xpath(theaterName)).click();
	   Thread.sleep(2000);
	   driver.findElement(By.xpath(movieName)).click();
	   Thread.sleep(2000);
	   driver.findElement(By.xpath(time)).click();
	   Thread.sleep(2000);
	   
	   try {
		   // Navigate to seats and reserve them
		   if (!(isElementPresent(By.xpath(seat)))) {
			   tearDown();
		   }
		   else {
			   driver.findElement(By.xpath(seat)).click();
			   Thread.sleep(3000);
			   driver.findElement(By.name("email")).sendKeys("webfilms.csci4830@gmail.com");
			   Thread.sleep(3000);
			   driver.findElement(By.xpath("//input[@value='Reserve Seats']")).click();
			   Thread.sleep(3000);
			   
			   String confirmationMsg = "";
			   confirmationMsg = driver.findElement(By.xpath("//body/h2")).getAttribute("innerHTML");
			   Assert.assertEquals("Thank you!<br>An email confirmation has been sent to you.", confirmationMsg);
			   System.out.println("Seats were successfully reserved");
			   driver.findElement(By.xpath("//input[@value='Home Page']")).click();
			   
			   // Navigate back to seats and make sure they were correctly reserved
			   // Need to reload the driver in order for "findElement" to work properly
			   driver.get("http://ec2-52-14-138-145.us-east-2.compute.amazonaws.com:8080/webfilmsProject/Index.html");
			   String HomePageHeader = "";
			   HomePageHeader = driver.findElement(By.xpath("//div/h1")).getAttribute("innerHTML");
			   Assert.assertEquals("WebFilms", HomePageHeader);
			      
			   driver.findElement(By.xpath(theaterName)).click();
			   Thread.sleep(2000);
			   driver.findElement(By.xpath(movieName)).click();
			   Thread.sleep(2000);
			   driver.findElement(By.xpath(time)).click();
			   Thread.sleep(2000);
		   }
		   if (!(isElementPresent(By.xpath(seat)))) {
			   tearDown();
		   }
	   } catch (Exception e) {
		   System.out.println("Seats specified have already been reserved");
	   }
   }

   @After
   public void tearDown() throws Exception {
      driver.quit();
      String verificationErrorString = verificationErrors.toString();
      if (!"".equals(verificationErrorString)) {
         fail(verificationErrorString);
      }
   }

   private boolean isElementPresent(By by) {
	      try {
	         driver.findElement(by);
	         return true;
	      } catch (NoSuchElementException e) {
	         return false;
	      }
	   }
}