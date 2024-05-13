package com.curso;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class DNIUtilsTest {
    private static final String nombreArchivoPruebas =  "dnis.txt";
    private static final String ruta = DNIUtilsTest.class.getClassLoader().getResource(nombreArchivoPruebas).getPath();

    @Test
    void leerFicheroDNIs() {

       Stream<String> resultadoLecturaFichero = DNIUtils.leerFicheroDNIs(ruta);

       List<String> dnisLeidos = resultadoLecturaFichero.collect(Collectors.toList());

       assertEquals(2, dnisLeidos.size(), "MAL... No se han leído los DNIs esperados");
       assertEquals("12345678T", dnisLeidos.get(0), "MAL... El primer DNI no es el esperado");
       assertEquals("23000000T", dnisLeidos.get(1), "MAL... El segundo DNI no es el esperado");
    }

    @Test
    void isDNIValido() {
        String dniValido = "2300000T";
        boolean respuesta = DNIUtils.isDNIValido(dniValido);
        assertTrue(respuesta, "MAL... El DNI debería haber sido dado por válido");
    }
    @Test
    void isDNIInvalido() {
        String dniValido = "2300000J";
        boolean respuesta = DNIUtils.isDNIValido(dniValido);
        assertFalse(respuesta, "MAL... El DNI debería haber sido dado por inválido");
    }

    @Test
    void filtrarDNISValidos() {
        Stream<String> resultadoLecturaFichero = DNIUtils.leerFicheroDNIs(ruta);
        List<DNIUtils.DNIValidado> dnisValidos = DNIUtils.validarDNIs(resultadoLecturaFichero);

        assertEquals(2, dnisValidos.size(), "MAL... No se han leído los DNIs esperados");
        assertFalse(dnisValidos.get(0).isValido(), "MAL... El DNI no se ha dado por bueno");
        assertTrue(dnisValidos.get(1).isValido(), "MAL... El DNI no se ha dado por bueno");
    }
}