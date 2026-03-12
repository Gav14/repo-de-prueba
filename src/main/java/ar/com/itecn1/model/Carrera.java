package ar.com.itecn1.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Carrera {

    private String nombre;
    private Turno turno;
    private PlanEstudio planEstudio;

    @Builder.Default
    private boolean activo = true;

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