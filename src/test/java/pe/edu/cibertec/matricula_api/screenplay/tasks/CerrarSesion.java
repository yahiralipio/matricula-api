package pe.edu.cibertec.matricula_api.screenplay.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.rest.interactions.Post;

public class CerrarSesion implements Task {

    public static CerrarSesion actual() {
        return Tasks.instrumented(CerrarSesion.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        String token = actor.recall("accessToken");
        actor.attemptsTo(
                Post.to("/api/auth/logout")
                        .with(request -> request.header("Authorization", "Bearer " + token))
        );
    }
}