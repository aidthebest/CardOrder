package order;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderTest {

    private WebDriver driver;

    @BeforeAll
    public static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }
//   @BeforeAll
//   static void  setOpAll() {
//       System.setProperty("webdriver.chrome.driver", "./driver/linux/chromedriver");
//   }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldBeSuccess() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иванов Тарас Игнатьевич");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("+79264775516");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String actualText = driver.findElement(By.className("Success_successBlock__2L3Cw")).getText();
        String expectedText = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expectedText.trim(), actualText.trim());
    }

    @Test
    void shouldBeErrorOnNameField() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("Ivanov Taras Ignat'evich");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("+79264775516");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String actualText = driver.findElement(By.className("input__sub")).getText();
        String expectedText = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expectedText.trim(), actualText.trim());
    }

    @Test
    void shouldBeErrorOnTelField(){
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("Иванов Тарас Игнатьевич");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("+7926477551600");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        List<WebElement> text = driver.findElements(By.className("input__sub"));
        String actualText = text.get(1).getText();
        String expectedText = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(expectedText.trim(), actualText.trim());
    }
}

