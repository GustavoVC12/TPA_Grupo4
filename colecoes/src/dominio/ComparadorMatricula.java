package dominio;

import java.util.Comparator;

public class ComparadorMatricula {

    // compara e ordena por nº
    public static final Comparator<Matricula> POR_NUMERO = new Comparator<Matricula>() {
        @Override
        public int compare(Matricula m1, Matricula m2) {
            return Integer.compare(m1.getNumeroMatricula(), m2.getNumeroMatricula());
        }
    };

    // compara e ordena por nome
    public static final Comparator<Matricula> POR_NOME = new Comparator<Matricula>() {
        @Override
        public int compare(Matricula m1, Matricula m2) {
            return m1.getNomeAluno().compareToIgnoreCase(m2.getNomeAluno());
        }
    };
}