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

    // Códigos de color
    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String RED = "\u001B[31m";
    private static final String CYAN = "\u001B[36m";
    private static final String BOLD = "\u001B[1m";

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
            System.out.println("\n" + BLUE + BOLD + "GESTIÓN DE INSCRIPCIONES A CARRERAS" + RESET);
            System.out.println(CYAN + "1. Inscribir alumno a carrera" + RESET);
            System.out.println(CYAN + "2. Listar inscripciones" + RESET);
            System.out.println(CYAN + "3. Actualizar inscripción" + RESET);
            System.out.println(CYAN + "4. Eliminar inscripción" + RESET);
            System.out.println(YELLOW + "0. Volver" + RESET);

            int opcion = leerEntero();
            switch (opcion) {
                case 1 -> crearInscripcion();
                case 2 -> listarInscripciones();
                case 3 -> actualizarInscripcion();
                case 4 -> eliminarInscripcion();
                case 0 -> continuar = false;
                default -> System.out.println(RED + "Opción inválida." + RESET);
            }
        }
    }

    // ============================================================
    // Crear inscripción
    // ============================================================

    private void crearInscripcion() {
        System.out.println("\n" + BLUE + BOLD + "---------- NUEVA INSCRIPCIÓN ----------" + RESET);

        scanner.nextLine();
        System.out.print("Ingrese el año de ingreso (ej. 2024): ");
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
            System.out.println(RED + "El alumno ya se encuentra inscripto a esta carrera." + RESET);
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

        System.out.println("\n" + YELLOW + "¿Confirmar inscripción? (1.Sí / 2.No)" + RESET);
        if (leerEntero() == 1) {
            inscripcionController.save(inscripcion);
            System.out.println(GREEN + "Inscripción registrada con éxito." + RESET);
        }
        pausa();
    }

    // ============================================================
    // Listar
    // ============================================================

    private void listarInscripciones() {
        System.out.println("\n" + BLUE + BOLD + "---------- LISTADO DE INSCRIPCIONES ----------" + RESET);
        List<AlumnoInscriptoCarrera> lista = inscripcionController.findAll();

        if (lista.isEmpty()) {
            System.out.println("No hay inscripciones registradas.");
            pausa();
            return;
        }

        for (int i = 0; i < lista.size(); i++) {
            AlumnoInscriptoCarrera a = lista.get(i);
            String estado = a.isActivo() ? GREEN + "Activo" + RESET : RED + "Inactivo" + RESET;
            System.out.println(
                    (i + 1) + ". " +
                            a.getAlumno().getApellido() + " " + a.getAlumno().getNombre() +
                            " | DNI: " + a.getAlumno().getDni() +
                            " | Carrera: " + a.getCarrera().getNombre() +
                            " | Año ingreso: " + a.getAnioIngreso() +
                            " | Estado: " + estado
            );
        }
        pausa();
    }

    // ============================================================
    // Actualizar inscripción
    // ============================================================

    private void actualizarInscripcion() {
        System.out.println("\n" + BLUE + BOLD + "---------- ACTUALIZAR INSCRIPCIÓN ----------" + RESET);

        AlumnoInscriptoCarrera ins = seleccionarInscripcion();
        if (ins == null) {
            pausa();
            return;
        }

        AlumnoInscriptoCarrera insAuxi = new AlumnoInscriptoCarrera();
        insAuxi.setActivo(true);
        insAuxi.setAlumno(ins.getAlumno());
        insAuxi.setCarrera(ins.getCarrera());
        insAuxi.setPlanEstudio(ins.getPlanEstudio());
        insAuxi.setAnioIngreso(ins.getAnioIngreso());

        scanner.nextLine();

        System.out.println("Año de ingreso actual: " + ins.getAnioIngreso());
        System.out.print("Nuevo año de ingreso (Enter para dejar igual): ");
        String nuevoAnio = scanner.nextLine();

        if (!nuevoAnio.isBlank()) insAuxi.setAnioIngreso(nuevoAnio);

        // EL ALUMNO NO PUEDE CAMBIARSE
        System.out.println("\nAlumno actual: " +
                ins.getAlumno().getApellido() + " " + ins.getAlumno().getNombre() +
                YELLOW + " (No puede modificarse)" + RESET);

        // CAMBIO DE CARRERA SÍ ESTÁ PERMITIDO
        System.out.println("\n" + YELLOW + "¿Desea cambiar la carrera? (1.Sí / 2.No)" + RESET);
        if (leerEntero() == 1) {
            Carrera nuevaCarrera = seleccionarCarrera();
            //validaciones para que no haya duplicados
            boolean bandera = false;
            for(AlumnoInscriptoCarrera alu : inscripcionController.findAll()){
                if(alu.getAlumno().getDni().equals(insAuxi.getAlumno().getDni())
                        && alu.getCarrera().getNombre().equalsIgnoreCase(insAuxi.getCarrera().getNombre())){
                    bandera = true;
                    break;
                }
            }
            if(bandera) {
                System.out.println(RED + "El alumno ya esta inscripto a esa carrera." + RESET);
                pausa();
                return;
            }
            if (nuevaCarrera != null) {
                insAuxi.setCarrera(nuevaCarrera);

                // PLAN DE ESTUDIO SE ACTUALIZA AUTOMÁTICAMENTE
                insAuxi.setPlanEstudio(nuevaCarrera.getPlanEstudio());
            }
        }

        mostrarResumenInscripcion(insAuxi);

        System.out.println("\n" + YELLOW + "¿Guardar cambios? (1.Sí / 2.No)" + RESET);
        if (leerEntero() == 1) {
            inscripcionController.update(insAuxi);
            System.out.println(GREEN + "Inscripción actualizada." + RESET);
        }
        pausa();
    }

    // ============================================================
    // Eliminar
    // ============================================================

    private void eliminarInscripcion() {
        System.out.println("\n" + BLUE + BOLD + "---------- ELIMINAR INSCRIPCIÓN ----------" + RESET);

        AlumnoInscriptoCarrera ins = seleccionarInscripcion();
        if (ins == null) {
            pausa();
            return;
        }

        mostrarResumenInscripcion(ins);

        System.out.println("\n" + YELLOW + "¿Confirmar baja? (1.Sí / 2.No)" + RESET);
        if (leerEntero() == 1) {
            inscripcionController.delete(ins);
            System.out.println(GREEN + "Inscripción eliminada." + RESET);
        }
        pausa();
    }

    // ============================================================
    // Métodos auxiliares
    // ============================================================

    private Alumno seleccionarAlumno() {
        List<Alumno> lista = alumnoController.findAll();
        if (lista.isEmpty()) {
            System.out.println(RED + "No hay alumnos registrados." + RESET);
            return null;
        }

        System.out.println("\n" + CYAN + "--- Seleccione un Alumno ---" + RESET);

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
            System.out.println(RED + "No hay carreras registradas." + RESET);
            return null;
        }

        System.out.println("\n" + CYAN + "--- Seleccione una Carrera ---" + RESET);

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
            System.out.println(RED + "No hay inscripciones registradas." + RESET);
            return null;
        }

        System.out.println("\n" + CYAN + "--- Seleccione una Inscripción ---" + RESET);

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

        String estado = i.isActivo() ? GREEN + "Activo" + RESET : RED + "Inactivo" + RESET;

        System.out.println("\n" + CYAN + "--- Resumen ---" + RESET);
        System.out.println("Alumno: " + i.getAlumno().getApellido() + " " + i.getAlumno().getNombre());
        System.out.println("DNI: " + i.getAlumno().getDni());
        System.out.println("Carrera: " + i.getCarrera().getNombre());
        System.out.println("Plan: " + planInfo);
        System.out.println("Año ingreso: " + i.getAnioIngreso());
        System.out.println("Estado: " + estado);
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
            System.out.print(RED + "Debe ingresar un número válido: " + RESET);
        }
        int n = scanner.nextInt();
        scanner.nextLine();
        return n;
    }

    private void pausa() {
        System.out.print("\n" + CYAN + "Presione ENTER para continuar..." + RESET);
        scanner.nextLine();
    }
}