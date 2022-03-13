import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class RegistrationTest {
    @BeforeEach
    void setup() {
        open("http://localhost:9999");
        Configuration.holdBrowserOpen = true;
    }

    @Test
    void shouldRegisteredAccount() {
        AccountInfo account = DataGenerator.Registration.getAccount("active");
        DataGenerator.setUpAccount(account);
        $("[data-test-id='login'] input").setValue(account.getLogin());
        $("[data-test-id='password'] input").setValue(account.getPassword());
        $("[data-test-id='action-login']").click();
        $("[id='root']").shouldBe(Condition.exactText("Личный кабинет"));
    }
    @Test
    void shouldRegisteredBlockedAccount() {
        AccountInfo account = DataGenerator.Registration.getAccount("blocked");
        DataGenerator.setUpAccount(account);
        $("[data-test-id='login'] input").setValue(account.getLogin());
        $("[data-test-id='password'] input").setValue(account.getPassword());
        $("[data-test-id='action-login']").click();
        $("[class='notification__content']").shouldBe(Condition.exactText("Ошибка! Пользователь заблокирован"));
    }
    @Test
    void shouldWrongLogin() {
        AccountInfo account = DataGenerator.Registration.getAccount("active");
        DataGenerator.setUpAccount(account);
        var randomLogin= DataGenerator.getLogin();
        $("[data-test-id='login'] input").setValue(randomLogin);
        $("[data-test-id='password'] input").setValue(account.getPassword());
        $("[data-test-id='action-login']").click();
        $("[class='notification__content']").shouldBe(Condition.exactText("Ошибка! Неверно указан логин или пароль"));
    }
    @Test
    void shouldWrongPassword() {
        AccountInfo account = DataGenerator.Registration.getAccount("active");
        DataGenerator.setUpAccount(account);
        var randomPassword= DataGenerator.getPassword();
        $("[data-test-id='login'] input").setValue(account.getLogin());
        $("[data-test-id='password'] input").setValue(randomPassword);
        $("[data-test-id='action-login']").click();
        $("[class='notification__content']").shouldBe(Condition.exactText("Ошибка! Неверно указан логин или пароль"));
    }
    @Test
    void shouldNotRegisteredAccount() {
        var notRegisteredAccount= DataGenerator.Registration.getAccount("active");
        $("[data-test-id='login'] input").setValue(notRegisteredAccount.getLogin());
        $("[data-test-id='password'] input").setValue(notRegisteredAccount.getPassword());
        $("[data-test-id='action-login']").click();
        $("[class='notification__content']").shouldBe(Condition.exactText("Ошибка! Неверно указан логин или пароль"));
    }

}
