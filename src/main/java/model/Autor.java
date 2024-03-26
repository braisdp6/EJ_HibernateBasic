package model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "autores")
public class Autor {
    @Id
    @Column(name = "dni_autor")
    private String dniAutor;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "nacionalidad")
    private String nacionalidad;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_telefono")
    private Telefono telefono;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL)
    private List<Libro> libros = new ArrayList<>();

    public Autor() {
    }

    public Autor(String dniAutor, String nombre, String nacionalidad) {
        this.dniAutor = dniAutor;
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
    }

    public String getDniAutor() {
        return dniAutor;
    }

    public void setDniAutor(String dniAutor) {
        this.dniAutor = dniAutor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public Telefono getTelefono() {
        return telefono;
    }

    public void setTelefono(Telefono telefono) {
        this.telefono = telefono;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    @Override
    public String toString() {
        return "Autor{" +
                "dniAutor='" + dniAutor + '\'' +
                ", nombre='" + nombre + '\'' +
                ", nacionalidad='" + nacionalidad + '\'' +
                ", telefono=" + telefono;
    }
}
