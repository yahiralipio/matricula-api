package pe.edu.cibertec.matricula_api.screenplay.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.rest.interactions.Post;
import io.restassured.http.ContentType;

import java.util.HashMap;
import java.util.Map;

public class RenovarToken implements Task {
    private final String refreshToken;

    public RenovarToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public static RenovarToken con(String refreshToken) {
        return Tasks.instrumented(RenovarToken.class, refreshToken);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        Map<String, String> body = new HashMap<>();
        body.put("refreshToken", refreshToken);

        actor.attemptsTo(
                Post.to("/api/auth/refresh")
                        .with(request -> request.contentType(ContentType.JSON).body(body))
        );
    }
}