package ru.netology.aqa0webselenide;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class AppOrderTest {

    @BeforeEach
    public void setup(){
        open("http://localhost:9999");
    }

    @Test
    public void shouldBeSuccessfulForm() {
        $x("//*[@data-test-id='name']//input").sendKeys("Иван Иваныч");
        $x("//*[@data-test-id='phone']//input").sendKeys("+78005553535");
        $x("//*[@data-test-id='agreement']").click();
        $x("//button").click();
        $x("//*[@data-test-id='order-success']")
                .shouldHave(Condition.exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."))
                .shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    public void shouldBeFailedIncorrectNameInput() {
        $x("//*[@data-test-id='name']//input").sendKeys("Gleb");
        $x("//*[@data-test-id='phone']//input").sendKeys("+78005553535");
        $x("//*[@data-test-id='agreement']").click();
        $x("//button").click();
        $x("//*[@data-test-id='name'][contains(@class, 'input_invalid')]//*[@class='input__sub']")
                .shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."))
                .shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    public void shouldBeFailedEmptyNameInput() {
        $x("//*[@data-test-id='phone']//input").sendKeys("+78005553535");
        $x("//*[@data-test-id='agreement']").click();
        $x("//button").click();
        $x("//*[@data-test-id='name'][contains(@class, 'input_invalid')]//*[@class='input__sub']")
                .shouldHave(Condition.exactText("Поле обязательно для заполнения"))
                .shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    public void shouldBeFailedIncorrectPhoneInput() {
        $x("//*[@data-test-id='name']//input").sendKeys("Глеб");
        $x("//*[@data-test-id='phone']//input").sendKeys("+Gleb");
        $x("//*[@data-test-id='agreement']").click();
        $x("//button").click();
        $x("//*[@data-test-id='phone'][contains(@class, 'input_invalid')]//*[@class='input__sub']")
                .shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."))
                .shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    public void shouldBeFailedEmptyPhoneInput() {
        $x("//*[@data-test-id='name']//input").sendKeys("Глеб");
        $x("//*[@data-test-id='agreement']").click();
        $x("//button").click();
        $x("//*[@data-test-id='phone'][contains(@class, 'input_invalid')]//*[@class='input__sub']")
                .shouldHave(Condition.exactText("Поле обязательно для заполнения"))
                .shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    public void shouldBeFailedUncheckedCheckbox() {
        $x("//*[@data-test-id='name']//input").sendKeys("Глеб");
        $x("//*[@data-test-id='phone']//input").sendKeys("+78005553535");
        $x("//button").click();
        $x("//*[@data-test-id='agreement'][contains(@class, 'input_invalid')]//*")
                .shouldBe(Condition.visible, Duration.ofSeconds(15));
    }
}
