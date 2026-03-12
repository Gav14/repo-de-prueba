package ar.com.itecn1.service;

import ar.com.itecn1.model.Carrera;
import java.util.List;

public interface CarreraService extends CRUDService<Carrera> {
    Carrera findByName(String nombre);
    List<Carrera> buscarCarreras(String texto);
    void reactivarCarrera(String nombre);  // ✅ NUEVO MÉTODO - Responsabilidad del Service
}