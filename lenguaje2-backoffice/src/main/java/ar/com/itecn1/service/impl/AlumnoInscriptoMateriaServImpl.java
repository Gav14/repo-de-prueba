package ar.com.itecn1.service.impl;

import ar.com.itecn1.model.AlumnoInscriptoMateria;
import ar.com.itecn1.repository.AlumnoInscriptoMateriaRepository;
import ar.com.itecn1.repository.impl.AlumnoInscriptoMateriaRepImpl;
import ar.com.itecn1.service.AlumnoInscriptoMateriaService;

import java.util.List;

public class AlumnoInscriptoMateriaServImpl implements AlumnoInscriptoMateriaService {
    private final AlumnoInscriptoMateriaRepository alumnoInscriptoMateriaRepository;

    public AlumnoInscriptoMateriaServImpl() {
        this.alumnoInscriptoMateriaRepository = new AlumnoInscriptoMateriaRepImpl();
    }

    @Override
    public AlumnoInscriptoMateria findByAlumnoDni(String dni){
        return this.alumnoInscriptoMateriaRepository.findByAlumnoDni(dni);
    }

    @Override
    public List<AlumnoInscriptoMateria> findAll(){
        return this.alumnoInscriptoMateriaRepository.findAll();
    }

    @Override
    public void save(AlumnoInscriptoMateria alumnoInscriptoMateria){
        this.alumnoInscriptoMateriaRepository.save(alumnoInscriptoMateria);
    }

    @Override
    public void update(AlumnoInscriptoMateria alumnoInscriptoMateria){
        this.alumnoInscriptoMateriaRepository.update(alumnoInscriptoMateria);
    }

    @Override
    public void delete (AlumnoInscriptoMateria alumnoInscriptoMateria){
        this.alumnoInscriptoMateriaRepository.delete(alumnoInscriptoMateria);
    }
}
