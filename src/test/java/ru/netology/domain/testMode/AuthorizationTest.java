package ru.netology.domain.testMode;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.domain.DataClass.DataGenerator;
import ru.netology.domain.DataClass.RegistrationInfo;

import static com.codeborne.selenide.Selenide.*;

public class AuthorizationTest {
 @BeforeEach
 void setUp(){
 open("http://localhost:9999/");
 }

 @Test
 @DisplayName("Should successfully login with active registered user")
    void shouldLoginRegisteredActiveUser(){
     RegistrationInfo activeUser = DataGenerator.getActiveUser();
     $("[data-test-id='login'] input").setValue(activeUser.getLogin());
     $("[data-test-id='password'] input").setValue(activeUser.getPassword());
     $("[data-test-id='action-login']")
             .shouldHave(Condition.exactText("Продолжить")).click();
     $(".App_appContainer__3jRx1").shouldHave(Condition.text("Личный кабинет"))
             .shouldBe(Condition.visible);
    }
    @Test
    @DisplayName("Should get error if login with blocked registered user")
    void shouldGetErrorIfBlockedUser(){
        RegistrationInfo blockedUser = DataGenerator.getBlockedUser();
        $("[data-test-id='login'] input").setValue(blockedUser.getLogin());
        $("[data-test-id='password'] input").setValue(blockedUser.getPassword());
        $("[data-test-id='action-login']")
                .shouldHave(Condition.exactText("Продолжить")).click();
        $("[data-test-id=error-notification] .notification__content")
                .shouldHave(Condition.text("Пользователь заблокирован"));
    }
    @Test
    @DisplayName("Should get error if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser(){
        RegistrationInfo notRegisteredUser = DataGenerator.getNotRegisteredUser("active");
        $("[data-test-id='login'] input").setValue(notRegisteredUser.getLogin());
        $("[data-test-id='password'] input").setValue(notRegisteredUser.getPassword());
        $("[data-test-id='action-login']")
                .shouldHave(Condition.exactText("Продолжить")).click();
        $("[data-test-id=error-notification] .notification__content")
                .shouldHave(Condition.text("Неверно указан логин или пароль"));
    }
    @Test
    @DisplayName("Should get error if all fields are empty")
    void shouldGetErrorIfAllFieldsEmpty(){
     RegistrationInfo activeUser = DataGenerator.getActiveUser();
        $("[data-test-id='login'] input").setValue("");
        $("[data-test-id='password'] input").setValue("");
        $("[data-test-id='action-login']")
                .shouldHave(Condition.exactText("Продолжить")).click();
        $("[data-test-id='login'].input_invalid .input__sub")
                .shouldHave(Condition.exactText("Поле обязательно для заполнения"));
        $("[data-test-id='password'].input_invalid .input__sub")
                .shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }
    @Test
    @DisplayName("Should get error if field Login is empty")
    void shouldGetErrorWithoutLogin(){
    RegistrationInfo activeUser = DataGenerator.getActiveUser();
        $("[data-test-id='login'] input").setValue("");
        $("[data-test-id='password'] input").setValue(activeUser.getPassword());
        $("[data-test-id='action-login']")
                .shouldHave(Condition.exactText("Продолжить")).click();
        $("[data-test-id='login'].input_invalid .input__sub")
                .shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }
    @Test
    @DisplayName("Should get error if field Login is invalid")
    void shouldGetErrorIfInvalidLogin(){
     RegistrationInfo invalidLoginUser = DataGenerator.getInvalidLoginUser("active");
        $("[data-test-id='login'] input").setValue(invalidLoginUser.getLogin());
        $("[data-test-id='password'] input").setValue(invalidLoginUser.getPassword());
        $("[data-test-id='action-login']")
                .shouldHave(Condition.exactText("Продолжить")).click();
        $("[data-test-id=error-notification] .notification__content")
                .shouldHave(Condition.text("Неверно указан логин или пароль"));
        }
    @Test
    @DisplayName("Should get error if field Password is empty")
    void shouldGetErrorWithoutPassword(){
    RegistrationInfo activeUser = DataGenerator.getActiveUser();
        $("[data-test-id='login'] input").setValue(activeUser.getLogin());
        $("[data-test-id='password'] input").setValue("");
        $("[data-test-id='action-login']")
                .shouldHave(Condition.exactText("Продолжить")).click();
        $("[data-test-id='password'].input_invalid .input__sub")
                .shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }
    @Test
    @DisplayName("Should get error if field Password is invalid")
    void shouldGetErrorIfInvalidPassword(){
        RegistrationInfo invalidPasswordUser = DataGenerator.getInvalidPasswordUser("active");
        $("[data-test-id='login'] input").setValue(invalidPasswordUser.getLogin());
        $("[data-test-id='password'] input").setValue(invalidPasswordUser.getPassword());
        $("[data-test-id='action-login']")
                .shouldHave(Condition.exactText("Продолжить")).click();
        $("[data-test-id=error-notification] .notification__content")
                .shouldHave(Condition.text("Неверно указан логин или пароль"));
    }
    }
