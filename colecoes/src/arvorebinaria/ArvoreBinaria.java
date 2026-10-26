package arvorebinaria;

import java.util.Comparator;
import java.util.function.Predicate;

/**
 * Classe concreta da Árvore Binária de Busca 100% Genérica.
 */
public class ArvoreBinaria<T> extends ArvoreBinariaBase<T> {

    protected No<T> raiz;

    public ArvoreBinaria(Comparator<T> comparador) {
        super(comparador);
        this.raiz = null;
    }

    @Override
    public boolean adicionar(T valor) {
        if (valor == null) {
            return false;
        }

        No<T> novoNo = new No<>(valor);

        // Se a árvore estiver vazia, o novo nó vira a raiz
        if (this.raiz == null) {
            this.raiz = novoNo;
            return true;
        }

        No<T> atual = this.raiz;
        No<T> pai = null;
        int comparacao = 0;

        // Navega iterativamente até encontrar uma folha/posição vaga
        while (atual != null) {
            pai = atual;
            comparacao = this.comparador.compare(valor, atual.getValor());

            if (comparacao < 0) {
                atual = atual.getFilhoEsquerda();
            } else if (comparacao > 0) {
                atual = atual.getFilhoDireita();
            } else {
                // Elemento duplicado
                return false;
            }
        }

        // Conecta o novo nó ao nó pai correspondente
        if (comparacao < 0) {
            pai.setFilhoEsquerda(novoNo);
        } else {
            pai.setFilhoDireita(novoNo);
        }

        return true;
    }

    public T pesquisarPorCondicao(Predicate<T> condicao) {
        if (this.raiz == null || condicao == null) {
            return null;
        }

        java.util.Queue<No<T>> fila = new java.util.LinkedList<>();
        fila.add(this.raiz);

        while (!fila.isEmpty()) {
            No<T> atual = fila.poll();

            if (condicao.test(atual.getValor())) {
                return atual.getValor();
            }

            if (atual.getFilhoEsquerda() != null) {
                fila.add(atual.getFilhoEsquerda());
            }
            if (atual.getFilhoDireita() != null) {
                fila.add(atual.getFilhoDireita());
            }
        }

        return null;
    }

    public T getUlt() {
        if (this.raiz == null) {
            return null;
        }
        No<T> atual = this.raiz;
        while (atual.getFilhoDireita() != null) {
            atual = atual.getFilhoDireita();
        }
        return atual.getValor();
    }

    @Override
    public T pesquisar(T valor) {
        if (valor == null || this.raiz == null) {
            return null;
        }

        No<T> atual = this.raiz;
        while (atual != null) {
            int comparacao = this.comparador.compare(valor, atual.getValor());

            if (comparacao == 0) {
                return atual.getValor();
            } else if (comparacao < 0) {
                atual = atual.getFilhoEsquerda();
            } else {
                atual = atual.getFilhoDireita();
            }
        }

        return null;
    }

    @Override
    public boolean remover(T valor) {
        if (valor == null || this.raiz == null) {
            return false;
        }

        No<T> atual = this.raiz;
        No<T> pai = null;

        // Busca o nó a ser removido mantendo a referência do pai
        while (atual != null) {
            int comparacao = this.comparador.compare(valor, atual.getValor());
            if (comparacao == 0) {
                break;
            }
            pai = atual;
            if (comparacao < 0) {
                atual = atual.getFilhoEsquerda();
            } else {
                atual = atual.getFilhoDireita();
            }
        }

        // Caso o elemento não exista na árvore
        if (atual == null) {
            return false;
        }

        // Casos 1 e 2: Nó possui no máximo 1 filho
        if (atual.getFilhoEsquerda() == null || atual.getFilhoDireita() == null) {
            No<T> novoFilho = (atual.getFilhoEsquerda() != null) ? atual.getFilhoEsquerda() : atual.getFilhoDireita();

            if (pai == null) {
                this.raiz = novoFilho; // Removendo a raiz principal
            } else if (pai.getFilhoEsquerda() == atual) {
                pai.setFilhoEsquerda(novoFilho);
            } else {
                pai.setFilhoDireita(novoFilho);
            }
        } else {
            // Caso 3: Nó possui 2 filhos
            // Encontra o menor elemento da subárvore direita (sucessor em-ordem)
            No<T> paiSucessor = atual;
            No<T> sucessor = atual.getFilhoDireita();

            while (sucessor.getFilhoEsquerda() != null) {
                paiSucessor = sucessor;
                sucessor = sucessor.getFilhoEsquerda();
            }

            // Copia o valor do sucessor para o nó corrente
            atual.setValor(sucessor.getValor());

            // Remove o nó sucessor original
            if (paiSucessor.getFilhoEsquerda() == sucessor) {
                paiSucessor.setFilhoEsquerda(sucessor.getFilhoDireita());
            } else {
                paiSucessor.setFilhoDireita(sucessor.getFilhoDireita());
            }
        }

        return true;
    }

    @Override
    public int quantidadeNos() {
        if (this.raiz == null) {
            return 0;
        }

        int contador = 0;
        java.util.Queue<No<T>> fila = new java.util.LinkedList<>();
        fila.add(this.raiz);

        while (!fila.isEmpty()) {
            No<T> atual = fila.poll();
            contador++;

            if (atual.getFilhoEsquerda() != null) {
                fila.add(atual.getFilhoEsquerda());
            }
            if (atual.getFilhoDireita() != null) {
                fila.add(atual.getFilhoDireita());
            }
        }

        return contador;
    }

    @Override
    public int altura() {
        if (this.raiz == null) {
            return -1;
        }

        int altura = -1;
        java.util.Queue<No<T>> fila = new java.util.LinkedList<>();
        fila.add(this.raiz);

        while (!fila.isEmpty()) {
            int tamanhoNivel = fila.size();
            altura++;

            for (int i = 0; i < tamanhoNivel; i++) {
                No<T> atual = fila.poll();

                if (atual.getFilhoEsquerda() != null) {
                    fila.add(atual.getFilhoEsquerda());
                }
                if (atual.getFilhoDireita() != null) {
                    fila.add(atual.getFilhoDireita());
                }
            }
        }

        return altura;
    }

    @Override
    public String caminharEmNivel() {
        if (this.raiz == null) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        java.util.Queue<No<T>> fila = new java.util.LinkedList<>();
        fila.add(this.raiz);

        while (!fila.isEmpty()) {
            int tamanhoNivel = fila.size();
            for (int i = 0; i < tamanhoNivel; i++) {
                No<T> atual = fila.poll();
                sb.append(atual.getValor().toString()).append(" ");

                if (atual.getFilhoEsquerda() != null) {
                    fila.add(atual.getFilhoEsquerda());
                }
                if (atual.getFilhoDireita() != null) {
                    fila.add(atual.getFilhoDireita());
                }
            }
            sb.append("\n");
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public String caminharEmOrdem() {
        if (this.raiz == null) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[");

        java.util.Deque<No<T>> pilha = new java.util.ArrayDeque<>();
        No<T> atual = this.raiz;

        while (atual != null || !pilha.isEmpty()) {
            while (atual != null) {
                pilha.push(atual);
                atual = atual.getFilhoEsquerda();
            }

            atual = pilha.pop();
            sb.append(atual.getValor().toString()).append(", ");
            atual = atual.getFilhoDireita();
        }

        String resultado = sb.toString();
        if (resultado.endsWith(", ")) {
            resultado = resultado.substring(0, resultado.length() - 2);
        }
        return resultado + "]";
    }

    public T getNoMaisDistante() {
        if (this.raiz == null) {
            return null;
        }

        java.util.Queue<No<T>> fila = new java.util.LinkedList<>();
        fila.add(this.raiz);
        No<T> ultimoNóVisitado = null;

        while (!fila.isEmpty()) {
            ultimoNóVisitado = fila.poll();

            if (ultimoNóVisitado.getFilhoEsquerda() != null) {
                fila.add(ultimoNóVisitado.getFilhoEsquerda());
            }
            if (ultimoNóVisitado.getFilhoDireita() != null) {
                fila.add(ultimoNóVisitado.getFilhoDireita());
            }
        }

        return ultimoNóVisitado != null ? ultimoNóVisitado.getValor() : null;
    }
}
