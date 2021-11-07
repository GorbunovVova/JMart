package stepDefs;

import cucumber.api.java.ru.Когда;
import steps.ApiSteps;

public class ApiStepDefs {

    ApiSteps apiSteps = new ApiSteps();

    @Когда("^получается токен гостя$")
    public void gettingGuestToken() {
        apiSteps.gettingGuestToken();
    }

    @Когда("^выполняется регистрация игрока$")
    public void signUpPlayer() {
        apiSteps.signUpPlayer();
    }

    @Когда("^авторизация под созданным игроком$")
    public void auth() {
        apiSteps.auth();
    }

    @Когда("^запрашиваются данные (не|)зарегистрированного игрока$")
    public void getPlayer(String negation) {
        apiSteps.getPlayer(negation);
    }

}
