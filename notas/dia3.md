# Spark SQL

Inicialmente, todo se trabajaba con la librería CORE.
Es que era la única que teníamos.

MAP-REDUCE es duro.

Para que más gente pudiera ponerse a usar Spark, se crea la librería SQL.
Hoy en día es prácticamente la que usamos.

La cosa es que al usar esta librería, cambia todo, incluso la forma de conectarnos a Spark.
Con esta librería ya no trabajamos con:
- SparkConf         ----> X
- JavaSparkContext  ----> SparkSession (se crea mediante un patrón builder)
- JavaRDD           ----> Dataset<Row>

La idea es tener operaciones de más alto nivel (como las que ofrece SQL), que por debajo se implementen usando técnicas MAP REDUCE...
pero todo eso queda oculto al desarrollador.

Tendremos un puente entre ambas librerías, pero no es directo:
- O trabajo con una o trabajo con la otra...
- Si quiero pasar cosas de la una a la otra: tendremos algunas funciones que lo permitirán... pero siempre comenzando desde la librería SQL

En todo lo posible nos quedaremos en la librería SQL.


SPARK Es una librería que usamos para montar ETLs... es su uso principal... y lo que haceis en el banco con Spark.

# ETL?

BBDD producción de mis apps.
App gestión de hipotecas ---> Expedientes (alta, estudio, aprobada, denegada... en revisión de riesgos) ---> BBDD

Pregunta: En esa BBDD tengo todos los datos de todos los expedientes que el banco ha procesado a lo largo de su vida? NO
En esa BBDD tengo los datos VIVOS (los expedientes en trámite)... y los muertos... de los últimos 6 meses...

Pero tampoco quiero perder esa información.

Donde la llevamos? A otra BBDD... pero una BBDD con otra estructura: DATAWAREHOUSE
Son BBDD que no están optimizadas para operaciones CRUD (INSERT, DELETE, UPDATE, SELECT) sino para SELECT.
Se usa otro tipo de modelos da datos. En una BBDD de producción TODO está HiperNormalizado.
En estas BBDD se desnormalizan los datos. MODELOS EN ESTRELLA o más avanzado EN COPO DE NIEVE.
Un DATAWAREHOUSE almacena datos MUERTOS (que no cambian ya en el tiempo)

Para llevar datos de una BBDD de producción a un DATAWAREHOUSE (trabajo que se hace con una periodicidad preestablecida: Todas las noches, los domingos...)
montamos un tipo de programa (script) al que denominamos ETL (Extract-Transform-Load)
- Extraigo datos de la BBDD de producción
- Los transformo con una herramienta (les quito datos que no me interesan... los desnormalizo... los fusiono con datos de otra bbdd...)
- Y al final, los cargo en el datawarehouse

Muchas veces, leemos datos de BBDD y los dejamos en BBDD
Muchas otras, leemos datos de ficheros y los dejamos en BBDD
Otras, leemos de BBDD y los dejamos en ficheros
Otras leemos de ficheros y los dejamos en ficheros ***

De hecho la última es la más habitual.
Los procesos de Transform suelen ser complejos... y se subdividen en etapas.
Me llegan unos datos, y les hago una primera transformación... y genero un fichero.

De eso ya datos ya transformados (fichero) puedo luego necesitar varias transformaciones paralelas... para distintos usos de los datos.

BBDD PRODUCCION
       v
    EXTRACT
       v 
    fichero = DATOS ORIGINALES ---> TRANSFORMACION 1  ----> FICHERO1 ---> TRANSFORMACION 2 ---> FICHERO 2 --> TRANSFORMACION 4 --> FICHERO 4 ----> CARGA ---> DATAWAREHOUSE
                                                                    ---> TRANSFORMACION 3 ---> FICHERO 3 --> CLIENTE (DPTO interno: RISGOS)

---

# Formatos paraq guardar datos:
- FORMATOS DE TEXTO
  - csv
  - json
  - xml
- FORMATO BINARIO
  - parquet
  - avro
---

## AVRO y PARQUET
En ambos tipos de ficheros, se guardan tanto los datos como el esquema.
Pero se diferencian en que:
- En PARQUET se guardan los datos POR COLUMNAS
    byte0          byte 267
    |esquema.......|datos: [columna1] [columna2] [columna3(reg1...reg2..reg3...)]|
- En AVRO se guardan los datos POR FILAS (como json, csv)
    |esquema.......|datos: [reg1 (col1..col2..col3...)] [reg2 (col1..col2..col3...)] [reg3 (col1..col2..col3...)]|

En la mayor parte de las situaciones, querremos leer y transformar COLUNAS... por lo que PARQUET es el formato más adecuado.
Lee de ese fichero, el DNI, NOMBRE, EDAD de TODO EL MUNDO...La COLUMNA DNI, la validas.

AVRO lo usamos para otras cosas:

  X
App móvil   ---->                                                   Bajo demanda                         1 vez cada hora
Tweets  ----> KAFKA ---> Programa Spark (sacar los hashtags?) ---> FICHERO PARQUET ---> Programa Spark (sacar trending Topic) ---> FICHERO PARQUET
              AVRO  ---> Programa Spark (sacar las menciones?) --->....
                    ---> Programa Spark (ML identificar personas en foto)
                    ---> Programa Spark (cargarlo en el feed de la app)
                    ---> Programa Spark (validar si contiene mensajes de odio.... o WOKES)

---

DNI: 12345678T

¿Cómo lo guardáis en una BBDD? VARCHAR(9) = RUINA !!!!

Cuánto ocupa un VARCHAR (9) en un BBDD (en su fichero)... 9 CHARs
Cuánto ocupa un CHAR en una BBDD? Depende del JUEGO DE CARACTERES (ASCII, UTF-8, UTF-16, UTF-32)
Cuánto ocupa un char ascii? 8 bits = 1 byte
    Y en un byte... hasta cuantos datos diferentes puedo representar? 2^8 = 256
    Puedo representar 256 caracteres diferentes...
            0000 0000 -> a
            0000 0001 -> b
            0000 0010 -> c
            0000 0011 -> d
    UTF? Unicode Transformation... Unicode? Juego de caracteres (estándar que recoge todos los caracteres que usa la humanidad = 150k)
    Me entran 150k en 1 byte? No... en 1 byte entran 256
    Y en 2 bytes? No. Entran 256x256 = 65k
    Y en 4 bytes? Si. Entran 256x256x256x256 = 4.3kM
    UTF define varias transformaciones para representar los caracteres:
    - UTF-8: De 1 a 4 bytes por carácter... Los caracteres más sencillos: ocupan 1 byte
    - UTF-16: De 2 a 4 bytes por carácter... Los caracteres más sencillos: ocupan 2 bytes
    - UTF-32: 4 bytes por carácter

Dado que un DNI son números simplones y una letra simplona... puedo usar 1 byte para representar cada carater...
Y por ende 1 DNI como VARCHAR(9) me ocuparía en el fichero de BBDD = 9 bytes.

En 1 byte... puedo representar 256 cosas... pueden ser números.
Y en 2 bytes: 65k cosas... pueden ser letras y números.
Y en 4 bytes: 4.3kM cosas... pueden ser letras, números, símbolos, emojis...
Cúantos DNIs tenemos? 100M... Cuánto me ocuparía el número del DNI como NUMERO en el fichero de la BBDD? 4 bytes

Y la letra?
- O no la guardo... ya sabemos como regenerarlas después.
- O si la quiero guardar: char: 1 byte
En total me ocupa el dni? 5 bytes FRENTE A 9 de guardarlo como TEXTO.

El almacenamiento es lo más caro con diferencia en un entorno de producción.
- Para mi casa.. para guardar pelis, compro un WESTERN BLUE... HDD BARATO
- En un entorno de prod, me voy a un western red pro o a un gold... y eso es otro dinerito...
   Pero necesito HDD que funcionen 24x7... de mejor calidad
Pero el problema es más grave.
LO UNICO QUE VALE (que aporta valor) de toda esta cadena es el DATO.

En mi casa, la peli, me la pela! Si se me rompe el disco... me bajo otra.
En la empresa eso ni de coña.

Cuántas copias se hacen de un dato mínimo en un entorno de producción? 3 copias

2 Tbs en mi casa (western blue) salen por 100€
2Tbs en una empresa son 3 x 2Tbs = 6Tbs... y de los caros ....= salen por 600€

Pero además... querré hacer backups de los datos (SON SAGRAOS)
Y querré hacer copias de los backups (por si acaso)
Y para esos 2Tbs

Las replicas (las 3 copias) me aportan ALTA DISPONIBILIDAD= Si una copia se rompe, sigo teniendo el dato para prestar servicio.
Los BACKUPS sirven para recuperación de desastres. Si un pringa@ hace un delete * from MI_TABLITA;
... puedo tener la tabla replicada en 3 HDD... y se borra de los 3.
Lo que quiero es poder dar marcha atrás... y recuperar la tabla.
Y normalmente tengo backups de 2 semanas atrás en el tiempo. o más... Hechas por días...
2Tbs -> 10 Tbs de backups... 
que guardo duplicados = 20Tbs + 6 produccion (replicas) = 26Tbs de discos caros... para conseguir 2 Tbs de almacenamiento.
