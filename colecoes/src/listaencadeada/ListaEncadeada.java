package listaencadeada;

// import colecao.IColecao;
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
            if (this.ordenada == false)
                inserirElementoNaoOrd(elem);
            else
                inserirElementoOrd(elem);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    // A fazer: O slide da lista ordenada apenas cita o método 'inserirElementoNaoOrd(elem)',
    // sem dar a estrutura com este nome exato. Foi colado aqui o método "Inserir na Lista" (padrão no final)
    // adaptado para o tipo genérico T.
    public void inserirElementoNaoOrd(T elem) {
        No<T> novo = new No<T>(elem);
        //Se a lista está vazia
        if (this.prim == null){
            this.prim = novo;
            this.ult = novo;
        }
        //Se não estiver colocarei o elemento no fim da Lista
        else{
            this.ult.setProx(novo);
            this.ult = novo;
        }
        this.quantidade++;
    }

    // Copiado e colado: "Lista Ordenada com Comparator"
    public void inserirElementoOrd(T elem) {
        No<T> novo = new No<T>(elem);
        No<T> atual, ant;
        atual = this.prim;
        ant = null;

        //Se a lista estiver vazia o novo elemento será primeiro e último
        if(this.prim == null)
            this.prim = this.ult = novo;
            //Se não estava vazia
        else{
            //Enquanto não for o fim da lista e o atual estiver
            //em um elemento menor que o novo vou para o proximo
            while(atual != null && comparador.compare(atual.getValor(), elem) < 0){
                ant = atual;
                atual = atual.getProx();
            }

            //Se ant estiver null é sinal de que não entrou no laço
            //Logo, o novo é menor do que o prim e deve entrar como novo prim
            if(ant == null){
                novo.setProx(this.prim);
                this.prim = novo;
            }
            //Se atual for null é sinal que varreu toda a lista e o novo deve
            //entrar como ult
            else if(atual == null){
                this.ult.setProx(novo);
                this.ult = novo;
            }
            //Se não for prim nem ult, entrara entre o ant e o atual
            else{
                ant.setProx(novo);
                novo.setProx(atual);
            }
        }
        this.quantidade++;
    }

    // Copiado e colado: "Buscar na Lista" (Adaptado Object para T)
    public T contemElemento(T elem) {
        No<T> aux = this.prim;
        while (aux != null){
            if (aux.getValor().equals(elem))
                return aux.getValor();
            aux = aux.getProx();
        }
        return null;
    }

    // Copiado e colado: "Excluir da Lista" (Adaptado Object para T)
    public boolean excluirElemento(T elem) {
        No<T> aux = this.prim;
        No<T> ant = null;
        while (aux != null){
            //Se encontrou remove o elemento
            if (aux.getValor().equals(elem)) {
                //Se for o primeiro elemento, o prim passa a apontar para o próximo
                if(aux == this.prim){
                    this.prim = this.prim.getProx();
                    //Verifico se tbm é o ult, ou seja, era o unico da lista
                    if(aux == this.ult)
                        this.ult = null;
                }
                //Se não é o primeiro, o anterior passa a apontar para o proximo
                else{
                    ant.setProx(aux.getProx());
                    //Se ele for o ult, o ult passa a ser o ant
                    if(aux == this.ult)
                        this.ult = ant;
                }
                //decremento a quantidade
                this.quantidade--;
                return true;
            }
            //Se não encontrou, ant vai pra aux e aux vai para o proximo.
            ant = aux;
            aux = aux.getProx();
        }
        //Se rodou tudo sem encontrar retorna false
        return false;
    }

    // Copiado e colado: "Sobrescrevendo o toString da Lista"
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

    // ====================================================================================
    // Implementação dos métodos obrigatórios exigidos pela interface IColecao
    // ====================================================================================

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