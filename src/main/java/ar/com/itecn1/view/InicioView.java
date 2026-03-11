package ar.com.itecn1.view;

import ar.com.itecn1.controller.*;
import java.util.Scanner;

public class InicioView {
    private final Scanner scanner = new Scanner(System.in);

    // --- INSTANCIACIÓN DE CONTROLADORES Y VISTAS (IGUAL QUE ANTES) ---
    // Entidades básicas
    private final AlumnoController alumnoController = new AlumnoController();
    private final ProfesorController profesorController = new ProfesorController();
    private final CarreraController carreraController = new CarreraController();
    private final CuatrimestreController cuatrimestreController = new CuatrimestreController();
    private final ModuloController moduloController = new ModuloController();
    private final ExamenController examenController = new ExamenController();

    // Entidades con dependencias
    private final MateriaController materiaController = new MateriaController();
    private final PlanEstudioController planEstudioController = new PlanEstudioController();
    private final HorarioController horarioController = new HorarioController();

    // Controladores de Relaciones
    private final AlumnoInscriptoCarreraController alumnoInscriptoCarreraController = new AlumnoInscriptoCarreraController();
    private final AlumnoInscriptoMateriaController alumnoInscriptoMateriaController = new AlumnoInscriptoMateriaController();
    private final ComisionController comisionController = new ComisionController();

    // --- VISTAS ---
    private final AlumnoView alumnoView = new AlumnoView(alumnoController, scanner);
    private final ProfesorView profesorView = new ProfesorView(profesorController, scanner);
    private final CarreraView carreraView = new CarreraView(carreraController, planEstudioController, scanner);
    private final CuatrimestreView cuatrimestreView = new CuatrimestreView(cuatrimestreController, scanner);
    private final ModuloView moduloView = new ModuloView(moduloController, scanner);
    private final MateriaView materiaView = new MateriaView(materiaController, scanner);
    private final PlanEstudioView planEstudioView = new PlanEstudioView(planEstudioController, materiaController, scanner);
    private final HorarioView horarioView = new HorarioView(horarioController, carreraController, cuatrimestreController, moduloController, scanner);

    private final AlumnoInscriptoCarreraView alumnoInscriptoCarreraView = new AlumnoInscriptoCarreraView(
            alumnoInscriptoCarreraController, alumnoController, carreraController, planEstudioController, scanner
    );

    private final ComisionView comisionView = new ComisionView(
            comisionController, cuatrimestreController, profesorController,
            horarioController, examenController, alumnoInscriptoMateriaController, alumnoInscriptoCarreraController, carreraController, scanner
    );

    // --- MÉTODO PRINCIPAL ---
    public void iniciar() {
        boolean continuar = true;

        while (continuar) {
            mostrarMenuPrincipal();
            if (scanner.hasNextInt()) {
                int opcion = scanner.nextInt();
                switch (opcion) {
                    case 1:
                        menuPersonas();
                        break;
                    case 2:
                        menuAcademico();
                        break;
                    case 3:
                        menuCursada();
                        break;
                    case 4:
                        menuConfiguracion();
                        break;
                    case 5:
                        continuar = false;
                        System.out.println("Saliendo del sistema...");
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }
            } else {
                System.out.println("Debe ingresar un número.");
                scanner.next();
            }
        }
    }

    // --- SUB-MENÚS ---

    // 1. PERSONAS
    private void menuPersonas() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- GESTIÓN DE PERSONAS ---");
            System.out.println("1. Alumnos");
            System.out.println("2. Profesores");
            System.out.println("3. Volver al menú principal");
            System.out.print("Seleccione: ");

            if (scanner.hasNextInt()) {
                int op = scanner.nextInt();
                switch (op) {
                    case 1: this.alumnoView.iniciar(); break;
                    case 2: this.profesorView.iniciar(); break;
                    case 3: volver = true; break;
                    default: System.out.println("Opción inválida");
                }
            } else { scanner.next(); }
        }
    }

    // 2. ACADÉMICO
    private void menuAcademico() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- ESTRUCTURA ACADÉMICA ---");
            System.out.println("1. Carreras");
            System.out.println("2. Planes de Estudio");
            System.out.println("3. Materias");
            System.out.println("4. Volver al menú principal");
            System.out.print("Seleccione: ");

            if (scanner.hasNextInt()) {
                int op = scanner.nextInt();
                switch (op) {
                    case 1: this.carreraView.iniciar(); break;
                    case 2: this.planEstudioView.iniciar(); break;
                    case 3: this.materiaView.iniciar(); break;
                    case 4: volver = true; break;
                    default: System.out.println("Opción inválida");
                }
            } else { scanner.next(); }
        }
    }

    // 3. CURSADA E INSCRIPCIONES
    private void menuCursada() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- GESTIÓN DE CURSADA ---");
            System.out.println("1. Comisiones (Cursado actual)");
            System.out.println("2. Inscripciones a Carreras");
            System.out.println("3. Volver al menú principal");
            System.out.print("Seleccione: ");

            if (scanner.hasNextInt()) {
                int op = scanner.nextInt();
                switch (op) {
                    case 1: this.comisionView.iniciar(); break;
                    case 2: this.alumnoInscriptoCarreraView.iniciar(); break;
                    case 3: volver = true; break;
                    default: System.out.println("Opción inválida");
                }
            } else { scanner.next(); }
        }
    }

    // 4. CONFIGURACIÓN
    private void menuConfiguracion() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- CONFIGURACIÓN Y CALENDARIO ---");
            System.out.println("1. Cuatrimestres");
            System.out.println("2. Horarios");
            System.out.println("3. Módulos Horarios");
            System.out.println("4. Volver al menú principal");
            System.out.print("Seleccione: ");

            if (scanner.hasNextInt()) {
                int op = scanner.nextInt();
                switch (op) {
                    case 1: this.cuatrimestreView.iniciar(); break;
                    case 2: this.horarioView.iniciar(); break;
                    case 3: this.moduloView.iniciar(); break;
                    case 4: volver = true; break;
                    default: System.out.println("Opción inválida");
                }
            } else { scanner.next(); }
        }
    }

    private void mostrarMenuPrincipal() {
        System.out.println("\n========= SISTEMA DE GESTIÓN =========");
        System.out.println("1. Personas (Alumnos, Profesores)");
        System.out.println("2. Estructura Académica (Carreras, Planes, Materias)");
        System.out.println("3. Cursada e Inscripciones (Comisiones, Inscripcion a Carrera)");
        System.out.println("4. Configuración (Cuatrimestres, Horarios)");
        System.out.println("5. Salir");
        System.out.print("Seleccione una categoría: ");
    }
}
