package ar.com.itecn1.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Materia {
    private String codigoMateria;
    private String nombre;
    private String anio;
    private List<Materia> correlativas;
    private boolean activo;

    public Materia(String codigoMateria, String nombre, String anio) {
        this.codigoMateria = codigoMateria;
        this.nombre = nombre;
        this.anio = anio;
        this.correlativas = new ArrayList<>();
        this.activo = true;
    }
}
