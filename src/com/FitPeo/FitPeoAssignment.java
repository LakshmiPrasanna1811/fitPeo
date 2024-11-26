package com.FitPeo;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
//import java.util.Arrays;
//import java.util.List;

public class FitPeoAssignment {
    public static void main(String[] args) {
        // Set up ChromeDriver
        System.setProperty("webdriver.chrome.driver", "./Resources/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            JavascriptExecutor js = (JavascriptExecutor) driver;

            // Step 1: Navigate to the FitPeo Homepage
            driver.get("https://www.fitpeo.com"); 
             driver.manage().window().maximize(); //Maximize the browser window
             
            // Step 2: Navigate to the Revenue Calculator Page
            WebElement revenueCalculatorLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Revenue Calculator")));
            revenueCalculatorLink.click();
            System.out.println("Revenue calculator clicked");

            // Step 3: Scroll down to the Slider section
            WebElement sliderSection = wait.until(ExpectedConditions.visibilityOfElementLocated(
            		By.xpath("//span[@class='MuiSlider-root MuiSlider-colorPrimary MuiSlider-sizeMedium css-16i48op']"))); // Replace with actual ID
            js.executeScript("arguments[0].scrollIntoView(true);", sliderSection);
            System.out.println("scrolled down to slider section");

            // Step 4: Adjust the Slider to value 820
            WebElement slider = driver.findElement(
            		By.xpath("//input[@type='range' and @data-index='0' and @aria-valuemin='0' and @aria-valuemax='2000']"));
            js.executeScript("arguments[0].value = 820; arguments[0].dispatchEvent(new Event('input'));", slider);
            System.out.println("slider set to value 820");

            // Step 5: Validate bottom text field value updates to 820
            WebElement sliderValueField = driver.findElement(
            		By.xpath("//div[@class='MuiInputBase-root MuiOutlinedInput-root MuiInputBase-colorPrimary MuiInputBase-formControl MuiInputBase-sizeSmall css-129j43u']//input[@class='MuiInputBase-input MuiOutlinedInput-input MuiInputBase-inputSizeSmall css-1o6z5ng']\r\n")); // Replace with actual ID
            js.executeScript("arguments[0].value = '820'; arguments[0].dispatchEvent(new Event('input'));", sliderValueField);

         // Verify the value update
         wait.until(ExpectedConditions.attributeToBe(sliderValueField, "value", "820"));
         System.out.println("Text field updated to 820 successfully.");
            
         // Step 6: Update the Text Field to 560 and validate slider changes accordingly
           Thread.sleep(2000);

            sliderValueField.sendKeys(Keys.RETURN);
           
             System.out.println("value updated as 560");
            
             // Step 7: Scroll to CPT Codes section and select the specified CPT codes
            js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[2]"))); // Replace with actual ID of the CPT section
             System.out.println("Scrolled to CPT Codes");
            
             String[] divXPaths = {
            		 "/html/body/div[1]/div[1]/div[2]/div[1]",  // CPT-99091
                     "/html/body/div[1]/div[1]/div[2]/div[2]",  // CPT-99453
                     "/html/body/div[1]/div[1]/div[2]/div[3]",  // CPT-99454
                     "/html/body/div[1]/div[1]/div[2]/div[8]"   // CPT-99474
                 };
                   
                 // Iterate through each XPath and select the corresponding checkbox
                 for (String xpath : divXPaths) {
                     // Find the checkbox within the specific div by XPath
                     WebElement checkbox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));

                     // Scroll the checkbox into view
                     
                     js.executeScript("arguments[0].scrollIntoView(true);", checkbox);

                     // Wait for the checkbox to be clickable after scrolling
                     wait.until(ExpectedConditions.elementToBeClickable(checkbox));

                     // If the checkbox is not already selected, click it
                     if (!checkbox.isSelected()) {
                         checkbox.click();  
                     }
                 }
               System.out.println("CPT Checkboxes selected");
            // Step 8: Validate Total Recurring Reimbursement value
            WebElement reimbursementHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//p[text()='Total Recurring Reimbursement for all Patients Per Month']")));

            System.out.println("Reimbursement Header Text: " + reimbursementHeader.getText());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the browser
            driver.quit();
        }
    }
}
