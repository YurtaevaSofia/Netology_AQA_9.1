import com.codeborne.selenide.Condition;
import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Locale;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;


public class Tests {

    AuthInfo person;

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    void shouldLoginWhenActive() {

        person = DataGenerator.createUser("active");
        $("[data-test-id=login] input").sendKeys(person.login);
        $("[data-test-id=password] input").sendKeys(person.password);
        $("button[data-test-id=action-login]").click();
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



















