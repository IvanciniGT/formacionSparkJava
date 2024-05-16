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
