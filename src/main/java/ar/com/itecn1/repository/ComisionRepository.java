package ar.com.itecn1.repository;

import ar.com.itecn1.model.ComisionMateria;
import java.util.List;

public interface ComisionRepository extends CRUDRepository<ComisionMateria> {
    ComisionMateria findByCode(String codigoComision);
    List<ComisionMateria> findByCarrera(String nombreCarrera);
    List<ComisionMateria> findByMateria(String codigoMateria);
    List<ComisionMateria> findByCuatrimestre(String numeroCuatrimestre);
    List<ComisionMateria> findByProfesor(int dniProfesor);
    boolean existeComisionActivaParaMateriaEnCuatrimestre(String codigoMateria, String numeroCuatrimestre);
}