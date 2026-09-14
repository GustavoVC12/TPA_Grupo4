package dominio;

import java.util.Objects;

public class Contato {
    private String nome;
    private long telefone;

    public Contato(String nome, long num) {
        this.nome = nome;
        this.telefone = num;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public long getTelefone() {
        return telefone;
    }

    public void setTelefone(long telefone) {
        this.telefone = telefone;
    }

    public String toString() {
        return nome + " - " + telefone;
    }



    @Override
    public boolean equals(Object o) { // elaborado com auxílio de IA
        if (this == o) return true; // se as referências do contato e do objeto apontam para o mesmo espaço, são iguais
        if (o == null || getClass() != o.getClass()) return false; // se contato e o objeto são de tipos diferentes (incluindo null), não são iguais
        Contato contato = (Contato) o;
        return telefone == contato.getTelefone(); // se o número já está cadastrado, entende-se que são iguais mesmo se os nomes forem diferentes
    }

    @Override
    public int hashCode(){ // elaborado com auxílio de IA
        return Objects.hash(telefone);
    }
}
