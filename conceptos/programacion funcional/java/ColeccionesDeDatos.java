import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ColeccionesDeDatos {
    
    public static void main(String[] args) {
        // Listas
        List<String> lista = new ArrayList<>();
        lista.add("Hola");
        lista.add("Mundo");
        lista.add("Java");
        
        // Quiero iterar la lista. Pre java 1.5
        for (int i = 0; i < lista.size(); i++) {
            System.out.println(lista.get(i));
        }

        // Pre java 1.8
        for (String elemento : lista) {
            System.out.println(elemento);
        }

        // Desde java 1.8, puedo usar programa funcional
        lista.forEach( ColeccionesDeDatos::imprimir );

        lista.forEach( (String texto)  -> {
            System.out.println(texto);
        } );

        lista.forEach( System.out::println );


        lista.stream()
             .map(                                            // Anoto que se genere un nuevo Stream, donde cada elemento será el resultado de aplicar
                                                              //   la función de transformación sobre cada elemento del Stream original
                    (String texto) -> texto.toUpperCase()     // Declaro una función que recibe un texto y lo devuelve en mayúsculas
              )
             .filter(
                    texto          -> texto.contains("A")
              )
             .collect(Collectors.toList())                    // Cambiar de un Stream a una Colection tradicional (List, Set, Map, etc.)
             .forEach(System.out::println);                   // Aplico una función sobre cada elemento del Stream para que se imprima en consola   
                                                                    // Este a su vez es el que dispara la generación de los elementos del Stream anterior.
    }

    private static void imprimir(String texto) { // Consumer<String>
        System.out.println(texto);
    }

}
