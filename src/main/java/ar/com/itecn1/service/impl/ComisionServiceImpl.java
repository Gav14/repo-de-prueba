package ar.com.itecn1.service.impl;

import ar.com.itecn1.model.ComisionMateria;
import ar.com.itecn1.repository.ComisionRepository;
import ar.com.itecn1.repository.impl.ComisionRepositoryImpl;
import ar.com.itecn1.service.ComisionService;

import java.util.List;

public class ComisionServiceImpl implements ComisionService {
    private final ComisionRepository comisionRepository;

    public ComisionServiceImpl() {
        this.comisionRepository = new ComisionRepositoryImpl();
    }

    @Override
    public ComisionMateria findByCode(String codigoMateria) {
        return this.comisionRepository.findByCode(codigoMateria);
    }

    @Override
    public List<ComisionMateria> findAll() {
        return this.comisionRepository.findAll();
    }

    @Override
    public void save(ComisionMateria comisionMateria) {
        this.comisionRepository.save(comisionMateria);
    }

    @Override
    public void update(ComisionMateria comisionMateria) {
        this.comisionRepository.update(comisionMateria);
    }

    @Override
    public void delete(ComisionMateria comisionMateria) {
        this.comisionRepository.delete(comisionMateria);
    }
}
