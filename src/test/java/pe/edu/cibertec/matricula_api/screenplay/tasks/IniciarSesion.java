package pe.edu.cibertec.matricula_api.screenplay.tasks;

import io.restassured.http.ContentType;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.rest.interactions.Post;

import java.util.HashMap;
import java.util.Map;

public class IniciarSesion implements Task {
    private final String usuario;
    private final String clave;

    public IniciarSesion(String usuario, String clave) {
        this.usuario = usuario;
        this.clave = clave;
    }

    public static IniciarSesion conCredenciales(String usuario, String clave) {
        return Tasks.instrumented(IniciarSesion.class, usuario, clave);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        Map<String, String> body = new HashMap<>();
        body.put("username", usuario);
        body.put("password", clave);

        actor.attemptsTo(
                Post.to("/api/auth/login")
                        .with(request -> request.contentType(ContentType.JSON).body(body))
        );

        // Usamos SerenityRest para verificar el status y guardar los tokens
        if (SerenityRest.lastResponse().statusCode() == 200) {
            actor.remember("accessToken", SerenityRest.lastResponse().jsonPath().getString("accessToken"));
            actor.remember("refreshToken", SerenityRest.lastResponse().jsonPath().getString("refreshToken"));
        }
    }
}