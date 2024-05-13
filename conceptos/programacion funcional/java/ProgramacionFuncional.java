// Y en JAVA 1.8 aparece un paquete nuevo, dentro del API de JAVA, que nos ofrece
// tipos de datos (interfaces "funcionales") que nos permiten declarar variables que apunten 
// a funciones: java.util.function

import java.util.function.*;
// Dentro de este paquete encontramos un huevo de interfaces:
// Consumer<T>      Una función que acepta un dato de tipo T y no devuelve nada (función void)
    //                  (public|private|protected) void <NOMBRE_FUNCION>(T dato){
    //                      // Código       
    //                  }    // ESTO SERA UN CONSUMER<T>
// Supplier<R>      Una función que no acepta datos y devuelve un dato de tipo R
    //                  (public|private|protected) R <NOMBRE_FUNCION>(){
    //                      // Código       
    //                  }    // ESTO SERA UN SUPPLIER<R>
// Function<T,R>    Una función que acepta un dato de tipo T y devuelve un dato de tipo R
    //                  (public|private|protected) R <NOMBRE_FUNCION>(T dato){
    //                      // Código       
    //                  }    // ESTO SERA UN FUNCTION<T,R>
// Predicate<T>     Una función que acepta un dato de tipo T y devuelve un booleano
    //                  (public|private|protected) boolean <NOMBRE_FUNCION>(T dato){
    //                      // Código       
    //                  }    // ESTO SERA UN PREDICATE<T>

// HASTA JAVA 1.8, en java no se admitía la programación funcional... cosa... 
// que TODO EL RESTO DE LENGUAJES DE PROGRAMACION DE USO GENERAL
// SI ADMITIAN: Python, Ruby, JavaScript, C#

// Esto es muy relevante. Me permite crear programas más sencillos, más fáciles de leer y de mantener que usando otros paradigmas de programación

// De hecho , desde JAVA 1.8, el API de java está migrando hacia la programación funcional.
// Cada vez más clases tienen métodos que aceptan funciones como argumentos

public class ProgramacionFuncional{
    public static void main(String[] args){
        Consumer<String> funcionSaludadora = ProgramacionFuncional::saluda;
        Function<String,String> funcionGeneradoraSaludoFormal = ProgramacionFuncional::funcionGeneradoraSaludoFormal;
        // En JAVA 1.8, aparece un nuevo operador: "::" que permite referenciar a una función declarada en un objeto o una clase

        funcionSaludadora.accept("Federico");                                   // AQUI EJECUTO LA FUNCION DESDE LA VARIABLE
        String resultado = funcionGeneradoraSaludoFormal.apply("Federico");     // AQUI EJECUTO LA FUNCION DESDE LA VARIABLE
        System.out.println(resultado);

        imprimirSaludo(ProgramacionFuncional::funcionGeneradoraSaludoFormal, "Federico");
        imprimirSaludo(ProgramacionFuncional::funcionGeneradoraSaludoInformal, "Federico");
        
        // En JAVA 1.8, se añade un segundo operador.... el operador FLECHA -> que permite definir expresiones LAMBDA.
        // Qué es una expresión lambda?
        // Una expresión lambda... es: LO PRIMERO DE TODO UNA EXPRESION!!!!
        // Qué es una expresión (en un lenguaje de programación)?
        String texto = "HOLA"; // STATEMENT= Declaración, Sentencia(=FRASE)
        int    numero = 7 + 9; // Otro STATEMENT
                        /////   Eso es una expresión: UN trozo de código que devuelve un valor.
        // Una lambda, por ser una expresión, es un trozo de código que devuelve un valor.
        // La pregunta es qué valor? LA REFERENCIA A UNA FUNCIÓN ANONIMA QUE DEFINIMOS DENTRO DE LA PROPIA EXPRESIÓN LAMBDA

        BiFunction<Integer, Integer, Integer> miOperacion  = ProgramacionFuncional::sumar;
        BiFunction<Integer, Integer, Integer> miOperacion2 = (Integer numero1, Integer numero2) -> {
                                                                    return numero1 + numero2;
                                                             }; // Declaro la función y devuelvo una referencia a ella
        // Pregunta.. qué tipo de dato devuelve mi función anónima? Integer... 
        // por qué? Porque cuando sumo numero1 + numero2, el resultado es un Integer
        // Y JAVA, que no es gilipollas, también lo sabe... y por eso, es capaz de INFERIRLO! y me evito poner que devuelve INTEGER (1)
        // De hecho, en JAVA, que cada vez se hace más listo (tarde... pero bueno) cada vez se infieren más cosas.
        var miNumero = 3; // Desde JAVA 11... sin problema... 
        // El 3? que tipo tiene? int... por qué? Porque se infiere? NO... porque es un número coño!
        // Y la variable miNumero, tiene tipo? SI... JAVA es un lenguaje de tipado ESTATICO... y por cojones las variables tienen tipo.
        // JAVA NO ES JS... Ni la palabra var en JAVA es igual a la palabra var en JS.
        // Y qué tipo tiene la variable miNumero? Se infiere del dato con que se inicializa: INT
        // miNumero = "HOLA"; ESTO SERIA ERROR

        // Con las lambdas se puede acortar mucho la sintaxis... principalmente por inferencia de tipos... pero también por otras característcais propias de la sintaxis de JAVA:
        BiFunction<Integer, Integer, Integer> miOperacion3 = (numero1, numero2) -> {
                                                                        return numero1 + numero2;
                                                             }; 
        // Y numero1 y numero2 (que son variables)? tiene tipo de datos? SI... son variables.. y en JAVA TODA VARIABLE TIENE TIPO DE DATOS! ES UN LENGUAJES DE TIPADO ESTATICO
        // Y qué tipo de dato tienen? Integer... ya que se INFIERE... en este caso desde la definición de la variable.
        // Pero aún podría acortarla más...
        BiFunction<Integer, Integer, Integer> miOperacion4 = (numero1, numero2) -> numero1 + numero2;

        System.out.println(miOperacion .apply(3, 5));
        System.out.println(miOperacion2.apply(3, 5));
        System.out.println(miOperacion3.apply(3, 5));
        System.out.println(miOperacion4.apply(3, 5));

        // (numero1, numero2) -> numero1 + numero2
        // Declaro una función que recibe dos Integer (Se infiere de la variable a la que se asigna) 
        // y devuelve un Integer (Se infiere de la operación que hago con los dos datos de entrada)
        // y obtengo una referencia a esa función anónima que he declarado en la propia expresión lambda.
        // Función que hemos definido en tiempo de EJECUCION... y que se ha guardado en memoria RAM! 
    }   
                //(1) Al declararla como lambda, me evito poner esto... JAVA lo infiere.
                // v
    public static int sumar(int numero1, int numero2){ // BiFunction<Integer, Integer, Integer>
        return numero1 + numero2;
    }

    public static void saluda(String nombre){   // CONSUMER<String>
        System.out.println("Hola " + nombre);
    }

    // BiConsumer<Function<String,String>,String>    Una función que acepta dos datos de tipo T y U y devuelve un dato de tipo R
    public static void imprimirSaludo(Function<String,String> funcionGeneradoraSaludo, String nombre){
        String saludo = funcionGeneradoraSaludo.apply(nombre);
        System.out.println(saludo);
    }

    public static String funcionGeneradoraSaludoFormal(String nombre){  // FUNCTION<String,String> El primer String es el tipo del argumento
        return "Buenos días " + nombre;
    }
    public static String funcionGeneradoraSaludoInformal(String nombre){ // FUNCTION<String,String> El primer String es el tipo del argumento
        return "Hola " + nombre;
    }
}

// Imaginad que monto una interfaz Diccionario... de forma que a un diccionario le pueda pedir las definiciones de una palabra.
// Entendemos una definición como un texto (String)

/*

public interface Diccionario{
    boolean tienesDefinicionesDe(String palabra);
    //List<String> getDefiniciones(String palabra);

        // Le paso a la función la palabra "MELÓN"... asumiendo que estoy trabajando con un Diccionario de ESPAÑOL, que me devolvería?
        // - Devolver una lista con loas definiciones (STRINGs) de "MELON": ["Fruta del melonero", "Persona con pocas luces (fig.)"]
        // Le paso a la función la palabra "ARCHILOCOCO"... asumiendo que estoy trabajando con un Diccionario de ESPAÑOL, que me devolvería?
        // NPI... opciones:
        // - Devolver una lista vacía   \ Ambos comportamientos son ambiguos. Viendo la firma (signatura) de la función
        // - Devolver null              / puedo saber, lo que me devolverá la función si no encuentra la palabra? Y entonces? Me voy a la docu? al código? En serio??? Año 2024???
        //                                  Y viendo la firma no se como se comporta??? No tiene sentido.
        // - Lanzar un NoSuchWordException ... No sería ambiguo... ya que ( throws NoSuchWordException) pero... es hacer un uso ILEGITIMO de las excepciones.
        //          Las excepciones están en un lenguaje para tratar casos excepcionales... no para controlar el flujo de un programa.
        //          Si no encuentro una palabra en un diccionario... no es un caso excepcional... es un caso NORMAL.
        //          Una excepción es muy cara de generar: Lo primero que debe hacer la JVM es un volcado del thread stack.
        //          Es más... si una palabra no se encuentra... la función ha podido ejecutarse sin error? SI.. dice... no tengo definiciones.

        // Para resolver este escenario, en JAVA 1.8 se añade una clase nueva: Optional<T>... que ES LA UNICA OPCION ACEPTABLE desde Java 1.8
    Optional<List<String>> getDefiniciones(String palabra);
        // Un optional es una caja... que puede tener algo dentro o no... opcionalmente.
        // miOptional.isPresent() ... me dice si viene algo en la caja
        // miOptional.isEmpty()   ... me dice si no viene nada en la caja
        // miOptional.get()       ... me da lo que hay en la caja... si hay algo

        // Desde java 1.8, está considerado una MUY MUY MUY MALA PRÁCTICA devolver null en una función. El Sonar me lo escupe!
        // Y el optional me quita la ambigüedad.


    // Desde JAVA 1.8, éste código me lo tiran a la cara!!!!
    // SonarQube? Es una herramienta de calidad de código.

    // Si alguien sabe explicarme el comportamiento (lo que devuelve) de esta función... se puede ir de la clase!
}

*/