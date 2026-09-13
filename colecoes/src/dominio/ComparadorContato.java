package dominio;

import java.util.Comparator;

public class ComparadorContato implements Comparator<Contato> {

    public ComparadorContato() {
    }

    @Override
    public int compare(Contato contato1, Contato contato2) {
        return Long.compare(contato1.getTelefone(), contato2.getTelefone());
    }
}
