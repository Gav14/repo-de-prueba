package ar.com.itecn1.controller;

import ar.com.itecn1.model.Cuatrimestre;
import ar.com.itecn1.service.CuatrimestreService;
import ar.com.itecn1.service.impl.CuatrimestreServiceImpl;

import java.util.List;

public class CuatrimestreController {
    private final CuatrimestreService cuatrimestreService;

    public CuatrimestreController() {
        this.cuatrimestreService = new CuatrimestreServiceImpl();
    }

    public List<Cuatrimestre> findAll(){
        return this.cuatrimestreService.findAll();
    }

    public Cuatrimestre findByNumber(String numeroCuatrimestre){
        return this.cuatrimestreService.findByNumber(numeroCuatrimestre);
    }

    public void createCuatrimestre(Cuatrimestre cuatrimestre){
        this.cuatrimestreService.save(cuatrimestre);
    }

    public void updateCuatrimestre(Cuatrimestre cuatrimestre){
        this.cuatrimestreService.update(cuatrimestre);
    }

    public void deleteCuatrimestre(Cuatrimestre cuatrimestre){
        this.cuatrimestreService.delete(cuatrimestre);
    }
}
