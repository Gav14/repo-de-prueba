package ar.com.itecn1.repository.impl;

import ar.com.itecn1.model.Materia;
import ar.com.itecn1.repository.MateriaRepository;

import java.util.ArrayList;
import java.util.List;

public class MateriaRepositoryImpl implements MateriaRepository {
    private final List<Materia> materiaDb;

    public MateriaRepositoryImpl() {
        this.materiaDb = new ArrayList<>();
        cargarDatos();
    }

    private void cargarDatos(){
        Materia matematica1 = new Materia("An25Mat1", "Matemática 1", "1");
        Materia matematica2 = new Materia("An25Mat2", "Matemática 2", "1");
        Materia matematica3 = new Materia("An25Mat3", "Matemática 3", "2");
        Materia ingles1 = new Materia("An25Ing1", "Inglés 1", "1");
        Materia ingles2 = new Materia("An25Ing2", "Inglés 2", "1");

        //agrego las correlativas
        List<Materia> correlativasMat2 = new ArrayList<>();
        correlativasMat2.add(matematica1);
        matematica2.setCorrelativas(correlativasMat2);

        List<Materia> correlativasMat3 = new ArrayList<>();
        correlativasMat3.add(matematica2);
        matematica3.setCorrelativas(correlativasMat3);

        List<Materia> correlativasIng2 = new ArrayList<>();
        correlativasIng2.add(ingles1);
        ingles2.setCorrelativas(correlativasIng2);
        

        this.materiaDb.add(matematica1);
        this.materiaDb.add(matematica2);
        this.materiaDb.add(matematica3);
        this.materiaDb.add(ingles1);
        this.materiaDb.add(ingles2);
    }

    @Override
    public Materia findByCode(String codigoMateria) {
        Materia materia = null;
        for (Materia materiaResult : this.materiaDb) {
            if (materiaResult.getCodigoMateria().equals(codigoMateria)) {
                materia = materiaResult;
                break;
            }
        }
        return materia;
    }

    @Override
    public List<Materia> findAll() {return this.materiaDb;}

    @Override
    public void save(Materia materia) {
        for (Materia materiaResult : this.materiaDb) {
            if (materiaResult.getNombre().equals(materia.getNombre())) {
                return;
            }
        }
        this.materiaDb.add(materia);
    }
    @Override
        public void update(Materia materia){
            for (Materia materiaResult: this.materiaDb){
                if (materiaResult.getNombre().equals(materia.getNombre())){
                    materia.setNombre(materia.getNombre());
                    materia.setAnio(materia.getAnio());
                    return;
                }
            }
    }

    @Override
    public void delete(Materia materia){
        for (Materia materiaResult: this.materiaDb) {
            if (materiaResult.getCodigoMateria().equals(materia.getCodigoMateria())) {
                materiaResult.setActivo(false);
                return;
            }
        }
    }
}