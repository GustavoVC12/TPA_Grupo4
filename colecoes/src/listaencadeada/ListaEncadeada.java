package listaencadeada;

import colecao.IColecao;

import java.util.Comparator;

public class ListaEncadeada<T> implements IColecao<T> {

    private No<T> prim, ult;
    private int quantidade;
    private final boolean ordenada;
    private Comparator<T> comparador;

    public ListaEncadeada(Comparator<T> comparador, boolean ehOrdenada) {
        this.prim = this.ult = null;
        this.quantidade = 0;
        this.ordenada = ehOrdenada;
        this.comparador = comparador;
    }

    public No<T> getPrim() {
        return prim;
    }

    public No<T> getUlt() {
        return ult;
    }

    public boolean inserirElemento(T elem) {
        try {
            if (this.ordenada) {
                inserirElementoOrd(elem);
            } else {
                inserirElementoNaoOrd(elem);
            }
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    public void inserirElementoNaoOrd(T elem) {
        No<T> novo = new No<T>(elem);
        if (this.prim == null){
            this.prim = novo;
        }
        else{
            this.ult.setProx(novo);
        }
        this.ult = novo;
        this.quantidade++;
    }

    // copiado e colado: "Lista Ordenada com Comparator"
    public void inserirElementoOrd(T elem) {
        No<T> novo = new No<T>(elem);
        No<T> atual, ant;
        atual = this.prim;
        ant = null;

        // se a lista está vazia, apenas adiciona como prim e ult
        if(this.prim == null)
            this.prim = this.ult = novo;
        else{
            // iterar até achar um elemento igual ou maior que o novo, ou um null
            while(atual != null && comparador.compare(atual.getValor(), elem) < 0){
                ant = atual;
                atual = atual.getProx();
            }

            // se ant == null, o novo elemento é menor que o primeiro
            if(ant == null){
                novo.setProx(this.prim);
                this.prim = novo;
            }
            // se atual for null, novo deve ser o último
            else if(atual == null){
                this.ult.setProx(novo);
                this.ult = novo;
            }
            // se não for prim nem ult, entrara entre o ant e o atual
            else{
                ant.setProx(novo);
                novo.setProx(atual);
            }
        }
        this.quantidade++;
    }

    // copiado e colado: "Buscar na Lista"
    public T contemElemento(T elem) {
        No<T> aux = this.prim;
        while (aux != null){
            if (aux.getValor().equals(elem))
                return aux.getValor();
            aux = aux.getProx();
        }
        return null;
    }

    // copiado e colado: "Excluir da Lista"
    public boolean excluirElemento(T elem) {
        No<T> aux = this.prim;
        No<T> ant = null;
        while (aux != null){ // lista não está vazia
            if (aux.getValor().equals(elem)) {
                // se for o primeiro, o prim se torna o segundo o próximo
                if(aux == this.prim){
                    this.prim = this.prim.getProx();
                    // também é o ult, prim = ult = null
                    if(aux == this.ult)
                        this.ult = null;
                }
                //Se não é o primeiro, o anterior passa a apontar para o proximo
                else{
                    ant.setProx(aux.getProx());
                    // se é o último, ult passa a ser o anterior
                    if(aux == this.ult)
                        this.ult = ant;
                }
                this.quantidade--;
                return true;
            }
            ant = aux;
            aux = aux.getProx();
        }
        return false;
    }

    @Override
    public String toString() {
        No<T> aux = this.prim;
        String s = "[";
        while (aux != null){
            s += aux.getValor();
            if (aux != this.ult)
                s += ", ";
            aux = aux.getProx();
        }
        return (s + "]");
    }

    @Override
    public boolean adicionar(T elemento) {
        return this.inserirElemento(elemento);
    }

    @Override
    public T pesquisar(T elemento) {
        return this.contemElemento(elemento);
    }

    @Override
    public boolean remover(T elemento) {
        return this.excluirElemento(elemento);
    }

    @Override
    public int quantidadeNos() {
        return this.quantidade;
    }
}