package ar.com.itecn1.service.impl;

import ar.com.itecn1.model.Alumno;
import ar.com.itecn1.repository.AlumnoRepository;
import ar.com.itecn1.repository.impl.AlumnoRepositoryImpl;
import ar.com.itecn1.service.AlumnoService;

import java.util.List;

public class AlumnoServiceImpl implements AlumnoService {
    private final AlumnoRepository alumnoRepository;

    public AlumnoServiceImpl() {
        this.alumnoRepository = AlumnoRepositoryImpl.getInstancia();
    }

    @Override
    public Alumno findByDni(String dni) {
        // La vista ya validó el formato, solo se delega al repositorio.
        return this.alumnoRepository.findByDni(dni);
    }

    @Override
    public List<Alumno> findAll() {
        return this.alumnoRepository.findAll();
    }

    @Override
    public void save(Alumno alumno) {
        // La vista ya realizó todas las validaciones de formato y duplicado.
        this.alumnoRepository.save(alumno);
    }

    @Override
    public void update(Alumno alumno) {
        this.alumnoRepository.update(alumno);
    }

    @Override
    public void delete(Alumno alumno) {
        this.alumnoRepository.delete(alumno);
    }
}