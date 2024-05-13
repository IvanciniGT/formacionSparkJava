package com.curso;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.List;

public class DNIUtils {

    private static final String LETRAS_DNI = "TRWAGMYFPDXBNJZSQVHLCKE";

    public static Stream<String> leerFicheroDNIs(String rutaFichero) {
        try {
            return Files.readString(Path.of(rutaFichero)).lines();
        } catch (Exception e) {
            throw new RuntimeException("Error al leer el fichero de DNIs", e);
        }
    }

    public static boolean isDNIValido(String dni) {
        boolean esValido = true;
        // Aplicar un patrón de expresiones regulares para verificar formato.
        if (!dni.matches("^[0-9]{1,8}[A-Z]$")){
            esValido = false;
        }else {
            // Saco la letra y el número para validar la letra
            char letra = dni.charAt(dni.length() - 1);
            String parteNumerica = dni.substring(0, dni.length() - 1);
            int numero = Integer.parseInt(parteNumerica);
            esValido = LETRAS_DNI.charAt(numero % 23) == letra;
        }
        return esValido;
        // Test-First: Primero as pruebas... luego el código
        // Hemos aplicado una metodología: TDD (Test Driven Development)
        // TDD = TEST-FIRST + REFACTORING
    }

    public static List<DNIValidado> validarDNIs(Stream<String> dnis){
        return dnis.map( dni -> new DNIValidado(dni, isDNIValido(dni)))
                   .collect(Collectors.toList());
    } // Porque no voy a mezclar Paradigma Funcional y Orientado a Objetos

    public static class DNIValidado {
        private String dni;
        private boolean valido;

        public DNIValidado(String dni, boolean valido) {
            this.dni = dni;
            this.valido = valido;
        }

        public String getDni() {
            return dni;
        }

        public boolean isValido() {
            return valido;
        }
    }
}
