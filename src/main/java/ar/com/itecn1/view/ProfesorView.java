package ar.com.itecn1.view;

import ar.com.itecn1.controller.ProfesorController;
import ar.com.itecn1.model.Profesor;
import ar.com.itecn1.view.utils.ValidacionesView;

import java.util.List;
import java.util.Scanner;

public class ProfesorView {
    private final ProfesorController profesorController;
    private final Scanner scanner;

    public ProfesorView(ProfesorController profesorController, Scanner scanner) {
        this.profesorController = profesorController;
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
            pausa(); // Limpia el buffer después de nextInt()

            switch (opcion) {
                case 1 -> listarProfesores();
                case 2 -> buscarProfesor();
                case 3 -> crearProfesor();
                case 4 -> actualizarProfesor();
                case 5 -> eliminarProfesor();
                case 0 -> continuar = false;
                default -> System.out.println("Ingrese una opción válida");
            }
        }
    }

    // ----------------------------------------------------------------
    // Métodos auxiliares
    // ----------------------------------------------------------------

    private void mostrarMenu() {
        System.out.println("\n┌────────────────────────────────────┐");
        System.out.println("│      GESTIÓN DE PROFESORES          │");
        System.out.println("├─────────────────────────────────────┤");
        System.out.println("│  1. Listar profesores               │");
        System.out.println("│  2. Buscar profesor por DNI         │");
        System.out.println("│  3. Registrar profesor              │");
        System.out.println("│  4. Actualizar datos de un profesor │");
        System.out.println("│  5. Dar de baja un profesor         │");
        System.out.println("│  0. Volver atrás                    │");
        System.out.println("└─────────────────────────────────────┘");
        System.out.print("  Opción: ");
    }

    private void mostrarProfesor(Profesor profesor) {
        System.out.println(
                "DNI: " + profesor.getDni() +
                        " | NOMBRE: " + profesor.getNombre() +
                        " | APELLIDO: " + profesor.getApellido() +
                        " | EMAIL: " + profesor.getEmail() +
                        " | TELÉFONO: " + profesor.getTelefono() +
                        " | ESTADO: " + (profesor.isActivo() ? "ACTIVO" : "INACTIVO")
        );
    }

    private int confirmarAccion() {
        int opcion;
        do {
            System.out.println("\n┌────────────┐");
            System.out.println("│ Confirmar  │");
            System.out.println("├────────────┤");
            System.out.println("│ 1. Sí      │");
            System.out.println("│ 2. No      │");
            System.out.println("│ 0. Cancelar│");
            System.out.println("└────────────┘");
            System.out.print("  Opción: ");

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Consumir el Enter
            } else {
                System.out.println("Opción no válida. Intente de nuevo.");
                scanner.next(); // Consumir entrada no numérica
                scanner.nextLine(); // Limpiar
                opcion = -1; // Valor para repetir el bucle
            }
        } while (opcion != 1 && opcion != 2 && opcion != 0);
        return opcion;
    }

    private void pausa() {
        scanner.nextLine(); // Limpia buffer tras nextInt()
    }

    // ----------------------------------------------------------------
    // Funcionalidades
    // ----------------------------------------------------------------

    private void listarProfesores() {
        System.out.println("----------Listado de Profesores----------");
        List<Profesor> profesores = profesorController.findAll();

        if (profesores.isEmpty()) {
            System.out.println("No hay profesores registrados.");
            System.out.println("Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        profesores.forEach(this::mostrarProfesor);
        System.out.println("Presione Enter para continuar...");
        scanner.nextLine();
    }

    private void buscarProfesor() {
        System.out.println("----------Buscar Profesor----------");
        System.out.print("Ingrese el DNI (0 para cancelar): ");
        String dni = scanner.nextLine().trim();

        if (dni.equals("0")) {
            System.out.println("Búsqueda cancelada.");
            System.out.println("Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        if (!ValidacionesView.validarDni(dni)) {
            System.out.println("Error: El DNI debe tener 8 dígitos numéricos.");
            System.out.println("Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        Profesor profesor = profesorController.findByDni(dni);

        if (profesor == null) {
            System.out.println("Profesor no encontrado.");
        } else {
            mostrarProfesor(profesor);
        }
        System.out.println("Presione Enter para continuar...");
        scanner.nextLine();
    }

    private void crearProfesor() {
        System.out.println("----------Registrar Profesor----------");
        System.out.println("(Ingrese 0 en cualquier momento para cancelar)");

        // Solicitar DNI con validación y verificar duplicado
        String dni;
        do {
            dni = ValidacionesView.solicitarDato(scanner, "DNI: ", "dni");
            if (dni.equals("0")) {
                System.out.println("Registro cancelado.");
                System.out.println("Presione Enter para continuar...");
                scanner.nextLine();
                return;
            }
            if (profesorController.findByDni(dni) != null) {
                System.out.println("Error: Ese DNI ya está registrado. Intente con otro.");
                continue;
            }
            break;
        } while (true);

        // Solicitar nombre
        String nombre = ValidacionesView.solicitarDato(scanner, "Nombre: ", "nombre");
        if (nombre.equals("0")) {
            System.out.println("Registro cancelado.");
            System.out.println("Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        // Solicitar apellido
        String apellido = ValidacionesView.solicitarDato(scanner, "Apellido: ", "apellido");
        if (apellido.equals("0")) {
            System.out.println("Registro cancelado.");
            System.out.println("Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        // Solicitar teléfono
        String telefono = ValidacionesView.solicitarDato(scanner, "Teléfono: ", "telefono");
        if (telefono.equals("0")) {
            System.out.println("Registro cancelado.");
            System.out.println("Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        // Solicitar email
        String email = ValidacionesView.solicitarDato(scanner, "Email: ", "email");
        if (email.equals("0")) {
            System.out.println("Registro cancelado.");
            System.out.println("Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        Profesor profesor = new Profesor(dni, nombre, apellido, telefono, email);

        System.out.println("\nVista previa:");
        mostrarProfesor(profesor);

        int confirmacion = confirmarAccion();
        if (confirmacion == 1) {
            profesorController.crearProfesor(profesor);
            System.out.println("¡Profesor registrado!");
        } else if (confirmacion == 2) {
            System.out.println("No se registró el profesor.");
        } else {
            System.out.println("Registro cancelado.");
        }
        System.out.println("Presione Enter para continuar...");
        scanner.nextLine();
    }

    private void actualizarProfesor() {
        System.out.println("----------Actualizar Profesor----------");
        System.out.println("(Ingrese 0 para cancelar)");

        System.out.print("Ingrese el DNI del profesor: ");
        String dni = scanner.nextLine().trim();

        if (dni.equals("0")) {
            System.out.println("Actualización cancelada.");
            System.out.println("Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        if (!ValidacionesView.validarDni(dni)) {
            System.out.println("Error: El DNI debe tener 8 dígitos numéricos.");
            System.out.println("Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        Profesor profesor = profesorController.findByDni(dni);
        if (profesor == null) {
            System.out.println("Profesor no encontrado.");
            System.out.println("Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        System.out.println("Profesor encontrado:");
        mostrarProfesor(profesor);

        System.out.println("\nNUEVOS DATOS (dejar en blanco para no cambiar / 0 para cancelar):");

        // Nuevo DNI
        String nuevoDni;
        do {
            nuevoDni = ValidacionesView.solicitarDatoActualizacion(scanner, "Nuevo DNI", profesor.getDni(), "dni");
            if (nuevoDni.equals("0")) {
                System.out.println("Actualización cancelada.");
                System.out.println("Presione Enter para continuar...");
                scanner.nextLine();
                return;
            }
            if (!nuevoDni.isEmpty() && profesorController.findByDni(nuevoDni) != null && !nuevoDni.equals(dni)) {
                System.out.println("Error: Ese DNI ya está registrado por otro profesor.");
                continue;
            }
            break;
        } while (true);

        // Nuevo nombre
        String nuevoNombre = ValidacionesView.solicitarDatoActualizacion(scanner, "Nuevo nombre", profesor.getNombre(), "nombre");
        if (nuevoNombre.equals("0")) {
            System.out.println("Actualización cancelada.");
            System.out.println("Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        // Nuevo apellido
        String nuevoApellido = ValidacionesView.solicitarDatoActualizacion(scanner, "Nuevo apellido", profesor.getApellido(), "apellido");
        if (nuevoApellido.equals("0")) {
            System.out.println("Actualización cancelada.");
            System.out.println("Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        // Nuevo teléfono
        String nuevoTelefono = ValidacionesView.solicitarDatoActualizacion(scanner, "Nuevo teléfono", profesor.getTelefono(), "telefono");
        if (nuevoTelefono.equals("0")) {
            System.out.println("Actualización cancelada.");
            System.out.println("Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        // Nuevo email
        String nuevoEmail = ValidacionesView.solicitarDatoActualizacion(scanner, "Nuevo email", profesor.getEmail(), "email");
        if (nuevoEmail.equals("0")) {
            System.out.println("Actualización cancelada.");
            System.out.println("Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        // Vista previa
        System.out.println("\nVista previa:");
        System.out.println("DNI: " + (nuevoDni.isEmpty() ? profesor.getDni() : nuevoDni));
        System.out.println("Nombre: " + (nuevoNombre.isEmpty() ? profesor.getNombre() : nuevoNombre));
        System.out.println("Apellido: " + (nuevoApellido.isEmpty() ? profesor.getApellido() : nuevoApellido));
        System.out.println("Teléfono: " + (nuevoTelefono.isEmpty() ? profesor.getTelefono() : nuevoTelefono));
        System.out.println("Email: " + (nuevoEmail.isEmpty() ? profesor.getEmail() : nuevoEmail));

        int confirmacion = confirmarAccion();
        if (confirmacion == 1) {
            if (!nuevoDni.isEmpty()) profesor.setDni(nuevoDni);
            if (!nuevoNombre.isEmpty()) profesor.setNombre(nuevoNombre);
            if (!nuevoApellido.isEmpty()) profesor.setApellido(nuevoApellido);
            if (!nuevoTelefono.isEmpty()) profesor.setTelefono(nuevoTelefono);
            if (!nuevoEmail.isEmpty()) profesor.setEmail(nuevoEmail);

            profesorController.editarProfesor(profesor);
            System.out.println("¡Profesor modificado!");
        } else if (confirmacion == 2) {
            System.out.println("No se realizaron cambios.");
        } else {
            System.out.println("Actualización cancelada.");
        }
        System.out.println("Presione Enter para continuar...");
        scanner.nextLine();
    }

    private void eliminarProfesor() {
        System.out.println("----------Eliminar Profesor----------");
        System.out.println("(Ingrese 0 para cancelar)");

        System.out.print("Ingrese el DNI del profesor: ");
        String dni = scanner.nextLine().trim();

        if (dni.equals("0")) {
            System.out.println("Eliminación cancelada.");
            System.out.println("Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        if (!ValidacionesView.validarDni(dni)) {
            System.out.println("Error: El DNI debe tener 8 dígitos numéricos.");
            System.out.println("Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        Profesor profesor = profesorController.findByDni(dni);
        if (profesor == null) {
            System.out.println("Profesor no encontrado.");
            System.out.println("Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        System.out.println("Profesor encontrado:");
        mostrarProfesor(profesor);

        int confirmacion = confirmarAccion();
        if (confirmacion == 1) {
            profesorController.eliminarProfesor(profesor);
            System.out.println("¡Profesor eliminado!");
        } else if (confirmacion == 2) {
            System.out.println("No se eliminó el profesor.");
        } else {
            System.out.println("Eliminación cancelada.");
        }
        System.out.println("Presione Enter para continuar...");
        scanner.nextLine();
    }
}