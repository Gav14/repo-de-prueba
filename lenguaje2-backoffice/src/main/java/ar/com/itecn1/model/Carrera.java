package ar.com.itecn1.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Carrera {
    private String nombre;
    private boolean activo;

    private Turno turno;
    private PlanEstudio planEstudio;

    public Carrera(String nombre, Turno turno) {
        this.nombre = nombre;
        this.turno = turno;
        this.activo = true;
    }

    public Carrera(String nombre, Turno turno, PlanEstudio planEstudio) {
        this.nombre = nombre;
        this.turno = turno;
        this.planEstudio = planEstudio;
        this.activo = true;
    }
}
