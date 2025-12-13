package ar.com.itecn1.controller;

import ar.com.itecn1.model.Horario;
import ar.com.itecn1.service.HorarioService;
import ar.com.itecn1.service.impl.HorarioServiceImpl;

import java.util.List;

public class HorarioController {
    private final HorarioService horarioService;

    public HorarioController() {
        this.horarioService = new HorarioServiceImpl();
    }

    public List<Horario> findAll(){
        return this.horarioService.findAll();
    }

    public Horario findById(String id){
        return this.horarioService.findById(id);
    }

    public void createHorario(Horario horario){
        this.horarioService.save(horario);
    }

    public void updateHorario(Horario horario){
        this.horarioService.update(horario);
    }

    public void deleteHorario(Horario horario){
        this.horarioService.delete(horario);
    }
}
