package model;

import javax.persistence.*;

@Entity(name = "telefonos")
public class Telefono {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_telefono")
    private long id;
    @Column(name = "num_telefono", unique = true)
    private int numTelefono;
    @OneToOne(mappedBy = "telefono", fetch = FetchType.LAZY)
    private Autor autor;

    public Telefono() {
    }

    public Telefono(int numTelefono) {
        this.numTelefono = numTelefono;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNumTelefono() {
        return numTelefono;
    }

    public void setNumTelefono(int numTelefono) {
        this.numTelefono = numTelefono;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "Telefono{" +
                "id=" + id +
                ", numTelefono=" + numTelefono +
                ", autor=" + autor +
                '}';
    }
}
