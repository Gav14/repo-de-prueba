package ar.com.itecn1.service.impl;

import ar.com.itecn1.model.Horario;
import ar.com.itecn1.repository.HorarioRepository;
import ar.com.itecn1.repository.impl.HorarioRepositoryImpl;
import ar.com.itecn1.service.HorarioService;

import java.util.List;

public class HorarioServiceImpl implements HorarioService {
    private final HorarioRepository horarioRepository;

    public HorarioServiceImpl() {
        this.horarioRepository = new HorarioRepositoryImpl();
    }

    @Override
    public Horario findById(String id) {
        return this.horarioRepository.findById(id);
    }

    @Override
    public List<Horario> findAll() {
        return this.horarioRepository.findAll();
    }

    @Override
    public void save(Horario horario) {
        this.horarioRepository.save(horario);
    }

    @Override
    public void update(Horario horario) {
        this.horarioRepository.update(horario);
    }

    @Override
    public void delete(Horario horario) {
        this.horarioRepository.delete(horario);
    }
}
