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

    public static void main(String[] arg){
        // Paso 1: Abrir conexión
        SparkSession conexion = SparkSession.builder()
                .appName("Intro")
                .master("local[3]")
                .getOrCreate();

        // Paso 2: Cargar datos
        Dataset<Row> persona = conexion.read().json("src/main/resources/personas.json");
        Dataset<Row> cps = conexion.read()
            .option("header", "true")
            .option("delimiter", ",")
            .csv("src/main/resources/cps.csv");



        conexion.close();
    }

}
