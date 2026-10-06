package arvorebinaria;

import java.util.Comparator;

public class ArvoreBinaria<T> extends ArvoreBinariaBase<T> {
    protected No<T> raiz;

    public ArvoreBinaria(Comparator<T> comparador) {
        super(comparador); // repassa o comparador para a classe base
        this.raiz = null;  // começa vazia
    }

    @Override
    public boolean adicionar(T novoValor) {
        No<T> novoNo = new No<T>(novoValor);

        // se a árvore estiver vazia, o novo nó se torna a raiz
        if (this.raiz == null) {
            this.raiz = novoNo;
        } else {
            // caso contrário, usa a recursão para encontrar o lugar certo
            this.raiz = addRecursao(this.raiz, novoNo);
        }
        return true; // retorna true para sinalizar sucesso na inserção
    }

    protected No<T> addRecursao(No<T> atual, No<T> novo) {
        // usa o comparador herdado da base para decidir a direção
        // se o resultado for menor que 0, o novo valor é menor, então vai para a esquerda
        if (this.comparador.compare(novo.getValor(), atual.getValor()) < 0) {
            if (atual.getFilhoEsquerda() == null) {
                atual.setFilhoEsquerda(novo);
            } else {
                atual.setFilhoEsquerda(addRecursao(atual.getFilhoEsquerda(), novo));
            }
        }
        // se for maior ou igual, vai para a direita
        else {
            if (atual.getFilhoDireita() == null) {
                atual.setFilhoDireita(novo);
            } else {
                atual.setFilhoDireita(addRecursao(atual.getFilhoDireita(), novo));
            }
        }
        return atual;
    }

    private T pesquisarRecursivo(No<T> no, T valor) {
        // chegou em uma folha e não encontrou
        if (no == null) {
            return null;
        }

        // compara o valor buscado com o valor do nó atual
        int comparacao = this.comparador.compare(valor, no.getValor());

        if (comparacao == 0) {
            // encontrou
            return no.getValor();
        } else if (comparacao < 0) {
            // se é menor, continua a busca pelo filho à esquerda
            return pesquisarRecursivo(no.getFilhoEsquerda(), valor);
        } else {
            // se valor é maior, continua a busca pelo filho à direita
            return pesquisarRecursivo(no.getFilhoDireita(), valor);
        }
    }

    private int contarNos(No<T> no) {
        // se é nulo, não conta
        if (no == null) {
            return 0;
        }
        //  total = 1 (atual) + quantidade da esquerda + quantidade da direita
        return 1 + contarNos(no.getFilhoEsquerda()) + contarNos(no.getFilhoDireita());
    }

    private No<T> removerRecursivo(No<T> no, T valor) {
        if (no == null) {
            return null;
        }

        int comparacao = this.comparador.compare(valor, no.getValor());

        if (comparacao < 0) {
            // se é menor, a remoção deve ocorrer na subárvore esquerda
            no.setFilhoEsquerda(removerRecursivo(no.getFilhoEsquerda(), valor));
        } else if (comparacao > 0) {
            // se é maior, a remoção deve ocorrer na subárvore direita
            no.setFilhoDireita(removerRecursivo(no.getFilhoDireita(), valor));
        } else {
            // encontrou

            // nó não tem filho à esquerda (pode ser folha ou ter apenas o filho da direita)
            if (no.getFilhoEsquerda() == null) {
                return no.getFilhoDireita();
            }
            // nó não tem filho à direita (tem apenas o filho da esquerda)
            else if (no.getFilhoDireita() == null) {
                return no.getFilhoEsquerda();
            }

            // nó tem dois filhos
            // achar sucessor
            T menorValorSubArvoreDireita = encontrarMenorValor(no.getFilhoDireita());

            // substituir nó atual pelo valor do sucessor
            no.setValor(menorValorSubArvoreDireita);

            // remover sucessor da posição original
            no.setFilhoDireita(removerRecursivo(no.getFilhoDireita(), menorValorSubArvoreDireita));
        }

        return no;
    }

    // -1 se for nulo, 0 se só raiz
    private int calcularAltura(No<T> no) {
        if (no == null) {
            return -1;
        }

        int alturaEsquerda = calcularAltura(no.getFilhoEsquerda());
        int alturaDireita = calcularAltura(no.getFilhoDireita());

        if (alturaEsquerda > alturaDireita) {
            return alturaEsquerda + 1;
        } else {
            return alturaDireita + 1;
        }
    }

    public T pesquisarPorNome(String nome) {
        return pesquisarPorNomeRecursivo(this.raiz, nome);
    }

    private T pesquisarPorNomeRecursivo(No<T> no, String nome) {
        if (no == null) {
            return null;
        }
        T valor = no.getValor();

        if (valor instanceof dominio.Matricula) {
            if (((dominio.Matricula) valor).getNomeAluno().equalsIgnoreCase(nome)) {
                return valor;
            }
        }

        T resultadoEsquerda = pesquisarPorNomeRecursivo(no.getFilhoEsquerda(), nome);
        if (resultadoEsquerda != null) {
            return resultadoEsquerda;
        }

        return pesquisarPorNomeRecursivo(no.getFilhoDireita(), nome);
    }

    public T pesquisarPorNumero(int numeroBuscado) {
        return pesquisarPorNumeroRecursivo(this.raiz, numeroBuscado);
    }

    private T pesquisarPorNumeroRecursivo(No<T> no, int numeroBuscado) {
        if (no == null) {
            return null;
        }

        T valor = no.getValor();
        if (valor instanceof dominio.Matricula) {
            if (((dominio.Matricula) valor).getNumeroMatricula() == numeroBuscado) {
                return valor;
            }
        }

        T resultadoEsquerda = pesquisarPorNumeroRecursivo(no.getFilhoEsquerda(), numeroBuscado);
        if (resultadoEsquerda != null) {
            return resultadoEsquerda;
        }

        return pesquisarPorNumeroRecursivo(no.getFilhoDireita(), numeroBuscado);
    }

    public T getUlt() {
        if (this.raiz == null) {
            return null;
        }
        arvorebinaria.No<T> atual = this.raiz;
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
        // Iniciamos a busca a partir da raiz da árvore
        return pesquisarRecursivo(this.raiz, valor);
    }

    @Override
    public boolean remover(T valor) {
        // Primeiro, verificamos se o valor existe na árvore.
        // Se não existir, retornamos false imediatamente[cite: 33].
        if (pesquisar(valor) == null) {
            return false;
        }

        // Se existir, chamamos o método recursivo para efetuar a remoção e atualizamos a raiz
        this.raiz = removerRecursivo(this.raiz, valor);
        return true;
    }

    @Override
    public int quantidadeNos() {
        return contarNos(this.raiz);
    }


    // obritatórios da interface

    @Override
    public int altura() {
        return calcularAltura(this.raiz);
    }

    /**
     * Retorna o resultado do caminhamento em nível na árvore[cite: 34].
     */
    @Override
    public String caminharEmNivel() {
        if (this.raiz == null) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[\n"); // A string deve iniciar com "["[cite: 34]

        // Utilizamos uma Fila para processar os nós nível por nível (Busca em Largura)
        java.util.Queue<No<T>> fila = new java.util.LinkedList<>();
        fila.add(this.raiz);

        while (!fila.isEmpty()) {
            int tamanhoNivel = fila.size(); // Quantidade de nós no nível atual

            // Processa todos os nós do nível atual para mantê-los na mesma linha[cite: 34]
            for (int i = 0; i < tamanhoNivel; i++) {
                No<T> atual = fila.poll();
                sb.append(atual.getValor().toString()).append(" ");

                // Adiciona os filhos na fila para serem processados no próximo nível
                if (atual.getFilhoEsquerda() != null) {
                    fila.add(atual.getFilhoEsquerda());
                }
                if (atual.getFilhoDireita() != null) {
                    fila.add(atual.getFilhoDireita());
                }
            }
            sb.append("\n"); // Os elementos de cada nível devem ficar em uma linha da String[cite: 34]
        }

        sb.append("]"); // A string deve finalizar com "]"[cite: 34]
        return sb.toString();
    }

    @Override
    public String caminharEmOrdem() {
        StringBuilder sb = new StringBuilder();
        sb.append("["); // A string deve iniciar com "["

        caminharEmOrdemRecursivo(this.raiz, sb);

        // Remove a última vírgula e o espaço extra, caso existam elementos
        String resultado = sb.toString();
        if (resultado.endsWith(", ")) {
            resultado = resultado.substring(0, resultado.length() - 2);
        }

        return resultado + "]"; // A string deve finalizar com "]"[cite: 34]
    }
}