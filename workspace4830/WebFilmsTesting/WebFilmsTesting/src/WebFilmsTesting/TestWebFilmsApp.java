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

public class TestWebFilmsApp {
   private WebDriver driver;
   private boolean acceptNextAlert = true;
   private StringBuffer verificationErrors = new StringBuffer();

   @Before
   public void setUp() throws Exception {
      System.setProperty("webdriver.chrome.driver", //
    		  "lib\\win\\chromedriver.exe");
      driver = new ChromeDriver();
      driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
   }

   @Test
   public void testNavigationButtons() throws Exception {
      driver.get("http://ec2-52-14-138-145.us-east-2.compute.amazonaws.com:8080/webfilmsProject/Index.html");
      
      //Test Homepage
      String HomePageHeader = "";
      HomePageHeader = driver.findElement(By.xpath("//div/h1")).getAttribute("innerHTML");
      Thread.sleep(1000);
      Assert.assertEquals("WebFilms", HomePageHeader);
      
      // Test all Theaters
      String theaterName = "";
      String theaterHeader = "";
      List<WebElement> listOfTheaters = driver.findElements(By.xpath("//input[@class='TheaterSelect']"));
      
      for (int z = 0; z < listOfTheaters.size(); z++) {
    	  listOfTheaters = driver.findElements(By.xpath("//input[@class='TheaterSelect']"));
    	  Thread.sleep(1000);
    	  theaterName = listOfTheaters.get(z).getAttribute("value");
    	  listOfTheaters.get(z).click();
	      theaterHeader = driver.findElement(By.xpath("//div[@style='background-color:#a9a9a9']/h1")).getAttribute("innerHTML");
	      Assert.assertEquals(theaterName, theaterHeader);
	      
	      // Test all Movies
	      String movieName = "";
	      String movieHeader = "";
	      List<WebElement> listOfMovies = driver.findElements(By.xpath("//input[@class='MovieSelect']"));
	      for (int a = 0; a <listOfMovies.size(); a++) {
	    	  listOfMovies = driver.findElements(By.xpath("//input[@class='MovieSelect']"));
	    	  Thread.sleep(1000);
	    	  movieName = listOfMovies.get(a).getAttribute("value");
	    	  listOfMovies.get(a).click();
	    	  movieHeader = driver.findElement(By.xpath("//div/h1")).getAttribute("innerHTML");
	    	  Assert.assertEquals(movieName, movieHeader);
	    	  
	          // Test all Times
	          List<WebElement> listOfTimes = driver.findElements(By.xpath("//form[@action='TimeSelect']/*"));
	          for (int i = 0; i < listOfTimes.size(); i++) {
	        	  listOfTimes = driver.findElements(By.xpath("//form[@action='TimeSelect']/*"));
	        	  listOfTimes.get(i).click();
	        	  Thread.sleep(100);
	        	  theaterName = driver.findElement(By.xpath("//div/h1")).getAttribute("innerHTML");
	              Assert.assertEquals("Seats for " + movieName, theaterName);
	              driver.navigate().back();
	              Thread.sleep(100);
	          }
	          driver.navigate().back();
	      }
	      driver.navigate().back();
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

}
