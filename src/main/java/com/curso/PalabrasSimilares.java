package com.curso;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PalabrasSimilares {

        private static final int DISTANTCIA_MAXIMA_ADMISIBLE = 2;
        private static final int MAXIMO_DE_PALABRAS_A_DEVOLVER = 10;

    public static void main(String[] args)throws IOException{
        String palabra="menchu";
        String nombreFichero = "diccionario.ES.txt";
        String rutaFichero = PalabrasSimilares.class.getClassLoader().getResource(nombreFichero).getPath();
        var todasLasPalabras = PalabrasSimilares.leerPalabrasDelFichero(rutaFichero);

        for(int i =0; i< 50; i++) {
            PalabrasSimilares.buscarPalabrasSimilares("manzana", todasLasPalabras);
            PalabrasSimilares.buscarPalabrasSimilares("pera", todasLasPalabras);
            PalabrasSimilares.buscarPalabrasSimilares("albaricoque", todasLasPalabras);
        }
        long tin= System.nanoTime();
        var similares = PalabrasSimilares.buscarPalabrasSimilares(palabra,todasLasPalabras);
        long tout = System.nanoTime();

        similares.forEach(System.out::println);
        System.out.println("Tiempo que tardamos en buscar la palabra: "+ (tout-tin) + "nanosegundos" );
    }

    private static List<String> buscarPalabrasSimilares(String palabraObjetivo, List<String> todasLasPalabras){
        final String palabraObjetivoNormalizada= normalizar(palabraObjetivo);
        return todasLasPalabras.parallelStream()
                .filter( palabra -> Math.abs(palabra.length() - palabraObjetivo.length())<= DISTANTCIA_MAXIMA_ADMISIBLE)
                .map( palabra -> new PalabraPuntuada(palabra, distanciaLevenshtein(palabra, palabraObjetivoNormalizada)))
                .filter( palabraPuntuada -> palabraPuntuada.distancia <= DISTANTCIA_MAXIMA_ADMISIBLE)
                .sorted(Comparator.comparing( palabraPuntuada-> palabraPuntuada.distancia))
                .limit(MAXIMO_DE_PALABRAS_A_DEVOLVER)
                .map( palabraPuntuada -> palabraPuntuada.palabra )
                .collect(Collectors.toList());
    }

    private static String normalizar(String palabra){
        return palabra.toUpperCase();
    }
    private static class PalabraPuntuada {
        public String palabra;
        public int distancia;
        PalabraPuntuada(String palabra, int distancia){
            this.palabra=palabra;
            this.distancia=distancia;
        }
    }

    private static List<String> leerPalabrasDelFichero(String rutaFichero) throws IOException {
        return Files.readString(Path.of(rutaFichero))
                .lines()
                .map(linea -> linea.split("="))
                .map( partes -> partes[0])
                .map( PalabrasSimilares::normalizar)
                .collect(Collectors.toList());
    }


    private static int distanciaLevenshtein(String a, String b) {
        int [] costs = new int [b.length() + 1];
        for (int j = 0; j < costs.length; j++)
            costs[j] = j;
        for (int i = 1; i <= a.length(); i++) {
            // j == 0; nw = lev(i - 1, j)
            costs[0] = i;
            int nw = i - 1;
            for (int j = 1; j <= b.length(); j++) {
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]), a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }
        return costs[b.length()];
    }

}
