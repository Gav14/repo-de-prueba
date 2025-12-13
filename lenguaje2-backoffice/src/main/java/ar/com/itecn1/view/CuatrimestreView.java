package ar.com.itecn1.view;

import ar.com.itecn1.controller.CuatrimestreController;
import ar.com.itecn1.model.Cuatrimestre;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class CuatrimestreView {

    private final CuatrimestreController cuatrimestreController;
    private final Scanner scanner;

    public CuatrimestreView(CuatrimestreController cuatrimestreController, Scanner scanner) {
        this.cuatrimestreController = cuatrimestreController;
        this.scanner = scanner;
    }

    public void iniciar() {
        boolean continuar = true;

        while (continuar) {
            mostrarMenu();
            int opcion = leerEntero();

            switch (opcion) {
                case 1 -> listarCuatrimestres();
                case 2 -> buscarCuatrimestre();
                case 3 -> crearCuatrimestre();
                case 4 -> actualizarCuatrimestre();
                case 5 -> eliminarCuatrimestre();
                case 6 -> continuar = false;
                default -> System.out.println("Ingrese una opción válida.");
            }
        }
    }

    private void mostrarMenu() {
        System.out.println("\nGESTIÓN DE CUATRIMESTRES");
        System.out.println("1. Listar cuatrimestres");
        System.out.println("2. Buscar cuatrimestre por número");
        System.out.println("3. Registrar cuatrimestre");
        System.out.println("4. Actualizar datos de un cuatrimestre");
        System.out.println("5. Eliminar cuatrimestre");
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

    private void mostrarCuatrimestre(Cuatrimestre c) {
        System.out.println(
            "NÚMERO: " + c.getNumero() +
            " | AÑO: " + c.getAnio() +
            " | INICIO: " + c.getInicio() +
            " | FIN: " + c.getFin() +
            " | ESTADO: " + (c.isActivo() ? "ACTIVO" : "INACTIVO")
        );
    }

    private void listarCuatrimestres() {
        System.out.println("---------- Listado de cuatrimestres ----------");

        List<Cuatrimestre> lista = cuatrimestreController.findAll();

        if (lista.isEmpty()) {
            System.out.println("No hay cuatrimestres registrados.");
            return;
        }

        lista.forEach(this::mostrarCuatrimestre);
    }

    private void buscarCuatrimestre() {
        System.out.println("---------- Buscar cuatrimestre ----------");
        System.out.println("Ingrese el número del cuatrimestre:");
        String numero = scanner.nextLine();

        Cuatrimestre c = cuatrimestreController.findByNumber(numero);

        if (c == null) {
            System.out.println("Cuatrimestre no encontrado.");
            return;
        }

        mostrarCuatrimestre(c);
    }

    private void crearCuatrimestre() {
        System.out.println("---------- Registrar cuatrimestre ----------");

        System.out.println("Número:");
        String numero = scanner.nextLine();

        if (cuatrimestreController.findByNumber(numero) != null) {
            System.out.println("Ya existe un cuatrimestre con ese número.");
            return;
        }

        System.out.println("Año:");
        String anio = scanner.nextLine();

        System.out.println("Fecha de inicio (YYYY-MM-DD):");
        LocalDate inicio = LocalDate.parse(scanner.nextLine());

        System.out.println("Fecha de fin (YYYY-MM-DD):");
        LocalDate fin = LocalDate.parse(scanner.nextLine());

        Cuatrimestre nuevo = new Cuatrimestre(numero, inicio, fin, anio);

        System.out.println("\nVista previa:");
        mostrarCuatrimestre(nuevo);

        if (confirmarAccion("¿Confirmar registro?")) {
            cuatrimestreController.createCuatrimestre(nuevo);
            System.out.println("Cuatrimestre registrado.");
        }
    }

    private void actualizarCuatrimestre() {
        System.out.println("---------- Actualizar cuatrimestre ----------");

        System.out.println("Ingrese el número del cuatrimestre:");
        String numero = scanner.nextLine();

        Cuatrimestre c = cuatrimestreController.findByNumber(numero);

        if (c == null) {
            System.out.println("Cuatrimestre no encontrado.");
            return;
        }

        System.out.println("Cuatrimestre encontrado:");
        mostrarCuatrimestre(c);
        System.out.println("\nNuevos datos (dejar vacío para no modificar):");

        System.out.println("Año (" + c.getAnio() + "):");
        String nuevoAnio = scanner.nextLine();
        if (!nuevoAnio.isEmpty()) c.setAnio(nuevoAnio);

        System.out.println("Fecha de inicio (" + c.getInicio() + ") (YYYY-MM-DD):");
        String nuevoInicio = scanner.nextLine();
        if (!nuevoInicio.isEmpty()) c.setInicio(LocalDate.parse(nuevoInicio));

        System.out.println("Fecha de fin (" + c.getFin() + ") (YYYY-MM-DD):");
        String nuevoFin = scanner.nextLine();
        if (!nuevoFin.isEmpty()) c.setFin(LocalDate.parse(nuevoFin));

        System.out.println("\nVista previa:");
        mostrarCuatrimestre(c);

        if (confirmarAccion("¿Guardar cambios?")) {
            cuatrimestreController.updateCuatrimestre(c);
            System.out.println("Cuatrimestre actualizado.");
        }
    }

    private void eliminarCuatrimestre() {
        System.out.println("---------- Eliminar cuatrimestre ----------");

        System.out.println("Ingrese el número del cuatrimestre:");
        String numero = scanner.nextLine();

        Cuatrimestre c = cuatrimestreController.findByNumber(numero);

        if (c == null) {
            System.out.println("Cuatrimestre no encontrado.");
            return;
        }

        mostrarCuatrimestre(c);

        if (confirmarAccion("\n¿Confirmar eliminación?")) {
            cuatrimestreController.deleteCuatrimestre(c);
            System.out.println("Cuatrimestre eliminado.");
        }
    }
}
