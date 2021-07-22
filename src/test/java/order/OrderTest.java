package order;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderTest {

    private WebDriver driver;

    @BeforeAll
    public static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldBeSuccess() {
//        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иванов Тарас Игнатьевич");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79264775516");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id=order-success")).getText();
        String expectedText = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expectedText.trim(), actualText.trim());
    }

    @Test
    void shouldBeErrorOnNameField() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Ivanov Taras Ignat'evich");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79264775516");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText();
        String expectedText = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expectedText.trim(), actualText.trim());
    }

    @Test
    void shouldBeErrorOnTelField() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иванов Тарас Игнатьевич");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+7926477551600");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText();
        String expectedText = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(expectedText.trim(), actualText.trim());
    }

    @Test
    void shouldBeErrorMessageWithUnuseChekBoxField() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иванов Тарас Игнатьевич");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79264775516");
        driver.findElement(By.className("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id=agreement].input_invalid .checkbox__text")).getText();
        String expectedText = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";
        assertEquals(expectedText.trim(), actualText.trim());
    }

    @Test
    void shouldBeErrorOnEmptyNameField() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79264775516");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText();
        String expectedText = "Поле обязательно для заполнения";
        assertEquals(expectedText.trim(), actualText.trim());
    }

    @Test
    void shouldBeErrorOnEmptyTelField() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иванов Тарас Игнатьевич");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText();
        String expectedText = "Поле обязательно для заполнения";
        assertEquals(expectedText.trim(), actualText.trim());
    }
}

