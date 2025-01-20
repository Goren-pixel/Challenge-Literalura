package challenge.literalura.repositorio;

import challenge.literalura.modelo.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;
import java.util.Optional;


public interface AutorRepositorio extends JpaRepository<Autor,Long>{
    Optional<Autor> findBynombre(String nombre);
    @Query("SELECT a FROM Autor a WHERE  a.fechaFallecimiento > :Year")
    List<Autor> findAutoresByYear(Integer Year);
}
