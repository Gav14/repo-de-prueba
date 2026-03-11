package ar.com.itecn1.repository.impl;

import ar.com.itecn1.model.*;
import ar.com.itecn1.repository.CarreraRepository;
import ar.com.itecn1.repository.CuatrimestreRepository;
import ar.com.itecn1.repository.HorarioRepository;
import ar.com.itecn1.repository.ModuloRepository;

import java.util.ArrayList;
import java.util.List;

public class HorarioRepositoryImpl implements HorarioRepository {

    private final List<Horario> horariosDb;
    private final ModuloRepository moduloRepository;
    private final CarreraRepository carreraRepository;
    private final CuatrimestreRepository cuatrimestreRepository;

    public HorarioRepositoryImpl() {
        this.horariosDb = new ArrayList<>();
        this.moduloRepository = new ModuloRepositoryImpl();
        this.carreraRepository = new CarreraRepositoryImpl();
        this.cuatrimestreRepository = new CuatrimestreRepositoryImpl();
        cargarDatos();
    }

    private void cargarDatos() {
        Carrera analista = this.carreraRepository.findByName("Tecnicatura en analisis de sistemas");

        Cuatrimestre cuatrimetre1 = this.cuatrimestreRepository.findByNumber("1");
        Cuatrimestre cuatrimetre2 = this.cuatrimestreRepository.findByNumber("2");

        // CUATRIMESTRE 1
        // horarios lunes
        Horario horario1 = new Horario("1", Dia.LUNES, analista, cuatrimetre1, this.moduloRepository.findByCode("AnMod1"));
        Horario horario2 = new Horario("2", Dia.LUNES, analista, cuatrimetre1, this.moduloRepository.findByCode("AnMod2"));
        Horario horario3 = new Horario("3", Dia.LUNES, analista, cuatrimetre1, this.moduloRepository.findByCode("AnMod3"));
        Horario horario4 = new Horario("4", Dia.LUNES, analista, cuatrimetre1, this.moduloRepository.findByCode("AnMod4"));
        // horarios martes
        Horario horario5 = new Horario("5", Dia.MARTES, analista, cuatrimetre1, this.moduloRepository.findByCode("AnMod1"));
        Horario horario6 = new Horario("6", Dia.MARTES, analista, cuatrimetre1, this.moduloRepository.findByCode("AnMod2"));
        Horario horario7 = new Horario("7", Dia.MARTES, analista, cuatrimetre1, this.moduloRepository.findByCode("AnMod3"));
        Horario horario8 = new Horario("8", Dia.MARTES, analista, cuatrimetre1, this.moduloRepository.findByCode("AnMod4"));
        // horarios miercoles
        Horario horario9 = new Horario("9", Dia.MIERCOLES, analista, cuatrimetre1, this.moduloRepository.findByCode("AnMod1"));
        Horario horario10 = new Horario("10", Dia.MIERCOLES, analista, cuatrimetre1, this.moduloRepository.findByCode("AnMod2"));
        Horario horario11 = new Horario("11", Dia.MIERCOLES, analista, cuatrimetre1, this.moduloRepository.findByCode("AnMod3"));
        Horario horario12 = new Horario("12", Dia.MIERCOLES, analista, cuatrimetre1, this.moduloRepository.findByCode("AnMod4"));
        // horarios jueves
        Horario horario13 = new Horario("13", Dia.JUEVES, analista, cuatrimetre1, this.moduloRepository.findByCode("AnMod1"));
        Horario horario14 = new Horario("14", Dia.JUEVES, analista, cuatrimetre1, this.moduloRepository.findByCode("AnMod2"));
        Horario horario15 = new Horario("15", Dia.JUEVES, analista, cuatrimetre1, this.moduloRepository.findByCode("AnMod3"));
        Horario horario16 = new Horario("16", Dia.JUEVES, analista, cuatrimetre1, this.moduloRepository.findByCode("AnMod4"));
        // horarios viernes
        Horario horario17 = new Horario("17", Dia.VIERNES, analista, cuatrimetre1, this.moduloRepository.findByCode("AnMod1"));
        Horario horario18 = new Horario("18", Dia.VIERNES, analista, cuatrimetre1, this.moduloRepository.findByCode("AnMod2"));
        Horario horario19 = new Horario("19", Dia.VIERNES, analista, cuatrimetre1, this.moduloRepository.findByCode("AnMod3"));
        Horario horario20 = new Horario("20", Dia.VIERNES, analista, cuatrimetre1, this.moduloRepository.findByCode("AnMod4"));

        //CUATRIMESTRE 2
        // horarios lunes
        Horario horario21 = new Horario("21", Dia.LUNES, analista, cuatrimetre2, this.moduloRepository.findByCode("AnMod1"));
        Horario horario22 = new Horario("22", Dia.LUNES, analista, cuatrimetre2, this.moduloRepository.findByCode("AnMod2"));
        Horario horario23 = new Horario("23", Dia.LUNES, analista, cuatrimetre2, this.moduloRepository.findByCode("AnMod3"));
        Horario horario24 = new Horario("24", Dia.LUNES, analista, cuatrimetre2, this.moduloRepository.findByCode("AnMod4"));
        // horarios martes
        Horario horario25 = new Horario("25", Dia.MARTES, analista, cuatrimetre2, this.moduloRepository.findByCode("AnMod1"));
        Horario horario26 = new Horario("26", Dia.MARTES, analista, cuatrimetre2, this.moduloRepository.findByCode("AnMod2"));
        Horario horario27 = new Horario("27", Dia.MARTES, analista, cuatrimetre2, this.moduloRepository.findByCode("AnMod3"));
        Horario horario28 = new Horario("28", Dia.MARTES, analista, cuatrimetre2, this.moduloRepository.findByCode("AnMod4"));
        // horarios miercoles
        Horario horario29 = new Horario("29", Dia.MIERCOLES, analista, cuatrimetre2, this.moduloRepository.findByCode("AnMod1"));
        Horario horario30 = new Horario("30", Dia.MIERCOLES, analista, cuatrimetre2, this.moduloRepository.findByCode("AnMod2"));
        Horario horario31 = new Horario("31", Dia.MIERCOLES, analista, cuatrimetre2, this.moduloRepository.findByCode("AnMod3"));
        Horario horario32 = new Horario("32", Dia.MIERCOLES, analista, cuatrimetre2, this.moduloRepository.findByCode("AnMod4"));
        // horarios jueves
        Horario horario33 = new Horario("33", Dia.JUEVES, analista, cuatrimetre2, this.moduloRepository.findByCode("AnMod1"));
        Horario horario34 = new Horario("34", Dia.JUEVES, analista, cuatrimetre2, this.moduloRepository.findByCode("AnMod2"));
        Horario horario35 = new Horario("35", Dia.JUEVES, analista, cuatrimetre2, this.moduloRepository.findByCode("AnMod3"));
        Horario horario36 = new Horario("36", Dia.JUEVES, analista, cuatrimetre2, this.moduloRepository.findByCode("AnMod4"));
        // horarios viernes
        Horario horario37 = new Horario("37", Dia.VIERNES, analista, cuatrimetre2, this.moduloRepository.findByCode("AnMod1"));
        Horario horario38 = new Horario("38", Dia.VIERNES, analista, cuatrimetre2, this.moduloRepository.findByCode("AnMod2"));
        Horario horario39 = new Horario("39", Dia.VIERNES, analista, cuatrimetre2, this.moduloRepository.findByCode("AnMod3"));
        Horario horario40 = new Horario("40", Dia.VIERNES, analista, cuatrimetre2, this.moduloRepository.findByCode("AnMod4"));

        this.horariosDb.add(horario1);
        this.horariosDb.add(horario2);
        this.horariosDb.add(horario3);
        this.horariosDb.add(horario4);
        this.horariosDb.add(horario5);
        this.horariosDb.add(horario6);
        this.horariosDb.add(horario7);
        this.horariosDb.add(horario8);
        this.horariosDb.add(horario9);
        this.horariosDb.add(horario10);
        this.horariosDb.add(horario11);
        this.horariosDb.add(horario12);
        this.horariosDb.add(horario13);
        this.horariosDb.add(horario14);
        this.horariosDb.add(horario15);
        this.horariosDb.add(horario16);
        this.horariosDb.add(horario17);
        this.horariosDb.add(horario18);
        this.horariosDb.add(horario19);
        this.horariosDb.add(horario20);
        this.horariosDb.add(horario21);
        this.horariosDb.add(horario22);
        this.horariosDb.add(horario23);
        this.horariosDb.add(horario24);
        this.horariosDb.add(horario25);
        this.horariosDb.add(horario26);
        this.horariosDb.add(horario27);
        this.horariosDb.add(horario28);
        this.horariosDb.add(horario29);
        this.horariosDb.add(horario30);
        this.horariosDb.add(horario31);
        this.horariosDb.add(horario32);
        this.horariosDb.add(horario33);
        this.horariosDb.add(horario34);
        this.horariosDb.add(horario35);
        this.horariosDb.add(horario36);
        this.horariosDb.add(horario37);
        this.horariosDb.add(horario38);
        this.horariosDb.add(horario39);
        this.horariosDb.add(horario40);

    }

    @Override
    public Horario findById(String id) {
        Horario horario = null;
        for (Horario horarioResult: this.horariosDb) {
            if (horarioResult.getId().equals(id)) {
                horario = horarioResult;
                break;
            }
        }
        return horario;
    }

    @Override
    public List<Horario> findAll() {
        return this.horariosDb;
    }

    @Override
    public void save(Horario horario) {
        for (Horario horarioResult: this.horariosDb) {
            if (horarioResult.getId().equals(horario.getId())) {
                return;
            }
        }
        this.horariosDb.add(horario);
    }

    @Override
    public void update(Horario horario) {
        for (Horario horarioResult: this.horariosDb) {
            if (horarioResult.getId().equals(horario.getId())) {
                horarioResult.setDia(horario.getDia());
                horarioResult.setCarrera(horario.getCarrera());
                horarioResult.setCuatrimestre(horario.getCuatrimestre());
                horarioResult.setModulo(horario.getModulo());
            }
        }
    }

    @Override
    public void delete(Horario horario) {
        for (Horario horarioResult: this.horariosDb) {
            if (horarioResult.getId().equals(horario.getId())) {
                horarioResult.setActivo(false);
            }
        }
    }
}

