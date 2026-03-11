package ar.com.itecn1.view;

import ar.com.itecn1.controller.CarreraController;
import ar.com.itecn1.controller.PlanEstudioController;
import ar.com.itecn1.model.Carrera;
import ar.com.itecn1.model.PlanEstudio;
import ar.com.itecn1.model.Turno;

import java.util.List;
import java.util.Scanner;

public class CarreraView {

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
                System.out.println("Debe ingresar un número.");
                scanner.next();
                continue;
            }

            int opcion = scanner.nextInt();
            pausa();

            switch (opcion) {
                case 1 -> listarCarreras();
                case 2 -> buscarCarrera();
                case 3 -> crearCarrera();
                case 4 -> actualizarCarrera();
                case 5 -> eliminarCarrera();
                case 0 -> continuar = false;
                default -> System.out.println("Ingrese una opción válida");
            }
        }
    }

    // ----------------------------------------------------
    // MÉTODOS AUXILIARES
    // ----------------------------------------------------

    private void mostrarMenu() {
        System.out.println("\nGESTIÓN DE CARRERAS");
        System.out.println("1. Listar carreras");
        System.out.println("2. Buscar carrera por nombre");
        System.out.println("3. Registrar carrera");
        System.out.println("4. Actualizar datos de una carrera");
        System.out.println("5. Dar de baja una carrera");
        System.out.println("0. Volver atrás");
        System.out.print("Seleccione una opción: ");
    }

    private void pausa() {
        scanner.nextLine();
    }

    private int confirmarAccion() {
        int opcion = 0;

        while (opcion != 1 && opcion != 2) {
            System.out.println("\nConfirmar:");
            System.out.println("1. Sí");
            System.out.println("2. No");
            System.out.print("Opción: ");

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
            } else {
                scanner.next();
            }
        }

        pausa();
        return opcion;
    }

    private void mostrarCarrera(Carrera carrera) {

        String planInfo;

        if (carrera.getPlanEstudio() != null) {
            planInfo = carrera.getPlanEstudio().getNombre();
        } else {
            planInfo = "NO POSEE";
        }

        System.out.println(
            "NOMBRE: " + carrera.getNombre() +
            " | TURNO: " + carrera.getTurno() +
            " | PLAN DE ESTUDIO: " + planInfo +
            " | ESTADO: " + (carrera.isActivo() ? "ACTIVO" : "INACTIVO")
        );
    }

    private void mostrarPlan(PlanEstudio planEstudio) {
        // Obtener nombres de materias si existen
        String materiasInfo;

        if (planEstudio.getMaterias() != null && !planEstudio.getMaterias().isEmpty()) {
            materiasInfo = planEstudio.getMaterias()
                    .stream()
                    .map(m -> m.getNombre())   // solo nombre de la materia
                    .reduce((a, b) -> a + ", " + b)
                    .orElse("NO POSEE");
        } else {
            materiasInfo = "NO POSEE";
        }

        System.out.println(
            "NOMBRE: " + planEstudio.getNombre() +
            " | TÍTULO: " + planEstudio.getTitulo() +
            " | MATERIAS: " + materiasInfo +
            " | ESTADO: " + (planEstudio.isActivo() ? "ACTIVO" : "INACTIVO")
        );
    }

    private void listarCarreras() {
        System.out.println("----------Listado de Carreras----------");
        List<Carrera> carreras = carreraController.findAll();

        if (carreras.isEmpty()) {
            System.out.println("No hay carreras registradas.");
            return;
        }

        carreras.forEach(this::mostrarCarrera);
    }

    private void buscarCarrera() {
        System.out.println("----------Buscar carrera----------");
        System.out.print("Ingrese el nombre de la carrera: ");
        String nombre = scanner.nextLine();

        Carrera carrera = carreraController.findByName(nombre);

        if (carrera == null) {
            System.out.println("Carrera no encontrada.");
        } else {
            mostrarCarrera(carrera);
        }
    }

    private void crearCarrera() {
        System.out.println("----------Registrar carrera----------");

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        if (carreraController.findByName(nombre) != null) {
            System.out.println("Ya existe una Carrera registrada con ese nombre.");
            return;
        }

        Turno turno = seleccionarTurno();
        Carrera carrera = new Carrera(nombre, turno);

        System.out.println("\n--- Plan de estudio (opcional) ---");
        gestionarPlanEstudio(carrera);

        System.out.println("\nVista previa:");
        mostrarCarrera(carrera);

        if (confirmarAccion() == 1) {
            carreraController.createCarrera(carrera);
            System.out.println("Carrera registrada!");
        }
    }

    private void actualizarCarrera() {
        System.out.println("----------Actualizar carrera----------");

        System.out.print("Ingrese el nombre de la carrera: ");
        String nombre = scanner.nextLine();

        Carrera carrera = carreraController.findByName(nombre);

        if (carrera == null) {
            System.out.println("Carrera no encontrada.");
            return;
        }

        System.out.println("Carrera encontrada:");
        mostrarCarrera(carrera);

        // Guardar valores originales antes de cualquier modificación
        Turno turnoOriginal = carrera.getTurno();
        PlanEstudio planEstudioOriginal = carrera.getPlanEstudio();

        Turno nuevoTurno = seleccionarTurnoOpcional(carrera.getTurno());
        carrera.setTurno(nuevoTurno);

        System.out.println("--- Gestión de plan de estudio ---");
        gestionarPlanEstudio(carrera);

        System.out.println("\nVista previa:");
        mostrarCarrera(carrera);

        if (confirmarAccion() == 1) {
            carreraController.updateCarrrera(carrera);
            System.out.println("Carrera modificada!");
        } else {
            // Restaurar valores originales
            carrera.setTurno(turnoOriginal);
            carrera.setPlanEstudio(planEstudioOriginal);
            System.out.println("Actualización cancelada.");
        }
    }

    private void eliminarCarrera() {
        System.out.println("----------Eliminar carrera----------");

        System.out.print("Ingrese el nombre de la carrera: ");
        String nombre = scanner.nextLine();

        Carrera carrera = carreraController.findByName(nombre);

        if (carrera == null) {
            System.out.println("Carrera no encontrada.");
            return;
        }

        System.out.println("\nCarrera encontrada:");
        mostrarCarrera(carrera);

        if (confirmarAccion() == 1) {
            carreraController.deleteCarrera(carrera);
            System.out.println("Carrera eliminada!");
        }
    }

    private Turno seleccionarTurno() {
        int opcion = 0;

        while (opcion < 1 || opcion > 3) {
            System.out.println("\nSeleccione turno:");
            System.out.println("1. MAÑANA");
            System.out.println("2. TARDE");
            System.out.println("3. NOCHE");
            System.out.print("Opción: ");

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
            } else {
                scanner.next();
            }
        }

        pausa();

        return switch (opcion) {
            case 1 -> Turno.MANANA;
            case 2 -> Turno.TARDE;
            case 3 -> Turno.NOCHE;
            default -> Turno.MANANA;
        };
    }

    private Turno seleccionarTurnoOpcional(Turno actual) {
        System.out.println("\nTurno actual: " + actual);
        System.out.println("Seleccione nuevo turno (ENTER para mantener actual):");
        System.out.println("1. MAÑANA");
        System.out.println("2. TARDE");
        System.out.println("3. NOCHE");
        System.out.print("Opción: ");

        String input = scanner.nextLine().trim();

        if (input.isEmpty()) {
            return actual;
        }

        return switch (input) {
            case "1" -> Turno.MANANA;
            case "2" -> Turno.TARDE;
            case "3" -> Turno.NOCHE;
            default -> {
                System.out.println("Opción inválida. Se mantiene el turno actual.");
                yield actual;
            }
        };
    }

    // ----------------------------------------------------
    // GESTIÓN PLAN DE ESTUDIO
    // ----------------------------------------------------
    private void gestionarPlanEstudio(Carrera carrera) {
        boolean gestionando = true;

        while (gestionando) {
            System.out.println("""
                \nGestor de Plan de Estudio
                1. Agregar plan
                2. Eliminar plan
                3. Ver plan actual
                0. Terminar gestión de Plan de Estudio"""
            );

            System.out.print("Opción: ");
            String opcion = scanner.nextLine();

            switch (opcion) {

                case "1" -> agregarPlanEstudio(carrera);

                case "2" -> eliminarPlanEstudio(carrera);

                case "3" -> {
                    if (carrera.getPlanEstudio() == null) {
                        System.out.println("La carrera no tiene plan asignado.");
                    } else {
                        System.out.println("\nPlan actual:");
                        mostrarPlan(carrera.getPlanEstudio());
                    }
                }

                case "0" -> gestionando = false;

                default -> System.out.println("Opción no válida.");
            }
        }
    }

    private void agregarPlanEstudio(Carrera carrera) {
        List<PlanEstudio> planes = planEstudioController.findAll();

        if (planes.isEmpty()) {
            System.out.println("No hay planes disponibles.");
            return;
        }

        System.out.println("\nPlanes disponibles:");
        planes.forEach(this::mostrarPlan);

        System.out.print("Ingrese el nombre del plan: ");
        String nombre = scanner.nextLine();

        PlanEstudio plan = planEstudioController.findByName(nombre);

        if (plan != null) {
            carrera.setPlanEstudio(plan);
            System.out.println("Plan agregado.");
        } else {
            System.out.println("Plan no válido.");
        }
    }

    private void eliminarPlanEstudio(Carrera carrera) {
        if (carrera.getPlanEstudio() == null) {
            System.out.println("La carrera no posee plan de estudio.");
            return;
        }

        System.out.println("Plan a eliminar: " + carrera.getPlanEstudio().getNombre());

        if (confirmarAccion() == 1) {
            carrera.setPlanEstudio(null);
            System.out.println("Plan eliminado.");
        }
    }
}
