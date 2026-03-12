package ar.com.itecn1.repository.impl;

import ar.com.itecn1.model.*;
import ar.com.itecn1.repository.ComisionRepository;
import ar.com.itecn1.repository.CarreraRepository;
import ar.com.itecn1.repository.MateriaRepository;
import ar.com.itecn1.repository.ProfesorRepository;
import ar.com.itecn1.repository.CuatrimestreRepository;

import java.util.ArrayList;
import java.util.List;

public class ComisionRepositoryImpl implements ComisionRepository {
    private final List<ComisionMateria> comisionesDb;

    public ComisionRepositoryImpl() {
        this.comisionesDb = new ArrayList<>();
        cargarDatosIniciales();
    }

    private void cargarDatosIniciales() {
        CarreraRepository carreraRepo = new CarreraRepositoryImpl();
        MateriaRepository materiaRepo = new MateriaRepositoryImpl();
        ProfesorRepository profesorRepo = new ProfesorRepositoryImpl();
        CuatrimestreRepository cuatrimestreRepo = new CuatrimestreRepositoryImpl();

        Carrera carreraAnalisis = null;
        for (Carrera c : carreraRepo.findAll()) {
            if (c.getNombre().equals("Tecnicatura en Analisis de Sistemas")) {
                carreraAnalisis = c;
                break;
            }
        }

        Materia matematica1 = materiaRepo.findByCode("MAT-101");

        Profesor profesorRamirez = profesorRepo.findByDni("10101010");

        Cuatrimestre cuatrimestre1 = null;
        for (Cuatrimestre c : cuatrimestreRepo.findAll()) {
            // CORREGIDO: Asumiendo que getAnio() retorna String
            if (c.getNumero().equals("1") && c.getAnio().equals("2025")) {
                cuatrimestre1 = c;
                break;
            }
        }

        if (carreraAnalisis != null && matematica1 != null &&
                profesorRamirez != null && cuatrimestre1 != null) {

            ComisionMateria comision1 = new ComisionMateria(
                    "AN-MAT101-2025-1",
                    carreraAnalisis,
                    matematica1,
                    cuatrimestre1,
                    profesorRamirez
            );

            comisionesDb.add(comision1);
            System.out.println("✓ Comisión precargada: AN-MAT101-2025-1");
        }
    }

    @Override
    public ComisionMateria findByCode(String codigoComision) {
        for (ComisionMateria comision : this.comisionesDb) {
            if (comision.getCodigo().equalsIgnoreCase(codigoComision)) {
                return comision;
            }
        }
        return null;
    }

    @Override
    public List<ComisionMateria> findAll() {
        return new ArrayList<>(this.comisionesDb);
    }

    @Override
    public List<ComisionMateria> findByCarrera(String nombreCarrera) {
        List<ComisionMateria> resultado = new ArrayList<>();
        for (ComisionMateria comision : this.comisionesDb) {
            if (comision.getCarrera() != null &&
                    comision.getCarrera().getNombre().equalsIgnoreCase(nombreCarrera)) {
                resultado.add(comision);
            }
        }
        return resultado;
    }

    @Override
    public List<ComisionMateria> findByMateria(String codigoMateria) {
        List<ComisionMateria> resultado = new ArrayList<>();
        for (ComisionMateria comision : this.comisionesDb) {
            if (comision.getMateria() != null &&
                    comision.getMateria().getCodigoMateria().equalsIgnoreCase(codigoMateria)) {
                resultado.add(comision);
            }
        }
        return resultado;
    }

    @Override
    public List<ComisionMateria> findByCuatrimestre(String numeroCuatrimestre) {
        List<ComisionMateria> resultado = new ArrayList<>();
        for (ComisionMateria comision : this.comisionesDb) {
            if (comision.getCuatrimestre() != null &&
                    comision.getCuatrimestre().getNumero().equals(numeroCuatrimestre)) {
                resultado.add(comision);
            }
        }
        return resultado;
    }

    @Override
    public List<ComisionMateria> findByProfesor(int dniProfesor) {
        List<ComisionMateria> resultado = new ArrayList<>();
        String dniProfesorStr = String.valueOf(dniProfesor);
        for (ComisionMateria comision : this.comisionesDb) {
            if (comision.getProfesor() != null &&
                    comision.getProfesor().getDni().equals(dniProfesorStr)) {
                resultado.add(comision);
            }
        }
        return resultado;
    }

    @Override
    public boolean existeComisionActivaParaMateriaEnCuatrimestre(String codigoMateria, String numeroCuatrimestre) {
        for (ComisionMateria comision : this.comisionesDb) {
            if (comision.isActivo() &&
                    comision.getMateria() != null &&
                    comision.getMateria().getCodigoMateria().equalsIgnoreCase(codigoMateria) &&
                    comision.getCuatrimestre() != null &&
                    comision.getCuatrimestre().getNumero().equals(numeroCuatrimestre)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void save(ComisionMateria comision) {
        if (findByCode(comision.getCodigo()) != null) {
            throw new IllegalArgumentException("Ya existe una comisión con el código: " + comision.getCodigo());
        }
        this.comisionesDb.add(comision);
    }

    @Override
    public void update(ComisionMateria comision) {
        for (int i = 0; i < this.comisionesDb.size(); i++) {
            if (this.comisionesDb.get(i).getCodigo().equalsIgnoreCase(comision.getCodigo())) {
                this.comisionesDb.set(i, comision);
                return;
            }
        }
    }

    @Override
    public void delete(ComisionMateria comision) {
        ComisionMateria existente = findByCode(comision.getCodigo());
        if (existente != null) {
            existente.setActivo(false);
        }
    }
}