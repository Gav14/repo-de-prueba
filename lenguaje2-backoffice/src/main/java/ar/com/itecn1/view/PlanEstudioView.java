package ar.com.itecn1.view;

import ar.com.itecn1.controller.MateriaController;
import ar.com.itecn1.controller.PlanEstudioController;
import ar.com.itecn1.model.Materia;
import ar.com.itecn1.model.PlanEstudio;

import java.util.List;
import java.util.Scanner;

public class PlanEstudioView {

    private final PlanEstudioController planEstudioController;
    private final MateriaController materiaController;
    private final Scanner scanner;

    public PlanEstudioView(PlanEstudioController planEstudioController,
                           MateriaController materiaController,
                           Scanner scanner) {

        this.planEstudioController = planEstudioController;
        this.materiaController = materiaController;
        this.scanner = scanner;
    }

    public void iniciar() {
        boolean continuar = true;

        while (continuar) {
            mostrarMenu();
            int opcion = leerEntero();

            switch (opcion) {
                case 1 -> listarPlanes();
                case 2 -> buscarPlan();
                case 3 -> crearPlan();
                case 4 -> actualizarPlan();
                case 5 -> eliminarPlan();
                case 6 -> continuar = false;
                default -> System.out.println("Ingrese una opción válida");
            }
        }
    }

    private void mostrarMenu() {
        System.out.println("\nGESTIÓN DE PLANES DE ESTUDIO");
        System.out.println("1. Listar planes");
        System.out.println("2. Buscar plan por Nombre");
        System.out.println("3. Registrar nuevo plan");
        System.out.println("4. Actualizar plan");
        System.out.println("5. Eliminar plan");
        System.out.println("6. Volver atrás");
    }

    // ============================================================
    // MÉTODOS UTILITARIOS REUTILIZABLES
    // ============================================================
    private int leerEntero() {
        while (!scanner.hasNextInt()) {
            scanner.nextLine();
            System.out.println("Debe ingresar un número.");
        }
        int num = scanner.nextInt();
        scanner.nextLine();
        return num;
    }

    private boolean confirmarAccion(String mensaje) {
        int opcion;
        do {
            System.out.println(mensaje);
            System.out.println("1. Sí");
            System.out.println("2. No");
            opcion = leerEntero();
        } while (opcion < 1 || opcion > 2);

        return opcion == 1;
    }

    private void mostrarPlan(PlanEstudio plan) {
        String materias = (plan.getMaterias() != null && !plan.getMaterias().isEmpty())
                ? plan.getMaterias().stream()
                .map(Materia::getNombre)
                .reduce((a, b) -> a + ", " + b)
                .orElse("NO POSEE")
                : "NO POSEE";

        System.out.println(
            "NOMBRE: " + plan.getNombre() +
            " | TÍTULO: " + plan.getTitulo() +
            " | MATERIAS: " + materias +
            " | ESTADO: " + (plan.isActivo() ? "ACTIVO" : "INACTIVO")
        );
    }

    private void mostrarMateria(Materia m) {
        System.out.println("CÓDIGO: " + m.getCodigoMateria() + " | MATERIA: " + m.getNombre() + " | AÑO: " + m.getAnio() );
    }

    private void listarPlanes() {
        System.out.println("---------- Listado de Planes ----------");
        List<PlanEstudio> planes = planEstudioController.findAll();

        if (planes.isEmpty()) {
            System.out.println("No hay planes registrados.");
            return;
        }

        planes.forEach(this::mostrarPlan);
    }

    private void buscarPlan() {
        System.out.println("---------- Buscar Plan ----------");
        System.out.println("Ingrese el nombre del plan:");
        String nombre = scanner.nextLine();

        PlanEstudio plan = planEstudioController.findByName(nombre);

        if (plan == null) {
            System.out.println("Plan no encontrado.");
        } else {
            mostrarPlan(plan);
        }
    }

    private void crearPlan() {
        System.out.println("---------- Registrar Plan ----------");

        System.out.println("Nombre:");
        String nombre = scanner.nextLine();

        if (planEstudioController.findByName(nombre) != null) {
            System.out.println("Ya existe un plan de estudio registrado con ese nombre.");
            return;
        }

        System.out.println("Título:");
        String titulo = scanner.nextLine();

        System.out.println("Duración:");
        String duracion = scanner.nextLine();

        System.out.println("Año:");
        String anio = scanner.nextLine();

        PlanEstudio nuevoPlan = new PlanEstudio(nombre, anio, duracion, titulo);

        System.out.println("\n--- Gestión de Materias del Plan ---");
        gestionarMateriasDelPlan(nuevoPlan.getMaterias());

        System.out.println("\nVista previa:");
        mostrarPlan(nuevoPlan);

        if (confirmarAccion("¿Confirmar registro?")) {
            planEstudioController.crearPlanEstudio(nuevoPlan);
            System.out.println("Plan registrado con éxito.");
        }
    }

    private void actualizarPlan() {
        System.out.println("---------- Actualizar Plan ----------");

        System.out.println("Ingrese el nombre del plan:");
        String nombre = scanner.nextLine();

        PlanEstudio plan = planEstudioController.findByName(nombre);

        if (plan == null) {
            System.out.println("Plan no encontrado.");
            return;
        }

        mostrarPlan(plan);

        System.out.println("\nTítulo (" + plan.getTitulo() + "):");
        String nuevoTitulo = scanner.nextLine();
        if (!nuevoTitulo.isEmpty()) plan.setTitulo(nuevoTitulo);

        System.out.println("Duración (" + plan.getDuracion() + "):");
        String nuevaDuracion = scanner.nextLine();
        if (!nuevaDuracion.isEmpty()) plan.setDuracion(nuevaDuracion);

        System.out.println("Año (" + plan.getAnio() + "):");
        String nuevoAnio = scanner.nextLine();
        if (!nuevoAnio.isEmpty()) plan.setAnio(nuevoAnio);

        System.out.println("\n--- Gestión de Materias del Plan ---");
        gestionarMateriasDelPlan(plan.getMaterias());

        System.out.println("\nVista previa:");
        mostrarPlan(plan);

        if (confirmarAccion("¿Guardar cambios?")) {
            planEstudioController.editarPlanEstudio(plan);
            System.out.println("Plan actualizado.");
        }
    }

    private void eliminarPlan() {
        System.out.println("---------- Eliminar Plan ----------");

        System.out.println("Ingrese el nombre del plan:");
        String nombre = scanner.nextLine();

        PlanEstudio plan = planEstudioController.findByName(nombre);

        if (plan == null) {
            System.out.println("Plan no encontrado.");
            return;
        }

        mostrarPlan(plan);

        if (confirmarAccion("\n¿Confirmar eliminación?")) {
            planEstudioController.eliminarPlanEstudio(plan);
            System.out.println("Plan eliminado.");
        }
    }

    // ============================================================
    // GESTIONAR MATERIAS
    // ============================================================
    private void gestionarMateriasDelPlan(List<Materia> materiasDelPlan) {
        boolean gestionando = true;

        while (gestionando) {
            System.out.println("\nGestor de Materias del Plan");
            System.out.println("1. Agregar materia");
            System.out.println("2. Quitar materia");
            System.out.println("3. Ver materias del plan");
            System.out.println("4. Terminar gestión de Materias");

            String opcion = scanner.nextLine();

            switch (opcion) {

                case "1" -> agregarMateria(materiasDelPlan);

                case "2" -> quitarMateria(materiasDelPlan);

                case "3" -> mostrarMateriasPlan(materiasDelPlan);

                case "4" -> gestionando = false;

                default -> System.out.println("Opción inválida.");
            }
        }
    }

    private void agregarMateria(List<Materia> materiasDelPlan) {
        List<Materia> disponibles = materiaController.findAll();

        if (disponibles.isEmpty()) {
            System.out.println("No hay materias registradas.");
            return;
        }

        System.out.println("Materias disponibles:");
        disponibles.forEach(this::mostrarMateria);

        System.out.println("\nCódigo de materia a agregar:");
        String codigo = scanner.nextLine();

        Materia materia = materiaController.findByCode(codigo);

        if (materia != null) {
            if (materiasDelPlan.contains(materia)) {
                System.out.println("La materia ya está en el plan.");
            } else {
                materiasDelPlan.add(materia);
                System.out.println("Materia agregada.");
            }
        } else {
            System.out.println("Materia no encontrada o inactiva.");
        }
    }

    private void quitarMateria(List<Materia> materiasDelPlan) {
        if (materiasDelPlan.isEmpty()) {
            System.out.println("El plan no tiene materias.");
            return;
        }

        System.out.println("Materias del plan:");
        materiasDelPlan.forEach(this::mostrarMateria);

        System.out.println("\nCódigo de materia a quitar:");
        String codigo = scanner.nextLine();

        Materia materia = materiaController.findByCode(codigo);

        if (materia != null && materiasDelPlan.remove(materia)) {
            System.out.println("Materia removida.");
        } else {
            System.out.println("La materia no está en el plan.");
        }
    }

    private void mostrarMateriasPlan(List<Materia> materiasDelPlan) {
        System.out.println("Materias del plan:");
        if (materiasDelPlan.isEmpty()) {
            System.out.println("No tiene materias cargadas.");
            return;
        }
        materiasDelPlan.forEach(this::mostrarMateria);
    }
}
