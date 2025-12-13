package ar.com.itecn1.model;

import lombok.*;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Modulo {
    private String codigo; //AnMat1mod1
    private LocalTime inicio;
    private LocalTime fin;
    private boolean activo;

    public Modulo(String codigo, LocalTime inicio, LocalTime fin) {
        this.codigo = codigo;
        this.inicio = inicio;
        this.fin = fin;
        this.activo = true;
    }
}
