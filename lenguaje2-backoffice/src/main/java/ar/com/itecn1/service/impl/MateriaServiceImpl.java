package ar.com.itecn1.service.impl;

import ar.com.itecn1.model.Materia;
import ar.com.itecn1.repository.MateriaRepository;
import ar.com.itecn1.repository.impl.MateriaRepositoryImpl;
import ar.com.itecn1.service.MateriaService;

import java.util.List;

public class MateriaServiceImpl implements MateriaService {
    private final MateriaRepository materiaRepository;

    public MateriaServiceImpl(){ this.materiaRepository= new MateriaRepositoryImpl();}

    @Override
    public Materia findByCode(String codigoMateria){return this.materiaRepository.findByCode(codigoMateria);}

    @Override
    public List<Materia> findAll(){return this.materiaRepository.findAll();}

    @Override
    public void save(Materia materia){this.materiaRepository.save(materia);}

    @Override
    public void update(Materia materia){this.materiaRepository.update(materia);}

    @Override
    public void delete(Materia materia){this.materiaRepository.delete(materia);}
}
