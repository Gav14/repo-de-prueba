package ar.com.itecn1.service.impl;

import ar.com.itecn1.model.Examen;
import ar.com.itecn1.repository.ExamenRepository;
import ar.com.itecn1.repository.impl.ExamenRepositoryImpl;
import ar.com.itecn1.service.ExamenService;

import java.util.List;

public class ExamenServiceImpl implements ExamenService {
    private final ExamenRepository examenRepository;

    public ExamenServiceImpl() {
        this.examenRepository = new ExamenRepositoryImpl();
    }

    @Override
    public Examen findById(String idExamen) {
        return this.examenRepository.findById(idExamen);
    }

    @Override
    public List<Examen> findAll() {
        return this.examenRepository.findAll();
    }

    @Override
    public void save(Examen examen) {
        this.examenRepository.save(examen);
    }

    @Override
    public void update(Examen examen) {
        this.examenRepository.update(examen);
    }

    @Override
    public void delete(Examen examen) {
        this.examenRepository.delete(examen);
    }
}
