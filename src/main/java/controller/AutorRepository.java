package controller;

import model.Autor;
import org.hibernate.Session;

import java.util.List;

public class AutorRepository implements Repository<Autor> {

    private final Session session;

    public AutorRepository(Session session) {
        this.session = session;
    }

    @Override
    public void save(Autor autor) {
        session.beginTransaction();
        session.save(autor);
        System.out.println("Autor guardado correctamente con el DNI:" + autor.getDniAutor());
        session.getTransaction().commit();
    }

    @Override
    public List<Autor> findAll() {
        session.beginTransaction();
        List<Autor> autores = session.createQuery("FROM autores", Autor.class).getResultList();
        session.getTransaction().commit();
        return autores;
    }

    @Override
    public Autor findOneById(long id) {
        return null;
    }

    @Override
    public Autor findOneById(String dniAutor) {
        session.beginTransaction();
        // nota: de la base de datos recuperame todos los alumnos por id("FROM alumnos WHERE personaId=:id"), y la clase a la cual queremos transformar los datos (Alumno.class) y lo convertimos en un único resultado (.getSingleResult())
        Autor autor = session.createQuery("FROM autores WHERE dni_autor=:dniAutor", Autor.class).setParameter("dniAutor", dniAutor).getSingleResult();
        session.getTransaction().commit();
        return autor;
    }

    @Override
    public void update(Autor autor) {
        session.beginTransaction();
        session.update(autor);
        System.out.println("Autor actualizado correctamente con el DNI:" + autor.getDniAutor());
        session.getTransaction().commit();
    }

    @Override
    public void delete(Autor autor) {
        session.beginTransaction();
        session.delete(autor);
        System.out.println("Autor borrado correctamente con el DNI:" + autor.getDniAutor());
        session.getTransaction().commit();
    }
}
