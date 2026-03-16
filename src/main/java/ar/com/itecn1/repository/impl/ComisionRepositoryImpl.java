package ar.com.itecn1.repository.impl;

import ar.com.itecn1.model.ComisionMateria;
import ar.com.itecn1.repository.ComisionRepository;

import java.util.ArrayList;
import java.util.List;

public class ComisionRepositoryImpl implements ComisionRepository {

    private final List<ComisionMateria> comisionesDb;

    public ComisionRepositoryImpl() {
        this.comisionesDb = new ArrayList<>();
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

            if (comision.isActivo()
                    && comision.getMateria() != null
                    && comision.getMateria().getCodigoMateria().equalsIgnoreCase(codigoMateria)
                    && comision.getCuatrimestre() != null
                    && comision.getCuatrimestre().getNumero().equals(numeroCuatrimestre)) {

                return true;
            }
        }

        return false;
    }

    @Override
    public void save(ComisionMateria comision) {

        if (findByCode(comision.getCodigo()) != null) {
            throw new IllegalArgumentException(
                    "Ya existe una comisión con el código: " + comision.getCodigo()
            );
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