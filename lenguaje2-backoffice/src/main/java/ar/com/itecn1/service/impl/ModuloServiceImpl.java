package ar.com.itecn1.service.impl;

import ar.com.itecn1.model.Modulo;
import ar.com.itecn1.repository.ModuloRepository;
import ar.com.itecn1.repository.impl.ModuloRepositoryImpl;
import ar.com.itecn1.service.ModuloService;

import java.util.List;

public class ModuloServiceImpl implements ModuloService {
    private final ModuloRepository moduloRepository;

    public ModuloServiceImpl() {
        this.moduloRepository = new ModuloRepositoryImpl();
    }

    @Override
    public Modulo findByCode(String codigoModulo) {
        return this.moduloRepository.findByCode(codigoModulo);
    }

    @Override
    public List<Modulo> findAll() {
        return this.moduloRepository.findAll();
    }

    @Override
    public void save(Modulo modulo) {
        this.moduloRepository.save(modulo);
    }

    @Override
    public void update(Modulo modulo) {
        this.moduloRepository.update(modulo);
    }

    @Override
    public void delete(Modulo modulo) {
        this.moduloRepository.delete(modulo);
    }
}
