package ar.com.itecn1.service;

import ar.com.itecn1.model.Horario;

public interface HorarioService extends CRUDService<Horario> {
    Horario findById(String id);
}
