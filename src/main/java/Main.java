import config.HibernateUtil;
import model.Autor;
import model.Libro;
import model.Telefono;
import org.hibernate.Session;
import controller.AutorRepository;
import controller.LibroRepository;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(java.lang.String[] args) {
        System.out.println("Iniciando conexion a MYSQL con Hibernate");
        Session session = HibernateUtil.get().openSession();
        menuInterfaz(session);

        session.close();
        System.out.println("Finalizando la conexion a MYSQL con Hibernate");
    }

    private static void menuInterfaz(Session session) {
        LibroRepository libroRepository = new LibroRepository(session);
        AutorRepository autorRepository = new AutorRepository(session);
        Scanner num = new Scanner(System.in);

        int opcion;
        boolean salir = false;
        System.out.println("*** BIENVENIDO A LA APLICACION DE GESTION DE UNA LIBRERIA ***");
        while (!salir) {
            System.out.println("Selecciona una de las siguientes opciones:");
            System.out.println("(1. Agregar a la BBDD.)\n(2. Borrar de la BBDD.)\n(3. Consultar a la BBDD.)\n(0. Salir aplicacion.)");
            opcion = num.nextInt();
            switch (opcion) {
                case 1:
                    System.out.println("(1. Insertar Autor.)\n(2. Insertar Libro.)\n(0. Volver.)");
                    opcion = num.nextInt();
                    switch (opcion) {
                        case 1:
                            insertarAutor(session);
                            break;
                        case 2:
                            insertarLibro(session);
                            break;
                        case 0:
                            break;
                        default:
                            System.err.println("Opción no válida, volviendo al menú principal...");
                            break;
                    }
                    break;
                case 2:
                    System.out.println("(1. Eliminar Autor.)\n(2. Eliminar Libro.)\n(0. Volver.)");
                    opcion = num.nextInt();
                    switch (opcion) {
                        case 1:
                            eliminarAutor(session);
                            break;
                        case 2:
                            eliminarLibro(session);
                            break;
                        case 0:
                            break;
                        default:
                            System.err.println("Opción no válida, volviendo al menú principal...");
                            break;
                    }
                    break;
                case 3:
                    System.out.println("(1. Visualizar Libros por Autor.)\n(2. Visualizar datos Libro por titulo.)\n(3. Visualizar todos los Libros.)\n(4. Visualizar todos Autores con sus Libros.)\n(0. Volver.)");
                    opcion = num.nextInt();
                    switch (opcion) {
                        case 1:
                            mostrarLibrosSegunAutor(session);
                            break;
                        case 2:
                            mostrarLibroSegunTitulo(session);
                            break;
                        case 3:
                            mostrarAllLibros(session);
                            break;
                        case 4:
                            mostrarAutoresConLibros(session);
                            break;
                        case 0:
                            break;
                        default:
                            System.err.println("Opción no válida, volviendo al menú principal...");
                            break;
                    }
                    break;
                case 0:
                    salir = true;
                    break;
                default:
                    System.err.println("Opcion no valida.");
                    break;
            }
        }

    }

    private static void mostrarAutoresConLibros(Session session) {
        AutorRepository autorRepository = new AutorRepository(session);
        List<Autor> autores = autorRepository.findAll();

        for (Autor autor : autores) {
            System.out.println("Autor: " + autor.getNombre() + " (" + autor.getDniAutor() + ")");
            System.out.println("Libros:");

            for (Libro libro : autor.getLibros()) {
                System.out.println("- " + libro.getTitulo() + ", " + libro.getPrecio() + "€");
            }

            System.out.println(); // Añade un salto de línea entre cada autor
        }
    }

    private static void mostrarAllLibros(Session session) {
        LibroRepository libroRepository = new LibroRepository(session);
        List<Libro> libros = libroRepository.findAll();

        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros.");
        } else {
            for (Libro libro : libros) {
                System.out.println(libro.toString());
            }
        }
    }

    private static void mostrarLibroSegunTitulo(Session session) {
        Scanner letras = new Scanner(System.in);

        System.out.println("Ingrese el título del libro:");
        String titulo = letras.nextLine();
        try {
            session.beginTransaction();
            List<Libro> libros = session.createQuery("FROM libros WHERE titulo = :titulo", Libro.class).setParameter("titulo", titulo).getResultList();
            session.getTransaction().commit();

            if (libros.isEmpty()) {
                System.out.println("No se encontraron libros con el título: " + titulo);
            } else {
                System.out.println("Libros con el título: " + titulo + ":");
                for (Libro libro : libros) {
                    System.out.println(libro.toString());
                }
            }
        } catch (NoResultException e) {
            System.out.println("No se encontró ningún libro con el título: " + titulo);
        } catch (Exception e) {
            if (session.getTransaction() != null && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }

    private static void mostrarLibrosSegunAutor(Session session) {
        AutorRepository autorRepository = new AutorRepository(session);
        Scanner letras = new Scanner(System.in);

        // nota: muestro todos los autores para que sea mas facil el manejo de estos
        System.out.println("****** Listado de autores ******");
        List<Autor> autores = autorRepository.findAll();
        for (Autor autor : autores) {
            System.out.println("- Autor: " + autor.getDniAutor());
        }
        // nota: buscamos un autor segun el dni ingresado
        System.out.println("Ingrese el dni del autor:");
        String dni = letras.nextLine();

        try {
            session.beginTransaction();
            List<Libro> libros = session.createQuery("FROM libros WHERE dni_autor=:dni", Libro.class).setParameter("dni", dni).getResultList();
            session.getTransaction().commit();
            // nota: visualizamos por pantalla los libros
            if (libros.isEmpty()) {
                System.out.println("No se encontraron libros para el autor con DNI: " + dni);
            } else {
                System.out.println("Libros del autor con DNI " + dni + ":");
                for (Libro libro : libros) {
                    System.out.println(libro.toString());
                }
            }
        } catch (NoResultException e) {
            System.out.println("No se encontró ningún autor con DNI: " + dni);
        } catch (Exception e) {
            if (session.getTransaction() != null && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }

    private static void eliminarLibro(Session session) {
        LibroRepository libroRepository = new LibroRepository(session);
        Scanner num = new Scanner(System.in);

        // nota: muestro todos los autores para que sea mas facil el manejo de estos
        System.out.println("****** Listado de libros ******");
        List<Libro> libros = libroRepository.findAll();
        for (Libro libro : libros) {
            System.out.println("- Libro: " + libro.getIdLibro());
        }
        // nota: buscamos un autor segun el dni ingresado
        System.out.println("Ingrese el ID del libro:");
        int id = num.nextInt();
        Libro libro = libroRepository.findOneById(id);
        libroRepository.delete(libro);
    }

    private static void eliminarAutor(Session session) {
        AutorRepository autorRepository = new AutorRepository(session);
        Scanner letras = new Scanner(System.in);

        // nota: muestro todos los autores para que sea mas facil el manejo de estos
        System.out.println("****** Listado de autores ******");
        List<Autor> autores = autorRepository.findAll();
        for (Autor autor : autores) {
            System.out.println("- Autor: " + autor.getDniAutor());
        }
        // nota: buscamos un autor segun el dni ingresado
        System.out.println("Ingrese el dni del autor:");
        String dni = letras.nextLine();
        Autor autor = autorRepository.findOneById(dni);
        autorRepository.delete(autor);
    }

    private static void insertarAutor(Session session) {
        AutorRepository autorRepository = new AutorRepository(session);
        Scanner num = new Scanner(System.in);
        Scanner letras = new Scanner(System.in);
        boolean salir = false;

        System.out.println("Ingrese el DNI del autor:");
        String dni = num.nextLine();
        // String dni = "00001022t";
        System.out.println("Ingrese el nombre del autor:");
        // String nombre = num.nextLine();
        String nombre = "Paco";
        System.out.println("Ingrese la nacionalidad del autor:");
        // String nacionalidad = num.nextLine();
        String nacionalidad = "Española";
        System.out.println("Ingrese el numero de telefono del autor:");
        int numTelefono = num.nextInt();
        // int numTelefono = 612738373;

        // nota: Crear el objeto Autor
        Autor autor = new Autor(dni, nombre, nacionalidad);
        autor.setTelefono(new Telefono(numTelefono));

        while (!salir) {
            System.out.println("¿Desea ingresar un libro? (y/n)");
            String ingresarLibro = letras.nextLine();
            if (ingresarLibro.equals("y")) {
                System.out.println("Ingrese el nombre del libro:");
                String nombreLibro = "Harry potter";
                // String nombreLibro = letras.nextLine();
                System.out.println("Ingrese el precio del libro:");
                // float precio = num.nextFloat();
                float precio = 12.95f;

                autor.getLibros().add(new Libro(nombreLibro, precio, autor));
            } else {
                salir = true;
            }
        }

        autorRepository.save(autor);
        System.out.println("Autor insertado correctamente.");
    }

    private static void insertarLibro(Session session) {
        AutorRepository autorRepository = new AutorRepository(session);
        LibroRepository libroRepository = new LibroRepository(session);
        Scanner num = new Scanner(System.in);
        Scanner letras = new Scanner(System.in);

        System.out.println("Ingrese el título del libro:");
        String titulo = letras.nextLine();
        System.out.println("Ingrese el precio del libro:");
        float precio = num.nextFloat();

        // nota: muestro todos los autores para que sea mas facil el manejo de estos
        System.out.println("****** Listado de autores ******");
        List<Autor> autores = autorRepository.findAll();
        for (Autor autor : autores) {
            System.out.println("- Autor: " + autor.getDniAutor());
        }
        // nota: buscamos un autor segun el dni ingresado
        System.out.println("Ingrese el dni del autor:");
        String dni = letras.nextLine();
        Autor autor = autorRepository.findOneById(dni);

        Libro libro = new Libro(titulo, precio, autor);

        libroRepository.save(libro);
        System.out.println("Libro insertado correctamente.");

    }

}