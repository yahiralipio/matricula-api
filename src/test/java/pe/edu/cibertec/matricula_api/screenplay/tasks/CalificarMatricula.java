package pe.edu.cibertec.matricula_api.screenplay.tasks;

import io.restassured.http.ContentType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.rest.interactions.Patch;

import java.util.HashMap;
import java.util.Map;

public class CalificarMatricula implements Task {
    private final int idMatricula;
    private final double nota;

    public CalificarMatricula(int idMatricula, double nota) {
        this.idMatricula = idMatricula;
        this.nota = nota;
    }

    public static CalificarMatricula con(int idMatricula, double nota) {
        return Tasks.instrumented(CalificarMatricula.class, idMatricula, nota);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        String token = actor.recall("accessToken");
        Map<String, Object> body = new HashMap<>();
        body.put("nota", nota);

        actor.attemptsTo(
                Patch.to("/api/matriculas/" + idMatricula + "/nota")
                        .with(request -> request.contentType(ContentType.JSON)
                                .header("Authorization", "Bearer " + token)
                                .body(body))
        );
    }
}