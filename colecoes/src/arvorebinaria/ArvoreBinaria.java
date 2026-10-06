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
    public boolean adicionar(T novoValor) {
        No<T> novoNo = new No<T>(novoValor);
        if (this.raiz == null) {
            this.raiz = novoNo;
        } else {
            this.raiz = addRecursao(this.raiz, novoNo);
        }
        return true;
    }

    protected No<T> addRecursao(No<T> atual, No<T> novo) {
        if (this.comparador.compare(novo.getValor(), atual.getValor()) < 0) {
            if (atual.getFilhoEsquerda() == null) {
                atual.setFilhoEsquerda(novo);
            } else {
                atual.setFilhoEsquerda(addRecursao(atual.getFilhoEsquerda(), novo));
            }
        } else {
            if (atual.getFilhoDireita() == null) {
                atual.setFilhoDireita(novo);
            } else {
                atual.setFilhoDireita(addRecursao(atual.getFilhoDireita(), novo));
            }
        }
        return atual;
    }

    private T pesquisarRecursivo(No<T> no, T valor) {
        if (no == null) {
            return null;
        }

        int comparacao = this.comparador.compare(valor, no.getValor());

        if (comparacao == 0) {
            return no.getValor();
        } else if (comparacao < 0) {
            return pesquisarRecursivo(no.getFilhoEsquerda(), valor);
        } else {
            return pesquisarRecursivo(no.getFilhoDireita(), valor);
        }
    }

    /**
     * Pesquisa genérica por condição (Predicate), permitindo varreduras por
     * atributos que não são a chave primária de indexação da árvore.
     */
    public T pesquisarPorCondicao(Predicate<T> condicao) {
        return pesquisarPorCondicaoRecursivo(this.raiz, condicao);
    }

    private T pesquisarPorCondicaoRecursivo(No<T> no, Predicate<T> condicao) {
        if (no == null) {
            return null;
        }

        if (condicao.test(no.getValor())) {
            return no.getValor();
        }

        T resultadoEsquerda = pesquisarPorCondicaoRecursivo(no.getFilhoEsquerda(), condicao);
        if (resultadoEsquerda != null) {
            return resultadoEsquerda;
        }

        return pesquisarPorCondicaoRecursivo(no.getFilhoDireita(), condicao);
    }

    private int contarNos(No<T> no) {
        if (no == null) {
            return 0;
        }
        return 1 + contarNos(no.getFilhoEsquerda()) + contarNos(no.getFilhoDireita());
    }

    private No<T> removerRecursivo(No<T> no, T valor) {
        if (no == null) {
            return null;
        }

        int comparacao = this.comparador.compare(valor, no.getValor());

        if (comparacao < 0) {
            no.setFilhoEsquerda(removerRecursivo(no.getFilhoEsquerda(), valor));
        } else if (comparacao > 0) {
            no.setFilhoDireita(removerRecursivo(no.getFilhoDireita(), valor));
        } else {
            if (no.getFilhoEsquerda() == null) {
                return no.getFilhoDireita();
            } else if (no.getFilhoDireita() == null) {
                return no.getFilhoEsquerda();
            }

            T menorValorSubArvoreDireita = encontrarMenorValor(no.getFilhoDireita());
            no.setValor(menorValorSubArvoreDireita);
            no.setFilhoDireita(removerRecursivo(no.getFilhoDireita(), menorValorSubArvoreDireita));
        }

        return no;
    }

    private int calcularAltura(No<T> no) {
        if (no == null) {
            return -1;
        }
        int alturaEsquerda = calcularAltura(no.getFilhoEsquerda());
        int alturaDireita = calcularAltura(no.getFilhoDireita());
        return Math.max(alturaEsquerda, alturaDireita) + 1;
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

    private void caminharEmOrdemRecursivo(No<T> no, StringBuilder sb) {
        if (no != null) {
            caminharEmOrdemRecursivo(no.getFilhoEsquerda(), sb);
            sb.append(no.getValor().toString()).append(", ");
            caminharEmOrdemRecursivo(no.getFilhoDireita(), sb);
        }
    }

    private T encontrarMenorValor(No<T> no) {
        T menorValor = no.getValor();
        while (no.getFilhoEsquerda() != null) {
            menorValor = no.getFilhoEsquerda().getValor();
            no = no.getFilhoEsquerda();
        }
        return menorValor;
    }

    @Override
    public T pesquisar(T valor) {
        return pesquisarRecursivo(this.raiz, valor);
    }

    @Override
    public boolean remover(T valor) {
        if (pesquisar(valor) == null) {
            return false;
        }
        this.raiz = removerRecursivo(this.raiz, valor);
        return true;
    }

    @Override
    public int quantidadeNos() {
        return contarNos(this.raiz);
    }

    @Override
    public int altura() {
        return calcularAltura(this.raiz);
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
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        caminharEmOrdemRecursivo(this.raiz, sb);
        String resultado = sb.toString();
        if (resultado.endsWith(", ")) {
            resultado = resultado.substring(0, resultado.length() - 2);
        }
        return resultado + "]";
    }
}
