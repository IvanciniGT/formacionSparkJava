package com.curso.spark.sql;

import com.curso.DNIUtils;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;

import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.udf;

public class ProcesarCPsPersonas {

    public static void main(String[] arg) throws InterruptedException {
        // Paso 1: Abrir conexión
        SparkSession conexion = SparkSession.builder()
                .appName("Intro")
                //.master("local[3]")
                .getOrCreate();

        // Paso 2: Cargar datos
        Dataset<Row> personas = conexion.read().json("src/main/resources/personas.json");
        Dataset<Row> cps = conexion.read()
            .option("header", "true")
            .option("delimiter", ",")
            .csv("src/main/resources/cps.csv");

        // CPs válidos
        cps=cps.select(col("cp").as("cp_existente"),col("municipio"),col("provincia"));
        //personas.join(cps, "cp")
        //        .show();
        var personasCPValido = personas.join(cps, personas.col("cp").equalTo(cps.col("cp_existente")));
        personasCPValido.show();

        personas.select("nombre","cp","edad","dni")
                .except(
                        personasCPValido.select("nombre","cp","edad","dni")
                ).show();

        //Thread.sleep(3600000); Para ver la interfaz de Spark: localhost:4040 y detectar cuellos de botella (problemas de rendimiento
        conexion.close();
    }

}
