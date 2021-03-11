import com.codeborne.selenide.Condition;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.github.javafaker.Faker;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;


public class Tests {

    AuthInfo person;

    @BeforeAll
    static void setUpAll(){
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @AfterAll
    static void tearDownAll(){
        SelenideLogger.removeListener("allure");
    }

    @Test
    void shouldLoginWhenActive() {

        person = DataGenerator.createUser("active");
        $("[data-test-id=login] input").sendKeys(person.login);
        $("[data-test-id=password] input").sendKeys(person.password);
        $("button[data-test-id=action-login]").click();
        $("[id=root]").shouldHave(text("Личный кабинет".trim()));
    }

    @Test
    void shouldNotLoginWhenBlocked() {
        person = DataGenerator.createUser("blocked");
        $("[data-test-id=login] input").sendKeys(person.login);
        $("[data-test-id=password] input").sendKeys(person.password);
        $("button[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]").shouldHave(text("Ошибка! Пользователь заблокирован"));
    }

    @Test
    void shouldNotLoginWhenWrongPassword() {
        person = DataGenerator.createUser( "active");
        String wrongPassword = person.getPassword() + "wrong";
        $("[data-test-id=login] input").sendKeys(person.login);
        $("[data-test-id=password] input").sendKeys(wrongPassword);
        $("button[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]").shouldHave(text("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    void shouldNotLoginWhenWrongLogin() {
        person = DataGenerator.createUser( "active");
        String wrongLogin = person.getLogin() + "wrong";
        $("[data-test-id=login] input").sendKeys(wrongLogin);
        $("[data-test-id=password] input").sendKeys(person.password);
        $("button[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]").shouldHave(text("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    void shouldNotLoginWhenUserNotExist() {
        person = DataGenerator.notCreateUser( "active");
        $("[data-test-id=login] input").sendKeys(person.login);
        $("[data-test-id=password] input").sendKeys(person.password);
        $("button[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]").shouldHave(text("Ошибка! Неверно указан логин или пароль"));
    }
}



















