package java_example;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class TestChrome {
	public WebDriver driver;
	public String URL, Node;
	protected ThreadLocal<RemoteWebDriver> threadDriver = null;

	@Parameters(value={"url","seleniumGridUrl"})
	@BeforeTest
	public void launchbrowser(String url, String seleniumGridUrl) throws MalformedURLException {
//		String URL = "http://www.calculator.net";
		String browser = "chrome";
		String URL = url;
		if (browser.equalsIgnoreCase("firefox")) {
			System.out.println(" Executing on FireFox at grid "+ seleniumGridUrl);
//			String Node = "http://192.168.99.100:32768/wd/hub";
			String Node = seleniumGridUrl;
			DesiredCapabilities cap = DesiredCapabilities.firefox();
			cap.setBrowserName("firefox");
			driver = new RemoteWebDriver(new URL(Node), cap);
			// Puts an Implicit wait, Will wait for 10 seconds before throwing
			// exception
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			// Launch website
			driver.navigate().to(URL);
			driver.manage().window().maximize();
		} else if (browser.equalsIgnoreCase("chrome")) {
			System.out.println(" Executing on CHROME at grid "+ seleniumGridUrl);
			DesiredCapabilities cap = DesiredCapabilities.chrome();
			cap.setBrowserName("chrome");
//			String Node = "http://192.168.99.100:32768/wd/hub";
			String Node = seleniumGridUrl;
			driver = new RemoteWebDriver(new URL(Node), cap);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			// Launch website
			driver.navigate().to(URL);
			driver.manage().window().maximize();
		} else if (browser.equalsIgnoreCase("ie")) {
			System.out.println(" Executing on IE at grid "+seleniumGridUrl);
			DesiredCapabilities cap = DesiredCapabilities.chrome();
			cap.setBrowserName("ie");
//			String Node = "http://192.168.99.100:32768/wd/hub";
			String Node = seleniumGridUrl;
			driver = new RemoteWebDriver(new URL(Node), cap);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			// Launch website
			driver.navigate().to(URL);
			driver.manage().window().maximize();
		} else {
			throw new IllegalArgumentException("The Browser Type is Undefined");
		}
		
		System.out.println("Remote session Id: "+ ((RemoteWebDriver) driver).getSessionId());
	}

	@Test
	public void calculatepercent() {
		System.out.println("Running the calculate percent test on chrome");
		// Click on Math Calculators
		driver.findElement(By.xpath("//a[contains(text(),'Math')]")).click();
		// Click on Percent Calculators
		driver.findElement(By.xpath("//a[contains(text(),'Percentage Calculator')]")).click();
		// Enter value 17 in the first number of the percent Calculator
		driver.findElement(By.id("cpar1")).sendKeys("17");
		// Enter value 35 in the second number of the percent Calculator
		driver.findElement(By.id("cpar2")).sendKeys("35");
		// Click Calculate Button
		driver.findElement(By.xpath("(//input[contains(@value,'Calculate')])[1]")).click();
		// Get the Result Text based on its xpath
		String result = driver.findElement(By.xpath(".//*[@id='content']/p[2]/font/b")).getText();
		// Print a Log In message to the screen
		System.out.println(" The Result is " + result);
		if (result.equals("5.95")) {
			System.out.println(" The Result is Pass");
		} else {
			System.out.println(" The Result is Fail");
		}
	}
	
	@Test
	 public void open_python_site() throws Exception  { 
		System.out.println("Running the open python doc test on chrome");
        driver.get("http://www.python.org");
		Thread.sleep(10000); //slow down for demo purposes
		WebElement element = driver.findElement(By.name("q"));
        element.sendKeys("documentation");
        //elem.send_keys(Keys.RETURN)
        element.submit();
        Thread.sleep(5000); //slow down for demo purposes
	}

	@AfterTest
	public void closeBrowser() {
		 driver.quit();
	}
}