package ar.com.itecn1.model;

import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlanEstudio {
    private String nombre;
    private String titulo;
    private int duracion;
    private int anio;

    @Builder.Default
    private List<Materia> materias = new ArrayList<>();

    @Builder.Default
    private boolean activo = true;

    public PlanEstudio(String nombre, int anio, int duracion, String titulo) {
        this.nombre = nombre;
        this.anio = anio;
        this.duracion = duracion;
        this.titulo = titulo;
        this.materias = new ArrayList<>();  // ✅ INICIALIZAR!
        this.activo = true;
    }
}