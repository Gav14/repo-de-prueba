package ar.com.itecn1.view.utils;

import java.util.Scanner;
import java.util.regex.Pattern;

public class ValidacionesView {

    private static final Pattern DNI_PATTERN = Pattern.compile("\\d{8}");
    private static final Pattern NOMBRE_PATTERN = Pattern.compile("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    private static final Pattern TELEFONO_PATTERN = Pattern.compile("\\d+");

    // Validaciones simples
    public static boolean validarDni(String dni) {
        return dni != null && DNI_PATTERN.matcher(dni).matches();
    }

    public static boolean validarNombre(String nombre) {
        return nombre != null && !nombre.trim().isEmpty() && NOMBRE_PATTERN.matcher(nombre).matches();
    }

    public static boolean validarApellido(String apellido) {
        return validarNombre(apellido);
    }

    public static boolean validarEmail(String email) {
        return email != null && !email.trim().isEmpty() && EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean validarTelefono(String telefono) {
        return telefono != null && !telefono.trim().isEmpty() && TELEFONO_PATTERN.matcher(telefono).matches();
    }

    // Solicita un dato con validación (para creación). Retorna "0" si se cancela.
    public static String solicitarDato(Scanner scanner, String mensaje, String tipo) {
        String dato;
        boolean valido;

        do {
            System.out.print(mensaje);
            dato = scanner.nextLine().trim();

            if (dato.equals("0")) {
                return "0";
            }

            valido = true;
            String errorMsg = null;

            switch (tipo) {
                case "dni":
                    if (!validarDni(dato)) errorMsg = "El DNI debe tener 8 dígitos numéricos.";
                    break;
                case "nombre":
                    if (!validarNombre(dato)) errorMsg = "El nombre no puede contener números ni estar vacío.";
                    break;
                case "apellido":
                    if (!validarApellido(dato)) errorMsg = "El apellido no puede contener números ni estar vacío.";
                    break;
                case "email":
                    if (!validarEmail(dato)) errorMsg = "El email debe tener un formato válido (ej: usuario@dominio.com).";
                    break;
                case "telefono":
                    if (!validarTelefono(dato)) errorMsg = "El teléfono debe contener solo números.";
                    break;
            }

            if (errorMsg != null) {
                System.out.println("Error: " + errorMsg);
                valido = false;
            }
        } while (!valido);

        return dato;
    }

    // Solicita un dato para actualización (permite vacío = no modificar). Retorna "0" para cancelar.
    public static String solicitarDatoActualizacion(Scanner scanner, String mensaje, String valorActual, String tipo) {
        System.out.print(mensaje + " (" + valorActual + "): ");
        String dato = scanner.nextLine().trim();

        if (dato.equals("0")) {
            return "0";
        }
        if (dato.isEmpty()) {
            return ""; // indica que no se modifica
        }

        // Validar el dato si no está vacío
        boolean valido = false;
        String errorMsg = null;

        while (!valido) {
            switch (tipo) {
                case "dni":
                    if (!validarDni(dato)) errorMsg = "El DNI debe tener 8 dígitos numéricos.";
                    else valido = true;
                    break;
                case "nombre":
                    if (!validarNombre(dato)) errorMsg = "El nombre no puede contener números.";
                    else valido = true;
                    break;
                case "apellido":
                    if (!validarApellido(dato)) errorMsg = "El apellido no puede contener números.";
                    else valido = true;
                    break;
                case "telefono":
                    if (!validarTelefono(dato)) errorMsg = "El teléfono debe contener solo números.";
                    else valido = true;
                    break;
                case "email":
                    if (!validarEmail(dato)) errorMsg = "El email debe tener un formato válido.";
                    else valido = true;
                    break;
            }

            if (!valido) {
                System.out.println("Error: " + errorMsg);
                System.out.print("Reingrese " + mensaje.toLowerCase() + " (o 0 para cancelar): ");
                dato = scanner.nextLine().trim();
                if (dato.equals("0")) return "0";
                if (dato.isEmpty()) return "";
            }
        }
        return dato;
    }
}