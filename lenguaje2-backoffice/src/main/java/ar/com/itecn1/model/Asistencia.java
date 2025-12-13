package ar.com.itecn1.model;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Asistencia {
    private LocalDate fecha;
    private boolean presente;
    private Horario horario;
    private Alumno alumno;
    private boolean activo;

    public Asistencia(LocalDate fecha, Horario horario, boolean presente, Alumno alumno) {
        this.fecha = fecha;
        this.horario = horario;
        this.presente = presente;
        this.alumno = alumno;
        this.activo = true;
    }
}
