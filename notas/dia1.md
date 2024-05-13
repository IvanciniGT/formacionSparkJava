
# Que vamos a hacer en esta formación?

- Entender el concepto de programación funcional, y cómo se usa en JAVA
---
- Entender el concepto de programación MAP-REDUCE. Desde JAVA
---
- Nos llevaremos lo que hagamos en JAVA con MAP-REDUCE a SPARK
- Entender qué es Apache SPARK = Framework para programación MAP-REDUCE distribuida.
- Aprender a usar una librería que tiene Apache Spark, que nos facilitará la escritura de código: SQL.
- Haremos distintos ejemplos.
- Formatos de almacenamiento de información en el mundo BIG-DATA: Parquet, Avro.

---
# Programación funcional, y cómo se usa en JAVA

Java es un lenguaje de programación... de tipado estático, compilado e interpretado, y que soporta varios paradigmas de programación.

## Paradigmas de programación

Es solo una forma de usar el lenguaje... para comunicar unos conceptos... También lo tenemos en Español.
- Imperativo            Es cuando escribo un programa como una secuencia de órdenes que deben ejecutarse secuencialmente.
  En ocasiones a nosotros nos interesa romper esa secuencialidad en cuanto a la ejecución de las órdenes.
  Y los lenguajes nos ofrecen formas de hacerlo: IF, WHILE, FOR, SWITCH, etc.
- Procedural            Cuando el lenguaje nos permite agrupar órdenes (lenguaje imperativo) dentro de unidades reutilizables
  (que normalmente llamamos: funciones, métodos, procedimiento, subrutinas) e invocarlas posteriormente,
  decimos que tenemos un lenguaje que soporta el paradigma de programación procedural.
- Funcional             Cuando el lenguaje me permite que una variable APUNTE (REFERENCIE) a una función...
  y posteriormente ejecutar la función desde la variable.
  CONCEPTO CHORRA DE COJONES !!!! Sencillo a más no poder.
  El problema no es lo que es la programación funcional (que conceptualmente es una chorrada, ya lo habeis visto...)
  La cuestión es lo que puedo empezar a hacer gracias a eso... Y AQUI ES DONDE NOS EXPLOTA LA CABEZA!

                        Desde el momento en que el lenguaje me permite que una variable apunte a una función y ejecutar la función desde la variable, puedo:
                        - Puedo crear funciones que acepten funciones como argumentos
                        - Puedo crear funciones que devuelvan funciones como resultado.

- Orientado a objetos   Todo lenguaje de programación nos permite trabajar con DATOS. Esos datos pueden ser de distinta naturaleza.
  Todo lenguaje de programación permite trabajar con ciertos tipos de datos: LOS DATOS TIENE TIPOS.
  - Números
  - Textos
  - Fechas
  - Listas
  - ...
  Los datos, en función a su tipo, tiene características:
  ¿Por qué se caracteriza?                    ¿ Qué puedo hacer con él?
  - Texto:    Su secuencia de caracteres                      A mayúsculas!!! o a minúsculas!!! ...
  o quitale los espacios en blanco circundantes.
  - Número:   Su valor numérico                               Sumar, restar, valor absoluto, redondear, ...      
  - Fecha:    Un día, un mes y un año                         En qué día de la semana cae?
  Está en un año bisieto?
  (que además guardan relaciones entre si)

                        Cuando el lenguaje me permite definir mis propios tipos de datos, con sus propias características y operaciones
                        decimos que soporta el paradigma de programación orientado a objetos.
                            - Persona:  Nombre, Apellidos, Fecha de nacimiento, DNI     Eres mayor de edad?
                                                                                        Cuántos años tienes?
                        A esos tipos de datos, les denominamos: CLASES

                        CUIDADO, no confundamos el tipo de datos, con el tipado de datos.

                        ```python
                           texto = "HOLA"  # str
                           numero = 17     # int
                           logico = True   # bool
                        ```

                        ```js
                           let texto = "HOLA"; // string
                           let numero = 17;    // number
                           let logico = true;  // boolean
                        ```

                        ```java
                           String texto = "HOLA";
                           int numero = 17;
                           boolean logico = true;
                        ```

                        En los lenguajes de programación usamos variables.
                        ```java
                           String texto = "HOLA";     // Asignamos la palabra "HOLA" a una variable String
                                                      // Las variables, al menos tal y como las entendemos en JAVA, PY, JS NO SON
                                                      // cajitas donde meto cosas... sin referencias (punteros) a datos almacenados
                                                      // EN ALGÚN SITIO.
                        ```
                        Cuántas cosas hace esa linea? Cuál es el primero trozo que se ejecuta.
                        - "HOLA"        Colocar un objeto nuevo de tipo String en memoria RAM, con valor "HOLA"
                        - String texto  Crear una nueva variable llamada texto, que puede apuntar, referenciar a objetos en memoria de tipo STRING.
                        - =             Asignar la variable "texto" al dato que tengo en memoria ("HOLA")
                                        LA VARIABLE APUNTA AL DATO... El dato NO LO METO EN LA VARIABLE
                        ```java
                        texto = "ADIOS";
                        ```
                        Qué ocurre aquí?
                        - "ADIOS"      Colocar un objeto nuevo de tipo String en memoria RAM, con valor "ADIOS"
                                       El valor(dato) "ADIOS" se coloca en memoria donde antes estaba el "HOLA" o en otro sitio?
                                       EN OTRO SITIO... y llegados a este punto, en RAM hay 2 cosas: "HOLA" y "ADIOS".
                        - texto        Desasigna la variable de su valor anterior.
                        - =            Muevo en postit(la variable) a otro sitio (lo vario.. es una variable).
                                       HAGO QUE APUNTE A UN NUEVO VALOR.
                                       En este punto, "HOLA" queda huérfano de postit (no hay ninguna variable que lo apunte) ...
                                       y se convierte en BASURA (GARBAGE)... y quizás o quizás no (NPI) en un momento dado entre el GARGAGE COLLECTOR a eliminarlo y liberar ese espacio de memoria.
                        Ahora podemos hablar de lenguajes de tipado ESTATICO vs DINAMICO.
                        - ESTATICO: Cuando en el lenguaje, las variables tienen un tipo de dato asociado.
                                    De forma que la variable solo pueda apuntar a datos de ese tipo.
                                    Como si tuviera postit de COLORES (amarilllos, blancos, azules, rosas...) 
                                    ```java
                                        String texto = "HOLA";
                                        texto = 3;  // ERROR: LA VARIABLE texto solo puede apuntar a objetos de tipo String
                                    ```
                        - DINAMICO: Cuando en el lenguaje, las variable NO TIENE TIPOS DE DATOS.
                                    Y las variables pueden apuntar a datos de cualquier tipo. 
                                    ```py
                                        texto = "HOLA"
                                        texto = 3       # CUELA SIN PROBLEMA
                                    ```


- Declarativo!

Felipe, pon una silla debajo de la ventana! IMPERATIVO

# Programación MAP-REDUCE. Desde JAVA

Es una forma de escribir (plantear) un algoritmo... diferente... pensada para la manipulación de colecciones de datos.

Originalmente, surge de un paper de GOOGLE, para procesar grandes cantidades de datos en paralelo en una infraestructura distribuida (granjas de servidores).

Pero ese mismo modelo, se puede aplicar en muchos lenguaje de programación, y a cualquier cantidad de datos.... y trabajando sobre una sola máquina.

Nuestro siguiente paso es aprender a diseñar programas con el modelo MAP-REDUCE, desde JAVA, usando los recursos de una máquina local.
Ese mismo programa, posteriormente pediremos a Spark que lo ejecute en un cluster de máquinas.... y esto será más o menos fácil... aunque habrá trucos que aprender.

Lo complejo estará en aprender el modelo MAP-REDUCE... y eso es lo que vamos a hacer.

En JAVA 1.8, además, añaden todo un nuevo paquete para trabajar con algoritmos de MAP-REDUCE: java.util.stream... y un nuevo tipo de objeto: Stream.

## Qué es un Stream?

Un Stream de Java 1.8 es un nuevo tipo de Collection, que permite operaciones de tipo MAP-REDUCE.
Que tipos Collection teníamos en java anteriormente: List, Set, Map...
Pues ahora tenemos también Stream.

Un Stream es como un Set... pero dentro tiene funciones MAP-REDUCE.

Qué son funciones MAP-REDUCE?
Son funciones que clasificamos en 2 grandes tipos, que vamos a analizar por separado:
- Funciones tipo MAP        Una función que partiendo de una colección de datos que soporta programación de tipo MAP-REDUCE, genera una nueva colección de datos que también soporta programación de tipo MAP-REDUCE.
- Funciones tipo REDUCE     Una función que partiendo de una colección de datos que soporta programación de tipo MAP-REDUCE, genera algo (si es que lo genera) que no soporta programación de tipo MAP-REDUCE.

Para que quede más claro... en JAVA, la colección de datos que soporta programación de tipo MAP-REDUCE: Stream.

- Funciones tipo MAP        Una función que aplicada sobre un Stream devuelve otro Stream.
  Estas funciones, las tipo MAP, se ejecutan en modo lazy (PEREZOSO)...
  es decir, no se ejecutan hasta que no es necesario su resultado.
  Hay muchas funciones de tipo map... y toman ese nombre (TIPO MAP) precisamente de una de ellas:
  - MAP           Aplicar una Function a cada elemento del Stream
  para generar un nuevo Stream, donde cada elemento del nuevo Stream
  es el resultado de aplicar la función al elemento original.

                                        .stream()->        .map( funcionDeTransformacion ) ->
                                                                Function<String, T>
                                List<String>     Stream<String>         Stream<T>
                                "hola"              "hola"      ->        "HOLA"
                                "mundo"             "mundo"     ->        "MUNDO"
                                "java"              "java"      ->        "JAVA"

                                                            funcionDeTransformación: A Mayúsculas

                            - FILTER         Recibe un Predicate ( es una función que recibe un parámetro y devuelve un booleano)
                                                       Son las típicas funciones isXXX(dato) o hasXXX(dato) que devuelven un booleano. 
                                             Genera un Stream que contendrá solo los elementos cue al ser suministrados al Predicate devuelvan True
                            - SORT
                            - FLATMAP
- Funciones tipo REDUCE     Una función que aplicada sobre un Stream devuelve algo que no es un Stream.
  Estas funciones, las tipo REDUCE, se ejecutan en modo eager (ANSIOSO)...
  es decir, se ejecutan en cuanto se invoca la función.
  Hay muchas funciones de tipo reduce... y toman ese nombre (TIPO REDUCE) precisamente de una de ellas:
  - REDUCE
  - FOREACH       Aplicar un Consumer a cada elemento del Stream
  - COUNT         Saber cuantos elementos tengo en el Stream
  - SUM           Sumar todos los elementos del Stream
  - LENGTH        Saber cuantos elementos tengo en el Stream
  - COLLECT       Convierte un Stream en otro tipo de colección (List, Set, Map)

Esa combinación [Funciones de tipo lazy + Funciones de tipo eager] va a generar un efecto dominó... donde voy colocando fichas.. y cuando cae una, se lleva por delate a las demás.

En un algoritmo de tipo MAP-REDUCE, siempre empiezo con una función de tipo MAP... y termino con una función de tipo REDUCE.
Entre medias, podré tener muchas más funciones de tipo MAP.

    Stream > MAP > MAP > MAP ... > MAP > REDUCE > DATO (no será un Stream)

    Lo que queremos es partiendo de una colección de datos, aplicar una serie de transformaciones (MAP) para obtener el resultado que me interesa.


    - Tengo un archivo de texto... con las palabras de un diccionario:
        Melón=Persona con pocas luces|Fruta del melonero                .split("=") => String[]
        Melocotón=Fruta del melocotonero

        Quiero leer ese archivo... y acabar con un MAP<String, LIST<String>>, con cada palabra y sus significados.
        Cuántas lineas de código (STATEMENTS) calculáis que nos hacen falta para hacer eso? 1... gracias programación funcional MAP-REDUCE... Esa es la potencia de este paradigma.

En JAVA:
- para pasar cualquier coleccion de datos (LIST, SET, MAP) a un stream, lo unico que tengo que haces es aplicar la función
  .stream() a la colección de datos.
- para pasar de un stream a una colección de datos (tradicional), lo que aplico es una función .collect(argumento) sobre el stream.
  A esa función le date como argumento la forma en la que quiero recopilar los datos (Básicamente a quñe tipo de coleeción lo quiero convertir: List, Set, Map)

  Colection TRADICIONAL (set, list, map)  -> .stream() > map|filter|sort|... > Función tipo reduce
  .collect() -> List | Set | Map


---

# BIGDATA


Trabajar con 100 millones de datos, es bigData? NO
Ni coger cosas de 40 BBDD es BigData...

BigData es otro concepto... que tiene que ver con INFRAESTRUCTURA(HARDWARE), Y CON SOFTWARE

Trabajar con una cantidad de datos para conseguir un objetivo que no soy capaz de conseguir con técnicas tradicionales.... y entonces me voy a un enfoque BIDATA.

Trabajar... en genérico... qué quiero con los datos? NPI: Almacenarlos... analizarlos... transmitirlos...
BIGDATA no es sinónimo de ANALIZAR DATOS .. muchos... NI DE COÑA !

    ClashRoyale... 2v2... 4 jugadores
        En un momento dado (un segundo) yo puedo hacer 2 movimientos... (Saco las arqueras... y caballero)
        Eso implica que desde mi teléfono tienen que acabar llegando a los otros 3 jugadores (teléfonos) un mensaje indicando que he jugado a las arqueras y al caballero. (De hecho 2 mensajes)... a los otros 3 jugadores.... Lo que hace un total de 6 mensajes.
        Pero los otros 3 están jugando a la vez... y hacen sus propios movimientos... en total tenemos 6x4 = 24 mensajes en un segundo.

        Si tenemos en cuenta que durante años fué el juego más descargado en la APP STORE... y que en un momento podría haber 50k partidas en paralelo... eso implica en 1 segundo: 24 x 50000 = 1.200.000 mensajes en un segundo.

        Qué servidor existe hoy en día en la industria con capacidad para procesar 1.200.000 mensajes en un segundo? NINGUNO

        Quiero almancenar los mensajes? Es el objetivo? NO
        Los quiero analizar? NO
        SIMPLEMENTE QUIERO TRANSMITIRLOS.... y las técnicas que he usado hasta ahora no me permiten transmitir esa cantidad de mensajes en un segundo. (NO HAY NI SERVIDORES, NI REDES, NI PROGRAMA QUE LO PERMITA)
        Y AHORA QUE?

    Tengo un pincho USB... de 16Gbs... recién sacado del paquete...
        Tengo un archivo de 5 GBs.. lo puedo grabar en mi pincho? NPI.
        Depende del formato con el que esté formateado el pincho... FAT-16 NO PODRIA: 2GBs
        Tengo alternativas: NTFS, EXT4... que me permiten almacenar archivos de más de 4GBs.

        Y si quiero guardar un archivo de 7Pbs... el NFS y el EXT4, se hacen caquita... no puedo!
        Ni siguiera tengo cabinas de almacenamiento con tal capacidad de almacenamiento.
        Y AHORA QUE?

    Quiero guardar la lista de la compra:
        Chorizo:        4
        Lentejas:       1 Paquete
        ...
        200 cosas...   Qué programa uso para crear mi lista de la compra? EXCEL
        200.000  ...   El Excel se hace caquita.... pero me puedo ir a MySQL
        20.000.000 ... MySQL se hace caquita... pero me puedo ir a MS SQL Server
        1.000.000.000  ... MS SQL Server se hace caquita... pero me puedo ir a ORACLE... montado en un Servidor EXADATA
        1.000.000.000.000 ... ORACLE se hace caquita... y ahora QUE?

    Llega un momento que me quedo sin máquina suficientemente potente, sin un sistema de archivos que me permita almacenar datos a mogollón... y sin un programa que me permita trabajar con esos datos. SE ME AGOTAN LAS VIAS TRADICIONALES.


Y Ahora es cuando me planteo un enfoque BIGDATA: Vas a coger en lugar de un maquinón: 2000 (commodity hardware) mierda-máquinas (i7, 16Gbs RAM, 3HDD 3Tbs)
Y pongo esas 200 mierda máquinas a funcionar como si fueran 1. ESO ES BIGDATA!
Eso si... necesito un programa que me permita hacer que esas 2000 mierda-máquinas funcionen como si fueran 1...
y ese programa se llama: APACHE HADOOP

Cuales son las principales responsabilidades de un SO?
- Gestionar la memoria
- Gestionar la CPU
- Asignar la prioridad de los procesos a la CPU

Y es lo que hace HADOOP:
- Usar la memoria, cpu y disco de las 2000 mierda-máquinas como si fueran 1
- Me permite pedirle una operación... y el repartirá ese trabajo para que haga uso de no una CPU local, sino de las 2000 mierda-máquinas... no de mi memoria RAM local... sino de las 2000 mierda-máquinas... no de mi disco duro local... sino de los 6000 discos duros de las 2000 mierda-máquinas.
  HADOOP es "como un SO" pero en un entorno distribuido.

Y al final, para que sirve un SO? PA NA'
Y dentro del mundo bigdata, tenemos tropecientas herramientas que instalo sobre un cluster controlado por HADOOP... y que me permiten hacer cosas con los datos... que antes no podía hacer:
- Bases de datos:
    - Cassandra
    - HBase
    - Hive
    - MongoDB
- Sistemas de mensajería:
    - Kafka
- Herramientas de procesamiento de datos:
    - Apache SPARK
    - Apache STORM
- Analítica de datos:
    - Mahout
---


JAVA es propiedad de Oracle... pero se lo llevo de regalo cuando compró Oracle otra empresa.
Sun Microsystems. DE HECHO ESTA FUE LA MUERTE DE JAVA
Le interesaba otra cosa de Oracle: El hardware que creaba.... con arquitectura SPARC...
Y la compro para poder vender maquinotes gordos: Exadata, Exalogic

    En los 2000.... 
        App web: JAVA
        App escritorio: JAVA (Swing)
        App Android: JAVA
        App software embebido: JAVA (Java micro edition)
            Lenguaje más usado del mundo durante muchos años
    
    24 años después
        App web: 
            Backend:    JAVA (SPRING -> KOTLIN)
            Frontend:   JAVASCRIPT
        App escritorio: C#, VB, ObjectiveC
        App Android:    Kotlin
            Kotlin es una sintaxis alternativa a JAVA para generar BYTE CODE que se ejecuta en la JVM
                .kt -> .class -> .jar -> JVM
                Pero Kotlin, que es un lenguaje creado por la gente de JET BRAINS (IDE de puta madre) que sabe un huevo y medio de gramáticas de lenguajes, resuelve casi todos los problemas de la MIERDA DE SINTAXIS que tiene JAVA (getters y setters)
            Scala... mismo concepto que Kotlin... Solo que Scala es el lenguaje que se usa principalmente en el mundo BIGDATA.
        App software embebido: Python, C
        Hoy en día, JAVA estar en tercer puesto: 
        - JS
        - Python
        - JAVA

```java

// Que las personas no puedan tener una edad <0 ;
public class Persona {

    public String nombre;
    private int edad;

    public Persona(String nombre, int edad) {
        this.nombre = nombre;
        this.setEdad(edad);
    }
    public int getEdad() {
        return edad;
    }
    public void setEdad(int edad) {
        if (edad < 0) { // Y esto lo hago el día 100.
            throw new IllegalArgumentException("La edad no puede ser negativa");
        }
        this.edad = edad;
    }

}

//...Entre el día 1 y el 100.. he tenid a 1000 personas escribiendo código, usando mi API anterior...
// Y al hacer el cambio de arriba, su código ni les compila = 1000 tios/as persiguiendome con un kalashnikov en mano para darme de tiros.

Persona p1= new Persona("Felipe", 45);
p1.nombre = "Menchu";
p1.edad = 46;
System.out.println(p1.nombre + " " + p1.edad);

// Y COMO JAVA NO OFRECE otra forma de añadir esa funcionalidad en el futuro, me dices: 
// PARA FACILITAR EL MNTO DEL PROYECTO, dado que el lenguajes es una puta mierda, CREATE desde el día 0, getters y setters de todas las variables... por si ACASO el dia de mañana.
////////////////////////////////////////////////////



public class Persona {

    private String nombre;
    private int edad;

    public Persona(String nombre, int edad) {
        this.setNombre(nombre);
        this.setEdad(edad);
    }
    // PA QUE???? Estoy protegiendo algo???
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public int getEdad() {
        return edad;
    }
    public void setEdad(int edad) {
        this.edad = edad;
    }

}


Persona p1= new Persona("Felipe", 45);
p1.setNombre("Menchu");
p1.setEdad(46);
System.out.println(p1.getNombre() + " " + p1.getEdad());


```


// ESO ES UNA MALA PRACTICA EN JAVA... Tienes que encapsular... con getters y setters. = MIERDA... MENTIRA !!! QUE NO OS LIEN
// ESO ES UNA MALA PRACTICA EN JAVA...Eso es cierto... pero porque JAVA no ofrece una alternatica a ello...
Cosa que si hace JS, PYTHON, C#.. y todo otro lenguaje de programación que se precie: PROPS


export class Persona{

    constructor(nombre, edad){
        this.nombre = nombre;
        this._edad = edad;
    }
    get edad(){
        return this._edad;
    }
    set edad(edad){
        if (edad < 0) {
            throw new Error("La edad no puede ser negativa");
        }
        this._edad = edad;
    }

}

var p1 = new Persona("Felipe", 45);
p1.nombre = "Menchu";
p1.edad = 46;
console.log(p1.nombre + " " + p1.edad);


---

# MAVEN

Es un concepto mucho más amplio que un gestor de dependencias.. De hecho eso es lo menos importante de MAVEN.

Maven es una herramienta de AUTOMATIZACION de trabajos... Trabajo habituales en proyectos de software...
principalmente en proyectos de software JAVA.

Me ayuda con tareas rutinarias:
- Gestión de dependencias
- Compilación
- Ejecución de pruebas
- Empaquetado
- Mandar mi código a sonar
- Generar una imagen de contenedor con mi aplicación

Esas tareas en maven reciben el nombre de GOALS.

Cada GOAL es ejecutado por un PLUGIN.

Al usar maven para la automatización de tareas en un proyecto, hemos de configurar un archivo pom.xml

proyecto/
src/
main/
java/           Código fuente de mi app.
resources/      Archivos que necesita mi programa: Configuraciones... datos...
test
java/           Código fuente de mis pruebas automatizadas
resources/      Archivos que necesita mis pruebas: Configuraciones... datos...
target/
classes/
test-classes/
miArchivo.jar
pom.xml                 Nuestro archivo de conf. de maven para nuestro proyecto

En el pom.xml definimos:
- Metadatos de mi proyecto:
    - Datos identificativos: LOCALIZADOR DE UN PROYECTO (groupID.artifactID+version)
    - Nombre
    - Descrición
    - Licencia
    - Sitio web
- Properties:
    - Configuraciones para los plugins de maven
    - Variables que yo puedo usar en el proyecto
- Plugins
    - Por defecto, maven ya me viene preconfigurado con una retaila de plugins... ara las operaciones más típicas:
        - compiler
        - resources
        - surefire
        - jar
        - clean
- Dependencias

## Goals por defecto en MAVEN:

- compile:          Compila lo que tengo en src/main/java... y los .class que genera los deja en target/classes
  Copia los archivos de src/main/resources en target/classes
- test-compile:     Compila lo que tengo en src/test/java... y los .class que genera los deja en target/test-classes
  Copia los archivos de src/test/resources en target/test-classes
- test:             Ejecuta los test que tengo en target/test-classes
- package:          Empaqueta la carpeta target/classes en un archivo .jar... que deposita en target/
- install:          Copia mi archivo .jar en mi carpeta .m2
  Para qué? Cuando mi proyecto pueda ser usado por otros proyectos como dependencia.
- clean:            Borra la carpeta target/

## Carpeta .m2

Una carpeta que se crea maven dentro de mi carpeta de usuario c:\Usuario\Ivan\.m2
/home/ivan/.m2
En esa carpeta es donde maven baja librerías de internet... y configura el classpath de mi proyecto, para compilación, test...

---

# Vamos a validar DNIs

Cómo es un DNI de España:
Entre 1 y 8 números (El DNI del rey 15) + UNA LETRA (DÍGITO DE CONTROL DEL DNI)

1234567-W
12345678W
12.345.678 W
00012345W
12345W

## CALCULO DEL DIGITO DE CONTROL

    Número ... lo divido (división entera) entre 23... y me quedo con el resto.

        23.000.001 | 23
                   +----------
                 1   1.000.000
                ^^^
                RESTO... que estará entre qué valores? 0-22  > R
El ministerio de interior nos da una tabla con la que saber que letra corresponde a cada uno de esos 23 números.

## Algoritmos de HASH... De huella!

La letra es una huella del número... que me permite saber si el número es correcto o no.
Un algoritmo de huella (MD5, SHA...)es una función que:
- Dada una entrada (un argumento) produce siempre la misma salida
- La salida es un resumen de los datos de entrada (Desde la salida es imposible regenerar el dato original)
- La probabilidad de que 2 datos diferentes generen la misma salida es lo suficientemente baja para nuestras necesidades.
  Que probabilidad hay que 2 DNIS compartan LETRA? 1/24


Queremos un programa que lea datos (lineas conteniendo un DNI) de un fichero... y me diga las que SON VALIDAS...

En JAVA 11... ya era HORA MACHO !!!!!!
Metieron en la clase Files la función readString(Path)

    String contenido = Files.readString(Path.of("miFichero.txt"));

En JAVA 11... metieron una función en la clase String: .lines() me da un Stream<String> con las lineas...
Directito para aplicar programación funcional.

PASO 1: Un fichero de texto con los DNIS
PASO 2: Leer el fichero -> Stream<String>
PASO 3: Validarlos (qué necesitaré? me crearé una función de VALIDACION - Predicate<String>)
PASO 4: Filtrar los DNIS válidos

---

# Expresiones reglares en 5 mins.

Cuando trabajamos con expresiones regulares, hacemos uso de PATRONES
Qué es un PATRON? Una forma de especificar el formato con el que se deben cumplir un texto.
Usamos la sintaxis de un lenguaje de programación ... de hecho el lenguaje de programación que definió las expresiones regulares: PERL
Ese lenguaje: regex-perl, es el que se usa en todos los lenguajes de programación para expresiones regulares: JAVA, PYTHON, JS, C#, ...

Según perl, un patrón es una secuencia de SUBPATRONES.

Un SUBPATRON es una secuencia de caracteres con un modificador de cantidad

Qué podemos usar como secuencia de caracteres:
PERL                LO QUE SIGNIFICA
- abc                Debe aparecer literalmente "abc" en el texto
- [abc]              Debe aperecer o la a, o la b, o la c
- [a-z]              Debe aparecer una letra minúscula
- [A-Z]              Debe aparecer una letra mayúscula
- [a-zA-Z]           Debe aparecer una letra
- [a-za-ZñÑ]         Debe aparecer una letra mayúscula o minúscula o la ñ o la Ñ
- [a-m-]             Debe aparecer una letra entre la a y la m en minúscula... o un guión
- [2-8]              Debe aparecer un carácter entre el 2 y el 8
- [0-2][0-9]         Aceptaría 00-29... y si quiero del 0 al 20? 17
- [0-20]             MU MAL!!!! Trabajamos con caracteres:
- .                  Cualquier carácter me vale
Modificadores de cantidad:
- No poner nada     Debe aparecer una vez
- ?                 Puede aparecer o no
- +                 Debe aparecer al menos una vez ... o muchas
- *                 Puede aparecer 0, 1, 2, 3, ... veces
- {4}               Debe aparecer 4 veces
- {2,4}             Debe aparecer entre 2 y 4 veces
- {4,}              Debe aparecer al menos 4 veces
Otros caracteres importantes:
- ^                 Empieza por
- $                 Termina por
- |                 O
- ()                Agrupar subpatrones

Números entre el 0 y el 20
^((1?[0-9])|20)$
19                   VALIDO
8                   VALIDO
20          VALIDO
00                NO VALIDO

---

Tengo un fichero lleno de Tweets (X)
Y quiero generar el Tending Topic de ese fichero.
#GoodVibes 17
#FelizMartes 15
#BuenosDias 12
#CacaDeTrabajo >>>>> Filtrado... contiene una palabra prohibida: CACA, CULO, PEDO, PIS

Un ejemplo de TWEET podría ser:

#BuenosDias a todos menos a los que no me saludan por la mañana#FelizMartes#QueOsCacaCuloPedoPis #Mucho

Cada tweet es una linea de texto.
Buscar los hashtags: OJO: Pueden aparecer juntos #FelizMartes#QueOsCacaCuloPedoPis
o separados: #FelizMartes #QueOsCacaCuloPedoPis
Al principio de la linea... o al final... o por en medio... o en varios sitios.
En Mayúsculas o minúsculas

Pero al final, quiero saber cuantos hay... de cada... habiendo filtrado los que contienen palabras prohibidas.
Y por supuesto, todo ello en 1 sola linea de código, lectura del fichero incluida!

                                                                        flatMap
Coleccion de Tweets -> Separar los tokens en ese tweet (Usando) , -.()  -> Separar cada token en una entrada  -> Filtrare los hashtags
Stream<String>          Stream<List<String>>                                Stream<String>                        Stream<String>

Les quitaré #
Los convertiré a mayúsculas o minúsculas
Quitare aquellos que contengan palabras prohibidas (Y esto tiene su truco)
Juntaré los que sean iguales...
Y ya cuento!