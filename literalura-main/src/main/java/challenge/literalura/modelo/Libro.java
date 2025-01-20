package challenge.literalura.modelo;

import jakarta.persistence.*;



@Entity
@Table(name ="libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String titulo;
    @ManyToOne
    private Autor autor;
    @Enumerated(EnumType.STRING)
    private CategoriaPorIdioma idioma;
    private Integer descargas;

    public Libro (){}

    public Libro  (DatosLibro datosLibro){
        this.titulo = datosLibro.titulo();
        this.autor = new Autor(datosLibro.autores().get(0));
        this.descargas = datosLibro.numeroDescargas();
        this.idioma = CategoriaPorIdioma.fromString(datosLibro.idiomas().toString().split(",")[0].trim());

    }

    @Override
    public String toString() {
        return "-----LIBRO-----\n"+
                "Título: " + titulo + "\n" +
                "Autor: " + autor.getNombre()+ "\n" +
                "Idioma: " + idioma + "\n" +
                "Número de numeroDescargas: " + descargas + "\n" +
                "---------------";
    }


    public String getTitulo() {

        return titulo;
    }

    public void setAutor(Autor autor) {

        this.autor = autor;
    }



}
