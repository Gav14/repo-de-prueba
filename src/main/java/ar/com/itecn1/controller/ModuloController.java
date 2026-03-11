package ar.com.itecn1.controller;

import ar.com.itecn1.model.Modulo;
import ar.com.itecn1.service.ModuloService;
import ar.com.itecn1.service.impl.ModuloServiceImpl;

import java.util.List;

public class ModuloController {
    private final ModuloService moduloService;


    public ModuloController() {
        this.moduloService = new ModuloServiceImpl();
    }

    public List<Modulo> findAll(){
        return this.moduloService.findAll();
    }

    public Modulo findByCode(String codigoModulo){
        return this.moduloService.findByCode(codigoModulo);
    }

    public void createModulo(Modulo modulo){
        this.moduloService.save(modulo);
    }

    public void updateModulo(Modulo modulo){
        this.moduloService.update(modulo);
    }

    public void deleteModulo(Modulo modulo){
        this.moduloService.delete(modulo);
    }
}
