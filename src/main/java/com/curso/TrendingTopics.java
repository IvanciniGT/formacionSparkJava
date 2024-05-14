package com.curso;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class TrendingTopics {
    private static final List<String> PALABRAS_PROHIBIDAS = List.of("CACA","CULO","PEDO","PIS");

    public static void main(String[] args) throws IOException {
        String nombreArchivo = "tweets.txt";
        String rutaArchivo = TrendingTopics.class.getClassLoader().getResource(nombreArchivo).getPath();
        List<String> hashtags = Files.readString(Path.of(rutaArchivo))
                .lines()                                                                         // Para cada linea del fichero (tweet)
                .map(    tweet       -> tweet.replace("#"," #")              )  // Añado un espacio delante de las almohadillas     // Stream<String>... siendo cada String un tweet
                .map(    tweet       -> tweet.split("[.,;:()<>{} [@!¡¿? -]'\"]+")       )  // Separo las palabras de los tweets                // Stream<String[]> siendo cada String[] las palabras de un tweet
                .flatMap( Arrays::stream                                                       )  // Consolido las palabras de todos los tweets       // Stream<String> siendo cada String una palabra de un tweet
                .filter(  palabra     -> palabra.startsWith("#")                               )  // Me quedo con los hashtags                        // Stream<String> siendo cada String un hashtag
                .map(    hashtag     -> hashtag.substring(1)                        )  // Quito las almohadillas                           // Stream<String> siendo cada String un hashtag sin el #
                .map(    String::toUpperCase                                                  )  // Convierto a mayúsculas                          // Stream<String> siendo cada String un hashtag en mayúsculas
                //.filter(  TrendingTopics::esHashtagValido                                    )  // Stream<String> siendo cada String un hashtag no prohibido
                .filter(  hashtag -> PALABRAS_PROHIBIDAS.stream().noneMatch(hashtag::contains) )  // Quito los hashtag que contienen alguna de las palabras prohibidas
                //.collect(Collectors.toList())
                .toList();                                                                       // Lo devuelvo como lista
        System.out.println(hashtags);
    }

    public static boolean esHashtagValido(String hashtag) {
        // Mirar si el hashtag contiene alguna de las palabras prohibidas
        // Para cada palabra prohibida, ver si está contenida en el hashtag
        // En cuantito alguna palabra prohibida esté en el hashtag, el hashtag se da por inválido
        /*return PALABRAS_PROHIBIDAS.stream()                                                     // Stream<String> donde cada String es una palabra PROHIBIDA
                                                                                                // ("CACA","CULO","PEDO","PIS")
                           .filter(palabraProhibida -> hashtag.contains(palabraProhibida))       // Stream<String> donde cada String es una palabra PROHIBIDA CONTENIDA
                                                                                                // ("CACA")
                           .count()                                                             // Número de palabras prohibidas contenidas en el hashtag
                            == 0;
         */
        //return PALABRAS_PROHIBIDAS.stream().anyMatch(palabraProhibida -> hashtag.contains(palabraProhibida));
        return PALABRAS_PROHIBIDAS.stream().anyMatch(hashtag::contains);

    }

}
