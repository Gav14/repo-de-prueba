package ar.com.itecn1.repository;

import ar.com.itecn1.model.Carrera;
import java.util.List;

public interface CarreraRepository extends CRUDRepository<Carrera> {
    Carrera findByName(String nombre);
    List<Carrera> findByNombreSimilar(String texto);
    // Nota: NO agregamos reactivar aquí porque el repositorio solo hace CRUD básico
    // reactivar es lógica de negocio que usa update()
}