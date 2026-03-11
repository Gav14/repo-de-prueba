package ar.com.itecn1.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Profesor {
    private String dni;
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
    private boolean activo;

    public Profesor(String dni, String nombre, String apellido, String telefono, String email) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.email = email;
        this.activo = true;
    }
}
