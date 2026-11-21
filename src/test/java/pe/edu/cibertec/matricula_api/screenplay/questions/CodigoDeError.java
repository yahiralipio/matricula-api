package pe.edu.cibertec.matricula_api.screenplay.questions;

import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class CodigoDeError implements Question<String> {

    public static CodigoDeError obtenido() {
        return new CodigoDeError();
    }

    @Override
    public String answeredBy(Actor actor) {
        // Usamos SerenityRest para obtener el body de la última respuesta
        return SerenityRest.lastResponse().jsonPath().getString("codigo");
    }
}