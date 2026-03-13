package ar.com.itecn1.view;

import ar.com.itecn1.controller.MateriaController;
import ar.com.itecn1.model.Materia;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MateriaView {
    private final MateriaController materiaController;
    private final Scanner scanner;

    public MateriaView(MateriaController materiaController, Scanner scanner) {
        this.materiaController = materiaController;
        this.scanner = scanner;
    }

    public void iniciar() {
        boolean continuar = true;

        while (continuar) {
            mostrarMenu();
            int opcion = leerEntero();

            switch (opcion) {
                case 1 -> listarMaterias();
                case 2 -> listarMateriasPorCuatrimestre();
                case 3 -> buscarMateria();
                case 4 -> crearMateria();
                case 5 -> actualizarMateria();
                case 6 -> eliminarMateria();
                case 0 -> continuar = false;
                default -> System.out.println("✗ Opción inválida");
            }
        }
    }

    private void mostrarMenu() {
        System.out.println("\n╔════════════════════════════════╗");
        System.out.println("║      GESTIÓN DE MATERIAS       ║");
        System.out.println("╠════════════════════════════════╣");
        System.out.println("║ 1. Listar todas                ║");
        System.out.println("║ 2. Listar por cuatrimestre     ║");
        System.out.println("║ 3. Buscar por código           ║");
        System.out.println("║ 4. Registrar materia           ║");
        System.out.println("║ 5. Actualizar materia          ║");
        System.out.println("║ 6. Eliminar materia            ║");
        System.out.println("║ 0. Volver atrás                ║");
        System.out.println("╚════════════════════════════════╝");
        System.out.print("Seleccione: ");
    }

    private int leerEntero() {
        while (!scanner.hasNextInt()) {
            scanner.nextLine();
            System.out.print("✗ Ingrese un número: ");
        }
        int num = scanner.nextInt();
        scanner.nextLine();
        return num;
    }

    private String leerNoVacio(String mensaje) {
        String input;
        do {
            System.out.print(mensaje);
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("✗ Este campo no puede estar vacío");
            }
        } while (input.isEmpty());
        return input;
    }

    private boolean confirmar(String mensaje) {
        System.out.println(mensaje);
        System.out.print("¿Confirmar? (1. Sí / 2. No): ");
        return leerEntero() == 1;
    }

    // ============================================================
    // FUNCIÓN AUXILIAR PARA FORMATEAR CORRELATIVAS DE PASANTÍAS
    // ============================================================
    private String formatearCorrelativas(Map<String, List<Materia>> correlativasPorCuatri) {
        if (correlativasPorCuatri.isEmpty()) {
            return "NO POSEE";
        }

        // Verificar si es Pasantías I (código PAS-401) o similar
        // Buscamos si tiene todos los cuatrimestres completos
        boolean tieneCompleto = false;

        // Si tiene materias de 1° y 2° cuatrimestre, verificar si son todas
        if (correlativasPorCuatri.containsKey("1") && correlativasPorCuatri.containsKey("2")) {
            List<Materia> primerCuatri = correlativasPorCuatri.get("1");
            List<Materia> segundoCuatri = correlativasPorCuatri.get("2");

            // Si tiene muchas materias (más de 5 en cada uno), asumimos que es completo
            if (primerCuatri.size() > 5 && segundoCuatri.size() > 5) {
                tieneCompleto = true;
            }
        }

        if (tieneCompleto) {
            // Si tiene 1° y 2° cuatrimestre completo
            if (correlativasPorCuatri.containsKey("3") || correlativasPorCuatri.containsKey("4") ||
                    correlativasPorCuatri.containsKey("5") || correlativasPorCuatri.containsKey("6")) {
                return "1° y 2° Cuatrimestre completo, más otros";
            } else {
                return "1° y 2° Cuatrimestre completo";
            }
        }

        // Si no es caso especial, mostrar normal
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, List<Materia>> entry : correlativasPorCuatri.entrySet()) {
            String cuatri = entry.getKey();
            List<Materia> materias = entry.getValue();

            if (sb.length() > 0) sb.append(" | ");
            sb.append(cuatri).append("°: ");

            // Si hay muchas materias en este cuatrimestre, resumir
            if (materias.size() > 4) {
                sb.append("Completo");
            } else {
                for (int i = 0; i < materias.size(); i++) {
                    if (i > 0) sb.append(", ");
                    sb.append(materias.get(i).getNombre());
                }
            }
        }
        return sb.toString();
    }

    private void mostrarMateria(Materia m) {
        String[] colores = {"\u001B[36m", "\u001B[32m", "\u001B[33m", "\u001B[35m", "\u001B[34m", "\u001B[31m"};
        String reset = "\u001B[0m";
        String color = colores[Integer.parseInt(m.getCuatrimestre()) - 1];

        System.out.println(color + "┌─────────────────────────────────────────────────────────┐" + reset);
        System.out.println(color + "│ " + reset + String.format("%-55s", "📌 " + m.getCodigoMateria()) + color + " │" + reset);
        System.out.println(color + "│ " + reset + String.format("%-55s", "📚 " + m.getNombre()) + color + " │" + reset);
        System.out.println(color + "│ " + reset + String.format("%-55s", "📅 " + m.getCuatrimestre() + "° Cuatrimestre") + color + " │" + reset);
        System.out.println(color + "│ " + reset + "🔗 Correlativas: " + formatearCorrelativas(materiaController.agruparCorrelativasPorCuatrimestre(m)) + color + " │" + reset);
        System.out.println(color + "│ " + reset + String.format("%-55s", "⚡ " + (m.isActivo() ? "✓ ACTIVA" : "✗ INACTIVA")) + color + " │" + reset);
        System.out.println(color + "└─────────────────────────────────────────────────────────┘" + reset);
    }

    private void listarMaterias() {
        System.out.println("\n📋 LISTADO COMPLETO DE MATERIAS");
        System.out.println("================================");
        List<Materia> materias = materiaController.findAll();

        if (materias.isEmpty()) {
            System.out.println("No hay materias registradas.");
            return;
        }

        int[] contador = {0, 0, 0, 0, 0, 0};
        for (Materia m : materias) {
            if (m.isActivo()) {
                mostrarMateria(m);
                contador[Integer.parseInt(m.getCuatrimestre()) - 1]++;
            }
        }

        System.out.println("\n📊 RESUMEN POR CUATRIMESTRE:");
        for (int i = 0; i < contador.length; i++) {
            if (contador[i] > 0) {
                System.out.println("  " + (i+1) + "° Cuatrimestre: " + contador[i] + " materias");
            }
        }
        System.out.println("  TOTAL: " + materias.stream().filter(Materia::isActivo).count() + " materias activas");
    }

    private void listarMateriasPorCuatrimestre() {
        System.out.println("\n📋 LISTAR POR CUATRIMESTRE");
        System.out.print("Ingrese el cuatrimestre (1-6): ");
        String cuatrimestre = scanner.nextLine().trim();

        if (!cuatrimestre.matches("[1-6]")) {
            System.out.println("✗ El cuatrimestre debe ser del 1 al 6");
            return;
        }

        List<Materia> materias = materiaController.findMateriasByCuatrimestre(cuatrimestre);

        if (materias.isEmpty()) {
            System.out.println("No hay materias activas para el " + cuatrimestre + "° cuatrimestre");
            return;
        }

        System.out.println("\n📚 MATERIAS DEL " + cuatrimestre + "° CUATRIMESTRE:");
        for (Materia m : materias) {
            System.out.println("  • " + m.getCodigoMateria() + " - " + m.getNombre());
        }
    }

    private void buscarMateria() {
        System.out.println("\n🔍 BUSCAR MATERIA");
        String codigo = leerNoVacio("Código: ").toUpperCase();

        Materia materia = materiaController.findByCode(codigo);
        if (materia == null) {
            System.out.println("✗ Materia no encontrada");
            return;
        }

        mostrarMateria(materia);
    }

    // ============================================================
    // MÉTODO CORREGIDO - Guardar antes de gestionar correlativas
    // ============================================================
    private void crearMateria() {
        System.out.println("\n📝 REGISTRAR MATERIA");
        System.out.println("Ejemplos: THT-101, MAT-101, PRO-201, BDD-301");

        String codigo = leerNoVacio("Código: ").toUpperCase();
        String nombre = leerNoVacio("Nombre: ");
        String cuatrimestre = leerNoVacio("Cuatrimestre (1-6): ");

        if (!cuatrimestre.matches("[1-6]")) {
            System.out.println("✗ El cuatrimestre debe ser del 1 al 6");
            return;
        }

        if (materiaController.findByCode(codigo) != null) {
            System.out.println("✗ Ya existe una materia con el código: " + codigo);
            return;
        }

        // Crear la materia
        Materia materia = new Materia(codigo, nombre, cuatrimestre);

        // PRIMERO: Guardar la materia en el repositorio
        materiaController.save(materia);
        System.out.println("✓ Materia guardada temporalmente");

        // SEGUNDO: Preguntar si quiere agregar correlativas
        System.out.println("\n📌 ¿Desea agregar correlativas?");
        if (confirmar("")) {
            // Recuperar la materia del repositorio para asegurar que es la misma instancia
            Materia materiaGuardada = materiaController.findByCode(codigo);
            if (materiaGuardada != null) {
                gestionarCorrelativas(materiaGuardada);
                // Actualizar los cambios en el repositorio
                materiaController.update(materiaGuardada);
                System.out.println("✓ Correlativas agregadas correctamente");
            }
        }

        System.out.println("✓ Materia registrada exitosamente: " + codigo);
    }

    private void actualizarMateria() {
        System.out.println("\n✏️ ACTUALIZAR MATERIA");

        String codigo = leerNoVacio("Código de la materia: ").toUpperCase();
        Materia materia = materiaController.findByCode(codigo);

        if (materia == null) {
            System.out.println("✗ Materia no encontrada");
            return;
        }

        mostrarMateria(materia);

        System.out.println("\nNuevos datos (Enter para mantener):");

        System.out.print("Nombre [" + materia.getNombre() + "]: ");
        String nombre = scanner.nextLine().trim();
        if (!nombre.isEmpty()) {
            materia.setNombre(nombre);
        }

        System.out.print("Cuatrimestre [" + materia.getCuatrimestre() + "]: ");
        String cuatrimestre = scanner.nextLine().trim();
        if (!cuatrimestre.isEmpty()) {
            if (!cuatrimestre.matches("[1-6]")) {
                System.out.println("✗ El cuatrimestre debe ser del 1 al 6");
                return;
            }
            materia.setCuatrimestre(cuatrimestre);
        }

        System.out.println("\n--- Gestión de Correlativas ---");
        gestionarCorrelativas(materia);

        if (confirmar("¿Guardar cambios?")) {
            materiaController.update(materia);
            System.out.println("✓ Materia actualizada");
        }
    }

    private void eliminarMateria() {
        System.out.println("\n🗑️ ELIMINAR MATERIA");

        String codigo = leerNoVacio("Código: ").toUpperCase();
        Materia materia = materiaController.findByCode(codigo);

        if (materia == null) {
            System.out.println("✗ Materia no encontrada");
            return;
        }

        mostrarMateria(materia);

        List<Materia> todas = materiaController.findAll();
        for (Materia m : todas) {
            if (m.getCorrelativas().contains(materia)) {
                System.out.println("✗ No se puede eliminar porque es correlativa de: " + m.getNombre());
                return;
            }
        }

        if (confirmar("⚠️ ¿Eliminar esta materia?")) {
            materiaController.delete(materia);
            System.out.println("✓ Materia eliminada");
        }
    }

    private void gestionarCorrelativas(Materia materia) {
        boolean gestionando = true;

        while (gestionando) {
            System.out.println("\n📌 GESTIÓN DE CORRELATIVAS");
            System.out.println("1. Agregar correlativa");
            System.out.println("2. Quitar correlativa");
            System.out.println("3. Ver correlativas");
            System.out.println("0. Terminar");
            System.out.print("Opción: ");

            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1" -> agregarCorrelativa(materia);
                case "2" -> quitarCorrelativa(materia);
                case "3" -> mostrarCorrelativas(materia);
                case "0" -> gestionando = false;
                default -> System.out.println("Opción inválida");
            }
        }
    }

    private void agregarCorrelativa(Materia materia) {
        List<Materia> disponibles = materiaController.findMateriasDisponiblesParaCorrelativa(materia);

        if (disponibles.isEmpty()) {
            System.out.println("No hay materias disponibles para agregar como correlativa");
            return;
        }

        System.out.println("\n📚 MATERIAS DISPONIBLES (solo de cuatrimestres anteriores):");
        String cuatriActual = "";
        for (Materia m : disponibles) {
            if (!m.getCuatrimestre().equals(cuatriActual)) {
                cuatriActual = m.getCuatrimestre();
                System.out.println("\n  " + cuatriActual + "° Cuatrimestre:");
            }
            System.out.println("    " + m.getCodigoMateria() + " - " + m.getNombre());
        }

        System.out.print("\nCódigo a agregar: ");
        String codigo = scanner.nextLine().trim().toUpperCase();

        String resultado = materiaController.agregarCorrelativa(materia.getCodigoMateria(), codigo);
        if (resultado.equals("CORRELATIVA_AGREGADA")) {
            System.out.println("✓ Correlativa agregada");
        } else {
            System.out.println("✗ " + resultado);
        }
    }

    private void quitarCorrelativa(Materia materia) {
        if (materia.getCorrelativas().isEmpty()) {
            System.out.println("No hay correlativas para quitar");
            return;
        }

        System.out.println("\n🔗 CORRELATIVAS ACTUALES:");
        Map<String, List<Materia>> correlativasPorCuatri = materiaController.agruparCorrelativasPorCuatrimestre(materia);

        for (Map.Entry<String, List<Materia>> entry : correlativasPorCuatri.entrySet()) {
            System.out.println("  " + entry.getKey() + "° Cuatrimestre:");
            for (Materia m : entry.getValue()) {
                System.out.println("    " + m.getCodigoMateria() + " - " + m.getNombre());
            }
        }

        System.out.print("\nCódigo a quitar: ");
        String codigo = scanner.nextLine().trim().toUpperCase();

        String resultado = materiaController.quitarCorrelativa(materia.getCodigoMateria(), codigo);
        if (resultado.equals("CORRELATIVA_QUITADA")) {
            System.out.println("✓ Correlativa quitada");
        } else {
            System.out.println("✗ " + resultado);
        }
    }

    private void mostrarCorrelativas(Materia materia) {
        System.out.println("\n🔗 CORRELATIVAS:");
        Map<String, List<Materia>> correlativasPorCuatri = materiaController.agruparCorrelativasPorCuatrimestre(materia);

        if (correlativasPorCuatri.isEmpty()) {
            System.out.println("  NO POSEE");
        } else {
            for (Map.Entry<String, List<Materia>> entry : correlativasPorCuatri.entrySet()) {
                System.out.println("  " + entry.getKey() + "° Cuatrimestre:");
                for (Materia m : entry.getValue()) {
                    System.out.println("    • " + m.getNombre() + " (" + m.getCodigoMateria() + ")");
                }
            }
        }
    }
}