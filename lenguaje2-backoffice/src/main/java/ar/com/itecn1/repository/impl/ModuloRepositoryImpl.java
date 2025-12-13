package ar.com.itecn1.repository.impl;

import ar.com.itecn1.model.Modulo;
import ar.com.itecn1.repository.ModuloRepository;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ModuloRepositoryImpl implements ModuloRepository {
    private final List<Modulo> modulosDb;

    public ModuloRepositoryImpl() {
        this.modulosDb = new ArrayList<>();
        cargarDatos();
    }

    private void cargarDatos() {
        // CUATRIMESTRE 1
        // lunes
        Modulo modulo1 = new Modulo("AnLunMod1", LocalTime.of(18, 0), LocalTime.of(19, 0));
        Modulo modulo2 = new Modulo("AnLunMod2", LocalTime.of(19, 0), LocalTime.of(20, 0));
        Modulo modulo3 = new Modulo("AnLunMod3", LocalTime.of(20, 0), LocalTime.of(21, 0));
        Modulo modulo4 = new Modulo("AnLunMod4", LocalTime.of(21, 0), LocalTime.of(22, 0));

        // martes
        Modulo modulo5 = new Modulo("AnMarMod1", LocalTime.of(18, 0), LocalTime.of(19, 0));
        Modulo modulo6 = new Modulo("AnMarMod2", LocalTime.of(19, 0), LocalTime.of(20, 0));
        Modulo modulo7 = new Modulo("AnMarMod3", LocalTime.of(20, 0), LocalTime.of(21, 0));
        Modulo modulo8 = new Modulo("AnMarMod4", LocalTime.of(21, 0), LocalTime.of(22, 0));

        // miercoles
        Modulo modulo9 = new Modulo("AnMieMod1", LocalTime.of(18, 0), LocalTime.of(19, 0));
        Modulo modulo10 = new Modulo("AnMieMod2", LocalTime.of(19, 0), LocalTime.of(20, 0));
        Modulo modulo11 = new Modulo("AnMieMod3", LocalTime.of(20, 0), LocalTime.of(21, 0));
        Modulo modulo12 = new Modulo("AnMieMod4", LocalTime.of(21, 0), LocalTime.of(22, 0));

        // jueves
        Modulo modulo13 = new Modulo("AnJueMod1", LocalTime.of(18, 0), LocalTime.of(19, 0));
        Modulo modulo14 = new Modulo("AnJueMod2", LocalTime.of(19, 0), LocalTime.of(20, 0));
        Modulo modulo15 = new Modulo("AnJueMod3", LocalTime.of(20, 0), LocalTime.of(21, 0));
        Modulo modulo16 = new Modulo("AnJueMod4", LocalTime.of(21, 0), LocalTime.of(22, 0));

        // viernes
        Modulo modulo17 = new Modulo("AnVieMod1", LocalTime.of(18, 0), LocalTime.of(19, 0));
        Modulo modulo18 = new Modulo("AnVieMod2", LocalTime.of(19, 0), LocalTime.of(20, 0));
        Modulo modulo19 = new Modulo("AnVieMod3", LocalTime.of(20, 0), LocalTime.of(21, 0));
        Modulo modulo20 = new Modulo("AnVieMod4", LocalTime.of(21, 0), LocalTime.of(22, 0));

//        // CUATRIMESTRE 2
//        // lunes
//        Modulo modulo21 = new Modulo("AnC2LunMod1", LocalTime.of(18, 0), LocalTime.of(19, 0));
//        Modulo modulo22 = new Modulo("AnC2LunMod2", LocalTime.of(19, 0), LocalTime.of(20, 0));
//        Modulo modulo23 = new Modulo("AnC2LunMod3", LocalTime.of(20, 0), LocalTime.of(21, 0));
//        Modulo modulo24 = new Modulo("AnC2LunMod4", LocalTime.of(21, 0), LocalTime.of(22, 0));
//
//        // martes
//        Modulo modulo25 = new Modulo("AnC2MarMod1", LocalTime.of(18, 0), LocalTime.of(19, 0));
//        Modulo modulo26 = new Modulo("AnC2MarMod2", LocalTime.of(19, 0), LocalTime.of(20, 0));
//        Modulo modulo27 = new Modulo("AnC2MarMod3", LocalTime.of(20, 0), LocalTime.of(21, 0));
//        Modulo modulo28 = new Modulo("AnC2MarMod4", LocalTime.of(21, 0), LocalTime.of(22, 0));
//
//        // miercoles
//        Modulo modulo29 = new Modulo("AnC2MieMod1", LocalTime.of(18, 0), LocalTime.of(19, 0));
//        Modulo modulo30 = new Modulo("AnC2MieMod2", LocalTime.of(19, 0), LocalTime.of(20, 0));
//        Modulo modulo31 = new Modulo("AnC2MieMod3", LocalTime.of(20, 0), LocalTime.of(21, 0));
//        Modulo modulo32 = new Modulo("AnC2MieMod4", LocalTime.of(21, 0), LocalTime.of(22, 0));
//
//        // jueves
//        Modulo modulo33 = new Modulo("AnC2JueMod1", LocalTime.of(18, 0), LocalTime.of(19, 0));
//        Modulo modulo34 = new Modulo("AnC2JueMod2", LocalTime.of(19, 0), LocalTime.of(20, 0));
//        Modulo modulo35 = new Modulo("AnC2JueMod3", LocalTime.of(20, 0), LocalTime.of(21, 0));
//        Modulo modulo36 = new Modulo("AnC2JueMod4", LocalTime.of(21, 0), LocalTime.of(22, 0));
//
//        // viernes
//        Modulo modulo37 = new Modulo("AnC2VieMod1", LocalTime.of(18, 0), LocalTime.of(19, 0));
//        Modulo modulo38 = new Modulo("AnC2VieMod2", LocalTime.of(19, 0), LocalTime.of(20, 0));
//        Modulo modulo39 = new Modulo("AnC2VieMod3", LocalTime.of(20, 0), LocalTime.of(21, 0));
//        Modulo modulo40 = new Modulo("AnC2VieMod4", LocalTime.of(21, 0), LocalTime.of(22, 0));

        // Guardar en la "base de datos" (la lista)
        this.modulosDb.add(modulo1);
        this.modulosDb.add(modulo2);
        this.modulosDb.add(modulo3);
        this.modulosDb.add(modulo4);
        this.modulosDb.add(modulo5);
        this.modulosDb.add(modulo6);
        this.modulosDb.add(modulo7);
        this.modulosDb.add(modulo8);
        this.modulosDb.add(modulo9);
        this.modulosDb.add(modulo10);
        this.modulosDb.add(modulo11);
        this.modulosDb.add(modulo12);
        this.modulosDb.add(modulo13);
        this.modulosDb.add(modulo14);
        this.modulosDb.add(modulo15);
        this.modulosDb.add(modulo16);
        this.modulosDb.add(modulo17);
        this.modulosDb.add(modulo18);
        this.modulosDb.add(modulo19);
        this.modulosDb.add(modulo20);
//        this.modulosDb.add(modulo21);
//        this.modulosDb.add(modulo22);
//        this.modulosDb.add(modulo23);
//        this.modulosDb.add(modulo24);
//        this.modulosDb.add(modulo25);
//        this.modulosDb.add(modulo26);
//        this.modulosDb.add(modulo27);
//        this.modulosDb.add(modulo28);
//        this.modulosDb.add(modulo29);
//        this.modulosDb.add(modulo30);
//        this.modulosDb.add(modulo31);
//        this.modulosDb.add(modulo32);
//        this.modulosDb.add(modulo33);
//        this.modulosDb.add(modulo34);
//        this.modulosDb.add(modulo35);
//        this.modulosDb.add(modulo36);
//        this.modulosDb.add(modulo37);
//        this.modulosDb.add(modulo38);
//        this.modulosDb.add(modulo39);
//        this.modulosDb.add(modulo40);
    }

    @Override
    public Modulo findByCode(String codigoModulo) {
        Modulo modulo = null;
        for(Modulo moduloResult: this.modulosDb){
            if(moduloResult.getCodigo().equals(codigoModulo)){
                modulo = moduloResult;
                break;
            }
        }
        return modulo;
    }

    @Override
    public List<Modulo> findAll() {
        return this.modulosDb;
    }

    @Override
    public void save(Modulo modulo) {
        for(Modulo moduloResult: this.modulosDb){
            if(moduloResult.getCodigo().equals(modulo.getCodigo())){
                return;
            }
        }
        this.modulosDb.add(modulo);
    }

    @Override
    public void update(Modulo modulo) {
        for(Modulo moduloResult: this.modulosDb){
            if(moduloResult.getCodigo().equals(modulo.getCodigo())){
                moduloResult.setInicio(modulo.getInicio());
                moduloResult.setFin(modulo.getFin());
                return;
            }
        }
    }

    @Override
    public void delete(Modulo modulo) {
        for(Modulo moduloResult: this.modulosDb){
            if(moduloResult.getCodigo().equals(modulo.getCodigo())){
                moduloResult.setActivo(false);
                return;
            }
        }
    }
}
