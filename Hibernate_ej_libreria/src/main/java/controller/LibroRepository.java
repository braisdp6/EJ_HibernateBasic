package controller;

import model.Autor;
import model.Libro;
import org.hibernate.Session;

import java.util.List;

public class LibroRepository implements Repository<Libro> {
    private final Session session;

    public LibroRepository(Session session) {
        this.session = session;
    }

    @Override
    public void save(Libro libro) {
        session.beginTransaction();
        session.save(libro);
        System.out.println("Libro guardado correctamente con el ID:" + libro.getIdLibro());
        session.getTransaction().commit();
    }

    @Override
    public List<Libro> findAll() {
        session.beginTransaction();
        List<Libro> libros = session.createQuery("FROM libros", Libro.class).getResultList();
        session.getTransaction().commit();
        return libros;
    }

    @Override
    public Libro findOneById(long id) {
        session.beginTransaction();
        // nota: de la base de datos recuperame todos los alumnos por id("FROM alumnos WHERE personaId=:id"), y la clase a la cual queremos transformar los datos (Alumno.class) y lo convertimos en un único resultado (.getSingleResult())
        Libro libro = session.createQuery("FROM libros WHERE libro_id=:id", Libro.class).setParameter("id", id).getSingleResult();
        session.getTransaction().commit();
        return libro;
    }

    @Override
    public Libro findOneById(String id) {
        return null;
    }

    @Override
    public void update(Libro libro) {

    }

    @Override
    public void delete(Libro libro) {
        session.beginTransaction();
        session.delete(libro);
        System.out.println("Libro borrado correctamente con el ID:" + libro.getIdLibro());
        session.getTransaction().commit();

    }
}
