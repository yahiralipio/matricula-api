package pe.edu.cibertec.matricula_api.screenplay.questions;

import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class HistorialAcademico implements Question<Double> {

    private final String campo;

    public HistorialAcademico(String campo) {
        this.campo = campo;
    }

    public static HistorialAcademico elPromedio() {
        return new HistorialAcademico("promedioPonderado");
    }

    public static HistorialAcademico losCreditosAprobados() {
        return new HistorialAcademico("creditosAprobados");
    }

    @Override
    public Double answeredBy(Actor actor) {
        // Imprime la respuesta para depurar
        System.out.println("RESPUESTA HISTORIAL: " + SerenityRest.lastResponse().asString());

        // Intentamos leer el campo directamente
        Double valor = SerenityRest.lastResponse().jsonPath().getDouble(campo);

        if (valor == null) {
            // Si no está en la raíz, intentamos rutas comunes
            valor = SerenityRest.lastResponse().jsonPath().getDouble("data." + campo);
        }
        if (valor == null) {
            valor = SerenityRest.lastResponse().jsonPath().getDouble("historial." + campo);
        }

        return valor;
    }
}