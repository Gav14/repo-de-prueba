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
        cargarPlanGastronomia();
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
        // CONFIGURAR CORRELATIVAS DE ANALISTA
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

        // Agregar todas las materias de analista
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

    private void cargarPlanGastronomia() {
        // ============================================================
        // CREAR MATERIAS DE GASTRONOMÍA
        // ============================================================

        // 1° Cuatrimestre
        Materia gas101 = new Materia("GAS-101", "Introducción a la Gastronomía", "1");
        Materia coc101 = new Materia("COC-101", "Técnicas Culinarias Básicas", "1");
        Materia hig101 = new Materia("HIG-101", "Higiene y Manipulación de Alimentos", "1");
        Materia nut101 = new Materia("NUT-101", "Nutrición Básica", "1");

        // 2° Cuatrimestre
        Materia pan201 = new Materia("PAN-201", "Panadería y Pastelería", "2");
        Materia coc201 = new Materia("COC-201", "Cocina Regional", "2");
        Materia beb201 = new Materia("BEB-201", "Bebidas y Coctelería", "2");
        Materia gas201 = new Materia("GAS-201", "Gastronomía Argentina", "2");

        // 3° Cuatrimestre
        Materia coc301 = new Materia("COC-301", "Cocina Internacional", "3");
        Materia par301 = new Materia("PAR-301", "Parilla y Asados", "3");
        Materia pos301 = new Materia("POS-301", "Postres y Repostería", "3");
        Materia gas301 = new Materia("GAS-301", "Gestión Gastronómica", "3");
        Materia eco301 = new Materia("ECO-301", "Economía Aplicada", "3");

        // 4° Cuatrimestre
        Materia eve401 = new Materia("EVE-401", "Eventos y Catering", "4");
        Materia coc401 = new Materia("COC-401", "Alta Cocina", "4");
        Materia eno401 = new Materia("ENO-401", "Enología", "4");
        Materia gas401 = new Materia("GAS-401", "Marketing Gastronómico", "4");

        // 5° Cuatrimestre
        Materia emp501 = new Materia("EMP-501", "Emprendimientos Gastronómicos", "5");
        Materia coc501 = new Materia("COC-501", "Cocina Molecular", "5");
        Materia gas501 = new Materia("GAS-501", "Legislación Alimentaria", "5");
        Materia neg501 = new Materia("NEG-501", "Gestión de Restaurantes", "5");

        // 6° Cuatrimestre
        Materia gas601 = new Materia("GAS-601", "Proyecto Final Gastronómico", "6");
        Materia coc601 = new Materia("COC-601", "Cocina de Autor", "6");
        Materia gas602 = new Materia("GAS-602", "Sommelier", "6");
        Materia gas603 = new Materia("GAS-603", "Gestión de Calidad", "6");

        // ============================================================
        // CONFIGURAR CORRELATIVAS DE GASTRONOMÍA
        // ============================================================

        // Buscar materias comunes que ya existen
        Materia mat1 = findByCode("MAT-101");
        Materia ing1 = findByCode("ING-101");
        Materia ing2 = findByCode("ING-201");
        Materia ecy = findByCode("ECY-201");
        Materia iec = findByCode("IEC-301");
        Materia rrhh = findByCode("RRH-501");
        Materia mkt = findByCode("MKT-601");
        Materia eyd = findByCode("EYD-601");

        // 2° Cuatrimestre - correlativas de 1°
        coc201.getCorrelativas().add(coc101);
        pan201.getCorrelativas().add(gas101);
        beb201.getCorrelativas().add(gas101);
        gas201.getCorrelativas().add(gas101);

        // 3° Cuatrimestre - correlativas de 2°
        coc301.getCorrelativas().add(coc201);
        par301.getCorrelativas().add(coc201);
        pos301.getCorrelativas().add(pan201);
        gas301.getCorrelativas().add(gas201);
        if (iec != null) eco301.getCorrelativas().add(iec);

        // 4° Cuatrimestre - correlativas de 3°
        eve401.getCorrelativas().add(coc301);
        coc401.getCorrelativas().add(coc301);
        if (ing2 != null) eno401.getCorrelativas().add(ing2);
        if (mkt != null) gas401.getCorrelativas().add(mkt);

        // 5° Cuatrimestre - correlativas de 4°
        emp501.getCorrelativas().add(gas401);
        coc501.getCorrelativas().add(coc401);
        gas501.getCorrelativas().add(hig101);
        if (rrhh != null) neg501.getCorrelativas().add(rrhh);

        // 6° Cuatrimestre - correlativas de 5°
        gas601.getCorrelativas().addAll(List.of(emp501, coc501, neg501));
        coc601.getCorrelativas().add(coc501);
        gas602.getCorrelativas().add(eno401);
        if (eyd != null) gas603.getCorrelativas().add(eyd);

        // Agregar todas las materias de gastronomía
        materiaDb.addAll(List.of(
                // 1° Cuatrimestre
                gas101, coc101, hig101, nut101,
                // 2° Cuatrimestre
                pan201, coc201, beb201, gas201,
                // 3° Cuatrimestre
                coc301, par301, pos301, gas301, eco301,
                // 4° Cuatrimestre
                eve401, coc401, eno401, gas401,
                // 5° Cuatrimestre
                emp501, coc501, gas501, neg501,
                // 6° Cuatrimestre
                gas601, coc601, gas602, gas603
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
        return new ArrayList<>(this.materiaDb);
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