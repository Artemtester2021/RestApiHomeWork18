package tests;

import api.requests.authorization.AuthResponseDto;
import api.requests.authorization.AuthorizationApi;
import api.requests.crud.GetAccountUserBooksByIdResponseDto;
import api.requests.crud.Requests;
import helpers.WithLogin;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.ProfilePage;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

@Tag("restApi")
public class DeleteBooksDemoQA extends TestBase {

    ProfilePage profilePage = new ProfilePage();
    Requests requests = new Requests();


    @Test
    @WithLogin
    @DisplayName("Проверка содержимого в корзине, после удаления книг")
    void CheckingTheContentsInTheTrashAfterDeletingBooks() {
        AuthResponseDto authResponseModel = step("Авторизация через Api", () ->
                AuthorizationApi.authorize()
        );

        String userId = authResponseModel.getUserId();

        step("Удаление всех книг из корзины", ()->
            requests.deleteAllBooksFromProfileById(userId)
        );

        step("Проверка, что корзина пустая через API", ()-> {
            GetAccountUserBooksByIdResponseDto response = requests.getAccountUserBooksById(userId);
            assertThat(response.getBooks()).isEmpty();
        });

        step("Добавление книги в корзину", ()->
                requests.addBookToProfile(userId, "9781449325862")
        );

        step("Открытие страницы профиля", ()->
                open("/profile")
        );

        step("Проверка отображения книги в корзине", ()->
                profilePage.bookShouldHaveTitle("Git Pocket Guide")
        );

        step("Удаление всех книг из корзины", ()->
                requests.deleteAllBooksFromProfileById(userId)
        );

        step("Проверка, что корзина пустая через API", ()-> {
            GetAccountUserBooksByIdResponseDto response = requests.getAccountUserBooksById(userId);
            assertThat(response.getBooks()).isEmpty();
        });
    }
}