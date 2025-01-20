package challenge.literalura.repositorio;

import challenge.literalura.modelo.CategoriaPorIdioma;
import challenge.literalura.modelo.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LibroRepositorio extends JpaRepository<Libro, Long> {
    Optional<Libro> findLibroBytitulo(String titulo);
    List<Libro> findLibrosByidioma(CategoriaPorIdioma idioma);


}
