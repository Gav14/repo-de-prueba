package ar.com.itecn1.repository.impl;

import ar.com.itecn1.model.Materia;
import ar.com.itecn1.repository.MateriaRepository;
import java.util.ArrayList;
import java.util.List;

public class MateriaRepositoryImpl implements MateriaRepository {
    private final List<Materia> materiaDb;

    public MateriaRepositoryImpl() {
        this.materiaDb = new ArrayList<>();
        cargarPlanAnalista();
    }

    private void cargarPlanAnalista() {
        // ============================================================
        // 1° CUATRIMESTRE - Materias sin correlativas
        // ============================================================
        Materia tht = new Materia("THT-101", "Taller de Herramientas", "1");
        Materia mat1 = new Materia("MAT-101", "Matemática I", "1");
        Materia icc = new Materia("ICC-101", "Introducción a la Computación", "1");
        Materia ipr = new Materia("IPR-101", "Introducción a la Programación", "1");
        Materia ing1 = new Materia("ING-101", "Inglés I", "1");
        Materia tds = new Materia("TDS-101", "Teoría de Sistemas", "1");
        Materia fde = new Materia("FDE-101", "Fundamentos de Electrónica", "1");

        // ============================================================
        // 2° CUATRIMESTRE
        // ============================================================
        Materia mat2 = new Materia("MAT-201", "Matemática II", "2");
        Materia so = new Materia("SOP-201", "Sistemas Operativos", "2");
        Materia len1 = new Materia("LEN-201", "Lenguaje I", "2");
        Materia ing2 = new Materia("ING-201", "Inglés II", "2");
        Materia ecy = new Materia("ECY-201", "Elementos de Contabilidad", "2");

        // ============================================================
        // 3° CUATRIMESTRE
        // ============================================================
        Materia mat3 = new Materia("MAT-301", "Matemática III", "3");
        Materia len2 = new Materia("LEN-301", "Lenguaje II", "3");
        Materia bdd = new Materia("BDD-301", "Base de Datos", "3");
        Materia ads = new Materia("ADS-301", "Análisis de Sistemas", "3");
        Materia iec = new Materia("IEC-301", "Introducción a la Economía", "3");

        // ============================================================
        // 4° CUATRIMESTRE
        // ============================================================
        Materia est = new Materia("EST-401", "Estadísticas", "4");
        Materia edd = new Materia("EDD-401", "Estructura de Datos", "4");
        Materia poo = new Materia("POO-401", "Programación Orientada a Objetos", "4");
        Materia rei = new Materia("REI-401", "Redes e Internet", "4");
        Materia pas1 = new Materia("PAS-401", "Pasantías I", "4");

        // ============================================================
        // 5° CUATRIMESTRE
        // ============================================================
        Materia tsi = new Materia("TSI-501", "Taller de Seguridad Informática", "5");
        Materia rrhh = new Materia("RRH-501", "Recursos Humanos", "5");
        Materia tred = new Materia("TRD-501", "Taller de Redes", "5");
        Materia tsis1 = new Materia("TS1-501", "Taller de Sistemas I", "5");
        Materia oym = new Materia("OYM-501", "Organización y Métodos", "5");
        Materia tpw = new Materia("TPW-501", "Taller de Página Web", "5");

        // ============================================================
        // 6° CUATRIMESTRE
        // ============================================================
        Materia adp = new Materia("ADP-601", "Administración de la Producción", "6");
        Materia isw = new Materia("ISW-601", "Ingeniería en Software", "6");
        Materia tsis2 = new Materia("TS2-601", "Taller de Sistemas II", "6");
        Materia mkt = new Materia("MKT-601", "Marketing", "6");
        Materia fin = new Materia("FIN-601", "Finanzas", "6");
        Materia eyd = new Materia("EYD-601", "Ética y Deontología", "6");
        Materia pas2 = new Materia("PAS-601", "Pasantías II", "6");

        // ============================================================
        // CONFIGURAR CORRELATIVAS
        // ============================================================

        // 2° Cuatrimestre
        mat2.getCorrelativas().add(mat1);           // Matemática II necesita Matemática I
        so.getCorrelativas().add(icc);               // Sistemas Operativos necesita Introducción a la Computación
        len1.getCorrelativas().add(ipr);              // Lenguaje I necesita Introducción a la Programación
        ing2.getCorrelativas().add(ing1);             // Inglés II necesita Inglés I

        // 3° Cuatrimestre
        mat3.getCorrelativas().add(mat2);            // Matemática III necesita Matemática II
        len2.getCorrelativas().add(len1);            // Lenguaje II necesita Lenguaje I
        bdd.getCorrelativas().add(len1);             // Base de Datos necesita Lenguaje I
        ads.getCorrelativas().add(tds);               // Análisis de Sistemas necesita Teoría de Sistemas

        // 4° Cuatrimestre
        est.getCorrelativas().add(mat3);              // Estadísticas necesita Matemática III
        edd.getCorrelativas().add(len2);              // Estructura de Datos necesita Lenguaje II
        poo.getCorrelativas().add(len2);              // POO necesita Lenguaje II
        rei.getCorrelativas().add(so);                 // Redes e Internet necesita Sistemas Operativos

        // Pasantías I necesita todas las del 1° y 2° Cuatrimestre
        List<Materia> primeroYSegundo = List.of(tht, mat1, icc, ipr, ing1, tds, fde, mat2, so, len1, ing2, ecy);
        pas1.getCorrelativas().addAll(primeroYSegundo);

        // 5° Cuatrimestre
        tsi.getCorrelativas().add(rei);               // Taller Seguridad necesita Redes
        rrhh.getCorrelativas().add(tds);               // RRHH necesita Teoría de Sistemas
        tred.getCorrelativas().add(rei);               // Taller Redes necesita Redes
        tsis1.getCorrelativas().add(ads);              // Taller Sistemas I necesita Análisis

        // 6° Cuatrimestre
        adp.getCorrelativas().add(so);                 // Adm Producción necesita Sistemas Operativos
        isw.getCorrelativas().add(poo);                // Ingeniería Software necesita POO
        tsis2.getCorrelativas().add(tsis1);            // Taller Sistemas II necesita Taller Sistemas I
        mkt.getCorrelativas().add(tds);                 // Marketing necesita Teoría de Sistemas
        fin.getCorrelativas().add(iec);                 // Finanzas necesita Introducción a Economía
        pas2.getCorrelativas().add(pas1);               // Pasantías II necesita Pasantías I

        // Agregar todas las materias
        materiaDb.addAll(List.of(
                // 1° Cuatrimestre
                tht, mat1, icc, ipr, ing1, tds, fde,
                // 2° Cuatrimestre
                mat2, so, len1, ing2, ecy,
                // 3° Cuatrimestre
                mat3, len2, bdd, ads, iec,
                // 4° Cuatrimestre
                est, edd, poo, rei, pas1,
                // 5° Cuatrimestre
                tsi, rrhh, tred, tsis1, oym, tpw,
                // 6° Cuatrimestre
                adp, isw, tsis2, mkt, fin, eyd, pas2
        ));
    }

    @Override
    public Materia findByCode(String codigoMateria) {
        for (Materia materia : this.materiaDb) {
            if (materia.getCodigoMateria().equals(codigoMateria)) {
                return materia;
            }
        }
        return null;
    }

    @Override
    public List<Materia> findAll() {
        return this.materiaDb;
    }

    @Override
    public void save(Materia materia) {
        // Verificar si ya existe
        for (Materia m : this.materiaDb) {
            if (m.getCodigoMateria().equals(materia.getCodigoMateria())) {
                return; // Ya existe, no guardar
            }
        }
        // Si no existe, agregarla
        this.materiaDb.add(materia);
    }

    @Override
    public void update(Materia materia) {
        for (int i = 0; i < this.materiaDb.size(); i++) {
            if (this.materiaDb.get(i).getCodigoMateria().equals(materia.getCodigoMateria())) {
                this.materiaDb.set(i, materia);
                return;
            }
        }
    }

    @Override
    public void delete(Materia materia) {
        for (Materia m : this.materiaDb) {
            if (m.getCodigoMateria().equals(materia.getCodigoMateria())) {
                m.setActivo(false);
                return;
            }
        }
    }
}