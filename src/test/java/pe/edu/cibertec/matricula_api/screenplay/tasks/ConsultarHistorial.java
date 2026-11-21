package pe.edu.cibertec.matricula_api.screenplay.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.rest.interactions.Get;
import pe.edu.cibertec.matricula_api.steps.CalificacionStepDefinitions;

public class ConsultarHistorial implements Task {
    private final String codigoAlumno;

    public ConsultarHistorial(String codigoAlumno) {
        this.codigoAlumno = codigoAlumno;
    }

    public static ConsultarHistorial de(String codigoAlumno) {
        return Tasks.instrumented(ConsultarHistorial.class, codigoAlumno);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        // Usamos el token de la alumna que guardamos en la variable global
        String token = CalificacionStepDefinitions.tokenAlumnaGlobal;
        actor.attemptsTo(
                Get.resource("/api/alumnos/" + codigoAlumno + "/historial")
                        .with(request -> request.header("Authorization", "Bearer " + token))
        );
    }
}