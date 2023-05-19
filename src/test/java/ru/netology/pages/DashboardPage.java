package ru.netology.pages;

import com.codeborne.selenide.SelenideElement;
import lombok.val;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";
    private SelenideElement card1 = $$( ".list__item" ).first();
    private SelenideElement card2 = $$( ".list__item" ).last();
    private SelenideElement reload = $( "[data-test-id=''action-reload']" );
    private SelenideElement card1Button = $$( "[data-test-id='action-deposit']" ).first();
    private SelenideElement card2Button = $$( "[data-test-id='action-deposit']" ).last();



    public MoneyTransfer firstCardButton() {
        card1Button.click();
        return new MoneyTransfer();

    }

    public MoneyTransfer secondCardButton() {
        card2Button.click();
        return new MoneyTransfer();
    }

    public int getFirstCardBalance() {
        val text = card1.text();
        return extractBalanceCard(text);
    }

    private int extractBalanceCard(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public int getSecondCardBalance() {
        val text = card2.text();
        return extractBalanceCard(text);
    }




}
