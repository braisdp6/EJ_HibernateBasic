package model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "libro_id")
    private int idLibro;
    @Column(name = "titulo")
    private String titulo;
    @Column(name = "precio")
    private float precio;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dni_autor")
    private Autor autor = new Autor();

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Libro() {
    }

    public Libro(String titulo, float precio, Autor autor) {
        this.titulo = titulo;
        this.precio = precio;
        this.autor = autor;
    }

    public int getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "idLibro=" + idLibro +
                ", titulo='" + titulo + '\'' +
                ", precio=" + precio;
    }
}
