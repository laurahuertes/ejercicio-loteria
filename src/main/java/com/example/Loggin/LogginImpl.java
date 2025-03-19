package com.example.Loggin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
public class LogginImpl {

    private static final Logger logger = LoggerFactory.getLogger(LogginImpl.class);


    public void menu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Bienvenido a tu administracion de confianza");
        int opcion;
        List<Persona> personas = new ArrayList<>();
        do {
            System.out.println("Seleccione la opcion que desee:");
            System.out.println("1. Añadir Jugador");
            System.out.println("2. Añadir apuesta");
            System.out.println("3. Mostrar apuestas");
            System.out.println("4. Mostrar personas");
            System.out.println("5. Realizar sorteo");
            System.out.println("6. Salir");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    Persona persona = new Persona();
                    persona = createJugador(personas);
                    personas.add(persona);
                    break;
                case 2:
                    // Preguntar si random o manual
                    System.out.println("Prefiere hacer la apuesta manualmente o aleatoria?  1: Manual 2: Aleatoria");
                    int op = scanner.nextInt();
                    if (op == 1) {
                        createApuestaManual(personas);
                    }
                    if (op == 2) {
                        createApuestaRandom(personas);
                    }
                    break;
                case 3:
                    mostrarApuestas(personas);
                    break;
                case 4:
                    imprimirListaPersonas(personas);
                    break;
                case 5:
                    realizarSorteo(personas);
                    break;
                case 6:
                    System.out.println("Ha salido de la administracion");
                    break;
                default:
                    System.out.println("Introduzca una opcion valida");
                    break;
            }

        } while (opcion != 6);
    }

    public Persona createJugador(List<Persona> personas) {
        logger.info("Creando jugador...");
        String jug = "";
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduzca su nombre: ");
        jug = scanner.nextLine();

        Persona newpersona = new Persona(jug);

        for (int i = 0; i < personas.size(); i++) {
            if (personas.get(i).getNombre().equals(jug)) {
                logger.error("Ya existe una persona con ese nombre");
                return null;
            }
        }
        logger.info("Se ha creado un jugador");
        logger.debug("Nombre: " + jug);
        return newpersona;
    }

    public void createApuestaManual(List<Persona> personas) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el ID de la persona (posición en la lista): ");
        int id = scanner.nextInt();
        if (id < 0 || id >= personas.size()) {
            System.out.println("ID inválido.");
            return;
        }

        int[] apuesta = new int[6];
        for (int i = 0; i < 6; i++) {
            System.out.println("Ingrese el número " + i + " de la apuesta: ");
            int num = scanner.nextInt();
            while (num < 0 || num > 49) {
                System.out.println("Numero no válido, tiene que ser entre 1 y 49 inclusives");
                num = scanner.nextInt();
            }
            apuesta[i] = num;
        }
        logger.info("Se ha creado una apuesta manual");
        logger.debug("La persona " + personas.get(id).getNombre() + " ha realizado la apuesta " + Arrays.toString(apuesta));
        personas.get(id).addApuesta(apuesta);
    }

    public void createApuestaRandom(List<Persona> personas) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el ID de la persona (posición en la lista): ");
        int id = scanner.nextInt();
        if (id < 0 || id >= personas.size()) {
            System.out.println("ID inválido.");
            return;
        }

        int[] numerosAleatorios = new int[6];
        Random rand = new Random();

        for (int i = 0; i < 6; i++) {
            // Generar un número aleatorio entre 1 y 49
            int numero = (rand.nextInt(49) + 1);
            //System.out.println("Numero " + i + ":" + numero);

            for (int numerosAleatorio : numerosAleatorios) {
                if (numerosAleatorio == numero) {
                    i--;
                }
            }
            numerosAleatorios[i] = numero;

        }

        // Mostrar los números generados
        System.out.println("Números aleatorios generados: ");
        for (int i = 0; i < 6; i++) {
            System.out.println("Numero " + i + ":" + numerosAleatorios[i]);

        }

        logger.info("Se ha creado una apuesta aleatoria");
        logger.debug("Se le ha realizado la apuesta aleatoria a " + personas.get(id).getNombre());
        personas.get(id).addApuesta(numerosAleatorios);

    }

    public void imprimirListaPersonas(List<Persona> personas) {
        for (int i = 0; i < personas.size(); i++) {
            imprimirPersona(i, personas.get(i));
        }
    }

    public void imprimirPersona(int id, Persona persona) {
        System.out.println("ID: " + id + " Nombre:" + persona.getNombre() + " Número de apuestas:" + persona.getApuestas().size() + "\n");
    }

    public void mostrarApuestas(List<Persona> personas) {
        logger.info("Mostrando personas...");
        for (Persona persona : personas) {
            System.out.println("Apuestas de " + persona.getNombre() + ":");
            for (int[] apuesta : persona.getApuestas()) {
                for (int num : apuesta) {
                    System.out.println(" Numero: " + num);

                }
                System.out.println();
            }
        }
    }

    public void realizarSorteo(List<Persona> personas) {
        logger.info("Empezando sorteo...");
        // Obtener 6 numeros aleatorios
        int[] numerosAleatorios = new int[6];

        Random rand = new Random();

        for (int i = 0; i < 6; i++) {
            // Generar un número aleatorio entre 1 y 49
            int numero = (rand.nextInt(49) + 1);
            //System.out.println("Numero " + i + ":" + numero);

            for (int numerosAleatorio : numerosAleatorios) {
                if (numerosAleatorio == numero) {
                    i--;
                }
            }
            numerosAleatorios[i] = numero;

        }

        System.out.println("El numero del sorteo ha sido: ");

        for (int i = 0; i < 6; i++) {
            System.out.println("Numero " + i + ":" + numerosAleatorios[i]);

        }

        // Recorrer personas, dentro de cada persona, recorrer sus apuestas
        System.out.println("Vamos a comprobar si alguien tiene el número ganador\n");
        for (int i = 0; i < personas.size(); i++) {
            Persona persona = new Persona();
            persona = personas.get(i);
            System.out.println("Persona: " + persona.getNombre());
            System.out.println("Numero de apuestas realizadas: " + persona.getApuestas().size());
            for (int j = 0; j < persona.getApuestas().size(); j++) {
                List<int[]> apuestasPersona = persona.getApuestas();
                System.out.println("Apuesta numero " + (j + 1) + ":");
                int[] apuesta = apuestasPersona.get(j);

                for (int k = 0; k < 6; k++) {
                    System.out.print("Numero: " + apuesta[k] + "\n");

                }

                if (Arrays.equals(numerosAleatorios, apuesta)) {
                    System.out.println("Coincide el numero con el sorteo! Ha ganado " + personas.get(i).getNombre());
                } else {
                    System.out.println("Lo siento, esta apuesta no coincide con el número ganador");
                }


            }

        }
    }
}
