package com.curso.spark.sql;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Persona {

    private String nombre;
    private String apellidos;
    private String email;
    private String cp;
    private String dni;
    private Long edad;

}
