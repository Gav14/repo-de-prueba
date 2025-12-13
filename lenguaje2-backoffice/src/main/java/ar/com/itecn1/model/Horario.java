package ar.com.itecn1.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Horario {
    private String id;
    private Dia dia;
    private Carrera carrera;
    private Cuatrimestre cuatrimestre;
    private Modulo modulo;
    private boolean disponible;
    private boolean activo;

    public Horario(String id, Dia dia, Carrera carrera, Cuatrimestre cuatrimestre) {
        this.id = id;
        this.dia = dia;
        this.carrera = carrera;
        this.cuatrimestre = cuatrimestre;
        this.disponible = true;
        this.activo = true;
    }

    public Horario(String id, Dia dia, Carrera carrera, Cuatrimestre cuatrimestre, Modulo modulo) {
        this.id = id;
        this.dia = dia;
        this.carrera = carrera;
        this.cuatrimestre = cuatrimestre;
        this.modulo = modulo;
        this.disponible = true;
        this.activo = true;
    }
}
