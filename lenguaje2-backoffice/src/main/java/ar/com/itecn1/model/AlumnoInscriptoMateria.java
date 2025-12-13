package ar.com.itecn1.model;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AlumnoInscriptoMateria {
    private AlumnoInscriptoCarrera alumnoInscriptoCarrera;
    private Estado estado;
    private List<Examen> examenes;
    private boolean activo;

    public AlumnoInscriptoMateria(AlumnoInscriptoCarrera alumnoInscriptoCarrera, List<Examen> examenes) {
        this.alumnoInscriptoCarrera = alumnoInscriptoCarrera;
        this.estado = Estado.REGULAR;
        this.examenes = examenes;
        this.activo = true;
    }
}
