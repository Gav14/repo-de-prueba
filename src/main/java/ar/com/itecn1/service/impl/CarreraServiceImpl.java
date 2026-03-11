package ar.com.itecn1.service.impl;

import ar.com.itecn1.model.Carrera;
import ar.com.itecn1.repository.CarreraRepository;
import ar.com.itecn1.repository.impl.CarreraRepositoryImpl;
import ar.com.itecn1.service.CarreraService;

import java.util.List;

public class CarreraServiceImpl implements CarreraService {
    private final CarreraRepository carreraRepository;

    public CarreraServiceImpl(){
        this.carreraRepository = new CarreraRepositoryImpl();
    }

    @Override
    public Carrera findByName(String nombre) {
       return this.carreraRepository.findByName(nombre);
    }

    @Override
    public List<Carrera> findAll() {
       return this.carreraRepository.findAll();
    }

    @Override
    public void save(Carrera carrera) {
        this.carreraRepository.save(carrera);
    }

    @Override
    public void update(Carrera carrera) {
        this.carreraRepository.update(carrera);
    }

    @Override
    public void delete(Carrera carrera) {
        this.carreraRepository.delete(carrera);
    }
}
