package dominio;

public class Resultado {
    private boolean concluido;
    private long tempo;

    public Resultado(boolean concluido, long tempo) {
        this.concluido = concluido;
        this.tempo = tempo;
    }

    public boolean isConcluido() {
        return concluido;
    }

    public long getTempo() {
        return tempo;
    }
}
