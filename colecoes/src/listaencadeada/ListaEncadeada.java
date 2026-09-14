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

    
    
    
    public void inserirElementoNaoOrd(T elem) {
        No<T> novo = new No<T>(elem);
        
        if (this.prim == null){
            this.prim = novo;
            this.ult = novo;
        }
       
        else{
            this.ult.setProx(novo);
            this.ult = novo;
        }
        this.quantidade++;
    }

    
    public void inserirElementoOrd(T elem) {
        No<T> novo = new No<T>(elem);
        No<T> atual, ant;
        atual = this.prim;
        ant = null;

       
        if(this.prim == null)
            this.prim = this.ult = novo;
            
        else{
            
            
            while(atual != null && comparador.compare(atual.getValor(), elem) < 0){
                ant = atual;
                atual = atual.getProx();
            }

            
            
            if(ant == null){
                novo.setProx(this.prim);
                this.prim = novo;
            }
            
            
            else if(atual == null){
                this.ult.setProx(novo);
                this.ult = novo;
            }
            
            else{
                ant.setProx(novo);
                novo.setProx(atual);
            }
        }
        this.quantidade++;
    }

    
    public T contemElemento(T elem) {
        No<T> aux = this.prim;
        while (aux != null){
            if (aux.getValor().equals(elem))
                return aux.getValor();
            aux = aux.getProx();
        }
        return null;
    }

    
    public boolean excluirElemento(T elem) {
        No<T> aux = this.prim;
        No<T> ant = null;
        while (aux != null){
            
            if (aux.getValor().equals(elem)) {
                
                if(aux == this.prim){
                    this.prim = this.prim.getProx();
                    
                    if(aux == this.ult)
                        this.ult = null;
                }
                
                else{
                    ant.setProx(aux.getProx());
                    
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
