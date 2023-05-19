package ru.netology.pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class MoneyTransfer {

    private final SelenideElement amount = $("[data-test-id=amount] input");
    private final SelenideElement from = $("[data-test-id=from] input");
    private final SelenideElement okButton = $("[data-test-id=action-transfer]");
    private final SelenideElement cancelButton = $("[data-test-id=action-cancel]");
    private final SelenideElement error = $("[data-test-id=error-notification]");

    public MoneyTransfer() {

    }

    public DashboardPage transferForm(int money, DataHelper.CardNumber cardNumber) {
        amount.sendKeys(Keys.LEFT_SHIFT, Keys.HOME, Keys.BACK_SPACE);
        amount.setValue(String.valueOf(money));
        from.sendKeys(Keys.LEFT_SHIFT, Keys.HOME, Keys.BACK_SPACE);
        from.setValue(String.valueOf(cardNumber));
        okButton.click();
        return new DashboardPage();

    }


    public void getError() {
        error.shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(text("Ошибка!"));
    }

    public DashboardPage cancelButton() {
        cancelButton.click();
        return new DashboardPage();
    }


}
