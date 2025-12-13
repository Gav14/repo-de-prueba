/*
package ar.com.itecn1.view;

import ar.com.itecn1.controller.AlumnoController;
import ar.com.itecn1.controller.AlumnoInscriptoCarreraController;
import ar.com.itecn1.controller.CarreraController;
import ar.com.itecn1.controller.PlanEstudioController;
import ar.com.itecn1.model.Alumno;
import ar.com.itecn1.model.AlumnoInscriptoCarrera;
import ar.com.itecn1.model.Carrera;
import ar.com.itecn1.model.PlanEstudio;

import java.util.List;
import java.util.Scanner;

public class AlumnoInscriptoCarreraView {

    private final AlumnoInscriptoCarreraController inscripcionController;
    private final AlumnoController alumnoController;
    private final CarreraController carreraController;
    private final PlanEstudioController planController;
    private final Scanner scanner;

    public AlumnoInscriptoCarreraView(
            AlumnoInscriptoCarreraController inscripcionController,
            AlumnoController alumnoController,
            CarreraController carreraController,
            PlanEstudioController planController,
            Scanner scanner) {
        this.inscripcionController = inscripcionController;
        this.alumnoController = alumnoController;
        this.carreraController = carreraController;
        this.planController = planController;
        this.scanner = scanner;
    }

    public void iniciar() {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\nGESTIÓN DE INSCRIPCIONES A CARRERAS");
            System.out.println("1. Listar inscripciones");
            System.out.println("2. Inscribir alumno a carrera");
            System.out.println("3. Actualizar inscripción");
            System.out.println("4. Eliminar inscripción");
            System.out.println("5. Volver");

            int opcion = leerEntero();
            switch (opcion) {
                case 1 -> listarInscripciones();
                case 2 -> crearInscripcion();
                case 3 -> actualizarInscripcion();
                case 4 -> eliminarInscripcion();
                case 5 -> continuar = false;
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    // ============================================================
    // Crear inscripción
    // ============================================================

    private void crearInscripcion() {
        System.out.println("---------- Nueva Inscripción ----------");

        scanner.nextLine();
        System.out.println("Ingrese el año de ingreso (ej. 2024):");
        String anio = scanner.nextLine();

        Alumno alumno = seleccionarAlumno();
        if (alumno == null) return;

        Carrera carrera = seleccionarCarrera();
        if (carrera == null) return;

        PlanEstudio plan = seleccionarPlan();
        if (plan == null) return;

        AlumnoInscriptoCarrera inscripcion =
                new AlumnoInscriptoCarrera(anio, carrera, alumno, plan);

        mostrarResumenInscripcion(inscripcion);

        System.out.println("\n¿Confirmar inscripción? (1.Sí / 2.No)");
        if (leerEntero() == 1) {
            inscripcionController.save(inscripcion);
            System.out.println("Inscripción registrada con éxito.");
        }
    }

    // ============================================================
    // Listar
    // ============================================================

    private void listarInscripciones() {
        System.out.println("---------- Listado de Inscripciones ----------");
        List<AlumnoInscriptoCarrera> lista = inscripcionController.findAll();

        if (lista.isEmpty()) {
            System.out.println("No hay inscripciones registradas.");
            return;
        }

        for (int i = 0; i < lista.size(); i++) {
            AlumnoInscriptoCarrera a = lista.get(i);
            System.out.println(
                    "ALUMNO: " + a.getAlumno().getApellido() + " " + a.getAlumno().getNombre() +
                    " | DNI: " + a.getAlumno().getDni() +
                    " | Carrera: " + a.getCarrera().getNombre() +
                    " | Año ingreso: " + a.getAnioIngreso() +
                    " | Estado: " + (a.isActivo() ? "Activo" : "Inactivo")
            );
        }
    }

    // ============================================================
    // Actualizar inscripción
    // ============================================================

    private void actualizarInscripcion() {
        System.out.println("---------- Actualizar Inscripción ----------");

        AlumnoInscriptoCarrera ins = seleccionarInscripcion();
        if (ins == null) return;

        scanner.nextLine();

        System.out.println("Año de ingreso actual: " + ins.getAnioIngreso());
        System.out.println("Nuevo año de ingreso (o Enter para dejar igual):");
        String nuevoAnio = scanner.nextLine();
        if (!nuevoAnio.isBlank()) ins.setAnioIngreso(nuevoAnio);

        System.out.println("¿Desea cambiar el alumno? (1.Sí / 2.No)");
        if (leerEntero() == 1) {
            Alumno nuevoAlumno = seleccionarAlumno();
            if (nuevoAlumno != null) ins.setAlumno(nuevoAlumno);
        }

        System.out.println("¿Desea cambiar la carrera? (1.Sí / 2.No)");
        if (leerEntero() == 1) {
            Carrera nuevaCarrera = seleccionarCarrera();
            if (nuevaCarrera != null) ins.setCarrera(nuevaCarrera);
        }

        System.out.println("¿Desea cambiar el plan de estudio? (1.Sí / 2.No)");
        if (leerEntero() == 1) {
            PlanEstudio nuevoPlan = seleccionarPlan();
            if (nuevoPlan != null) ins.setPlanEstudio(nuevoPlan);
        }

        mostrarResumenInscripcion(ins);

        System.out.println("\n¿Guardar cambios? (1.Sí / 2.No)");
        if (leerEntero() == 1) {
            inscripcionController.update(ins);
            System.out.println("Inscripción actualizada.");
        }
    }

    // ============================================================
    // Eliminar
    // ============================================================

    private void eliminarInscripcion() {
        System.out.println("---------- Eliminar Inscripción ----------");

        AlumnoInscriptoCarrera ins = seleccionarInscripcion();
        if (ins == null) return;

        mostrarResumenInscripcion(ins);

        System.out.println("\n¿Confirmar baja? (1.Sí / 2.No)");
        if (leerEntero() == 1) {
            inscripcionController.delete(ins);
            System.out.println("Inscripción eliminada.");
        }
    }

    // ============================================================
    // Métodos auxiliares
    // ============================================================

    private Alumno seleccionarAlumno() {
        List<Alumno> lista = alumnoController.findAll();
        if (lista.isEmpty()) {
            System.out.println("No hay alumnos registrados.");
            return null;
        }

        System.out.println("\n--- Seleccione un Alumno ---");

        for (int i = 0; i < lista.size(); i++) {
            Alumno a = lista.get(i);
            System.out.println((i + 1) + ". DNI: " + a.getDni() + " | ALUMNO: " + a.getApellido() + " " + a.getNombre());
        }

        int op = seleccionarDeLista(lista.size());
        return lista.get(op - 1);
    }

    private Carrera seleccionarCarrera() {
        List<Carrera> lista = carreraController.findAll();
        if (lista.isEmpty()) {
            System.out.println("No hay carreras registradas.");
            return null;
        }

        System.out.println("\n--- Seleccione una Carrera ---");

        for (int i = 0; i < lista.size(); i++) {
            Carrera c = lista.get(i);
            System.out.println((i + 1) + ". " + c.getNombre() + " | Turno: " + c.getTurno());
        }

        int op = seleccionarDeLista(lista.size());
        return lista.get(op - 1);
    }

    private PlanEstudio seleccionarPlan() {
        List<PlanEstudio> lista = planController.findAll();
        if (lista.isEmpty()) {
            System.out.println("No hay planes registrados.");
            return null;
        }

        System.out.println("\n--- Seleccione un Plan de Estudio ---");

        for (int i = 0; i < lista.size(); i++) {
            PlanEstudio p = lista.get(i);
            System.out.println((i + 1) + ". " + p.getNombre() + " | " + p.getTitulo());
        }

        int op = seleccionarDeLista(lista.size());
        return lista.get(op - 1);
    }

    private AlumnoInscriptoCarrera seleccionarInscripcion() {
        List<AlumnoInscriptoCarrera> lista = inscripcionController.findAll();
        if (lista.isEmpty()) {
            System.out.println("No hay inscripciones registradas.");
            return null;
        }

        System.out.println("\n--- Seleccione una Inscripción ---");

        for (int i = 0; i < lista.size(); i++) {
            AlumnoInscriptoCarrera a = lista.get(i);
            System.out.println((i + 1) + ". " +
                    a.getAlumno().getApellido() + " " + a.getAlumno().getNombre() +
                    " | " + a.getCarrera().getNombre() +
                    " | " + a.getPlanEstudio().getNombre());
        }

        int op = seleccionarDeLista(lista.size());
        return lista.get(op - 1);
    }

    private int seleccionarDeLista(int max) {
        int op;
        do {
            System.out.print("Seleccione opción: ");
            op = leerEntero();
        } while (op < 1 || op > max);
        return op;
    }

    private void mostrarResumenInscripcion(AlumnoInscriptoCarrera i) {
        System.out.println("\n--- Resumen ---");
        System.out.println("Alumno: " + i.getAlumno().getApellido() + " " + i.getAlumno().getNombre());
        System.out.println("DNI: " + i.getAlumno().getDni());
        System.out.println("Carrera: " + i.getCarrera().getNombre());
        System.out.println("Plan: " + i.getPlanEstudio().getNombre());
        System.out.println("Año ingreso: " + i.getAnioIngreso());
        System.out.println("Estado: " + (i.isActivo() ? "Activo" : "Inactivo"));
    }

    private int leerEntero() {
        while (!scanner.hasNextInt()) {
            scanner.next();
            System.out.println("Debe ingresar un número válido:");
        }
        int n = scanner.nextInt();
        return n;
    }
}
*/
package ar.com.itecn1.view;

import ar.com.itecn1.controller.AlumnoController;
import ar.com.itecn1.controller.AlumnoInscriptoCarreraController;
import ar.com.itecn1.controller.CarreraController;
import ar.com.itecn1.controller.PlanEstudioController;
import ar.com.itecn1.model.Alumno;
import ar.com.itecn1.model.AlumnoInscriptoCarrera;
import ar.com.itecn1.model.Carrera;
import ar.com.itecn1.model.PlanEstudio;

import java.util.List;
import java.util.Scanner;

public class AlumnoInscriptoCarreraView {

    private final AlumnoInscriptoCarreraController inscripcionController;
    private final AlumnoController alumnoController;
    private final CarreraController carreraController;
    private final PlanEstudioController planController;
    private final Scanner scanner;

    public AlumnoInscriptoCarreraView(
            AlumnoInscriptoCarreraController inscripcionController,
            AlumnoController alumnoController,
            CarreraController carreraController,
            PlanEstudioController planController,
            Scanner scanner) {
        this.inscripcionController = inscripcionController;
        this.alumnoController = alumnoController;
        this.carreraController = carreraController;
        this.planController = planController;
        this.scanner = scanner;
    }

    public void iniciar() {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\nGESTIÓN DE INSCRIPCIONES A CARRERAS");
            System.out.println("1. Inscribir alumno a carrera");
            System.out.println("2. Listar inscripciones");
            System.out.println("3. Actualizar inscripción");
            System.out.println("4. Eliminar inscripción");
            System.out.println("5. Volver");

            int opcion = leerEntero();
            switch (opcion) {
                case 1 -> crearInscripcion();
                case 2 -> listarInscripciones();
                case 3 -> actualizarInscripcion();
                case 4 -> eliminarInscripcion();
                case 5 -> continuar = false;
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    // ============================================================
    // Crear inscripción
    // ============================================================

    private void crearInscripcion() {
        System.out.println("---------- Nueva Inscripción ----------");

        scanner.nextLine();
        System.out.println("Ingrese el año de ingreso (ej. 2024):");
        String anio = scanner.nextLine();

        Alumno alumno = seleccionarAlumno();
        if (alumno == null) return;

        Carrera carrera = seleccionarCarrera();
        if (carrera == null) return;

        List<AlumnoInscriptoCarrera> inscripciones = inscripcionController.findAll();
        boolean yaInscripto = inscripciones.stream()
                .anyMatch(i -> i.getAlumno().getDni().equals(alumno.getDni())
                        && i.getCarrera().getNombre().equalsIgnoreCase(carrera.getNombre())
                        && i.isActivo());

        if (yaInscripto) {
            System.out.println("El alumno ya se encuentra inscripto a esta carrera.");
            return;
        }

        // PLAN DE ESTUDIO SE OBTIENE DIRECTO DESDE LA CARRERA
        PlanEstudio plan = null;
        if(carrera.getPlanEstudio() != null){
            plan = carrera.getPlanEstudio();
        }

        AlumnoInscriptoCarrera inscripcion =
                new AlumnoInscriptoCarrera(anio, carrera, alumno, plan);

        mostrarResumenInscripcion(inscripcion);

        System.out.println("¿Confirmar inscripción? (1.Sí / 2.No)");
        if (leerEntero() == 1) {
            inscripcionController.save(inscripcion);
            System.out.println("Inscripción registrada con éxito.");
        }
    }

    // ============================================================
    // Listar
    // ============================================================

    private void listarInscripciones() {
        System.out.println("---------- Listado de Inscripciones ----------");
        List<AlumnoInscriptoCarrera> lista = inscripcionController.findAll();

        if (lista.isEmpty()) {
            System.out.println("No hay inscripciones registradas.");
            return;
        }

        for (int i = 0; i < lista.size(); i++) {
            AlumnoInscriptoCarrera a = lista.get(i);
            System.out.println(
                    (i + 1) + ". " +
                            a.getAlumno().getApellido() + " " + a.getAlumno().getNombre() +
                            " | DNI: " + a.getAlumno().getDni() +
                            " | Carrera: " + a.getCarrera().getNombre() +
                            " | Año ingreso: " + a.getAnioIngreso() +
                            " | Estado: " + (a.isActivo() ? "Activo" : "Inactivo")
            );
        }
    }

    // ============================================================
    // Actualizar inscripción
    // ============================================================

    private void actualizarInscripcion() {
        System.out.println("---------- Actualizar Inscripción ----------");

        AlumnoInscriptoCarrera ins = seleccionarInscripcion();
        if (ins == null) return;

        scanner.nextLine();

        System.out.println("Año de ingreso actual: " + ins.getAnioIngreso());
        System.out.println("Nuevo año de ingreso (Enter para dejar igual):");
        String nuevoAnio = scanner.nextLine();
        if (!nuevoAnio.isBlank()) ins.setAnioIngreso(nuevoAnio);

        // EL ALUMNO NO PUEDE CAMBIARSE
        System.out.println("\nAlumno actual: " +
                ins.getAlumno().getApellido() + " " + ins.getAlumno().getNombre() +
                " (No puede modificarse)");

        // CAMBIO DE CARRERA SÍ ESTÁ PERMITIDO
        System.out.println("\n¿Desea cambiar la carrera? (1.Sí / 2.No)");
        if (leerEntero() == 1) {
            Carrera nuevaCarrera = seleccionarCarrera();
            if (nuevaCarrera != null) {
                ins.setCarrera(nuevaCarrera);

                // PLAN DE ESTUDIO SE ACTUALIZA AUTOMÁTICAMENTE
                ins.setPlanEstudio(nuevaCarrera.getPlanEstudio());
            }
        }

        mostrarResumenInscripcion(ins);

        System.out.println("¿Guardar cambios? (1.Sí / 2.No)");
        if (leerEntero() == 1) {
            inscripcionController.update(ins);
            System.out.println("Inscripción actualizada.");
        }
    }

    // ============================================================
    // Eliminar
    // ============================================================

    private void eliminarInscripcion() {
        System.out.println("---------- Eliminar Inscripción ----------");

        AlumnoInscriptoCarrera ins = seleccionarInscripcion();
        if (ins == null) return;

        mostrarResumenInscripcion(ins);

        System.out.println("¿Confirmar baja? (1.Sí / 2.No)");
        if (leerEntero() == 1) {
            inscripcionController.delete(ins);
            System.out.println("Inscripción eliminada.");
        }
    }

    // ============================================================
    // Métodos auxiliares
    // ============================================================

    private Alumno seleccionarAlumno() {
        List<Alumno> lista = alumnoController.findAll();
        if (lista.isEmpty()) {
            System.out.println("No hay alumnos registrados.");
            return null;
        }

        System.out.println("\n--- Seleccione un Alumno ---");

        for (int i = 0; i < lista.size(); i++) {
            Alumno a = lista.get(i);
            System.out.println((i + 1) + ". " + a.getDni() + " - " + a.getApellido() + " " + a.getNombre());
        }

        int op = seleccionarDeLista(lista.size());
        return lista.get(op - 1);
    }

    private Carrera seleccionarCarrera() {
        List<Carrera> lista = carreraController.findAll();
        if (lista.isEmpty()) {
            System.out.println("No hay carreras registradas.");
            return null;
        }

        System.out.println("\n--- Seleccione una Carrera ---");

        for (int i = 0; i < lista.size(); i++) {
            Carrera c = lista.get(i);
            System.out.println((i + 1) + ". " + c.getNombre() + " | Turno: " + c.getTurno());
        }

        int op = seleccionarDeLista(lista.size());
        return lista.get(op - 1);
    }

    private AlumnoInscriptoCarrera seleccionarInscripcion() {
        List<AlumnoInscriptoCarrera> lista = inscripcionController.findAll();
        if (lista.isEmpty()) {
            System.out.println("No hay inscripciones registradas.");
            return null;
        }

        System.out.println("\n--- Seleccione una Inscripción ---");

        for (int i = 0; i < lista.size(); i++) {
            AlumnoInscriptoCarrera a = lista.get(i);
            System.out.println((i + 1) + ". " +
                    a.getAlumno().getApellido() + " " + a.getAlumno().getNombre() +
                    " | Carrera: " + a.getCarrera().getNombre() +
                    " | Año: " + a.getAnioIngreso());
        }

        int op = seleccionarDeLista(lista.size());
        return lista.get(op - 1);
    }

    private void mostrarResumenInscripcion(AlumnoInscriptoCarrera i) {
        String planInfo;

        if (i.getPlanEstudio() != null) {
            planInfo = i.getPlanEstudio().getNombre();
        } else {
            planInfo = "NO POSEE";
        }

        System.out.println("\n--- Resumen ---");
        System.out.println("Alumno: " + i.getAlumno().getApellido() + " " + i.getAlumno().getNombre());
        System.out.println("DNI: " + i.getAlumno().getDni());
        System.out.println("Carrera: " + i.getCarrera().getNombre());
        System.out.println("Plan: " + planInfo);
        System.out.println("Año ingreso: " + i.getAnioIngreso());
        System.out.println("Estado: " + (i.isActivo() ? "Activo" : "Inactivo"));
    }

    private int seleccionarDeLista(int max) {
        int op;
        do {
            System.out.print("Seleccione opción: ");
            op = leerEntero();
        } while (op < 1 || op > max);
        return op;
    }

    private int leerEntero() {
        while (!scanner.hasNextInt()) {
            scanner.next();
            System.out.println("Debe ingresar un número válido:");
        }
        int n = scanner.nextInt();
        return n;
    }
}
