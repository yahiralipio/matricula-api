package pe.edu.cibertec.matricula_api.screenplay.tasks;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Patch;

public class AnularMatricula implements Task {

    private final long id;

    public AnularMatricula(long id) {
        this.id = id;
    }

    public static AnularMatricula conId(long id) {
        return new AnularMatricula(id);
    }

    @Override
    @Step("{0} anula la matricula #id")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Patch.to("/api/matriculas/{id}/anular")
                        .with((req -> req.pathParam("id", id)))
        );
    }
}
