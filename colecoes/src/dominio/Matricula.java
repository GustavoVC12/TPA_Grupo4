package dominio;

import java.util.Objects;

public class Matricula {
    private int numeroMatricula;
    private String nomeAluno;

    public Matricula(int numeroMatricula, String nomeAluno) {
        this.numeroMatricula = numeroMatricula;
        this.nomeAluno = nomeAluno;
    }

    public int getNumeroMatricula() {
        return numeroMatricula;
    }

    public void setNumeroMatricula(int numeroMatricula) {
        this.numeroMatricula = numeroMatricula;
    }

    public String getNomeAluno() {
        return nomeAluno;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }

    @Override
    public String toString() {
        return numeroMatricula + " - " + nomeAluno;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matricula matricula = (Matricula) o;
        return numeroMatricula == matricula.getNumeroMatricula();
    }

    @Override
    public int hashCode() {
        return Objects.hash(numeroMatricula);
    }
}