package ru.netology.test;

import com.codeborne.selenide.Configuration;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.pages.DashboardPage;
import ru.netology.pages.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTransferTest {

    @BeforeEach
    public void openPage() {

        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");

        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
    }


    @Test
    void testValidRefillFromCard1() {

        val dashboardPage = new DashboardPage();

        int refillMoney = 10000;
        int balanceFirstCard = dashboardPage.getFirstCardBalance();
        int balanceSecondCard = dashboardPage.getSecondCardBalance();
        val moneyTransfer = dashboardPage.firstCardButton();
        val infoCard = DataHelper.getSecondCardNumber();
        moneyTransfer.transferForm(Integer.parseInt(String.valueOf(refillMoney)), infoCard);

        assertEquals(balanceFirstCard + Integer.parseInt(String.valueOf(refillMoney)), dashboardPage.getFirstCardBalance());
        assertEquals(balanceSecondCard - Integer.parseInt(String.valueOf(refillMoney)), dashboardPage.getSecondCardBalance());
    }


    @Test
    void testValidRefillFromCard2() {

        val dashboardPage = new DashboardPage();

        int refillMoney = 19999;
        int balanceFirstCard = dashboardPage.getFirstCardBalance();
        int balanceSecondCard = dashboardPage.getSecondCardBalance();
        val moneyTransfer = dashboardPage.secondCardButton();
        val infoCard = DataHelper.getFirstCardNumber();
        moneyTransfer.transferForm(Integer.parseInt(String.valueOf(refillMoney)), infoCard);

        assertEquals(balanceFirstCard - Integer.parseInt(String.valueOf(refillMoney)), dashboardPage.getFirstCardBalance());
        assertEquals(balanceSecondCard + Integer.parseInt(String.valueOf(refillMoney)), dashboardPage.getSecondCardBalance());
    }

    @Test
    void testRefillZero() {

        val dashboardPage = new DashboardPage();

        int refillMoney = 0;
        int balanceFirstCard = dashboardPage.getFirstCardBalance();
        int balanceSecondCard = dashboardPage.getSecondCardBalance();
        val moneyTransfer = dashboardPage.secondCardButton();
        val infoCard = DataHelper.getFirstCardNumber();
        moneyTransfer.transferForm(Integer.parseInt(String.valueOf(refillMoney)), infoCard);

        assertEquals(balanceFirstCard - Integer.parseInt(String.valueOf(refillMoney)), dashboardPage.getFirstCardBalance());
        assertEquals(balanceSecondCard + Integer.parseInt(String.valueOf(refillMoney)), dashboardPage.getSecondCardBalance());
        //При переводе 0 рублей система не предлагает сумму минимального платежа - баг!
    }



    @Test
    void testInvalidTransaction() {
        val dashboardPage = new DashboardPage();

        int refillMoney = 100;
        int balanceFirstCard = dashboardPage.getFirstCardBalance();
        int balanceSecondCard = dashboardPage.getSecondCardBalance();
        val moneyTransfer = dashboardPage.secondCardButton();
        val infoCard = DataHelper.getFirstCardNumber();
        moneyTransfer.transferForm(Integer.parseInt(String.valueOf(refillMoney)), infoCard);

        assertEquals(balanceFirstCard - Integer.parseInt(String.valueOf(refillMoney)), dashboardPage.getFirstCardBalance());
        assertEquals(balanceSecondCard + Integer.parseInt(String.valueOf(refillMoney)), dashboardPage.getSecondCardBalance());
        //moneyTransfer.getError();
        //Система не выдает ошибку при переводе суммы большей, чем остаток средств на карте - баг!
    }

}