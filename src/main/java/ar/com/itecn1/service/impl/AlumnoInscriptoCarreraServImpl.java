package ar.com.itecn1.service.impl;

import ar.com.itecn1.model.AlumnoInscriptoCarrera;
import ar.com.itecn1.repository.AlumnoInscriptoCarreraRepository;
import ar.com.itecn1.repository.impl.AlumnoInscriptoCarreraRepImpl;
import ar.com.itecn1.service.AlumnoInscriptoCarreraService;

import java.util.List;

public class AlumnoInscriptoCarreraServImpl implements AlumnoInscriptoCarreraService {
    private final AlumnoInscriptoCarreraRepository repository;

    public AlumnoInscriptoCarreraServImpl() {
        this.repository = new AlumnoInscriptoCarreraRepImpl();
    }

    @Override
    public AlumnoInscriptoCarrera findByDni(String dni) {
        return this.repository.findByDni(dni);
    }

    @Override
    public List<AlumnoInscriptoCarrera> findAll() {
        return this.repository.findAll();
    }

    @Override
    public void save(AlumnoInscriptoCarrera alumnoInscriptoCarrera) {
        this.repository.save(alumnoInscriptoCarrera);
    }

    @Override
    public void update(AlumnoInscriptoCarrera alumnoInscriptoCarrera) {
        this.repository.update(alumnoInscriptoCarrera);
    }

    @Override
    public void delete(AlumnoInscriptoCarrera alumnoInscriptoCarrera) {
        this.repository.delete(alumnoInscriptoCarrera);
    }
}
