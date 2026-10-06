package arvorebinaria;

public class No<T> {
    private T valor;
    private No<T> filhoEsquerda;
    private No<T> filhoDireita;

    public No(T valor) {
        this.valor = valor;
        this.filhoEsquerda = null;
        this.filhoDireita = null;
    }

    public T getValor() { return valor; }
    public void setValor(T valor) { this.valor = valor; }

    public No<T> getFilhoEsquerda() { return filhoEsquerda; }
    public void setFilhoEsquerda(No<T> filhoEsquerda) { this.filhoEsquerda = filhoEsquerda; }

    public No<T> getFilhoDireita() { return filhoDireita; }
    public void setFilhoDireita(No<T> filhoDireita) { this.filhoDireita = filhoDireita; }
}