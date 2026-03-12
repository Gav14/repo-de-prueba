package ar.com.itecn1.view;

import ar.com.itecn1.controller.MateriaController;
import ar.com.itecn1.controller.PlanEstudioController;
import ar.com.itecn1.model.Materia;
import ar.com.itecn1.model.PlanEstudio;

import java.util.ArrayList;
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
            try {
                mostrarMenu();

                if (!scanner.hasNextInt()) {
                    System.out.println("Debe ingresar un número.");
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
                    default -> System.out.println("Opción no válida. Ingrese un número del 0 al 6.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("❌ Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("❌ Error inesperado: " + e.getMessage());
            }
        }
    }

    private void mostrarMenu() {
        System.out.println("\n╔════════════════════════════════╗");
        System.out.println("║    GESTIÓN DE PLANES DE ESTUDIO ║");
        System.out.println("╠════════════════════════════════╣");
        System.out.println("║ 1. Listar todos los planes     ║");
        System.out.println("║ 2. Buscar plan                 ║");
        System.out.println("║ 3. Registrar nuevo plan        ║");
        System.out.println("║ 4. Modificar datos de un plan  ║");
        System.out.println("║ 5. Eliminar plan (dar de baja) ║");
        System.out.println("║ 6. Reactivar plan              ║");
        System.out.println("║ 0. Volver al menú anterior     ║");
        System.out.println("╚════════════════════════════════╝");
        System.out.print("Seleccione una opción: ");
    }

    private int leerEntero() {
        while (!scanner.hasNextInt()) {
            String input = scanner.nextLine();
            if (input.isEmpty()) {
                return 0;
            }
            System.out.println("Debe ingresar un número.");
        }
        int num = scanner.nextInt();
        scanner.nextLine();
        return num;
    }

    private int leerEnteroConRango(String mensaje, int min, int max, int valorSugerido) {
        while (true) {
            System.out.print(mensaje + " [" + valorSugerido + "]: ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                return valorSugerido;
            }

            try {
                int num = Integer.parseInt(input);
                if (num >= min && num <= max) {
                    return num;
                }
                System.out.printf("❌ El valor debe estar entre %d y %d%n", min, max);
            } catch (NumberFormatException e) {
                System.out.println("❌ Debe ingresar un número válido");
            }
        }
    }

    private int confirmarAccion() {
        while (true) {
            System.out.println("\n┌─────────────────────────┐");
            System.out.println("│     CONFIRMAR ACCIÓN     │");
            System.out.println("├─────────────────────────┤");
            System.out.println("│ 1. Sí, confirmar         │");
            System.out.println("│ 2. No, cancelar          │");
            System.out.println("└─────────────────────────┘");
            System.out.print("Opción: ");

            String input = scanner.nextLine().trim();

            if (input.equals("1") || input.equalsIgnoreCase("S")) {
                return 1;
            }
            if (input.equals("2") || input.equalsIgnoreCase("N")) {
                return 2;
            }
            System.out.println("❌ Opción no válida. Ingrese 1(S) o 2(N)");
        }
    }

    private void mostrarPlan(PlanEstudio plan) {
        String materias = (plan.getMaterias() != null && !plan.getMaterias().isEmpty())
                ? plan.getMaterias().stream()
                .map(Materia::getNombre)
                .reduce((a, b) -> a + ", " + b)
                .orElse("NO POSEE")
                : "NO POSEE";

        String estado = plan.isActivo() ? "ACTIVO" : "INACTIVO";

        System.out.printf("│ • %-20s │ Año: %d │ Duración: %d años │ %-8s │%n",
                plan.getNombre(),
                plan.getAnio(),
                plan.getDuracion(),
                estado
        );
        System.out.println("│   Título: " + plan.getTitulo());

        if (materias.length() > 60) {
            System.out.println("│   Materias: " + materias.substring(0, 57) + "...");
        } else {
            System.out.println("│   Materias: " + materias);
        }
    }

    private void mostrarMateria(Materia m) {
        // CORREGIDO: Cambié getAnio() por getCuatrimestre()
        System.out.println("   • " + m.getCodigoMateria() + " - " + m.getNombre() + " (" + m.getCuatrimestre() + "° cuatrimestre)");
    }

    private void listarPlanes() {
        System.out.println("\n┌────────────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                           LISTADO DE PLANES DE ESTUDIO                          │");
        System.out.println("├────────────────────────────────────────────────────────────────────────────────┤");

        List<PlanEstudio> planes = planEstudioController.findAll();

        if (planes.isEmpty()) {
            System.out.println("│                      No hay planes de estudio registrados.                     │");
        } else {
            for (int i = 0; i < planes.size(); i++) {
                mostrarPlan(planes.get(i));
                if (i < planes.size() - 1) {
                    System.out.println("├────────────────────────────────────────────────────────────────────────────────┤");
                }
            }
        }

        System.out.println("└────────────────────────────────────────────────────────────────────────────────┘");
    }

    private void buscarPlan() {
        System.out.println("\n🔍 BUSCAR PLAN DE ESTUDIO");
        System.out.print("Ingrese el nombre o parte del nombre: ");
        String texto = scanner.nextLine();

        try {
            List<PlanEstudio> resultados = planEstudioController.buscarPlanes(texto);

            if (resultados.isEmpty()) {
                System.out.println("❌ No se encontraron planes que coincidan con: \"" + texto + "\"");
            } else {
                System.out.println("\n✅ Se encontraron " + resultados.size() + " plan(es):");
                System.out.println("┌────────────────────────────────────────────────────────────────────────────────┐");
                for (int i = 0; i < resultados.size(); i++) {
                    mostrarPlan(resultados.get(i));
                    if (i < resultados.size() - 1) {
                        System.out.println("├────────────────────────────────────────────────────────────────────────────────┤");
                    }
                }
                System.out.println("└────────────────────────────────────────────────────────────────────────────────┘");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private void registrarPlan() {
        System.out.println("\n📝 REGISTRAR NUEVO PLAN DE ESTUDIO");

        System.out.print("Nombre del plan: ");
        String nombre = scanner.nextLine();

        if (nombre.trim().isEmpty()) {
            System.out.println("❌ El nombre no puede estar vacío.");
            return;
        }

        try {
            PlanEstudio existente = planEstudioController.findByName(nombre);

            if (existente != null) {
                if (existente.isActivo()) {
                    System.out.println("❌ Ya existe un plan ACTIVO con ese nombre.");
                    return;
                } else {
                    System.out.println("⚠️  Ya existe un plan INACTIVO con ese nombre.");
                    System.out.println("¿Desea reactivarlo en lugar de crear uno nuevo?");
                    if (confirmarAccion() == 1) {
                        planEstudioController.reactivarPlanEstudio(nombre);
                        System.out.println("✅ Plan reactivado exitosamente!");
                    }
                    return;
                }
            }

            List<PlanEstudio> similares = planEstudioController.buscarPlanes(nombre);
            if (!similares.isEmpty()) {
                System.out.println("\n⚠️  Planes con nombres similares encontrados:");
                similares.forEach(this::mostrarPlan);
                System.out.println("\n¿Desea continuar con el registro de \"" + nombre + "\"?");
                if (confirmarAccion() != 1) {
                    System.out.println("Registro cancelado.");
                    return;
                }
            }

            int anio = leerEnteroConRango("Año de vigencia", 1900, 2100, 2024);
            int duracion = leerEnteroConRango("Duración en años", 1, 6, 3);

            System.out.print("Título que otorga: ");
            String titulo = scanner.nextLine();

            PlanEstudio nuevoPlan = new PlanEstudio(nombre, anio, duracion, titulo);

            System.out.println("\n📚 Materias del plan");
            System.out.print("¿Desea agregar materias ahora? (S/N) o (1/2): ");
            String respuesta = scanner.nextLine().trim();
            if (respuesta.equalsIgnoreCase("S") || respuesta.equals("1")) {
                gestionarMateriasDelPlan(nuevoPlan.getMaterias());
            }

            System.out.println("\n📋 Vista previa del plan:");
            mostrarPlan(nuevoPlan);

            System.out.println("\n¿Confirma el registro de este plan?");
            if (confirmarAccion() == 1) {
                planEstudioController.crearPlanEstudio(nuevoPlan);
                System.out.println("✅ ¡Plan registrado exitosamente!");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private void modificarPlan() {
        System.out.println("\n✏️  MODIFICAR PLAN DE ESTUDIO");
        System.out.print("Ingrese el nombre o parte del nombre del plan: ");
        String texto = scanner.nextLine();

        try {
            List<PlanEstudio> resultados = planEstudioController.buscarPlanes(texto);
            List<PlanEstudio> activos = resultados.stream().filter(PlanEstudio::isActivo).toList();

            if (activos.isEmpty()) {
                System.out.println("❌ No se encontraron planes ACTIVOS para modificar.");
                if (!resultados.isEmpty()) {
                    System.out.println("(Los planes encontrados están INACTIVOS. Use opción 6 para reactivarlos)");
                }
                return;
            }

            PlanEstudio planSeleccionado = (activos.size() == 1) ?
                    activos.get(0) : seleccionarPlanDeLista(activos, "modificar");

            if (planSeleccionado == null) return;

            System.out.println("\n🔄 Modificando plan: " + planSeleccionado.getNombre());

            String tituloOriginal = planSeleccionado.getTitulo();
            int anioOriginal = planSeleccionado.getAnio();
            int duracionOriginal = planSeleccionado.getDuracion();
            List<Materia> materiasOriginales = new ArrayList<>(planSeleccionado.getMaterias());

            System.out.println("\n📄 Título actual: " + planSeleccionado.getTitulo());
            System.out.print("Nuevo título (ENTER para mantener): ");
            String nuevoTitulo = scanner.nextLine();
            if (!nuevoTitulo.isEmpty()) {
                planSeleccionado.setTitulo(nuevoTitulo);
            }

            int nuevoAnio = leerEnteroConRango(
                    "📅 Nuevo año",
                    1900, 2100,
                    planSeleccionado.getAnio()
            );
            planSeleccionado.setAnio(nuevoAnio);

            int nuevaDuracion = leerEnteroConRango(
                    "⏱️  Nueva duración",
                    1, 6,
                    planSeleccionado.getDuracion()
            );
            planSeleccionado.setDuracion(nuevaDuracion);

            System.out.println("\n📚 Gestión de materias");
            System.out.print("¿Desea modificar las materias del plan? (S/N) o (1/2): ");
            String respuesta = scanner.nextLine().trim();

            if (respuesta.equalsIgnoreCase("S") || respuesta.equals("1")) {
                gestionarMateriasDelPlan(planSeleccionado.getMaterias());
            }

            System.out.println("\n📋 Vista previa de los cambios:");
            mostrarPlan(planSeleccionado);

            System.out.println("\n¿Confirma los cambios?");
            if (confirmarAccion() == 1) {
                planEstudioController.editarPlanEstudio(planSeleccionado);
                System.out.println("✅ ¡Plan modificado exitosamente!");
            } else {
                planSeleccionado.setTitulo(tituloOriginal);
                planSeleccionado.setAnio(anioOriginal);
                planSeleccionado.setDuracion(duracionOriginal);
                planSeleccionado.setMaterias(materiasOriginales);
                System.out.println("Modificación cancelada.");
            }

        } catch (IllegalArgumentException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private void eliminarPlan() {
        System.out.println("\n🗑️  ELIMINAR PLAN (DAR DE BAJA)");
        System.out.print("Ingrese el nombre o parte del nombre del plan: ");
        String texto = scanner.nextLine();

        try {
            List<PlanEstudio> resultados = planEstudioController.buscarPlanes(texto);
            List<PlanEstudio> activos = resultados.stream().filter(PlanEstudio::isActivo).toList();

            if (activos.isEmpty()) {
                System.out.println("❌ No se encontraron planes ACTIVOS para eliminar.");
                if (!resultados.isEmpty()) {
                    System.out.println("(Los planes encontrados ya están INACTIVOS)");
                }
                return;
            }

            PlanEstudio planSeleccionado = (activos.size() == 1) ?
                    activos.get(0) : seleccionarPlanDeLista(activos, "eliminar");

            if (planSeleccionado == null) return;

            System.out.println("\n⚠️  ADVERTENCIA: Esta acción dará de baja el plan:");
            mostrarPlan(planSeleccionado);
            System.out.println("\n¿Está seguro que desea ELIMINAR (dar de baja) este plan?");

            if (confirmarAccion() == 1) {
                planEstudioController.eliminarPlanEstudio(planSeleccionado);
                System.out.println("✅ Plan dado de baja correctamente (estado: INACTIVO)");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private void reactivarPlan() {
        System.out.println("\n🔄 REACTIVAR PLAN DE ESTUDIO");
        System.out.print("Ingrese el nombre o parte del nombre del plan: ");
        String texto = scanner.nextLine();

        try {
            List<PlanEstudio> resultados = planEstudioController.buscarPlanes(texto);
            List<PlanEstudio> inactivos = resultados.stream().filter(p -> !p.isActivo()).toList();

            if (inactivos.isEmpty()) {
                System.out.println("❌ No se encontraron planes INACTIVOS para reactivar.");
                if (!resultados.isEmpty()) {
                    System.out.println("(Los planes encontrados ya están ACTIVOS)");
                }
                return;
            }

            PlanEstudio planSeleccionado = (inactivos.size() == 1) ?
                    inactivos.get(0) : seleccionarPlanDeLista(inactivos, "reactivar");

            if (planSeleccionado == null) return;

            System.out.println("\n🔄 Plan seleccionado para reactivar:");
            mostrarPlan(planSeleccionado);

            System.out.println("\n¿Confirma la reactivación de este plan?");
            if (confirmarAccion() == 1) {
                planEstudioController.reactivarPlanEstudio(planSeleccionado.getNombre());
                System.out.println("✅ ¡Plan reactivado exitosamente!");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private PlanEstudio seleccionarPlanDeLista(List<PlanEstudio> planes, String accion) {
        System.out.println("\n📋 Se encontraron los siguientes planes:");
        for (int i = 0; i < planes.size(); i++) {
            System.out.print((i + 1) + ". ");
            System.out.println(planes.get(i).getNombre() + " (" + planes.get(i).getAnio() + ") - " +
                    (planes.get(i).isActivo() ? "ACTIVO" : "INACTIVO"));
        }

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
            System.out.println("❌ Opción no válida.");
            return null;
        }

        return planes.get(seleccion - 1);
    }

    // ============================================================
    // GESTIÓN DE MATERIAS
    // ============================================================
    private void gestionarMateriasDelPlan(List<Materia> materiasDelPlan) {
        if (materiasDelPlan == null) {
            System.out.println("⚠️  Error interno: lista de materias no inicializada");
            return;
        }
        boolean gestionando = true;

        while (gestionando) {
            System.out.println("\n┌─────────────────────────┐");
            System.out.println("│   GESTIÓN DE MATERIAS    │");
            System.out.println("├─────────────────────────┤");
            System.out.println("│ 1. Agregar materia      │");
            System.out.println("│ 2. Quitar materia       │");
            System.out.println("│ 3. Ver materias del plan│");
            System.out.println("│ 4. Terminar             │");
            System.out.println("└─────────────────────────┘");
            System.out.print("Opción: ");

            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1" -> agregarMateria(materiasDelPlan);
                case "2" -> quitarMateria(materiasDelPlan);
                case "3" -> mostrarMateriasPlan(materiasDelPlan);
                case "4" -> gestionando = false;
                default -> System.out.println("❌ Opción no válida.");
            }
        }
    }

    private void agregarMateria(List<Materia> materiasDelPlan) {
        List<Materia> disponibles = materiaController.findAll();
        List<Materia> activas = disponibles.stream().filter(Materia::isActivo).toList();

        if (activas.isEmpty()) {
            System.out.println("❌ No hay materias activas disponibles.");
            return;
        }

        System.out.println("\n📚 Materias activas disponibles:");
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
            System.out.println("❌ Opción no válida.");
            return;
        }

        Materia materiaSeleccionada = activas.get(seleccion - 1);

        if (materiasDelPlan.contains(materiaSeleccionada)) {
            System.out.println("❌ La materia ya está en el plan.");
        } else {
            materiasDelPlan.add(materiaSeleccionada);
            System.out.println("✅ Materia agregada al plan.");
        }
    }

    private void quitarMateria(List<Materia> materiasDelPlan) {
        if (materiasDelPlan.isEmpty()) {
            System.out.println("❌ El plan no tiene materias.");
            return;
        }

        System.out.println("\n📚 Materias del plan:");
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
            System.out.println("❌ Opción no válida.");
            return;
        }

        Materia materiaRemovida = materiasDelPlan.remove(seleccion - 1);
        System.out.println("✅ Materia removida: " + materiaRemovida.getNombre());
    }

    private void mostrarMateriasPlan(List<Materia> materiasDelPlan) {
        System.out.println("\n📚 Materias del plan:");
        if (materiasDelPlan.isEmpty()) {
            System.out.println("   El plan no tiene materias cargadas.");
            return;
        }
        materiasDelPlan.forEach(this::mostrarMateria);
    }
}