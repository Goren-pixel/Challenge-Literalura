package challenge.literalura.modelo;

public enum CategoriaPorIdioma {
    ESPANOL("[es]", "Español"),
    INGLES("[en]", "Ingles"),
    FRANCES("[fr]", "Frances"),
    PORTUGUES("[pt]", "Portugues");

    private String categoriaGutendex;
    private String categoriaEspanol;

    CategoriaPorIdioma(String categoriaGutendex, String categoriaEspanol){
        this.categoriaEspanol = categoriaEspanol;
        this.categoriaGutendex = categoriaGutendex;
    }
    public static CategoriaPorIdioma fromString(String text){
        for (CategoriaPorIdioma categoriaPorIdioma : CategoriaPorIdioma.values()){
            if (categoriaPorIdioma.categoriaGutendex.equalsIgnoreCase(text)){
                return categoriaPorIdioma;
            }
        }
        throw new IllegalArgumentException("Ninguna categoria encontrada: " + text);
    }

    public static CategoriaPorIdioma fromEspanol (String text){
        for (CategoriaPorIdioma categoriaPorIdioma : CategoriaPorIdioma.values()){
            if (categoriaPorIdioma.categoriaEspanol.equalsIgnoreCase(text)){
                return categoriaPorIdioma;
            }
        }
        throw new IllegalArgumentException("Ninguna categoria encontrada: " + text);
    }
}
