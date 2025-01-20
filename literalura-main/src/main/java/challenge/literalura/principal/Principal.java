package challenge.literalura.principal;

import challenge.literalura.modelo.*;
import challenge.literalura.repositorio.LibroRepositorio;
import challenge.literalura.service.ConvierteDatos;
import com.aluracursos.literalura.modelo.*;
import challenge.literalura.repositorio.AutorRepositorio;
import challenge.literalura.service.ConsumoAPI;


import java.util.*;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos convierteDatos = new ConvierteDatos();
    private List<Libro> libros;
    private List<Autor> autores;
    private LibroRepositorio libroRepositorio;
    private AutorRepositorio autorRepositorio;


    public Principal(AutorRepositorio autorRepositorio, LibroRepositorio libroRepositorio){
        this.libroRepositorio = libroRepositorio;
        this.autorRepositorio = autorRepositorio;
    }
    //Menu de opciones
    public void muestramenu(){
        int opcion = -1;
        String menu = """
          ******************** Libros ********************
          1) Buscar libro por título 
          2) Listar libros registrados
          3) Listar autores registrados
          4) Listar autores vivos en un determinado año
          5) Listar libros por idioma
          
          0) Salir
          ************************************************      
          """;


        while (opcion != 0) {
            System.out.println(menu);
            try {
                opcion = teclado.nextInt();
                teclado.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número del 0 al 5.");
                teclado.nextLine();
                continue;
            }
            switch (opcion) {
                case 1:
                    libroPorTitulo();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    autoresRegistrados();
                    break;
                case 4:
                    autoresPorFecha();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.printf("Opción inválidaPor favor, ingrese un número del 0 al 5.\n");
            }

    }

    }

    private void listarLibrosPorIdioma() {
        String menuIdioma = """
                Ingrese el idioma para buscar los libros: 
                es : Español
                en : Ingles
                fr : Frances 
                pt : Portugues
                """;
        System.out.println(menuIdioma);
        String idiomaBuscado = teclado.nextLine();
        CategoriaPorIdioma idioma;
        switch (idiomaBuscado){
            case "es":
                idioma = CategoriaPorIdioma.fromEspanol("Español") ;
                break;
            case "en":
                idioma = CategoriaPorIdioma.fromEspanol("Ingles") ;
                break;
            case "fr":
                idioma = CategoriaPorIdioma.fromEspanol("Frances") ;
                break;
            case "pt":
                idioma = CategoriaPorIdioma.fromEspanol("Portugues");
                break;
            default:
                System.out.println("Seleccione una opcion valida");
                return;

        }
        buscarPorIdioma(idioma);

    }

    private void buscarPorIdioma(CategoriaPorIdioma idioma){
        libros = libroRepositorio.findLibrosByidioma(idioma);
        if (libros.isEmpty()){
            System.out.println("Aun no hay libros registrados");
        } else {
            libros.stream().forEach(System.out::println);
        }
    }

    private void listarLibrosRegistrados() {
        libros = libroRepositorio.findAll();
        libros.stream().forEach(System.out::println);

    }

    private void autoresPorFecha() {

        System.out.println("Ingrese el año que desea para buscar autores vivos: ");
        try {
            Integer year = teclado.nextInt();
            autores = autorRepositorio.findAutoresByYear(year);
            if (autores.isEmpty()){
                System.out.println("No hay autores en ese rango");
            } else {
                autores.stream().forEach(System.out::println);
            }
        } catch (InputMismatchException e) {
            System.out.println("Ingrese un año correcto");
            teclado.nextLine();
        }

    }


    private void autoresRegistrados() {
        autores = autorRepositorio.findAll();
        autores.stream().forEach(System.out::println);
    }

    private String Consulta(){
        System.out.println("Escribe el nombre del libro a buscar: ");
        var nombreLibro = teclado.nextLine();
        String url = URL_BASE + "?search=" + nombreLibro.replace(" ", "%20");
        System.out.println("Esperando la respuesta...");
        String respuesta = consumoAPI.obtenerDatosApi(url);
        return respuesta;
    }

    private void libroPorTitulo() {
        String respuesta = Consulta();
        DatosConsulta datosConsulta = convierteDatos.obtenerDatos(respuesta, DatosConsulta.class);
        if (datosConsulta.numeroLibros() !=0) {
            DatosLibro primerLibro = datosConsulta.resultado().get(0);
            Autor autorLibro = new Autor(primerLibro.autores().get(0));
            Optional<Libro> libroBase = libroRepositorio.findLibroBytitulo(primerLibro.titulo());
            if (libroBase.isPresent()) {
                System.out.println("No se puede registrar el mismo líbro ");
                //System.out.println(libroBase);
            } else {
                Optional<Autor> autorDeBase = autorRepositorio.findBynombre(autorLibro.getNombre());
                if (autorDeBase.isPresent()) {
                    autorLibro = autorDeBase.get();
                } else {
                    autorRepositorio.save(autorLibro);
                }

                Libro libro = new Libro(primerLibro);
                libro.setAutor(autorLibro);
                libroRepositorio.save(libro);
                System.out.println(libro);
            }
        } else {
            System.out.println("Líbro no encontrado...");
        }
    }


}
