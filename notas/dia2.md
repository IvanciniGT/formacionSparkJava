
Archivo.readString.lines->                                                                Stream<String>... Donde cada String es un tweet
Fiesta en la playa #BeachParty#SunFun, bronceado extremo!

# reemplazar # -> " #"                                              MAP                   Stream<String>... Donde cada String es un tweet     
Fiesta en la playa  #BeachParty #SunFun, bronceado extremo!

----
# Separar por [ ,._;:()!¿?@<>'"-]+  (SPLIT)                         FLATMAP               Stream<String[]>... Donde cada String[] es un tweet separado por palabras
["Fiesta", "en", "la", "playa", "#BeachParty", "#SunFun", "bronceado", "extremo"]
["#CaféEnLaMañana", "con", "un", "libro", "#Relax", "pero", "mi", "café", "está", "frío"]
        FLATMAP = MAP + FLATTEN
        FLATTEN?                      String[] -> Stream<String>                          Donde cada String es cada palabra del array

                                                                    MAP
                                        Stream<String[]>                          Stream<Stream<String>>
                                                                    FLATMAP       Stream<String>
---
Fiesta                                                                                    Stream<String>... Donde cada String es una palabra
en
la
playa
#BeachParty
#SunFun
bronceado
extremo
#CaféEnLaMañana
con
un
libro
#Relax
pero
mi
café
está
frío
----
# Filtro los que empiezan por #                                   FILTER                  Stream<String>... Donde cada String es un hashtag

#BeachParty
#SunFun

---
# Quito los cuadraditos                                           MAP                     Stream<String>... Donde cada String es un hashtag sin cuadraditos
BeachParty
SunFun

---
# A mayúsculas                                                    MAP                     Stream<String>... Donde cada String es un hashtag en mayúsculas    
BEACHPARTY
SUNFUN

---
ACABAO !


----
# Problema de PI

Yo tiraré N dardos = 1.000.000

Queremos calcular PI... pero para calcular PI me hace falta 1 dato: n (cuántos caen en el círculo??????)

Parto siempre de una colección de datos ------> map...map...map ----> Reduce: n
 ????????                                           ???????

N = 1.000.000 dardos que voy a tirar                                                                                                                  REDUCE!
                Para cada dardo...      calcular su punto de impacto....  calcular su distancia al origen.... me quedo con los caen dentro (filter)... los cuento

        IntStream.range                         
Dardo1                          1               (x1,y1)                         raiz(x·x+y·y)                     d<=1
Dardo2                                          (x2,y2)                         raiz(x^2+y^2)
Dardo3
Dardo4
...
Dardo10000000                   1000000

N            ->              ~~Stream<Integer>~~ ----->      Stream<Double[]>   --->    Stream<Double> ------->      Stream<Double> ---->     Integer      
                                IntStream.mapToObject

# MIERDAS DE JAVA... parte II

Otro gran mierdolo nos lo colaron en Java 1.5.... Cuando se introdujo el concepto de "Genericos"... y las Colecciones
        List<T>
        Set<T>
        Map<T>

Pero T debe ser una clase que extienda de Object...
Y en java, hasta entonces, teníamos 2 grandes tipos de datos (2 familias): Objects y los primitivos

Dentro de los primitivos tenemos:
- int       4 bytes                     Integer
- double    8 bytes                     Double
- long      8 bytes                     Long
- float      4 bytes                     ...
- boolean   1 bit
- byte      8 bit
- char      8 bit
- short     2 bytes
El problema es: Puedo crear un List<int>??? o un Set<boolean>??
NO... los tipo s simples no extienden de Object

Y entonces... no puedo tener una puñetera lista de números tios/as de JAVA (PINGADOS TODOS Y TODAS !!!!)??? En serio.
Ya... la verdad que deberías (pensaron ellos/ellas) => SOLUCION MIERDA QUE TE CAGAS: Clases para representar los tipos simples:

Pero internamente la JVM los gestiona de forma distinta... muy distinta...
Y entonces... si tengo el 3 y el 7 (que son int)... y los quiero meter en una Lista, los tengo que convertir primero a Integers
(en serio? Tios y tias de JAVA??? PRINGAOS TODOS/AS)
Y se inventaron otro concepto: (MIERDA)AUTOBOXING
que en automático convierte tipos simples en sus correspondientes Objetos... eso si... derrochando RAM por el camino que te cagas. *** GRAVE !!!!!
Si voy a meter 100 numeritos en una lista (List<Integer)... por mucho que quiera tardar... son 100 numeritos... = QUE SE JODA LA JVM
Si voy a meter 1.000.000 de numeritos... la cosa cambia!

Y entonces... cuando implementaron los Stream... claro... yo puedo tener un Stream<Integer>... preparate para el derroche de RAM y CPU en conversión de objetos a tipos simples y viceversa
Pero como ahí se notaba mucho (demasiado) la cagada, crearon IntStream... que es un Stream que por dentro trabaja con int, en lugar de Integer

## PARALELIZACION DE TRABAJOS:

La gran gracia del MODELO MAP-REDUCE es que da lugar a procesos fácilmente PARALELIZABLES.

Al fin y al cabo, estoy haciendo el mismo trabajo sobre cada DATO.
De un dardo: Genero coordenadas... > Genero la distancia > Filtro
Y del siguiente dardo? lo mismo
Y del siguiente? lo mismo
y asi, por los dardos de los dardos, amén!

Pues puedo repartir los dardos entre mis cores... 
Puedo abrir 8 hilos... y a cada hilo ir mandándole dardos...
Y al final combinar todas las respuestas... antes de hacer el ??? REDUCE (cuenta final)

Y es algo que me regala JAVA


---

Dada una palabra: manana
Que se compare con todas las palabras de ese fichero (que habré precargado) y me ofrezca un listado con las 10 palabras (.limit(10), previo ordenar por distancia .sorted() ) más similiares

- mañana
- manzana
- ananá

Distancia de levensthein: El número de caracteres que he de:
- cambiar
- añadir
- eliminar
de una palabra para transformala en otra.

---

Carga del fichero
    Lineas del fichero -> split(=)  -> me quedo con la primera parte ->  palabras

Búsqueda de palabras similares                                                            Comparator.comparing(palabraPuntuada-> palabraPuntuada.distancia)
    Stream<String>                              -> Stream<PalabraPuntuada>              ->  Stream<PalabraPuntuada>      -> Stream<PalabraPuntuada> -->      Stream<String>       -->   List<String>
    para cada palabra -> map(new PalabraPuntuada(palabra, distanciaLev))        -> sorted(distancia)               --> limit(10)           --->       map(palabra)         ---> .collect()
                                    ***********                                     ************
                       ^                                                     ^
                filter(Math.abs(palabra.length-comparada.length<=2))
             manzana                                                           filter(distancia<=2)
             albaricoque (4 caracteres más)                                     Para que 2 palabras se parezcan...
                               ||                                               como mucho te dejo que cambies hasta 2
                            distanciaMinimaLeve=4                               manzana
             sal(4 de menos???)                                                 montana
                                                                                pontana


Clase : Palabra Puntuada
 - Palabra
 - Distancia

---
# APACHE SPARK 

    Programa (SCRIPT SPARK)
   ----------------------------
    Me voy a conectar con un cluster                            Nodo Maestro Spark          Nodos Trabajadores
    Le configuro un trabajo                                                                      Nodo 1
    Obtengo resultado                                                                           Nodo 2
    Cierro conexión


Ya os comenté que Apache Spark es una reimplementación del motor de procesamiento
MAP/REDUCE de Apache Hadoop trabajando sobre memoria RAM.

Básicamente es una versión de la librería java.util.stream, que reparte el trabajo entre máquinas remotas.
Spark nos ofrece su propia implementación de la clase "Stream", llamada RDD.

Un RDD de spark es el equivalente a un Stream de Java.
Dentro de un RDD tendremos por tanto: funciones MAP y REDUCE...más o menos las mismas que en la clase Stream...
Con ligeras diferencias en algunas.

Spark se puede usar desde:
- SCALA
- PYTHON
- JAVA   <<<<   Es la peor opción posible para trabajar contra Spark
- R

---

JAVA 1.5            
- Genéricos
- Collections
- Iterables
- Anotaciones

JAVA 1.8
- Programación funcional
- Streams
- Optional

JAVA 1.9
- Proyecto Jigsaw
  Antes de Java 1.9, que tipos de cosas podíamos crear en JAVA: classes y interfaces.. que se agrupaban en paquetes
  Y que modificadores de privacidad teníamos disponibles? private, public, protected, default(friendly)
  Y eso es suficiente? NI DE COÑA !
  - CAGADAS DE JAVA ... parte III
  En JAVA 1.9 se crea un nuevo tipo de objeto en java: modules
    ```java
    module "libreria-diccionario" {
      
      exports  "com.diccionarios.api";
      uses
      provides
      requires
    }
    ```
    
    En JAVA 9, se modulariza la JVM.
    La gente puede crear sus propios módulos... y exportar de ellos solamente los paquetes que quieran exportar.
    
    ---
    package com.diccionarios.api;
    public interface Diccionario{}
    ---
    package com.diccionarios.api;
    public interface SuministradorDeDiccionarios{
    public Optional<Diccionario> getDiccionario(String idioma);
    }
    
    ---
    package com.diccionarios.api;
    import com.diccionarios.api.SuministradorDeDiccionarios;
    import com.diccionarios.impl.SuministradorDeDiccionariosImpl;
    public interface SuministradorDeDiccionariosFactory{ // PUES CLARO... YAA ERA HORA... hasta puto java 1.8 tuvimos que esperar. CAGADAS DE JAVA ... parte 4
    public static SuministradorDeDiccionarios getInstance(){
    // Una cache de suministradores (Antes de)
    return new SuministradorDeDiccionariosImpl();
    }
    }
    ---
    
    package com.diccionarios.impl;
    public class DiccionarioImpl implements Diccionario{}
    public class SuministradorDeDiccionariosImpl implements SuministradorDeDiccionarios{}
    ---
    diccionarios-libreria.jar
    
    ...
    
    SuministradorDeDiccionarios miSuministrador = SuministradorDeDiccionariosFactory.getInstance();
    SuministradorDeDiccionarios miSuministrador = new SuministardorDeDiccionariosImpl();
  Todo el código del API de JAVA se metió en MODULOS
  Y al arrancar la JVM puedo elegir qué módulos quiero activar
  Y además, varios módulos considerados INSEGUROS: REFLECTIONS se desactivan por defecto en la JVM... Inicialos a tu discreción!!!
Hadoop implementa su propio sistema de archivos: HDFS
Y usa funciones posix para la gestión de archivos:
  - mkdir
  - cd
  - rm
  - rm -r
  - ls 

---
Tuple2??? ._1, ._2

JavaPairRDD ~= JavaRDD<Tuple2>
Algo así como un hashmap... pero donde las claves... no son claves únicas

JavaRDD.mapToPair( (T)-> Tuple2  ) -> JavaPairRDD
JavaPairRDD.reduceByKey( (val1,val2) -> val1+val2 ) --> JavaPairRDD .mapToPair( tupla -> new Tuple2( tupla._2, tupla._1))
                                                                .sortByKey().mapToPair( tupla -> new Tuple2( tupla._2, tupla._1))
                                                                .take(10)
NO HAY ALTERNATIVA A ESTO !

GoodVibes       -> [GoodVives, 1]           -> [GoodVibes, 3]
GoodVibes       -> [GoodVives, 1]
SummerLove
GoodVibes       -> [GoodVives, 1]
SummerLove
MierdaDeCurso


GoodVibes       3
SummerLove      2
------------------
MierdaDeCurso   1

