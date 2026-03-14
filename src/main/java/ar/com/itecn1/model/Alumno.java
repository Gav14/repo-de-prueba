package ar.com.itecn1.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Alumno {
    private String dni;
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
    private boolean activo;

    public Alumno(String dni, String apellido,String nombre, String telefono, String email) {
        this.dni = dni;
        this.apellido = apellido;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.activo = true;
    }
}
