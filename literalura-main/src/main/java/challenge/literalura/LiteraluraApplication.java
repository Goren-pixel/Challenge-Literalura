package challenge.literalura;

import challenge.literalura.principal.Principal;
import challenge.literalura.repositorio.AutorRepositorio;
import challenge.literalura.repositorio.LibroRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	@Autowired
	private AutorRepositorio autorRepositorio;

	@Autowired
	private LibroRepositorio libroRepositorio;

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(autorRepositorio, libroRepositorio);
		principal.muestramenu();
	}


}
