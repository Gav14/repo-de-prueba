package ar.com.itecn1.repository.impl;

import ar.com.itecn1.model.Cuatrimestre;
import ar.com.itecn1.repository.CuatrimestreRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CuatrimestreRepositoryImpl implements CuatrimestreRepository {

    private final List<Cuatrimestre> cuatrimestresDb;

    public CuatrimestreRepositoryImpl() {
        this.cuatrimestresDb = new ArrayList<>();
        cargarDatos();
    }
    private void cargarDatos(){
        Cuatrimestre cuatrimestre1 = new Cuatrimestre(
                "1",
                LocalDate.of(2025, 3, 1),
                LocalDate.of(2025, 7, 31),
                "2025"
        );

        Cuatrimestre cuatrimestre2 = new Cuatrimestre(
                "2",
                LocalDate.of(2025, 8, 1),
                LocalDate.of(2025, 12, 15),
                "2025"
        );

        Cuatrimestre cuatrimestre3 = new Cuatrimestre(
                "3",
                LocalDate.of(2026, 3, 1),
                LocalDate.of(2026, 7, 31),
                "2026"
        );

        Cuatrimestre cuatrimestre4 = new Cuatrimestre(
                "4",
                LocalDate.of(2026, 8, 1),
                LocalDate.of(2026, 12, 15),
                "2026"
        );

        Cuatrimestre cuatrimestre5 = new Cuatrimestre(
                "5",
                LocalDate.of(2027, 3, 1),
                LocalDate.of(2027, 7, 31),
                "2027"
        );

        Cuatrimestre cuatrimestre6 = new Cuatrimestre(
                "6",
                LocalDate.of(2027, 8, 1),
                LocalDate.of(2027, 12, 12),
                "2027"
        );

        this.cuatrimestresDb.add(cuatrimestre1);
        this.cuatrimestresDb.add(cuatrimestre2);
        this.cuatrimestresDb.add(cuatrimestre3);
        this.cuatrimestresDb.add(cuatrimestre4);
        this.cuatrimestresDb.add(cuatrimestre5);
        this.cuatrimestresDb.add(cuatrimestre6);
    }

    @Override
    public Cuatrimestre findByNumber(String numeroCuatrimestre) {
        Cuatrimestre cuatrimestre = null;
        for(Cuatrimestre cuatrimestreResult: this.cuatrimestresDb){
            if(cuatrimestreResult.getNumero().equals(numeroCuatrimestre)){
                cuatrimestre = cuatrimestreResult;
                break;
            }
        }
        return cuatrimestre;
    }

    @Override
    public List<Cuatrimestre> findAll() {
        return this.cuatrimestresDb;
    }

    @Override
    public void save(Cuatrimestre cuatrimestre) {
        for(Cuatrimestre cuatrimestreResult: this.cuatrimestresDb){
            if(cuatrimestreResult.getNumero().equals(cuatrimestre.getNumero())){
                return;
            }
        }
        this.cuatrimestresDb.add(cuatrimestre);
    }

    @Override
    public void update(Cuatrimestre cuatrimestre) {
        for(Cuatrimestre cuatrimestreResult: this.cuatrimestresDb){
            if(cuatrimestreResult.getNumero().equals(cuatrimestre.getNumero())){
                cuatrimestreResult.setAnio(cuatrimestre.getAnio());
                cuatrimestreResult.setNumero(cuatrimestre.getNumero());
                cuatrimestreResult.setInicio(cuatrimestre.getInicio());
                cuatrimestreResult.setFin(cuatrimestre.getFin());
            }
        }
    }

    @Override
    public void delete(Cuatrimestre cuatrimestre) {
        for(Cuatrimestre cuatrimestreResult: this.cuatrimestresDb){
            if(cuatrimestreResult.getNumero().equals(cuatrimestre.getNumero())){
                cuatrimestreResult.setActivo(false);
            }
        }
    }
}
