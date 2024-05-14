package com.curso;

import java.util.stream.IntStream;

public class EstimarPi {

    public static double estimarPi(int numeroTotalDeDardos){
        return IntStream.range(1, numeroTotalDeDardos)
                //.parallel() // En este caso, va más lento con múltiples hilos... YA QUE
                            // le estoy metiendo una grna sobrecarga al SO... para el reparto de tareas entre los cores...
                            // Tarda más tiempo el SO en decidir a qué core le manda un trabajo, que el core en ejecutarlo (Son trabajos muy simples)
                            // En este caso, por la naturaleza del trabajo que realizamos, no nos compensa paralelizar!... o al menos de esta forma.

                            // Quien lleva el trabajo a un core es un hilo gestionado por el SO.
                            // En un proceso... puedo tener muchos hilos... y obtener paralelización
                            // Otra forma de paralelizar trabajos es abrir varios procesos... cada uno con 1 hilo => ESTO ES LO QUE HARÁ SPARK

                .mapToObj( numeroDeDardo     -> new Double[]{Math.random(), Math.random()} )
                .map(      coordenadas       -> Math.sqrt(coordenadas[0]*coordenadas[0] + coordenadas[1]*coordenadas[1]))
                .filter(    distanciaAlCentro -> distanciaAlCentro <=1 )
                .count() * 4. / numeroTotalDeDardos;
    }
    // Cuantas CPUS / Cores estoy usando en mi programa? 1... Si solo tengo 1 hilo: MAIN_THREAD
    // Si tengo 4 cores... y 2 procesos por core... 8 VCPUs...
    // Lo máximo que voy a ver el uso de CPU es al 12.5%
    public static void main(String[] args){
        final int NUMERO_DE_DARDOS = 1000 * 1000 * 1000;
        System.out.println("Mi estimación de pi vale: "+estimarPi(NUMERO_DE_DARDOS));
    }

}
