package ar.com.itecn1.repository.impl;

import ar.com.itecn1.model.Materia;
import ar.com.itecn1.model.PlanEstudio;
import ar.com.itecn1.repository.MateriaRepository;
import ar.com.itecn1.repository.PlanEstudioRepository;

import java.util.ArrayList;
import java.util.List;

public class PlanEstudioRepositoryImpl implements PlanEstudioRepository {
    private final List<PlanEstudio> planEstudiosDb;
    private final MateriaRepository materiaRepository;

    public PlanEstudioRepositoryImpl() {
        this.planEstudiosDb = new ArrayList<>();
        this.materiaRepository = new MateriaRepositoryImpl();
        cargarDatos();
    }

    private void cargarDatos(){
        PlanEstudio planAnalista2025 = new PlanEstudio("Analista", "2025", "3", "Tecnicatura en análisis de sistemas");
        PlanEstudio planGastronomia2025 = new PlanEstudio("Gastronomia", "2025", "3", "Tecnicatura en gastronomía");

        //agrega las materias al plan
        List<Materia> materiasAnalista = new ArrayList<>();
        materiasAnalista.add(this.materiaRepository.findByCode("An25Mat1"));
        materiasAnalista.add(this.materiaRepository.findByCode("An25Mat2"));
        materiasAnalista.add(this.materiaRepository.findByCode("An25Mat3"));
        materiasAnalista.add(this.materiaRepository.findByCode("An25Ing1"));
        materiasAnalista.add(this.materiaRepository.findByCode("An25Ing2"));
        planAnalista2025.setMaterias(materiasAnalista);

        this.planEstudiosDb.add(planAnalista2025);
        this.planEstudiosDb.add(planGastronomia2025);
    }

    @Override
    public PlanEstudio findByName(String nombre){
        PlanEstudio planEstudio = null;
        for (PlanEstudio planEstudioResult: this.planEstudiosDb){
            if(planEstudioResult.getNombre().equals(nombre)){
                planEstudio = planEstudioResult;
                        break;
            }
        }
        return planEstudio;
    }

    @Override
    public List<PlanEstudio> findAll(){return this.planEstudiosDb;}

    @Override
    public void save(PlanEstudio planEstudio){
        for (PlanEstudio planEstudioResult: this.planEstudiosDb){
            if (planEstudioResult.getNombre().equals(planEstudio.getNombre())){
                return;
            }
        }
        this.planEstudiosDb.add(planEstudio);
    }

    @Override
    public void update(PlanEstudio planEstudio){
        for (PlanEstudio planEstudioResult: this.planEstudiosDb){
            if (planEstudioResult.getNombre().equals(planEstudio.getNombre())){
                planEstudioResult.setNombre(planEstudio.getNombre());
                planEstudioResult.setAnio(planEstudio.getAnio());
                planEstudioResult.setDuracion(planEstudio.getDuracion());
                planEstudioResult.setTitulo(planEstudio.getTitulo());
                return;
            }
        }
    }
    @Override
    public void delete(PlanEstudio planEstudio){
        for(PlanEstudio planEstudioResult: this.planEstudiosDb){
            if(planEstudioResult.getNombre().equals(planEstudio.getNombre())){
                planEstudioResult.setActivo(false);
                return;
            }
        }
    }
}
