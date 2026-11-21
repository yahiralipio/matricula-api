package pe.edu.cibertec.matricula_api.screenplay.tasks;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Get;

public class ListarCursos implements Task {

    private final String modalidad;

    public ListarCursos(String modalidad) {
        this.modalidad = modalidad;
    }

    public static ListarCursos porModalidad(String modalidad) {
        return new ListarCursos(modalidad);
    }

    @Override
    @Step("{0} lista los cursos con modalidad #modalidad")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Get.resource("/api/cursos")
                        .with(req -> req.queryParam("modalidad", modalidad))
        );

    }
}
