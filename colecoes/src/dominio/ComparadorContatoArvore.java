package dominio;

import java.util.Comparator;

public class ComparadorContatoArvore {

    // compara e ordena por nº
    public static final Comparator<Contato> POR_TELEFONE = new Comparator<Contato>() {
        @Override
        public int compare(Contato c1, Contato c2) {
            return Long.compare(c1.getTelefone(), c2.getTelefone());
        }
    };

    // compara e ordena por nome
    public static final Comparator<Contato> POR_NOME = new Comparator<Contato>() {
        @Override
        public int compare(Contato c1, Contato c2) {
            return c1.getNome().compareToIgnoreCase(c2.getNome());
        }
    };
}