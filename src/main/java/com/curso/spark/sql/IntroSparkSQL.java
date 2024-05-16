package com.curso.spark.sql;

import com.curso.DNIUtils;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;

import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.udf;

public class IntroSparkSQL {

    public static void main(String[] arg){
        // Paso 1: Abrir conexión
        SparkSession conexion = SparkSession.builder()
                .appName("Intro")
                .master("local[3]")
                .getOrCreate();

        // Paso 2: Cargar datos
        Dataset<Row> datos = conexion.read().json("src/main/resources/personas.json");
            // Un dataset contiene filas (Row)... pero además
            // Al igual que en cualquier BBDD, una tabla (que es lo que representa el objeto Dataset
            // va a tener asociado un Schema, que indicará los campos disponibles, su tipo y si pueden ser nulos.
            // Ese esquema se va a generar en automático, en base a los datos que pasemos.

        // Paso 3: Configurar operaciones con SQL
        // Ambos métodos están bien mientras desarrollo... en producción ni de coña!
        datos.printSchema();
        datos.show();

        // Vamos a tener operaciones dentro de ese Dataset, equivalentes a las operaciones que tengo en una BBDD
        // OPCION 1: Trabajar con los nombres de las columnas
        datos.select("nombre", "edad") /* esto me devuelve un nuevo Dataset */ .show();
        // OPCION 2: Trabajar con un tipo de datos Column que tenemos dentro de SparkSQL.
        // Podemos generar un dato de tipo Column mediante la función col("NOMBRE DE LA COLUMNA")
        datos.select( col("nombre"), col("edad") )                       .show();
        // Estas opciones no las puedo mezclar... O uso una, o uso la otra.
        datos.select( col("nombre"), col("edad").plus(3) )         .show();

        // WHERE
        datos.filter(col("edad").geq(18)).show();
        // Group BY
        datos.groupBy("nombre")
             .sum("edad")
             .orderBy(col("sum(edad)").desc())
             .show();


        // Vamos a más... QUE EMPIECE LA MAGIA!
        datos.createOrReplaceTempView("personas");
        // Esta función registra los datos bajo el nombre "personas". Para que? Para que ahora pueda escribir SQL PELAO
        Dataset<Row> datosTransformados = conexion.sql("SELECT nombre, sum(edad) FROM personas WHERE edad >= 18 GROUP BY nombre ORDER BY sum(edad) DESC"); // Me devuelve es un Dataset<Row>
        datosTransformados
                .repartition(5)
                .write()
                .mode("overwrite")
                .json("src/main/resources/resultado.json");
        // ESTAMOS TRABAJANDO CON MUCHOS DATOS... y además, estos programas los vamos a ejecutar 500 veces al año.
        // El hecho es que si los datos entrasen en un fichero en una máquina, ME PLANTEARIA USAR SPARK? NO
        // Voy a tener muchos datos... y las formas tradicionales de gestionarlos (almacenarlos) ya no me sirven... y me voy a un enfoque BIG_DATA
        // Donde los datos van a repartirse entre 20 servidores... y cada servidor va a guardar una parte de los datos.
        // HABITUALMENTE DEJAMOS ESTA CARPETA en un servidor HDFS.
        // Realmente el fichero es "src/main/resources/resultado.json"
        // Pero... HDFS lo parte en trozos... y cada trozo irá a un servidor... puede ser que un servidor guarde 20 trozos.
        // Paso 4: cierro conexión

        // Esto está bonito.... pero... y si ahora quiero validar los DNIs de las personas?
        // Como hago eso en SQL?
        JavaRDD<Persona> datosComoRDD = datos.toJavaRDD() /*JavaRDD<Rows>*/
                .map( row -> Persona.builder()
                        .nombre(row.getString(row.fieldIndex("nombre")))
                        .apellidos(row.getString(row.fieldIndex("apellidos")))
                        .edad(row.getLong(row.fieldIndex("edad")))
                        .email(row.getString(row.fieldIndex("email")))
                        .cp(row.getString(row.fieldIndex("cp")))
                        .dni(row.getString(row.fieldIndex("dni")))
                        .build()
                );
        // Personas con DNI Valido
        JavaRDD<Persona> personasConDNIValidoRDD = datosComoRDD.filter( persona -> DNIUtils.isDNIValido(persona.getDni()));
        Dataset<Row> personasConDNIValido = conexion.createDataFrame(personasConDNIValidoRDD, Persona.class); // Persona.class se usa para generar el schema del ROW
        personasConDNIValido.show();

        conexion.udf().register(
                "esValido",
                udf( (String dni) -> DNIUtils.isDNIValido(dni) , DataTypes.BooleanType )
        );
        conexion.sql("SELECT nombre, dni FROM personas WHERE esValido(dni)").show();

        conexion.close();
    }

}
