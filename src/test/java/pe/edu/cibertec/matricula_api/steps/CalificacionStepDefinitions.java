package pe.edu.cibertec.matricula_api.steps;

import io.cucumber.java.es.*;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.rest.interactions.Get;
import pe.edu.cibertec.matricula_api.screenplay.questions.HistorialAcademico;
import pe.edu.cibertec.matricula_api.screenplay.tasks.CalificarMatricula;
import pe.edu.cibertec.matricula_api.screenplay.tasks.ConsultarHistorial;
import pe.edu.cibertec.matricula_api.screenplay.tasks.IniciarSesion;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;

public class CalificacionStepDefinitions {

    private static Integer idMatriculaGlobal = null;
    public static String tokenAlumnaGlobal = null;

    @Dado("que el docente {string} inicia sesión con la clave {string}")
    public void docenteIniciaSesion(String usuario, String clave) {
        OnStage.setTheStage(new OnlineCast());
        theActorCalled("Docente").whoCan(
                pe.edu.cibertec.matricula_api.screenplay.ConfiguracionApi.paraElActor()
        );
        theActorInTheSpotlight().attemptsTo(
                IniciarSesion.conCredenciales(usuario, clave)
        );
    }

    @Dado("que la alumna {string} inicia sesión con la clave {string}")
    public void alumnaIniciaSesion(String usuario, String clave) {
        OnStage.setTheStage(new OnlineCast());
        theActorCalled("Alumna").whoCan(
                pe.edu.cibertec.matricula_api.screenplay.ConfiguracionApi.paraElActor()
        );
        theActorInTheSpotlight().attemptsTo(
                IniciarSesion.conCredenciales(usuario, clave)
        );
        tokenAlumnaGlobal = theActorInTheSpotlight().recall("accessToken");
    }

    @Dado("que la alumna {string} consulta sus matrículas para obtener el id de {string}")
    public void alumnaConsultaMatriculas(String usuario, String curso) {
        String token = tokenAlumnaGlobal;
        theActorInTheSpotlight().attemptsTo(
                Get.resource("/api/matriculas")
                        .with(request -> request.header("Authorization", "Bearer " + token)
                                .queryParam("curso", curso))
        );
        Integer id = SerenityRest.lastResponse().jsonPath().getInt("find { it.codigoCurso == '" + curso + "' }.id");
        if (id == null) {
            throw new RuntimeException("No se encontró la matrícula para el curso " + curso);
        }
        idMatriculaGlobal = id;
    }

    @Cuando("consulta sus matrículas para obtener el id de {string}")
    public void consultaMatriculas(String curso) {
        String token = tokenAlumnaGlobal;
        theActorInTheSpotlight().attemptsTo(
                Get.resource("/api/matriculas")
                        .with(request -> request.header("Authorization", "Bearer " + token)
                                .queryParam("curso", curso))
        );
        Integer id = SerenityRest.lastResponse().jsonPath().getInt("find { it.codigoCurso == '" + curso + "' }.id");
        if (id == null) {
            throw new RuntimeException("No se encontró la matrícula para el curso " + curso);
        }
        idMatriculaGlobal = id;
    }

    @Cuando("registra la nota {string} en la matrícula")
    public void registraNota(String nota) {
        double notaDouble = Double.parseDouble(nota.replace(",", "."));
        if (idMatriculaGlobal == null) {
            throw new RuntimeException("No se ha guardado el id de la matrícula. Revisa el paso anterior.");
        }
        theActorInTheSpotlight().attemptsTo(
                CalificarMatricula.con(idMatriculaGlobal, notaDouble)
        );
    }

    @Cuando("la alumna {string} consulta el historial de {string}")
    public void consultaHistorial(String usuario, String codigoAlumno) {
        theActorInTheSpotlight().attemptsTo(
                ConsultarHistorial.de(codigoAlumno)
        );
    }

    @Entonces("el historial muestra el promedio {string}")
    public void verPromedio(String promedio) {
        double promedioDouble = Double.parseDouble(promedio.replace(",", "."));
        theActorInTheSpotlight().should(
                seeThat("El promedio ponderado", HistorialAcademico.elPromedio(), closeTo(promedioDouble, 0.01))
        );
    }

    @Entonces("el historial muestra {string} créditos aprobados")
    public void verCreditos(String creditos) {
        double creditosDouble = Double.parseDouble(creditos);
        theActorInTheSpotlight().should(
                seeThat("Los créditos aprobados", HistorialAcademico.losCreditosAprobados(), is(creditosDouble))
        );
    }
}