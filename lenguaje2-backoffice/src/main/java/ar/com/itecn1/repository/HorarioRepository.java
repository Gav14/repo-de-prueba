package ar.com.itecn1.repository;

import ar.com.itecn1.model.Horario;

public interface HorarioRepository extends CRUDRepository<Horario> {
    Horario findById(String id);
    // agregarModulos
}
