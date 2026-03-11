package ar.com.itecn1.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PlanEstudio {
    private String nombre;
    private String titulo;
    private String duracion;
    private String anio;

    private List<Materia> materias;
    private boolean activo;

    public PlanEstudio(String nombre, String anio, String duracion, String titulo) {
        this.nombre = nombre;
        this.anio = anio;
        this.duracion = duracion;
        this.titulo = titulo;
        materias = new ArrayList<>();
        this.activo = true;
    }

}
