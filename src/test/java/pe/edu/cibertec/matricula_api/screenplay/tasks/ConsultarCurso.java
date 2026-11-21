package pe.edu.cibertec.matricula_api.screenplay.tasks;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Get;

public class ConsultarCurso implements Task {

    private final String codigo;

    public ConsultarCurso(String codigo) {
        this.codigo = codigo;
    }

    public static ConsultarCurso conCodigo(String codigo) {
        return new ConsultarCurso(codigo);
    }

    @Override
    @Step("{0} consulta el curso #codigo")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Get.resource("/api/cursos/{codigo}")
                        .with(req -> req.pathParam("codigo", codigo))
        );
    }
}
