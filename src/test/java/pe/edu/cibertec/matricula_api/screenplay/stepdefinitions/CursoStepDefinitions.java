package pe.edu.cibertec.matricula_api.screenplay.stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import pe.edu.cibertec.matricula_api.screenplay.questions.LaRespuesta;
import pe.edu.cibertec.matricula_api.screenplay.tasks.ConsultarCurso;
import pe.edu.cibertec.matricula_api.screenplay.tasks.ListarCursos;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.Matchers.equalTo;

public class CursoStepDefinitions {

    @When("busca el curso {string}")
    public void buscaElCurso(String codigo) {
        theActorInTheSpotlight().attemptsTo(ConsultarCurso.conCodigo(codigo));
    }


    @When("lista los cursos con modalidad {string}")
    public void listaLosCursosConModalidad(String modalidad) {
        theActorInTheSpotlight().attemptsTo(ListarCursos.porModalidad(modalidad));
    }

    @And("el curso se llama {string}")
    public void elCursoSeLlama(String nombre) {
        theActorInTheSpotlight().should(seeThat(LaRespuesta.campo("nombre"), equalTo(nombre)));
    }

    @And("obtiene {int} cursos")
    public void obtieneCursosPorModalidad(int cantidad) {
        theActorInTheSpotlight().should(seeThat(LaRespuesta.cantidadDeElementos(), equalTo(cantidad)));
    }

    @And("la respuesta cumple el esquema {string}")
    public void laRespuestaCumpleElEsquema(String esquema) {
        theActorInTheSpotlight().should(
                seeThatResponse("cumple el esquema " + esquema,
                        res -> res.body(
                                matchesJsonSchemaInClasspath("schemas/" + esquema)
                        )
                )
        );
    }
}
