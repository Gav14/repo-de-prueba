package ar.com.itecn1.service.impl;

import ar.com.itecn1.model.Materia;
import ar.com.itecn1.repository.MateriaRepository;
import ar.com.itecn1.repository.impl.MateriaRepositoryImpl;
import ar.com.itecn1.service.MateriaService;
import java.util.*;

public class MateriaServiceImpl implements MateriaService {
    private final MateriaRepository materiaRepository;

    public MateriaServiceImpl() {
        this.materiaRepository = new MateriaRepositoryImpl();
    }

    @Override
    public Materia findByCode(String codigoMateria) {
        if (codigoMateria == null || codigoMateria.trim().isEmpty()) {
            return null;
        }
        return materiaRepository.findByCode(codigoMateria.trim().toUpperCase());
    }

    @Override
    public List<Materia> findAll() {
        return materiaRepository.findAll();
    }

    @Override
    public List<Materia> findMateriasByCuatrimestre(String cuatrimestre) {
        List<Materia> resultado = new ArrayList<>();
        if (cuatrimestre == null || cuatrimestre.trim().isEmpty()) {
            return resultado;
        }
        for (Materia m : materiaRepository.findAll()) {
            if (m.getCuatrimestre().equals(cuatrimestre.trim()) && m.isActivo()) {
                resultado.add(m);
            }
        }
        return resultado;
    }

    @Override
    public Map<String, List<Materia>> agruparCorrelativasPorCuatrimestre(Materia materia) {
        Map<String, List<Materia>> correlativasPorCuatri = new LinkedHashMap<>();

        if (materia == null || materia.getCorrelativas() == null) {
            return correlativasPorCuatri;
        }

        // Inicializar map con todos los cuatrimestres
        for (int i = 1; i <= 6; i++) {
            correlativasPorCuatri.put(String.valueOf(i), new ArrayList<>());
        }

        // Agrupar correlativas por cuatrimestre
        for (Materia correlativa : materia.getCorrelativas()) {
            String cuatri = correlativa.getCuatrimestre();
            if (correlativasPorCuatri.containsKey(cuatri)) {
                correlativasPorCuatri.get(cuatri).add(correlativa);
            }
        }

        // Remover cuatrimestres vacíos
        correlativasPorCuatri.entrySet().removeIf(entry -> entry.getValue().isEmpty());

        return correlativasPorCuatri;
    }

    @Override
    public void save(Materia materia) {
        if (materia == null) return;
        if (materia.getCodigoMateria() == null || materia.getCodigoMateria().trim().isEmpty()) return;
        if (materia.getNombre() == null || materia.getNombre().trim().isEmpty()) return;
        if (materia.getCuatrimestre() == null || materia.getCuatrimestre().trim().isEmpty()) return;

        materia.setCodigoMateria(materia.getCodigoMateria().trim().toUpperCase());
        materia.setNombre(materia.getNombre().trim());
        materia.setCuatrimestre(materia.getCuatrimestre().trim());

        Materia existente = materiaRepository.findByCode(materia.getCodigoMateria());
        if (existente != null) {
            return;
        }

        if (materia.getCorrelativas() == null) {
            materia.setCorrelativas(new ArrayList<>());
        }

        materiaRepository.save(materia);
    }

    @Override
    public void update(Materia materia) {
        if (materia == null) return;
        if (materia.getCodigoMateria() == null || materia.getCodigoMateria().trim().isEmpty()) return;

        Materia existente = materiaRepository.findByCode(materia.getCodigoMateria());
        if (existente == null) {
            return;
        }

        if (materia.getCorrelativas() != null) {
            for (Materia correlativa : materia.getCorrelativas()) {
                if (correlativa == null) return;
                Materia corrExistente = materiaRepository.findByCode(correlativa.getCodigoMateria());
                if (corrExistente == null) return;
                if (!corrExistente.isActivo()) return;
            }
        }

        materiaRepository.update(materia);
    }

    @Override
    public void delete(Materia materia) {
        if (materia == null) return;

        Materia existente = materiaRepository.findByCode(materia.getCodigoMateria());
        if (existente == null) return;

        List<Materia> todas = materiaRepository.findAll();
        for (Materia m : todas) {
            if (m.getCorrelativas() != null && m.getCorrelativas().contains(existente)) {
                return;
            }
        }

        materiaRepository.delete(materia);
    }

    @Override
    public String agregarCorrelativa(String codigoMateria, String codigoCorrelativa) {
        if (codigoMateria == null || codigoMateria.trim().isEmpty()) {
            return "El código de materia no puede estar vacío";
        }
        if (codigoCorrelativa == null || codigoCorrelativa.trim().isEmpty()) {
            return "El código de correlativa no puede estar vacío";
        }

        Materia materia = materiaRepository.findByCode(codigoMateria.trim().toUpperCase());
        Materia correlativa = materiaRepository.findByCode(codigoCorrelativa.trim().toUpperCase());

        if (materia == null) {
            return "No se encontró la materia con código: " + codigoMateria;
        }
        if (correlativa == null) {
            return "No se encontró la correlativa con código: " + codigoCorrelativa;
        }
        if (!correlativa.isActivo()) {
            return "La materia " + correlativa.getNombre() + " está inactiva";
        }
        if (materia.equals(correlativa)) {
            return "Una materia no puede ser correlativa de sí misma";
        }
        if (Integer.parseInt(correlativa.getCuatrimestre()) >= Integer.parseInt(materia.getCuatrimestre())) {
            return "Las correlativas deben ser de cuatrimestres anteriores";
        }
        if (materia.getCorrelativas().contains(correlativa)) {
            return "La materia ya está agregada como correlativa";
        }
        if (existeCiclo(materia, correlativa)) {
            return "No se puede agregar porque crearía un ciclo en las correlativas";
        }

        materia.getCorrelativas().add(correlativa);
        materiaRepository.update(materia);
        return "CORRELATIVA_AGREGADA";
    }

    private boolean existeCiclo(Materia materia, Materia posibleCorrelativa) {
        if (posibleCorrelativa.getCorrelativas().contains(materia)) {
            return true;
        }
        for (Materia m : posibleCorrelativa.getCorrelativas()) {
            if (existeCiclo(materia, m)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String quitarCorrelativa(String codigoMateria, String codigoCorrelativa) {
        if (codigoMateria == null || codigoMateria.trim().isEmpty()) {
            return "El código de materia no puede estar vacío";
        }
        if (codigoCorrelativa == null || codigoCorrelativa.trim().isEmpty()) {
            return "El código de correlativa no puede estar vacío";
        }

        Materia materia = materiaRepository.findByCode(codigoMateria.trim().toUpperCase());
        Materia correlativa = materiaRepository.findByCode(codigoCorrelativa.trim().toUpperCase());

        if (materia == null) {
            return "No se encontró la materia con código: " + codigoMateria;
        }
        if (correlativa == null) {
            return "No se encontró la correlativa con código: " + codigoCorrelativa;
        }

        boolean quitada = materia.getCorrelativas().remove(correlativa);
        if (quitada) {
            materiaRepository.update(materia);
            return "CORRELATIVA_QUITADA";
        }
        return "La materia no era correlativa";
    }

    @Override
    public List<Materia> findMateriasDisponiblesParaCorrelativa(Materia materiaActual) {
        List<Materia> disponibles = new ArrayList<>();
        if (materiaActual == null) return disponibles;

        List<Materia> todas = materiaRepository.findAll();
        int cuatriActual = Integer.parseInt(materiaActual.getCuatrimestre());

        for (Materia m : todas) {
            int cuatriM = Integer.parseInt(m.getCuatrimestre());
            if (m.isActivo() &&
                    cuatriM < cuatriActual && // Solo cuatrimestres anteriores
                    !m.equals(materiaActual) &&
                    !materiaActual.getCorrelativas().contains(m) &&
                    !existeCiclo(materiaActual, m)) {
                disponibles.add(m);
            }
        }

        // Ordenar por cuatrimestre
        disponibles.sort(Comparator.comparing(Materia::getCuatrimestre));
        return disponibles;
    }
}