package ar.com.itecn1.view;

import ar.com.itecn1.controller.AlumnoController;
import ar.com.itecn1.model.Alumno;

import java.util.List;
import java.util.Scanner;

public class AlumnoView {
    private final AlumnoController alumnoController;
    private final Scanner scanner;

    public AlumnoView(AlumnoController alumnoController, Scanner scanner) {
        this.alumnoController = alumnoController;
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
                case 1 -> listarAlumnos();
                case 2 -> buscarAlumno();
                case 3 -> crearAlumno();
                case 4 -> actualizarAlumno();
                case 5 -> eliminarAlumno();
                case 0 -> continuar = false;
                default -> System.out.println("Ingrese una opción válida");
            }
        }
    }

    // -----------------------------
    // Métodos auxiliares
    // -----------------------------

    private void mostrarMenu() {
        System.out.println("┌────────────────────────────────────┐");
        System.out.println("│       GESTIÓN DE ALUMNOS           │");
        System.out.println("├────────────────────────────────────┤");
        System.out.println("│  1. Listar alumnos                 │");
        System.out.println("│  2. Buscar alumno por DNI          │");
        System.out.println("│  3. Registrar alumno               │");
        System.out.println("│  4. Actualizar datos de un alumno  │");
        System.out.println("│  5. Dar de baja un alumno          │");
        System.out.println("│  0. Volver atrás                   │");
        System.out.println("└────────────────────────────────────┘");
        System.out.print("  Opción: ");
    }

    private void mostrarAlumno(Alumno alumno) {
        System.out.println(
                "DNI: " + alumno.getDni() +
                        " | NOMBRE: " + alumno.getNombre() +
                        " | APELLIDO: " + alumno.getApellido() +
                        " | EMAIL: " + alumno.getEmail() +
                        " | TELÉFONO: " + alumno.getTelefono() +
                        " | ESTADO: " + (alumno.isActivo() ? "ACTIVO" : "INACTIVO")
        );
    }

    private int confirmarAccion() {
        int opcion = 0;
        while (opcion != 1 && opcion != 2 && opcion != 0) {
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
            } else {
                scanner.next();
            }
        }
        pausa();
        return opcion;
    }

    private void pausa() {
        scanner.nextLine(); // limpia buffer luego de nextInt()
    }

    // -----------------------------
    // Funcionalidades
    // -----------------------------

    private void listarAlumnos() {
        System.out.println("----------Listado de Alumnos----------");
        List<Alumno> alumnos = alumnoController.findAll();

        if (alumnos.isEmpty()) {
            System.out.println("No hay alumnos registrados.");
            return;
        }

        alumnos.forEach(this::mostrarAlumno);
        System.out.println("Presione Enter para continuar...");
        scanner.nextLine();
    }

    private void buscarAlumno() {
        System.out.println("----------Buscar alumno----------");
        System.out.print("Ingrese el DNI (0 para cancelar): ");
        String dni = scanner.nextLine().trim();

        if (dni.equals("0")) {
            System.out.println("Búsqueda cancelada.");
            System.out.println("Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        if (!alumnoController.validarDni(dni)) {
            System.out.println("Error: El DNI debe tener 8 dígitos numéricos.");
            System.out.println("Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        Alumno alumno = alumnoController.findByDni(dni);

        if (alumno == null) {
            System.out.println("Alumno no encontrado.");
        } else {
            mostrarAlumno(alumno);
        }
        System.out.println("Presione Enter para continuar...");
        scanner.nextLine();
    }

    private String solicitarDato(String mensaje, String tipo) {
        String dato;
        boolean valido;

        do {
            System.out.print(mensaje);
            dato = scanner.nextLine().trim();

            if (dato.equals("0")) {
                return "0";
            }

            valido = true;

            switch (tipo) {
                case "nombre":
                    if (!alumnoController.validarNombre(dato)) {
                        System.out.println("Error: El nombre no puede contener números y no debe estar vacío.");
                        valido = false;
                    }
                    break;
                case "apellido":
                    if (!alumnoController.validarApellido(dato)) {
                        System.out.println("Error: El apellido no puede contener números y no debe estar vacío.");
                        valido = false;
                    }
                    break;
                case "email":
                    if (!alumnoController.validarEmail(dato)) {
                        System.out.println("Error: El email debe tener un formato válido (ej: usuario@dominio.com) y no debe estar vacío.");
                        valido = false;
                    }
                    break;
                case "telefono":
                    if (!alumnoController.validarTelefono(dato)) {
                        System.out.println("Error: El teléfono debe contener solo números y no debe estar vacío.");
                        valido = false;
                    }
                    break;
            }
        } while (!valido);

        return dato;
    }

    private void crearAlumno() {
        System.out.println("----------Registrar alumno----------");
        System.out.println("(Ingrese 0 en cualquier momento para cancelar)");

        // Solicitar DNI con validación
        String dni;
        do {
            System.out.print("DNI: ");
            dni = scanner.nextLine().trim();

            if (dni.equals("0")) {
                System.out.println("Registro cancelado.");
                System.out.println("Presione Enter para continuar...");
                scanner.nextLine();
                return;
            }

            if (!alumnoController.validarDni(dni)) {
                System.out.println("Error: El DNI debe tener 8 dígitos numéricos.");
                continue;
            }

            if (alumnoController.findByDni(dni) != null) {
                System.out.println("Error: Ese DNI ya está registrado.");
                return; // Salimos del método si el DNI ya existe
            }
            break;
        } while (true);

        // Solicitar nombre con validación (sin números)
        String nombre = solicitarDato("Nombre: ", "nombre");
        if (nombre.equals("0")) {
            System.out.println("Registro cancelado.");
            System.out.println("Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        // Solicitar apellido con validación (sin números)
        String apellido = solicitarDato("Apellido: ", "apellido");
        if (apellido.equals("0")) {
            System.out.println("Registro cancelado.");
            System.out.println("Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        // Solicitar teléfono con validación (solo números)
        String telefono = solicitarDato("Teléfono: ", "telefono");
        if (telefono.equals("0")) {
            System.out.println("Registro cancelado.");
            System.out.println("Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        // Solicitar email con validación
        String email = solicitarDato("Email: ", "email");
        if (email.equals("0")) {
            System.out.println("Registro cancelado.");
            System.out.println("Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        Alumno alumno = new Alumno(dni, apellido, nombre, telefono, email);

        System.out.println("\nVista previa:");
        mostrarAlumno(alumno);

        int confirmacion = confirmarAccion();
        if (confirmacion == 1) {
            alumnoController.createAlumno(alumno);
            System.out.println("Alumno registrado!");
        } else if (confirmacion == 0) {
            System.out.println("Registro cancelado.");
        }
        System.out.println("Presione Enter para continuar...");
        scanner.nextLine();
    }

    private void actualizarAlumno() {
        System.out.println("----------Actualizar alumno----------");
        System.out.println("(Ingrese 0 para cancelar)");

        System.out.print("Ingrese el DNI: ");
        String dni = scanner.nextLine().trim();

        if (dni.equals("0")) {
            System.out.println("Actualización cancelada.");
            System.out.println("Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        if (!alumnoController.validarDni(dni)) {
            System.out.println("Error: El DNI debe tener 8 dígitos numéricos.");
            System.out.println("Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        Alumno alumno = alumnoController.findByDni(dni);

        if (alumno == null) {
            System.out.println("Alumno no encontrado.");
            System.out.println("Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        System.out.println("Alumno encontrado:");
        mostrarAlumno(alumno);

        System.out.println("\nNUEVOS DATOS (dejar en blanco para no cambiar / 0 para cancelar):");

        // Nuevo DNI (con validación)
        String nuevoDni;
        do {
            System.out.print("Nuevo DNI (" + dni + "): ");
            nuevoDni = scanner.nextLine().trim();

            if (nuevoDni.equals("0")) {
                System.out.println("Actualización cancelada.");
                System.out.println("Presione Enter para continuar...");
                scanner.nextLine();
                return;
            }

            if (!nuevoDni.isEmpty() && !alumnoController.validarDni(nuevoDni)) {
                System.out.println("Error: El DNI debe tener 8 dígitos numéricos.");
                continue;
            }

            if (!nuevoDni.isEmpty() && alumnoController.findByDni(nuevoDni) != null && !nuevoDni.equals(dni)) {
                System.out.println("Error: Ese DNI ya está registrado por otro alumno.");
                continue;
            }
            break;
        } while (true);

        // Nuevo nombre (con validación)
        String nuevoNombre;
        do {
            System.out.print("Nuevo nombre (" + alumno.getNombre() + "): ");
            nuevoNombre = scanner.nextLine().trim();

            if (nuevoNombre.equals("0")) {
                System.out.println("Actualización cancelada.");
                System.out.println("Presione Enter para continuar...");
                scanner.nextLine();
                return;
            }

            if (!nuevoNombre.isEmpty() && !alumnoController.validarNombre(nuevoNombre)) {
                System.out.println("Error: El nombre no puede contener números.");
                continue;
            }
            break;
        } while (true);

        // Nuevo apellido (con validación)
        String nuevoApellido;
        do {
            System.out.print("Nuevo apellido (" + alumno.getApellido() + "): ");
            nuevoApellido = scanner.nextLine().trim();

            if (nuevoApellido.equals("0")) {
                System.out.println("Actualización cancelada.");
                System.out.println("Presione Enter para continuar...");
                scanner.nextLine();
                return;
            }

            if (!nuevoApellido.isEmpty() && !alumnoController.validarApellido(nuevoApellido)) {
                System.out.println("Error: El apellido no puede contener números.");
                continue;
            }
            break;
        } while (true);

        // Nuevo teléfono (con validación - solo números)
        String nuevoTelefono;
        do {
            System.out.print("Nuevo teléfono (" + alumno.getTelefono() + "): ");
            nuevoTelefono = scanner.nextLine().trim();

            if (nuevoTelefono.equals("0")) {
                System.out.println("Actualización cancelada.");
                System.out.println("Presione Enter para continuar...");
                scanner.nextLine();
                return;
            }

            if (!nuevoTelefono.isEmpty() && !alumnoController.validarTelefono(nuevoTelefono)) {
                System.out.println("Error: El teléfono debe contener solo números.");
                continue;
            }
            break;
        } while (true);

        // Nuevo email (con validación)
        String nuevoEmail;
        do {
            System.out.print("Nuevo email (" + alumno.getEmail() + "): ");
            nuevoEmail = scanner.nextLine().trim();

            if (nuevoEmail.equals("0")) {
                System.out.println("Actualización cancelada.");
                System.out.println("Presione Enter para continuar...");
                scanner.nextLine();
                return;
            }

            if (!nuevoEmail.isEmpty() && !alumnoController.validarEmail(nuevoEmail)) {
                System.out.println("Error: El email debe tener un formato válido.");
                continue;
            }
            break;
        } while (true);

        // Vista previa
        System.out.println("\nVista previa:");
        System.out.println("DNI: " + (nuevoDni.isEmpty() ? alumno.getDni() : nuevoDni));
        System.out.println("Nombre: " + (nuevoNombre.isEmpty() ? alumno.getNombre() : nuevoNombre));
        System.out.println("Apellido: " + (nuevoApellido.isEmpty() ? alumno.getApellido() : nuevoApellido));
        System.out.println("Teléfono: " + (nuevoTelefono.isEmpty() ? alumno.getTelefono() : nuevoTelefono));
        System.out.println("Email: " + (nuevoEmail.isEmpty() ? alumno.getEmail() : nuevoEmail));

        int confirmacion = confirmarAccion();
        if (confirmacion == 1) {
            if (!nuevoDni.isEmpty()) alumno.setDni(nuevoDni);
            if (!nuevoNombre.isEmpty()) alumno.setNombre(nuevoNombre);
            if (!nuevoApellido.isEmpty()) alumno.setApellido(nuevoApellido);
            if (!nuevoTelefono.isEmpty()) alumno.setTelefono(nuevoTelefono);
            if (!nuevoEmail.isEmpty()) alumno.setEmail(nuevoEmail);

            alumnoController.updateAlumno(alumno);
            System.out.println("Alumno modificado!");
        } else if (confirmacion == 0) {
            System.out.println("Actualización cancelada.");
        }
        System.out.println("Presione Enter para continuar...");
        scanner.nextLine();
    }

    private void eliminarAlumno() {
        System.out.println("----------Eliminar alumno----------");
        System.out.println("(Ingrese 0 para cancelar)");

        System.out.print("Ingrese el DNI: ");
        String dni = scanner.nextLine().trim();

        if (dni.equals("0")) {
            System.out.println("Eliminación cancelada.");
            System.out.println("Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        if (!alumnoController.validarDni(dni)) {
            System.out.println("Error: El DNI debe tener 8 dígitos numéricos.");
            System.out.println("Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        Alumno alumno = alumnoController.findByDni(dni);

        if (alumno == null) {
            System.out.println("Alumno no encontrado.");
            System.out.println("Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        System.out.println("Alumno encontrado:");
        mostrarAlumno(alumno);

        int confirmacion = confirmarAccion();
        if (confirmacion == 1) {
            alumnoController.deleteAlumno(alumno);
            System.out.println("Alumno eliminado!");
        } else if (confirmacion == 0) {
            System.out.println("Eliminación cancelada.");
        }
        System.out.println("Presione Enter para continuar...");
        scanner.nextLine();
    }

}