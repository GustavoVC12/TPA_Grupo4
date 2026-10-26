package arvorebinaria;

import java.util.Comparator;

public class ArvoreAVL<T> extends ArvoreBinaria<T> {

    public ArvoreAVL(Comparator<T> comparador) {
        super(comparador);
    }

    @Override
    public boolean adicionar(T novoValor) {
        if (novoValor == null) {
            return false;
        }
        this.raiz = adicionarRecursivoAVL(this.raiz, novoValor);
        return true;
    }

    private No<T> adicionarRecursivoAVL(No<T> no, T valor) {
        if (no == null) {
            return new No<T>(valor);
        }

        int comparacao = this.comparador.compare(valor, no.getValor());

        if (comparacao < 0) {
            no.setFilhoEsquerda(adicionarRecursivoAVL(no.getFilhoEsquerda(), valor));
        } else {
            no.setFilhoDireita(adicionarRecursivoAVL(no.getFilhoDireita(), valor));
        }

        // Aplica o rebalanceamento no caminho de volta da recursão
        return rebalancear(no);
    }

    @Override
    public boolean remover(T valor) {
        if (pesquisar(valor) == null) {
            return false;
        }
        this.raiz = removerRecursivoAVL(this.raiz, valor);
        return true;
    }

    private No<T> removerRecursivoAVL(No<T> no, T valor) {
        if (no == null) {
            return null;
        }

        int comparacao = this.comparador.compare(valor, no.getValor());

        if (comparacao < 0) {
            no.setFilhoEsquerda(removerRecursivoAVL(no.getFilhoEsquerda(), valor));
        } else if (comparacao > 0) {
            no.setFilhoDireita(removerRecursivoAVL(no.getFilhoDireita(), valor));
        } else {
            // Nó encontrado
            if (no.getFilhoEsquerda() == null) {
                return no.getFilhoDireita();
            } else if (no.getFilhoDireita() == null) {
                return no.getFilhoEsquerda();
            }

            // Nó com dois filhos: obtém o menor valor da subárvore direita
            T menorValorSubArvoreDireita = encontrarMenorValor(no.getFilhoDireita());
            no.setValor(menorValorSubArvoreDireita);
            no.setFilhoDireita(removerRecursivoAVL(no.getFilhoDireita(), menorValorSubArvoreDireita));
        }

        // Aplica o rebalanceamento após a remoção
        return rebalancear(no);
    }

    // --- Métodos de Auxílio e Balanceamento AVL ---

    private int getAltura(No<T> no) {
        if (no == null) {
            return -1;
        }
        return Math.max(getAltura(no.getFilhoEsquerda()), getAltura(no.getFilhoDireita())) + 1;
    }

    private int fatorBalanco(No<T> no) {
        if (no == null) {
            return 0;
        }
        return getAltura(no.getFilhoEsquerda()) - getAltura(no.getFilhoDireita());
    }

    private No<T> rebalancear(No<T> no) {
        if (no == null) {
            return null;
        }

        int fb = fatorBalanco(no);

        // Caso 1: Desbalanceado para a Esquerda (FB > 1)
        if (fb > 1) {
            if (fatorBalanco(no.getFilhoEsquerda()) < 0) {
                // Rotação Dupla à Direita (Esquerda-Direita / LR)
                no.setFilhoEsquerda(rotacaoEsquerda(no.getFilhoEsquerda()));
            }
            return rotacaoDireita(no);
        }

        // Caso 2: Desbalanceado para a Direita (FB < -1)
        if (fb < -1) {
            if (fatorBalanco(no.getFilhoDireita()) > 0) {
                // Rotação Dupla à Esquerda (Direita-Esquerda / RL)
                no.setFilhoDireita(rotacaoDireita(no.getFilhoDireita()));
            }
            return rotacaoEsquerda(no);
        }

        return no;
    }

    private No<T> rotacaoDireita(No<T> y) {
        No<T> x = y.getFilhoEsquerda();
        No<T> T2 = x.getFilhoDireita();

        x.setFilhoDireita(y);
        y.setFilhoEsquerda(T2);

        return x;
    }

    private No<T> rotacaoEsquerda(No<T> x) {
        No<T> y = x.getFilhoDireita();
        No<T> T2 = y.getFilhoEsquerda();

        y.setFilhoEsquerda(x);
        x.setFilhoDireita(T2);

        return y;
    }

    private T encontrarMenorValor(No<T> no) {
        T menorValor = no.getValor();
        while (no.getFilhoEsquerda() != null) {
            menorValor = no.getFilhoEsquerda().getValor();
            no = no.getFilhoEsquerda();
        }
        return menorValor;
    }
}