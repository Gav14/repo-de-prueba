package ar.com.itecn1.model;

import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Materia {
    private String codigoMateria;
    private String nombre;
    private String cuatrimestre; // Cambié de anio a cuatrimestre
    private List<Materia> correlativas;
    private boolean activo;

    public Materia(String codigoMateria, String nombre, String cuatrimestre) {
        this.codigoMateria = codigoMateria;
        this.nombre = nombre;
        this.cuatrimestre = cuatrimestre;
        this.correlativas = new ArrayList<>();
        this.activo = true;
    }
}