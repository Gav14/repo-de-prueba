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
        Modulo modulo1 = new Modulo("AnMod1", LocalTime.of(18, 0), LocalTime.of(19, 0));
        Modulo modulo2 = new Modulo("AnMod2", LocalTime.of(19, 0), LocalTime.of(20, 0));
        Modulo modulo3 = new Modulo("AnMod3", LocalTime.of(20, 0), LocalTime.of(21, 0));
        Modulo modulo4 = new Modulo("AnMod4", LocalTime.of(21, 0), LocalTime.of(22, 0));

        // Guardar en la "base de datos" (la lista)
        this.modulosDb.add(modulo1);
        this.modulosDb.add(modulo2);
        this.modulosDb.add(modulo3);
        this.modulosDb.add(modulo4);
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
