package ar.com.itecn1.model;

import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComisionMateria {
    private String codigo;
    private Carrera carrera;
    private Materia materia;
    private Cuatrimestre cuatrimestre;
    private Profesor profesor;
    private List<AlumnoInscriptoMateria> alumnosInscriptos;
    private List<Examen> examenes;
    private List<Asistencia> asistencias;
    private List<Horario> horarios;
    private boolean activo;

    public ComisionMateria(String codigo, Carrera carrera, Materia materia,
                           Cuatrimestre cuatrimestre, Profesor profesor) {
        this.codigo = codigo;
        this.carrera = carrera;
        this.materia = materia;
        this.cuatrimestre = cuatrimestre;
        this.profesor = profesor;
        this.alumnosInscriptos = new ArrayList<>();
        this.examenes = new ArrayList<>();
        this.asistencias = new ArrayList<>();
        this.horarios = new ArrayList<>();
        this.activo = true;
    }
}