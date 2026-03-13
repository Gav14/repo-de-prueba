package ar.com.itecn1.repository.impl;

import ar.com.itecn1.model.Alumno;
import ar.com.itecn1.repository.AlumnoRepository;

import java.util.ArrayList;
import java.util.List;

public class AlumnoRepositoryImpl implements AlumnoRepository {

    private static AlumnoRepositoryImpl instancia;
    private final List<Alumno> alumnosDb;

    private AlumnoRepositoryImpl() {
        this.alumnosDb = new ArrayList<>();
        cargarDatos();
    }

    public static AlumnoRepositoryImpl getInstancia() {
        if (instancia == null) {
            instancia = new AlumnoRepositoryImpl();
        }
        return instancia;
    }

    private void cargarDatos(){

        Alumno alumno1 = new Alumno("43563697", "Gavilan", "Mauro", "3764738002", "mauro.gavilan@gmail.com");
        Alumno alumno2 = new Alumno("47426591", "Cuello", "Julio", "3765050155", "julio.cuello@gmail.com");
        Alumno alumno3 = new Alumno("44231908", "Godoy", "Efrain", "3764552845", "efrain.godoy@gmail.com");
        Alumno alumno4 = new Alumno("46023942", "Arieli", "Mayra", "3764091001", "mayra.arieli@gmail.com");
        Alumno alumno5 = new Alumno("48472019", "Mendoza", "Armando", "3764238420", "armando.mendoza@gmail.com");

        Alumno alumno6 = new Alumno("33125487", "Acosta", "Lucas", "3764982143", "lucas.acosta@gmail.com");
        Alumno alumno7 = new Alumno("34789621", "Benitez", "Matias", "3765123498", "matias.benitez@gmail.com");
        Alumno alumno8 = new Alumno("35641278", "Cabrera", "Sofia", "3764782345", "sofia.cabrera@gmail.com");
        Alumno alumno9 = new Alumno("36874125", "Cardozo", "Juan", "3765342981", "juan.cardozo@gmail.com");
        Alumno alumno10 = new Alumno("37254169", "Dominguez", "Valentina", "3764891234", "valentina.dominguez@gmail.com");
        Alumno alumno11 = new Alumno("38451236", "Escobar", "Tomas", "3765234890", "tomas.escobar@gmail.com");
        Alumno alumno12 = new Alumno("39547128", "Ferreyra", "Lucia", "3764567812", "lucia.ferreyra@gmail.com");
        Alumno alumno13 = new Alumno("40125896", "Figueroa", "Nicolas", "3765129087", "nicolas.figueroa@gmail.com");
        Alumno alumno14 = new Alumno("41254789", "Franco", "Camila", "3764789034", "camila.franco@gmail.com");
        Alumno alumno15 = new Alumno("42365124", "Gimenez", "Agustin", "3765341029", "agustin.gimenez@gmail.com");
        Alumno alumno16 = new Alumno("43251478", "Herrera", "Martina", "3764893456", "martina.herrera@gmail.com");
        Alumno alumno17 = new Alumno("44521478", "Ibarra", "Franco", "3765127643", "franco.ibarra@gmail.com");
        Alumno alumno18 = new Alumno("32987451", "Juarez", "Paula", "3764560987", "paula.juarez@gmail.com");
        Alumno alumno19 = new Alumno("33478521", "Ledesma", "Bruno", "3765348712", "bruno.ledesma@gmail.com");
        Alumno alumno20 = new Alumno("34871236", "Leiva", "Florencia", "3764897612", "florencia.leiva@gmail.com");
        Alumno alumno21 = new Alumno("35987412", "Luna", "Gabriel", "3765129871", "gabriel.luna@gmail.com");
        Alumno alumno22 = new Alumno("36451278", "Maidana", "Julieta", "3764785612", "julieta.maidana@gmail.com");
        Alumno alumno23 = new Alumno("37854123", "Martinez", "Ezequiel", "3765346721", "ezequiel.martinez@gmail.com");
        Alumno alumno24 = new Alumno("38974512", "Medina", "Carla", "3764892365", "carla.medina@gmail.com");
        Alumno alumno25 = new Alumno("39784512", "Miño", "Santiago", "3765128745", "santiago.mino@gmail.com");
        Alumno alumno26 = new Alumno("40214587", "Morel", "Daniela", "3764562341", "daniela.morel@gmail.com");
        Alumno alumno27 = new Alumno("41578412", "Navarro", "Cristian", "3765349823", "cristian.navarro@gmail.com");
        Alumno alumno28 = new Alumno("42874512", "Nuñez", "Antonella", "3764896712", "antonella.nunez@gmail.com");
        Alumno alumno29 = new Alumno("43984512", "Ojeda", "Kevin", "3765126734", "kevin.ojeda@gmail.com");
        Alumno alumno30 = new Alumno("44781235", "Ortiz", "Milagros", "3764789231", "milagros.ortiz@gmail.com");
        Alumno alumno31 = new Alumno("33214587", "Palacios", "Facundo", "3765347812", "facundo.palacios@gmail.com");
        Alumno alumno32 = new Alumno("34659874", "Paredes", "Aldana", "3764894523", "aldana.paredes@gmail.com");
        Alumno alumno33 = new Alumno("35741269", "Peralta", "Marcos", "3765121234", "marcos.peralta@gmail.com");
        Alumno alumno34 = new Alumno("36895412", "Quiroga", "Rocio", "3764568765", "rocio.quiroga@gmail.com");
        Alumno alumno35 = new Alumno("37214598", "Ramirez", "Diego", "3765341123", "diego.ramirez@gmail.com");
        Alumno alumno36 = new Alumno("38478521", "Ramos", "Noelia", "3764898732", "noelia.ramos@gmail.com");
        Alumno alumno37 = new Alumno("39578412", "Rios", "Alan", "3765129987", "alan.rios@gmail.com");
        Alumno alumno38 = new Alumno("40123587", "Rivero", "Cintia", "3764783421", "cintia.rivero@gmail.com");
        Alumno alumno39 = new Alumno("41359874", "Rodriguez", "Matias", "3765345632", "matias.rodriguez@gmail.com");
        Alumno alumno40 = new Alumno("42587412", "Rojas", "Tamara", "3764892345", "tamara.rojas@gmail.com");
        Alumno alumno41 = new Alumno("43784512", "Salinas", "Leonel", "3765124456", "leonel.salinas@gmail.com");
        Alumno alumno42 = new Alumno("44874512", "Sosa", "Daiana", "3764562345", "daiana.sosa@gmail.com");
        Alumno alumno43 = new Alumno("33321458", "Suarez", "Javier", "3765348877", "javier.suarez@gmail.com");
        Alumno alumno44 = new Alumno("34798521", "Toledo", "Melina", "3764899087", "melina.toledo@gmail.com");
        Alumno alumno45 = new Alumno("35874125", "Valdez", "Fernando", "3765123321", "fernando.valdez@gmail.com");
        Alumno alumno46 = new Alumno("36987451", "Vargas", "Karen", "3764785567", "karen.vargas@gmail.com");
        Alumno alumno47 = new Alumno("37451236", "Velazquez", "Martin", "3765346712", "martin.velazquez@gmail.com");
        Alumno alumno48 = new Alumno("38654127", "Vera", "Belen", "3764894456", "belen.vera@gmail.com");
        Alumno alumno49 = new Alumno("39741258", "Villalba", "Damian", "3765127812", "damian.villalba@gmail.com");
        Alumno alumno50 = new Alumno("40587412", "Villalobos", "Luciana", "3764560981", "luciana.villalobos@gmail.com");
        Alumno alumno51 = new Alumno("41874521", "Zalazar", "Esteban", "3765342234", "esteban.zalazar@gmail.com");
        Alumno alumno52 = new Alumno("42987412", "Zarate", "Julian", "3764896632", "julian.zarate@gmail.com");
        Alumno alumno53 = new Alumno("43874512", "Almiron", "Gabriela", "3765125521", "gabriela.almiron@gmail.com");
        Alumno alumno54 = new Alumno("44785412", "Barrios", "Oscar", "3764789923", "oscar.barrios@gmail.com");
        Alumno alumno55 = new Alumno("33487521", "Caceres", "Veronica", "3765348899", "veronica.caceres@gmail.com");
        Alumno alumno56 = new Alumno("34587412", "Chamorro", "Ricardo", "3764891145", "ricardo.chamorro@gmail.com");
        Alumno alumno57 = new Alumno("35687412", "Correa", "Daniel", "3765126678", "daniel.correa@gmail.com");
        Alumno alumno58 = new Alumno("36784512", "Da Silva", "Natalia", "3764567765", "natalia.dasilva@gmail.com");
        Alumno alumno59 = new Alumno("37895412", "Encina", "Mauricio", "3765344477", "mauricio.encina@gmail.com");
        Alumno alumno60 = new Alumno("38987412", "Flores", "Claudia", "3764893321", "claudia.flores@gmail.com");
        Alumno alumno61 = new Alumno("40187412", "Gauna", "Roberto", "3765122211", "roberto.gauna@gmail.com");
        Alumno alumno62 = new Alumno("41287412", "Leguizamon", "Sandra", "3764786655", "sandra.leguizamon@gmail.com");

        this.alumnosDb.add(alumno1);
        this.alumnosDb.add(alumno2);
        this.alumnosDb.add(alumno3);
        this.alumnosDb.add(alumno4);
        this.alumnosDb.add(alumno5);
        this.alumnosDb.add(alumno6);
        this.alumnosDb.add(alumno7);
        this.alumnosDb.add(alumno8);
        this.alumnosDb.add(alumno9);
        this.alumnosDb.add(alumno10);
        this.alumnosDb.add(alumno11);
        this.alumnosDb.add(alumno12);
        this.alumnosDb.add(alumno13);
        this.alumnosDb.add(alumno14);
        this.alumnosDb.add(alumno15);
        this.alumnosDb.add(alumno16);
        this.alumnosDb.add(alumno17);
        this.alumnosDb.add(alumno18);
        this.alumnosDb.add(alumno19);
        this.alumnosDb.add(alumno20);
        this.alumnosDb.add(alumno21);
        this.alumnosDb.add(alumno22);
        this.alumnosDb.add(alumno23);
        this.alumnosDb.add(alumno24);
        this.alumnosDb.add(alumno25);
        this.alumnosDb.add(alumno26);
        this.alumnosDb.add(alumno27);
        this.alumnosDb.add(alumno28);
        this.alumnosDb.add(alumno29);
        this.alumnosDb.add(alumno30);
        this.alumnosDb.add(alumno31);
        this.alumnosDb.add(alumno32);
        this.alumnosDb.add(alumno33);
        this.alumnosDb.add(alumno34);
        this.alumnosDb.add(alumno35);
        this.alumnosDb.add(alumno36);
        this.alumnosDb.add(alumno37);
        this.alumnosDb.add(alumno38);
        this.alumnosDb.add(alumno39);
        this.alumnosDb.add(alumno40);
        this.alumnosDb.add(alumno41);
        this.alumnosDb.add(alumno42);
        this.alumnosDb.add(alumno43);
        this.alumnosDb.add(alumno44);
        this.alumnosDb.add(alumno45);
        this.alumnosDb.add(alumno46);
        this.alumnosDb.add(alumno47);
        this.alumnosDb.add(alumno48);
        this.alumnosDb.add(alumno49);
        this.alumnosDb.add(alumno50);
        this.alumnosDb.add(alumno51);
        this.alumnosDb.add(alumno52);
        this.alumnosDb.add(alumno53);
        this.alumnosDb.add(alumno54);
        this.alumnosDb.add(alumno55);
        this.alumnosDb.add(alumno56);
        this.alumnosDb.add(alumno57);
        this.alumnosDb.add(alumno58);
        this.alumnosDb.add(alumno59);
        this.alumnosDb.add(alumno60);
        this.alumnosDb.add(alumno61);
        this.alumnosDb.add(alumno62);
    }

    @Override
    public Alumno findByDni(String dni) {
        for(Alumno alumno : alumnosDb){
            if(alumno.getDni().equals(dni)){
                return alumno;
            }
        }
        return null;
    }

    @Override
    public List<Alumno> findAll() {
        return alumnosDb;
    }

    @Override
    public void save(Alumno alumno) {
        for(Alumno a : alumnosDb){
            if(a.getDni().equals(alumno.getDni())){
                return;
            }
        }
        alumnosDb.add(alumno);
    }

    @Override
    public void update(Alumno alumno) {
        for(Alumno a : alumnosDb){
            if(a.getDni().equals(alumno.getDni())){
                a.setNombre(alumno.getNombre());
                a.setApellido(alumno.getApellido());
                a.setTelefono(alumno.getTelefono());
                a.setEmail(alumno.getEmail());
                return;
            }
        }
    }

    @Override
    public void delete(Alumno alumno) {
        for(Alumno a : alumnosDb){
            if(a.getDni().equals(alumno.getDni())){
                a.setActivo(false);
                return;
            }
        }
    }
}