package ar.com.itecn1.view;

import ar.com.itecn1.controller.CarreraController;
import ar.com.itecn1.controller.PlanEstudioController;
import ar.com.itecn1.model.Carrera;
import ar.com.itecn1.model.PlanEstudio;
import ar.com.itecn1.model.Turno;

import java.util.List;
import java.util.Scanner;

public class CarreraView {

    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String RED = "\u001B[31m";
    private static final String CYAN = "\u001B[36m";
    private static final String BOLD = "\u001B[1m";

    private final CarreraController carreraController;
    private final PlanEstudioController planEstudioController;
    private final Scanner scanner;

    public CarreraView(CarreraController carreraController, PlanEstudioController planEstudioController, Scanner scanner) {
        this.carreraController = carreraController;
        this.planEstudioController = planEstudioController;
        this.scanner = scanner;
    }

    public void iniciar() {
        boolean continuar = true;

        while (continuar) {
            mostrarMenu();

            if (!scanner.hasNextInt()) {
                System.out.println(RED + "Debe ingresar un número." + RESET);
                scanner.next();
                continue;
            }

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> listarCarreras();
                case 2 -> buscarCarrera();
                case 3 -> registrarCarrera();
                case 4 -> modificarCarrera();
                case 5 -> eliminarCarrera();
                case 6 -> reactivarCarrera();
                case 0 -> continuar = false;
                default -> System.out.println(RED + "Opción no válida" + RESET);
            }
        }
    }

    private void mostrarMenu() {
        System.out.println("\n" + BLUE + BOLD + "CARRERAS" + RESET);
        System.out.println("--------");
        System.out.println(CYAN + "1. Listar todas las carreras" + RESET);
        System.out.println(CYAN + "2. Buscar carrera" + RESET);
        System.out.println(CYAN + "3. Registrar nueva carrera" + RESET);
        System.out.println(CYAN + "4. Modificar carrera" + RESET);
        System.out.println(CYAN + "5. Eliminar carrera (dar de baja)" + RESET);
        System.out.println(CYAN + "6. Reactivar carrera" + RESET);
        System.out.println(YELLOW + "0. Volver" + RESET);
        System.out.print("\nSeleccione: ");
    }

    private boolean confirmarAccion(String mensaje) {
        System.out.println("\n" + mensaje);
        System.out.println(YELLOW + "1. Sí" + RESET);
        System.out.println(YELLOW + "2. No" + RESET);
        System.out.print("Opción: ");

        int opcion;
        do {
            while (!scanner.hasNextInt()) {
                System.out.println(RED + "Debe ingresar un número." + RESET);
                scanner.next();
            }
            opcion = scanner.nextInt();
            scanner.nextLine();
        } while (opcion < 1 || opcion > 2);

        return opcion == 1;
    }

    private void mostrarCarreraSimple(Carrera carrera) {
        String planInfo = (carrera.getPlanEstudio() != null) ? carrera.getPlanEstudio().getNombre() : "NO POSEE";
        String estado = carrera.isActivo() ? GREEN + "ACTIVA" + RESET : RED + "INACTIVA" + RESET;

        System.out.println("  • " + CYAN + carrera.getNombre() + RESET);
        System.out.println("    Turno: " + carrera.getTurno());
        System.out.println("    Plan: " + planInfo);
        System.out.println("    Estado: " + estado);
        System.out.println();
    }

    private void mostrarCarreraTabla(Carrera carrera) {
        String planInfo = (carrera.getPlanEstudio() != null) ? carrera.getPlanEstudio().getNombre() : "NO POSEE";
        String estado = carrera.isActivo() ? "ACTIVA" : "INACTIVA";

        System.out.printf("│ %-35s │ %-8s │ %-15s │ %-8s │%n",
                carrera.getNombre(),
                carrera.getTurno(),
                planInfo.length() > 15 ? planInfo.substring(0, 12) + "..." : planInfo,
                estado
        );
    }

    private void mostrarPlan(PlanEstudio planEstudio) {
        String materiasInfo = (planEstudio.getMaterias() != null && !planEstudio.getMaterias().isEmpty()) ?
                planEstudio.getMaterias().stream().map(m -> m.getNombre()).reduce((a, b) -> a + ", " + b).orElse("NO POSEE") :
                "NO POSEE";

        System.out.println("  Nombre: " + planEstudio.getNombre());
        System.out.println("  Título: " + planEstudio.getTitulo());
        System.out.println("  Materias: " + materiasInfo);
    }

    private void listarCarreras() {
        System.out.println("\n" + BLUE + BOLD + "LISTADO DE CARRERAS" + RESET);
        System.out.println("====================");

        List<Carrera> carreras = carreraController.findAll();

        if (carreras.isEmpty()) {
            System.out.println("No hay carreras registradas.");
            pausa();
            return;
        }

        System.out.println("┌─────────────────────────────────────┬──────────┬─────────────────┬──────────┐");
        System.out.println("│ CARRERA                              │ TURNO    │ PLAN            │ ESTADO   │");
        System.out.println("├─────────────────────────────────────┼──────────┼─────────────────┼──────────┤");

        for (Carrera c : carreras) {
            String planInfo = (c.getPlanEstudio() != null) ? c.getPlanEstudio().getNombre() : "NO POSEE";
            String estado = c.isActivo() ? "ACTIVA" : "INACTIVA";
            System.out.printf("│ %-35s │ %-8s │ %-15s │ %-8s │%n",
                    c.getNombre().length() > 35 ? c.getNombre().substring(0, 32) + "..." : c.getNombre(),
                    c.getTurno(),
                    planInfo.length() > 15 ? planInfo.substring(0, 12) + "..." : planInfo,
                    estado
            );
        }
        System.out.println("└─────────────────────────────────────┴──────────┴─────────────────┴──────────┘");
        pausa();
    }

    private void buscarCarrera() {
        System.out.println("\n" + BLUE + BOLD + "BUSCAR CARRERA" + RESET);
        System.out.println("===============");
        System.out.print("Ingrese el nombre o parte del nombre: ");
        String texto = scanner.nextLine();

        try {
            List<Carrera> resultados = carreraController.buscarCarreras(texto);

            if (resultados.isEmpty()) {
                System.out.println(RED + "No se encontraron carreras con: \"" + texto + "\"" + RESET);
            } else {
                System.out.println(GREEN + "Se encontraron " + resultados.size() + " carrera(s):" + RESET);
                System.out.println();
                for (Carrera c : resultados) {
                    mostrarCarreraSimple(c);
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println(RED + "Error: " + e.getMessage() + RESET);
        }
        pausa();
    }

    private void registrarCarrera() {
        System.out.println("\n" + BLUE + BOLD + "REGISTRAR CARRERA" + RESET);
        System.out.println("==================");

        System.out.print("Nombre de la carrera: ");
        String nombre = scanner.nextLine();

        if (nombre.trim().isEmpty()) {
            System.out.println(RED + "El nombre no puede estar vacío." + RESET);
            pausa();
            return;
        }

        try {
            Carrera existente = carreraController.findByName(nombre);

            if (existente != null) {
                if (existente.isActivo()) {
                    System.out.println(RED + "Ya existe una carrera ACTIVA con ese nombre." + RESET);
                } else {
                    System.out.println(YELLOW + "Ya existe una carrera INACTIVA con ese nombre." + RESET);
                    if (confirmarAccion("¿Desea reactivarla?")) {
                        carreraController.reactivarCarrera(nombre);
                        System.out.println(GREEN + "✓ Carrera reactivada!" + RESET);
                    }
                }
                pausa();
                return;
            }

            Turno turno = seleccionarTurno();
            Carrera carrera = new Carrera(nombre, turno);

            System.out.println("\n¿Desea asignar un plan de estudio?");
            if (confirmarAccion("")) {
                asignarPlanEstudio(carrera);
            }

            System.out.println("\n" + CYAN + "Vista previa:" + RESET);
            mostrarCarreraSimple(carrera);

            if (confirmarAccion("¿Confirmar registro?")) {
                carreraController.createCarrera(carrera);
                System.out.println(GREEN + "✓ Carrera registrada!" + RESET);
            }
        } catch (IllegalArgumentException e) {
            System.out.println(RED + "Error: " + e.getMessage() + RESET);
        }
        pausa();
    }

    private void modificarCarrera() {
        System.out.println("\n" + BLUE + BOLD + "MODIFICAR CARRERA" + RESET);
        System.out.println("==================");

        System.out.print("Ingrese el nombre o parte del nombre: ");
        String texto = scanner.nextLine();

        try {
            List<Carrera> resultados = carreraController.buscarCarreras(texto);
            List<Carrera> activas = resultados.stream().filter(Carrera::isActivo).toList();

            if (activas.isEmpty()) {
                System.out.println(RED + "No se encontraron carreras ACTIVAS." + RESET);
                pausa();
                return;
            }

            Carrera carrera;
            if (activas.size() == 1) {
                carrera = activas.get(0);
                System.out.println("\n" + CYAN + "Carrera encontrada:" + RESET);
                mostrarCarreraSimple(carrera);
            } else {
                carrera = seleccionarCarreraDeLista(activas, "modificar");
                if (carrera == null) return;
            }

            Turno turnoOriginal = carrera.getTurno();
            PlanEstudio planOriginal = carrera.getPlanEstudio();

            System.out.println("\nTurno actual: " + carrera.getTurno());
            if (confirmarAccion("¿Cambiar turno?")) {
                Turno nuevoTurno = seleccionarTurno();
                carrera.setTurno(nuevoTurno);
            }

            System.out.println("\nPlan actual: " + (carrera.getPlanEstudio() != null ?
                    carrera.getPlanEstudio().getNombre() : "NO POSEE"));
            if (confirmarAccion("¿Modificar plan de estudio?")) {
                if (carrera.getPlanEstudio() == null) {
                    asignarPlanEstudio(carrera);
                } else {
                    if (confirmarAccion("¿Quitar plan actual?")) {
                        carrera.setPlanEstudio(null);
                    } else {
                        asignarPlanEstudio(carrera);
                    }
                }
            }

            System.out.println("\n" + CYAN + "Vista previa:" + RESET);
            mostrarCarreraSimple(carrera);

            if (confirmarAccion("¿Confirmar cambios?")) {
                carreraController.updateCarrera(carrera);
                System.out.println(GREEN + "✓ Carrera modificada!" + RESET);
            } else {
                carrera.setTurno(turnoOriginal);
                carrera.setPlanEstudio(planOriginal);
                System.out.println("Modificación cancelada.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(RED + "Error: " + e.getMessage() + RESET);
        }
        pausa();
    }

    private void eliminarCarrera() {
        System.out.println("\n" + BLUE + BOLD + "ELIMINAR CARRERA" + RESET);
        System.out.println("=================");

        System.out.print("Ingrese el nombre o parte del nombre: ");
        String texto = scanner.nextLine();

        try {
            List<Carrera> resultados = carreraController.buscarCarreras(texto);
            List<Carrera> activas = resultados.stream().filter(Carrera::isActivo).toList();

            if (activas.isEmpty()) {
                System.out.println(RED + "No se encontraron carreras ACTIVAS para eliminar." + RESET);
                pausa();
                return;
            }

            Carrera carrera;
            if (activas.size() == 1) {
                carrera = activas.get(0);
            } else {
                carrera = seleccionarCarreraDeLista(activas, "eliminar");
                if (carrera == null) return;
            }

            System.out.println("\n" + YELLOW + "Carrera a eliminar:" + RESET);
            mostrarCarreraSimple(carrera);

            if (confirmarAccion(RED + "¿Está seguro de eliminar esta carrera?" + RESET)) {
                carreraController.deleteCarrera(carrera);
                System.out.println(GREEN + "✓ Carrera eliminada (estado: INACTIVA)" + RESET);
            }
        } catch (IllegalArgumentException e) {
            System.out.println(RED + "Error: " + e.getMessage() + RESET);
        }
        pausa();
    }

    private void reactivarCarrera() {
        System.out.println("\n" + BLUE + BOLD + "REACTIVAR CARRERA" + RESET);
        System.out.println("==================");

        System.out.print("Ingrese el nombre o parte del nombre: ");
        String texto = scanner.nextLine();

        try {
            List<Carrera> resultados = carreraController.buscarCarreras(texto);
            List<Carrera> inactivas = resultados.stream().filter(c -> !c.isActivo()).toList();

            if (inactivas.isEmpty()) {
                System.out.println(RED + "No se encontraron carreras INACTIVAS." + RESET);
                pausa();
                return;
            }

            Carrera carrera;
            if (inactivas.size() == 1) {
                carrera = inactivas.get(0);
            } else {
                carrera = seleccionarCarreraDeLista(inactivas, "reactivar");
                if (carrera == null) return;
            }

            System.out.println("\n" + CYAN + "Carrera a reactivar:" + RESET);
            mostrarCarreraSimple(carrera);

            if (confirmarAccion("¿Confirmar reactivación?")) {
                carreraController.reactivarCarrera(carrera.getNombre());
                System.out.println(GREEN + "✓ Carrera reactivada!" + RESET);
            }
        } catch (IllegalArgumentException e) {
            System.out.println(RED + "Error: " + e.getMessage() + RESET);
        }
        pausa();
    }

    private Carrera seleccionarCarreraDeLista(List<Carrera> carreras, String accion) {
        System.out.println("\n" + CYAN + "Carreras encontradas:" + RESET);
        for (int i = 0; i < carreras.size(); i++) {
            Carrera c = carreras.get(i);
            System.out.println((i + 1) + ". " + c.getNombre() + " (" + c.getTurno() + ")");
        }

        System.out.print("\nSeleccione número (0 para cancelar): ");
        int seleccion;
        do {
            while (!scanner.hasNextInt()) {
                System.out.println(RED + "Debe ingresar un número." + RESET);
                scanner.next();
            }
            seleccion = scanner.nextInt();
            scanner.nextLine();
        } while (seleccion < 0 || seleccion > carreras.size());

        if (seleccion == 0) {
            System.out.println("Operación cancelada.");
            return null;
        }
        return carreras.get(seleccion - 1);
    }

    private Turno seleccionarTurno() {
        System.out.println("\nTurnos disponibles:");
        System.out.println("1. MAÑANA");
        System.out.println("2. TARDE");
        System.out.println("3. NOCHE");
        System.out.print("Seleccione (1-3): ");

        int opcion;
        do {
            while (!scanner.hasNextInt()) {
                System.out.println(RED + "Debe ingresar un número." + RESET);
                scanner.next();
            }
            opcion = scanner.nextInt();
            scanner.nextLine();
        } while (opcion < 1 || opcion > 3);

        return switch (opcion) {
            case 1 -> Turno.MANANA;
            case 2 -> Turno.TARDE;
            default -> Turno.NOCHE;
        };
    }

    private void asignarPlanEstudio(Carrera carrera) {
        try {
            List<PlanEstudio> planes = planEstudioController.findAll();
            List<PlanEstudio> activos = planes.stream().filter(PlanEstudio::isActivo).toList();

            if (activos.isEmpty()) {
                System.out.println(YELLOW + "No hay planes de estudio activos." + RESET);
                return;
            }

            System.out.println("\nPlanes disponibles:");
            for (int i = 0; i < activos.size(); i++) {
                System.out.println((i + 1) + ". " + activos.get(i).getNombre());
            }

            System.out.print("Seleccione (0 para cancelar): ");
            int seleccion;
            do {
                while (!scanner.hasNextInt()) {
                    System.out.println(RED + "Debe ingresar un número." + RESET);
                    scanner.next();
                }
                seleccion = scanner.nextInt();
                scanner.nextLine();
            } while (seleccion < 0 || seleccion > activos.size());

            if (seleccion > 0) {
                carrera.setPlanEstudio(activos.get(seleccion - 1));
                System.out.println(GREEN + "✓ Plan asignado." + RESET);
            }
        } catch (Exception e) {
            System.out.println(RED + "Error al asignar plan." + RESET);
        }
    }

    private void pausa() {
        System.out.print("\n" + CYAN + "Presione ENTER para continuar..." + RESET);
        scanner.nextLine();
    }
}