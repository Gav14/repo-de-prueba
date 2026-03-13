package ar.com.itecn1.view;

import ar.com.itecn1.controller.MateriaController;
import ar.com.itecn1.controller.PlanEstudioController;
import ar.com.itecn1.model.Materia;
import ar.com.itecn1.model.PlanEstudio;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PlanEstudioView {

    // Códigos de color
    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String RED = "\u001B[31m";
    private static final String CYAN = "\u001B[36m";
    private static final String PURPLE = "\u001B[35m";
    private static final String BOLD = "\u001B[1m";

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

            if (!scanner.hasNextInt()) {
                System.out.println(RED + "Debe ingresar un número." + RESET);
                scanner.next();
                scanner.nextLine();
                continue;
            }

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> listarPlanes();
                case 2 -> buscarPlan();
                case 3 -> registrarPlan();
                case 4 -> modificarPlan();
                case 5 -> eliminarPlan();
                case 6 -> reactivarPlan();
                case 0 -> continuar = false;
                default -> System.out.println(RED + "Opción no válida. Ingrese un número del 0 al 6." + RESET);
            }
        }
    }

    private void mostrarMenu() {
        System.out.println("\n" + BLUE + BOLD + "PLANES DE ESTUDIO" + RESET);
        System.out.println("-------------------");
        System.out.println(CYAN + "1. Listar todos los planes" + RESET);
        System.out.println(CYAN + "2. Buscar plan" + RESET);
        System.out.println(CYAN + "3. Registrar nuevo plan" + RESET);
        System.out.println(CYAN + "4. Modificar datos de un plan" + RESET);
        System.out.println(CYAN + "5. Eliminar plan (dar de baja)" + RESET);
        System.out.println(CYAN + "6. Reactivar plan" + RESET);
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
                scanner.nextLine();
            }
            opcion = scanner.nextInt();
            scanner.nextLine();
        } while (opcion < 1 || opcion > 2);

        return opcion == 1;
    }

    private int leerEntero(String mensaje, int valorPorDefecto) {
        System.out.print(mensaje + " [" + valorPorDefecto + "]: ");
        String input = scanner.nextLine().trim();

        if (input.isEmpty()) {
            return valorPorDefecto;
        }

        while (true) {
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print(RED + "Debe ingresar un número válido: " + RESET);
                input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    return valorPorDefecto;
                }
            }
        }
    }

    private int leerEnteroRango(String mensaje, int min, int max, int valorPorDefecto) {
        while (true) {
            int valor = leerEntero(mensaje, valorPorDefecto);
            if (valor >= min && valor <= max) {
                return valor;
            }
            System.out.println(RED + "El valor debe estar entre " + min + " y " + max + RESET);
        }
    }

    private void mostrarPlan(PlanEstudio plan) {
        String estado = plan.isActivo() ? GREEN + "ACTIVO" + RESET : RED + "INACTIVO" + RESET;
        String materias = (plan.getMaterias() != null && !plan.getMaterias().isEmpty())
                ? plan.getMaterias().stream()
                .map(Materia::getNombre)
                .reduce((a, b) -> a + ", " + b)
                .orElse("NO POSEE")
                : "NO POSEE";

        System.out.println("  " + BOLD + "Nombre:" + RESET + " " + plan.getNombre());
        System.out.println("  " + BOLD + "Título:" + RESET + " " + plan.getTitulo());
        System.out.println("  " + BOLD + "Año:" + RESET + " " + plan.getAnio() +
                " | " + BOLD + "Duración:" + RESET + " " + plan.getDuracion() + " años" +
                " | " + BOLD + "Estado:" + RESET + " " + estado);

        if (materias.length() > 80) {
            System.out.println("  " + BOLD + "Materias:" + RESET + " " + materias.substring(0, 77) + "...");
        } else {
            System.out.println("  " + BOLD + "Materias:" + RESET + " " + materias);
        }
    }

    private void mostrarPlanCompacto(PlanEstudio plan) {
        String estado = plan.isActivo() ? "ACTIVO" : "INACTIVO";
        System.out.printf("│ %-25s │ %-4d │ %-3d años │ %-8s │%n",
                plan.getNombre().length() > 25 ? plan.getNombre().substring(0, 22) + "..." : plan.getNombre(),
                plan.getAnio(),
                plan.getDuracion(),
                estado
        );
    }

    private void mostrarMateria(Materia m) {
        System.out.println("     • " + CYAN + m.getCodigoMateria() + RESET +
                " - " + m.getNombre() +
                " (" + m.getCuatrimestre() + "° cuatrimestre)");
    }

    private void listarPlanes() {
        System.out.println("\n" + BLUE + BOLD + "LISTADO DE PLANES DE ESTUDIO" + RESET);
        System.out.println("=============================");

        List<PlanEstudio> planes = planEstudioController.findAll();

        if (planes.isEmpty()) {
            System.out.println("No hay planes de estudio registrados.");
            pausa();
            return;
        }

        System.out.println("┌───────────────────────────┬──────┬───────────┬──────────┐");
        System.out.println("│ NOMBRE                    │ AÑO  │ DURACIÓN  │ ESTADO   │");
        System.out.println("├───────────────────────────┼──────┼───────────┼──────────┤");

        for (PlanEstudio p : planes) {
            mostrarPlanCompacto(p);
        }
        System.out.println("└───────────────────────────┴──────┴───────────┴──────────┘");

        System.out.println("\n" + CYAN + "Detalles completos:" + RESET);
        for (int i = 0; i < planes.size(); i++) {
            System.out.println("\n" + YELLOW + "Plan " + (i + 1) + ":" + RESET);
            mostrarPlan(planes.get(i));
        }
        pausa();
    }

    private void buscarPlan() {
        System.out.println("\n" + BLUE + BOLD + "BUSCAR PLAN DE ESTUDIO" + RESET);
        System.out.println("=======================");
        System.out.print("Ingrese el nombre o parte del nombre: ");
        String texto = scanner.nextLine();

        try {
            List<PlanEstudio> resultados = planEstudioController.buscarPlanes(texto);

            if (resultados.isEmpty()) {
                System.out.println(RED + "No se encontraron planes que coincidan con: \"" + texto + "\"" + RESET);
            } else {
                System.out.println("\n" + GREEN + "Se encontraron " + resultados.size() + " plan(es):" + RESET);
                System.out.println("┌────────────────────────────────────────────────────────────────┐");
                for (int i = 0; i < resultados.size(); i++) {
                    System.out.println("│ " + (i + 1) + ". " + resultados.get(i).getNombre() +
                            " (" + resultados.get(i).getAnio() + ") - " +
                            (resultados.get(i).isActivo() ? "ACTIVO" : "INACTIVO"));
                }
                System.out.println("└────────────────────────────────────────────────────────────────┘");

                System.out.print("\n¿Desea ver detalles de algún plan? (Ingrese número o 0 para salir): ");
                if (scanner.hasNextInt()) {
                    int idx = scanner.nextInt();
                    scanner.nextLine();
                    if (idx > 0 && idx <= resultados.size()) {
                        System.out.println("\n" + CYAN + "Detalles del plan:" + RESET);
                        mostrarPlan(resultados.get(idx - 1));
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println(RED + "Error: " + e.getMessage() + RESET);
        }
        pausa();
    }

    private void registrarPlan() {
        System.out.println("\n" + BLUE + BOLD + "REGISTRAR NUEVO PLAN DE ESTUDIO" + RESET);
        System.out.println("================================");

        System.out.print("Nombre del plan: ");
        String nombre = scanner.nextLine();

        if (nombre.trim().isEmpty()) {
            System.out.println(RED + "El nombre no puede estar vacío." + RESET);
            pausa();
            return;
        }

        try {
            PlanEstudio existente = planEstudioController.findByName(nombre);

            if (existente != null) {
                if (existente.isActivo()) {
                    System.out.println(RED + "Ya existe un plan ACTIVO con ese nombre." + RESET);
                    pausa();
                    return;
                } else {
                    System.out.println(YELLOW + "Ya existe un plan INACTIVO con ese nombre." + RESET);
                    if (confirmarAccion("¿Desea reactivarlo en lugar de crear uno nuevo?")) {
                        planEstudioController.reactivarPlanEstudio(nombre);
                        System.out.println(GREEN + "✓ Plan reactivado exitosamente!" + RESET);
                    }
                    pausa();
                    return;
                }
            }

            List<PlanEstudio> similares = planEstudioController.buscarPlanes(nombre);
            if (!similares.isEmpty()) {
                System.out.println(YELLOW + "\nPlanes con nombres similares encontrados:" + RESET);
                for (PlanEstudio p : similares) {
                    System.out.println("  • " + p.getNombre() + " (" + p.getAnio() + ")");
                }
                if (!confirmarAccion("\n¿Desea continuar con el registro de \"" + nombre + "\"?")) {
                    System.out.println("Registro cancelado.");
                    pausa();
                    return;
                }
            }

            int anio = leerEnteroRango("Año de vigencia", 1900, 2100, 2024);
            int duracion = leerEnteroRango("Duración en años", 1, 6, 3);

            System.out.print("Título que otorga: ");
            String titulo = scanner.nextLine();
            if (titulo.trim().isEmpty()) {
                titulo = "Sin título especificado";
            }

            PlanEstudio nuevoPlan = new PlanEstudio(nombre, anio, duracion, titulo);

            System.out.println("\n" + CYAN + "📚 Materias del plan" + RESET);
            if (confirmarAccion("¿Desea agregar materias ahora?")) {
                gestionarMateriasDelPlan(nuevoPlan.getMaterias());
            }

            System.out.println("\n" + CYAN + "Vista previa del plan:" + RESET);
            mostrarPlan(nuevoPlan);

            if (confirmarAccion("¿Confirmar registro?")) {
                planEstudioController.crearPlanEstudio(nuevoPlan);
                System.out.println(GREEN + "✓ Plan registrado exitosamente!" + RESET);
            } else {
                System.out.println("Registro cancelado.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(RED + "Error: " + e.getMessage() + RESET);
        }
        pausa();
    }

    private void modificarPlan() {
        System.out.println("\n" + BLUE + BOLD + "MODIFICAR PLAN DE ESTUDIO" + RESET);
        System.out.println("=========================");

        System.out.print("Ingrese el nombre o parte del nombre del plan: ");
        String texto = scanner.nextLine();

        try {
            List<PlanEstudio> resultados = planEstudioController.buscarPlanes(texto);
            List<PlanEstudio> activos = resultados.stream().filter(PlanEstudio::isActivo).toList();

            if (activos.isEmpty()) {
                System.out.println(RED + "No se encontraron planes ACTIVOS para modificar." + RESET);
                if (!resultados.isEmpty()) {
                    System.out.println(YELLOW + "(Los planes encontrados están INACTIVOS. Use opción 6 para reactivarlos)" + RESET);
                }
                pausa();
                return;
            }

            PlanEstudio planSeleccionado = (activos.size() == 1) ?
                    activos.get(0) : seleccionarPlanDeLista(activos, "modificar");

            if (planSeleccionado == null) {
                pausa();
                return;
            }

            System.out.println("\n" + CYAN + "Datos actuales:" + RESET);
            mostrarPlan(planSeleccionado);

            String tituloOriginal = planSeleccionado.getTitulo();
            int anioOriginal = planSeleccionado.getAnio();
            int duracionOriginal = planSeleccionado.getDuracion();
            List<Materia> materiasOriginales = new ArrayList<>(planSeleccionado.getMaterias());

            System.out.println("\n" + YELLOW + "NUEVOS DATOS (dejar en blanco para no cambiar):" + RESET);

            System.out.print("Nuevo título [" + planSeleccionado.getTitulo() + "]: ");
            String nuevoTitulo = scanner.nextLine();
            if (!nuevoTitulo.isBlank()) {
                planSeleccionado.setTitulo(nuevoTitulo);
            }

            int nuevoAnio = leerEnteroRango("Nuevo año", 1900, 2100, planSeleccionado.getAnio());
            planSeleccionado.setAnio(nuevoAnio);

            int nuevaDuracion = leerEnteroRango("Nueva duración en años", 1, 6, planSeleccionado.getDuracion());
            planSeleccionado.setDuracion(nuevaDuracion);

            if (confirmarAccion("¿Desea modificar las materias del plan?")) {
                gestionarMateriasDelPlan(planSeleccionado.getMaterias());
            }

            System.out.println("\n" + CYAN + "Vista previa de los cambios:" + RESET);
            mostrarPlan(planSeleccionado);

            if (confirmarAccion("¿Confirmar cambios?")) {
                planEstudioController.editarPlanEstudio(planSeleccionado);
                System.out.println(GREEN + "✓ Plan modificado exitosamente!" + RESET);
            } else {
                planSeleccionado.setTitulo(tituloOriginal);
                planSeleccionado.setAnio(anioOriginal);
                planSeleccionado.setDuracion(duracionOriginal);
                planSeleccionado.setMaterias(materiasOriginales);
                System.out.println("Modificación cancelada.");
            }

        } catch (IllegalArgumentException e) {
            System.out.println(RED + "Error: " + e.getMessage() + RESET);
        }
        pausa();
    }

    private void eliminarPlan() {
        System.out.println("\n" + BLUE + BOLD + "ELIMINAR PLAN DE ESTUDIO" + RESET);
        System.out.println("=========================");

        System.out.print("Ingrese el nombre o parte del nombre del plan: ");
        String texto = scanner.nextLine();

        try {
            List<PlanEstudio> resultados = planEstudioController.buscarPlanes(texto);
            List<PlanEstudio> activos = resultados.stream().filter(PlanEstudio::isActivo).toList();

            if (activos.isEmpty()) {
                System.out.println(RED + "No se encontraron planes ACTIVOS para eliminar." + RESET);
                if (!resultados.isEmpty()) {
                    System.out.println(YELLOW + "(Los planes encontrados ya están INACTIVOS)" + RESET);
                }
                pausa();
                return;
            }

            PlanEstudio planSeleccionado = (activos.size() == 1) ?
                    activos.get(0) : seleccionarPlanDeLista(activos, "eliminar");

            if (planSeleccionado == null) {
                pausa();
                return;
            }

            System.out.println("\n" + CYAN + "Plan a eliminar:" + RESET);
            mostrarPlan(planSeleccionado);

            if (confirmarAccion(RED + "¿Está seguro que desea ELIMINAR (dar de baja) este plan?" + RESET)) {
                planEstudioController.eliminarPlanEstudio(planSeleccionado);
                System.out.println(GREEN + "✓ Plan dado de baja correctamente (estado: INACTIVO)" + RESET);
            } else {
                System.out.println("Eliminación cancelada.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(RED + "Error: " + e.getMessage() + RESET);
        }
        pausa();
    }

    private void reactivarPlan() {
        System.out.println("\n" + BLUE + BOLD + "REACTIVAR PLAN DE ESTUDIO" + RESET);
        System.out.println("==========================");

        System.out.print("Ingrese el nombre o parte del nombre del plan: ");
        String texto = scanner.nextLine();

        try {
            List<PlanEstudio> resultados = planEstudioController.buscarPlanes(texto);
            List<PlanEstudio> inactivos = resultados.stream().filter(p -> !p.isActivo()).toList();

            if (inactivos.isEmpty()) {
                System.out.println(RED + "No se encontraron planes INACTIVOS para reactivar." + RESET);
                if (!resultados.isEmpty()) {
                    System.out.println(YELLOW + "(Los planes encontrados ya están ACTIVOS)" + RESET);
                }
                pausa();
                return;
            }

            PlanEstudio planSeleccionado = (inactivos.size() == 1) ?
                    inactivos.get(0) : seleccionarPlanDeLista(inactivos, "reactivar");

            if (planSeleccionado == null) {
                pausa();
                return;
            }

            System.out.println("\n" + CYAN + "Plan seleccionado para reactivar:" + RESET);
            mostrarPlan(planSeleccionado);

            if (confirmarAccion("¿Confirmar reactivación?")) {
                planEstudioController.reactivarPlanEstudio(planSeleccionado.getNombre());
                System.out.println(GREEN + "✓ Plan reactivado exitosamente!" + RESET);
            } else {
                System.out.println("Reactivación cancelada.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(RED + "Error: " + e.getMessage() + RESET);
        }
        pausa();
    }

    private PlanEstudio seleccionarPlanDeLista(List<PlanEstudio> planes, String accion) {
        System.out.println("\n" + CYAN + "Planes encontrados:" + RESET);
        System.out.println("┌────────────────────────────────────────────────────────────────┐");
        for (int i = 0; i < planes.size(); i++) {
            PlanEstudio p = planes.get(i);
            System.out.printf("│ %d. %-30s (%d) %s%n",
                    (i + 1),
                    p.getNombre().length() > 30 ? p.getNombre().substring(0, 27) + "..." : p.getNombre(),
                    p.getAnio(),
                    p.isActivo() ? "ACTIVO" : "INACTIVO");
        }
        System.out.println("└────────────────────────────────────────────────────────────────┘");

        System.out.print("\nSeleccione el número del plan que desea " + accion + " (0 para cancelar): ");

        int seleccion = -1;
        if (scanner.hasNextInt()) {
            seleccion = scanner.nextInt();
        }
        scanner.nextLine();

        if (seleccion == 0) {
            System.out.println("Operación cancelada.");
            return null;
        }

        if (seleccion < 1 || seleccion > planes.size()) {
            System.out.println(RED + "Opción no válida." + RESET);
            return null;
        }

        return planes.get(seleccion - 1);
    }

    private void gestionarMateriasDelPlan(List<Materia> materiasDelPlan) {
        if (materiasDelPlan == null) {
            System.out.println(RED + "Error interno: lista de materias no inicializada" + RESET);
            return;
        }

        boolean gestionando = true;

        while (gestionando) {
            System.out.println("\n" + PURPLE + BOLD + "GESTIÓN DE MATERIAS" + RESET);
            System.out.println("┌─────────────────────────┐");
            System.out.println("│ 1. Agregar materia      │");
            System.out.println("│ 2. Quitar materia       │");
            System.out.println("│ 3. Ver materias del plan│");
            System.out.println("│ 4. Terminar             │");
            System.out.println("└─────────────────────────┘");
            System.out.print("Seleccione: ");

            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1" -> agregarMateria(materiasDelPlan);
                case "2" -> quitarMateria(materiasDelPlan);
                case "3" -> mostrarMateriasPlan(materiasDelPlan);
                case "4" -> gestionando = false;
                default -> System.out.println(RED + "Opción no válida." + RESET);
            }
        }
    }

    private void agregarMateria(List<Materia> materiasDelPlan) {
        List<Materia> disponibles = materiaController.findAll();
        List<Materia> activas = disponibles.stream().filter(Materia::isActivo).toList();

        if (activas.isEmpty()) {
            System.out.println(RED + "No hay materias activas disponibles." + RESET);
            return;
        }

        System.out.println("\n" + CYAN + "Materias activas disponibles:" + RESET);
        for (int i = 0; i < activas.size(); i++) {
            System.out.print((i + 1) + ". ");
            mostrarMateria(activas.get(i));
        }

        System.out.print("\nSeleccione el número de la materia a agregar (0 para cancelar): ");

        int seleccion = -1;
        if (scanner.hasNextInt()) {
            seleccion = scanner.nextInt();
        }
        scanner.nextLine();

        if (seleccion == 0) return;

        if (seleccion < 1 || seleccion > activas.size()) {
            System.out.println(RED + "Opción no válida." + RESET);
            return;
        }

        Materia materiaSeleccionada = activas.get(seleccion - 1);

        if (materiasDelPlan.contains(materiaSeleccionada)) {
            System.out.println(RED + "La materia ya está en el plan." + RESET);
        } else {
            materiasDelPlan.add(materiaSeleccionada);
            System.out.println(GREEN + "✓ Materia agregada al plan." + RESET);
        }
    }

    private void quitarMateria(List<Materia> materiasDelPlan) {
        if (materiasDelPlan.isEmpty()) {
            System.out.println(RED + "El plan no tiene materias." + RESET);
            return;
        }

        System.out.println("\n" + CYAN + "Materias del plan:" + RESET);
        for (int i = 0; i < materiasDelPlan.size(); i++) {
            System.out.print((i + 1) + ". ");
            mostrarMateria(materiasDelPlan.get(i));
        }

        System.out.print("\nSeleccione el número de la materia a quitar (0 para cancelar): ");

        int seleccion = -1;
        if (scanner.hasNextInt()) {
            seleccion = scanner.nextInt();
        }
        scanner.nextLine();

        if (seleccion == 0) return;

        if (seleccion < 1 || seleccion > materiasDelPlan.size()) {
            System.out.println(RED + "Opción no válida." + RESET);
            return;
        }

        Materia materiaRemovida = materiasDelPlan.remove(seleccion - 1);
        System.out.println(GREEN + "✓ Materia removida: " + materiaRemovida.getNombre() + RESET);
    }

    private void mostrarMateriasPlan(List<Materia> materiasDelPlan) {
        System.out.println("\n" + CYAN + "Materias del plan:" + RESET);
        if (materiasDelPlan.isEmpty()) {
            System.out.println("  El plan no tiene materias cargadas.");
            return;
        }
        materiasDelPlan.forEach(this::mostrarMateria);
    }

    private void pausa() {
        System.out.print("\n" + CYAN + "Presione ENTER para continuar..." + RESET);
        scanner.nextLine();
    }
}