package ar.com.itecn1.service.impl;

import ar.com.itecn1.model.Cuatrimestre;
import ar.com.itecn1.repository.CuatrimestreRepository;
import ar.com.itecn1.repository.impl.CuatrimestreRepositoryImpl;
import ar.com.itecn1.service.CuatrimestreService;

import java.util.List;

public class CuatrimestreServiceImpl implements CuatrimestreService {
    private final CuatrimestreRepository cuatrimestreRepository;

    public CuatrimestreServiceImpl() {
        this.cuatrimestreRepository = new CuatrimestreRepositoryImpl();
    }

    @Override
    public Cuatrimestre findByNumber(String numeroCuatrimestre) {
        return this.cuatrimestreRepository.findByNumber(numeroCuatrimestre);
    }

    @Override
    public List<Cuatrimestre> findAll() {
        return this.cuatrimestreRepository.findAll();
    }

    @Override
    public void save(Cuatrimestre cuatrimestre) {
        this.cuatrimestreRepository.save(cuatrimestre);
    }

    @Override
    public void update(Cuatrimestre cuatrimestre) {
        this.cuatrimestreRepository.update(cuatrimestre);
    }

    @Override
    public void delete(Cuatrimestre cuatrimestre) {
        this.cuatrimestreRepository.delete(cuatrimestre);
    }
}
