package ar.com.itecn1.view;

import ar.com.itecn1.controller.ModuloController;
import ar.com.itecn1.model.Modulo;

import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class ModuloView {

    private final ModuloController moduloController;
    private final Scanner scanner;

    public ModuloView(ModuloController moduloController, Scanner scanner) {
        this.moduloController = moduloController;
        this.scanner = scanner;
    }

    public void iniciar() {
        boolean continuar = true;

        while (continuar) {
            mostrarMenu();
            int opcion = leerEntero();

            switch (opcion) {
                case 1 -> listarModulos();
                case 2 -> buscarModulo();
                case 3 -> crearModulo();
                case 4 -> actualizarModulo();
                case 5 -> eliminarModulo();
                case 6 -> continuar = false;
                default -> System.out.println("Ingrese una opción válida.");
            }
        }
    }

    // ============================================================
    // MENÚ
    // ============================================================
    private void mostrarMenu() {
        System.out.println("\nGESTIÓN DE MÓDULOS");
        System.out.println("1. Listar módulos");
        System.out.println("2. Buscar módulo por código");
        System.out.println("3. Registrar módulo");
        System.out.println("4. Actualizar módulo");
        System.out.println("5. Dar de baja módulo");
        System.out.println("6. Volver atrás");
    }

    // ============================================================
    // MÉTODOS UTILITARIOS
    // ============================================================
    private int leerEntero() {
        while (!scanner.hasNextInt()) {
            scanner.nextLine();
            System.out.println("Debe ingresar un número.");
        }
        int num = scanner.nextInt();
        scanner.nextLine(); // limpiar
        return num;
    }

    private String leerOpcional(String mensaje, String valorActual) {
        System.out.print(mensaje + " (actual: " + valorActual + ", ENTER para mantener): ");
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? valorActual : input;
    }

    private LocalTime leerHoraOpcional(String mensaje, LocalTime valorActual) {
        System.out.print(mensaje + " (actual: " + valorActual + ", ENTER para mantener): ");
        String input = scanner.nextLine().trim();

        if (input.isEmpty()) return valorActual; // no modificar

        while (true) {
            try {
                return LocalTime.parse(input); // intenta parsear
            } catch (Exception e) {
                System.out.print("Formato inválido. Use HH:mm (ej: 08:30). Intente nuevamente: ");
                input = scanner.nextLine().trim();
                if (input.isEmpty()) return valorActual; // permite volver atrás
            }
        }
    }

    private LocalTime leerHoraObligatoria(String mensaje) {
        System.out.print(mensaje + " (formato HH:mm): ");
        String input = scanner.nextLine().trim();

        while (true) {
            try {
                return LocalTime.parse(input);
            } catch (Exception e) {
                System.out.print("Formato inválido. Use HH:mm (ej: 08:30). Intente nuevamente: ");
                input = scanner.nextLine().trim();
            }
        }
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

    private void mostrarModulo(Modulo m) {
        System.out.println(
            "CÓDIGO: " + m.getCodigo() +
            " | INICIO: " + m.getInicio() +
            " | FIN: " + m.getFin() +
            " | ESTADO: " + (m.isActivo() ? "ACTIVO" : "INACTIVO")
        );
    }

    // ============================================================
    // CRUD
    // ============================================================
    private void listarModulos() {
        System.out.println("---------- Listado de módulos ----------");

        List<Modulo> lista = moduloController.findAll();

        if (lista.isEmpty()) {
            System.out.println("No hay módulos registrados.");
            return;
        }

        lista.forEach(this::mostrarModulo);
    }

    private void buscarModulo() {
        System.out.println("---------- Buscar módulo ----------");

        System.out.println("Ingrese el código del módulo:");
        String codigo = scanner.nextLine();

        Modulo m = moduloController.findByCode(codigo);

        if (m == null) {
            System.out.println("Módulo no encontrado.");
            return;
        }

        mostrarModulo(m);
    }

    private void crearModulo() {
        System.out.println("---------- Registrar módulo ----------");

        System.out.println("Código del módulo:");
        String codigo = scanner.nextLine();

        if (moduloController.findByCode(codigo) != null) {
            System.out.println("Ya existe un módulo con ese código.");
            return;
        }

        LocalTime inicio = leerHoraObligatoria("Hora de inicio");
        LocalTime fin = leerHoraObligatoria("Hora de fin");

        Modulo nuevo = new Modulo(codigo, inicio, fin);

        System.out.println("\nVista previa:");
        mostrarModulo(nuevo);

        if (confirmarAccion("¿Confirmar registro?")) {
            moduloController.createModulo(nuevo);
            System.out.println("Módulo registrado.");
        }
    }

    private void actualizarModulo() {
        System.out.println("---------- Actualizar módulo ----------");

        System.out.println("Ingrese el código del módulo:");
        String codigo = scanner.nextLine();

        Modulo m = moduloController.findByCode(codigo);

        if (m == null) {
            System.out.println("Módulo no encontrado o inactivo.");
            return;
        }

        System.out.println("Módulo encontrado:");
        mostrarModulo(m);

        System.out.println("\nIngrese los nuevos valores (ENTER para mantener el actual):");

        // Hora de inicio opcional
        LocalTime nuevoInicio = leerHoraOpcional("Nueva hora de inicio (HH:mm)", m.getInicio());

        // Hora de fin opcional
        LocalTime nuevoFin = leerHoraOpcional("Nueva hora de fin (HH:mm)", m.getFin());

        // Aplicar cambios
        m.setInicio(nuevoInicio);
        m.setFin(nuevoFin);

        System.out.println("\nVista previa:");
        mostrarModulo(m);

        if (confirmarAccion("¿Guardar cambios?")) {
            moduloController.updateModulo(m);
            System.out.println("Módulo actualizado.");
        }
    }

    private void eliminarModulo() {
        System.out.println("---------- Dar de baja módulo ----------");

        System.out.println("Ingrese el código del módulo:");
        String codigo = scanner.nextLine();

        Modulo m = moduloController.findByCode(codigo);

        if (m == null) {
            System.out.println("Módulo no encontrado.");
            return;
        }

        mostrarModulo(m);

        if (confirmarAccion("\n¿Confirmar baja lógica?")) {
            moduloController.deleteModulo(m);
            System.out.println("Módulo dado de baja.");
        }
    }
}
