package Service;

import java.io.*;
import java.time.Duration;
import java.time.Instant;

import Util.Utilidades;
import colecao.IColecao;
import dominio.Contato;
import dominio.Resultado;
import listaencadeada.ListaEncadeada;
import listaencadeada.No;
import arvorebinaria.ArvoreBinaria;

public class Sistema {

    //======================== MANIPULAÇÃO DE LISTAS ========================//
    // Carregar dados do arquivo texto
    public static void carregarContatosLista(IColecao<Contato> lista) {
        // definir temporizadores
        Instant fim;
        Instant inicio = Instant.now();

        //abrir arquivo
        try {
            File arquivo = new File("entrada.txt");
            arquivo.createNewFile();
        } catch (IOException e) {
            System.out.println("Deu pau no carregamento");
        }

        // transformar texto do arquivo em objetos e adicionar à lista
        try (BufferedReader leitor = new BufferedReader(new FileReader("entrada.txt"))) {
            String linha;
            Contato contato;
            while ((linha = leitor.readLine()) != null) {
                String[] atributos = linha.split(";");
                contato = new Contato(atributos[0],Long.parseLong(atributos[1]));
                // como o gerador não entrega números repetidos, a checagem foi desabilitada
                //if (!contatoExiste(lista, contato)){
                lista.adicionar(contato);
                //}
            }
            System.out.println("Lista carregada!");
        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao carregar a lista.");
        }
        fim = Instant.now();
        System.out.println("Tempo decorrido: " + Duration.between(inicio,fim).toMillis() + "ms");
    }

    // Adicionar contato
    public static void adicionarContatoLista(IColecao<Contato> lista) {
        System.out.println("======== Criando novo contato ========");
        String nome = Utilidades.lerString("Nome: ");
        long telefone = Utilidades.lerLong("Telefone: ");

        Contato contato = new Contato(nome, telefone);
        if(!contatoExiste(lista, contato)) {
            lista.adicionar(contato);
            System.out.println("Contato adicionado!");
            return;
        }
        System.out.println("Este telefone já foi cadastrado.");
    }
    public static boolean adicionarContatoAtualizadoLista(IColecao<Contato> lista) {
        String nome = Utilidades.lerString("Nome atualizado: ");
        long telefone = Utilidades.lerLong("Telefone atualizado: ");
        Contato contato = new Contato(nome, telefone);
        if(!contatoExiste(lista, contato)) {
            lista.adicionar(contato);
            return true;
        }
        return false;
    }

    // Pesquisar contato
    // Pesquisa geral
    public static void pesquisarContatoLista(IColecao<Contato> lista, int tipo) {
        long inicio, fim;
        Contato contato;

        System.out.println("======= Pesquisa de contato =======");
        if (tipo == 0) {
            String nome = Utilidades.lerString("Nome: ");
            inicio = System.nanoTime(); // Marcação em nanossegundos
            contato = pesquisarContatoPorNomeLista(lista, nome);
            fim = System.nanoTime();
        } else {
            long telefone = Utilidades.lerLong("Telefone: ");
            inicio = System.nanoTime();
            contato = pesquisarContatoPorTelefoneLista(lista, telefone);
            fim = System.nanoTime();
        }
        // Execução extremamente rápida, logo optou-se por usar nanossegundos ao invés de milissegundos
        long duracaoNanos = fim - inicio;

        if (contato != null) {
            System.out.println("Correspondência encontrada: " + contato.getNome() + " - " + contato.getTelefone());
        } else {
            System.out.println("Nenhum contato encontrado.");
        }
        System.out.printf("Tempo decorrido: %.4f ms %n", duracaoNanos / 1_000_000.0);
    }
    // Pesquisas auxiliares
    public static Contato pesquisarContatoPorNomeLista(IColecao<Contato> lista, String nome) {
        // Se for a Lista Encadeada antiga, usa a busca sequencial nó a nó
        if (lista instanceof ListaEncadeada) {
            ListaEncadeada<Contato> listaAux = (ListaEncadeada<Contato>) lista;
            No<Contato> aux = listaAux.getPrim();

            while (aux != null){
                if (aux.getValor().getNome().equalsIgnoreCase(nome)){
                    return aux.getValor();
                }
                aux = aux.getProx();
            }
            return null;
        }
        return null;
    }
    public static Contato pesquisarContatoPorTelefoneLista(IColecao<Contato> lista, long telefone) {
        Contato contato = new Contato("", telefone);
        return lista.pesquisar(contato);
    }

    // Remover contato
    public static Resultado removerContatoPorTelefoneLista(IColecao<Contato> lista) {
        long telefoneRemover = Utilidades.lerLong("Telefone do contato a ser removido: ");
        Instant inicio = Instant.now();
        boolean removido = lista.remover(pesquisarContatoPorTelefoneLista(lista, telefoneRemover));
        Instant fim = Instant.now();
        return new Resultado(removido, Duration.between(inicio,fim).toMillis());
    }
    public static void removerContatoPorTelefoneLista(IColecao<Contato> lista, Contato contato) {
        lista.remover(contato);
    }

    // Atualizar contato
    public static void atualizarContatoLista(IColecao<Contato> lista) {
        System.out.println("====== Atualizando Contato ======"); // mantido conforme o original
        Contato contatoRemover = pesquisarContatoPorNomeLista(lista, Utilidades.lerString("Nome do contato a ser atualizado: "));
        if (contatoRemover != null) {
            boolean sucesso = adicionarContatoAtualizadoLista(lista);
            if (sucesso) {
                removerContatoPorTelefoneLista(lista, contatoRemover);
                System.out.println("Contato atualizado com sucesso!");
            }
            else {
                System.out.println("O número especificado já consta no sistema.");
            }
            return;
        }
        System.out.println("Contato não encontrado.");
    }
    public static void excluirDadosLista(IColecao<Contato> l) {
        if (l instanceof ListaEncadeada) {
            ListaEncadeada<Contato> lAux = (ListaEncadeada<Contato>) l;
            No<Contato> aux = lAux.getPrim();
            if (aux == null) {
                System.out.println("Lista estava vazia.");
                return;
            }
            // Guarda a referência do próximo antes de remover o atual para não quebrar o while
            while (aux != null){
                No<Contato> prox = aux.getProx();
                removerContatoPorTelefoneLista(l, aux.getValor());
                aux = prox;
            }
            System.out.println("Lista esvaziada.");
        } else {
            System.out.println("Para esvaziar a Árvore, feche e abra o sistema (ou re-instancie a coleção no Main).");
        }
    }

    // Último contato
    public static void ultimoContato(IColecao<Contato> l) {
        if (l instanceof ListaEncadeada) {
            ListaEncadeada<Contato> lAux = (ListaEncadeada<Contato>) l;
            if (lAux.getUlt() != null) {
                Contato ult = lAux.getUlt().getValor();
                System.out.println("Último contato: " + ult.getNome() + " - " + ult.getTelefone());
            } else {
                System.out.println("A lista está vazia.");
            }
        } else if (l instanceof ArvoreBinaria) {
            ArvoreBinaria<Contato> aAux = (ArvoreBinaria<Contato>) l;
            Contato ult = aAux.getUlt();
            if (ult != null) {
                System.out.println("Último contato: " + ult.getNome() + " - " + ult.getTelefone());
            } else {
                System.out.println("A árvore está vazia.");
            }
        }
    }

    // Contato existe
    public static boolean contatoExiste(IColecao<Contato> l, Contato c) {
        return l.pesquisar(c) != null;
    }



    //======================== MANIPULAÇÃO DE ARVORES ========================//
    // Carregar dados do arquivo texto
    public static void carregarContatosArvore(IColecao<Contato> arvore) {
        Instant fim;
        Instant inicio = Instant.now();
        File arquivo = new File("entrada.txt");

        try {
            arquivo.createNewFile();
        } catch (IOException e) {
            System.out.println("Deu pau no carregamento");
        }

        try (BufferedReader leitor = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                String[] atributos = linha.split(";");
                String nome = atributos[0];
                long telefone = Long.parseLong(atributos[1]);

                arvore.adicionar(new Contato(nome, telefone));
            }
            System.out.println("Árvore carregada!");
        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao carregar a árvore...");
        }
        fim = Instant.now();
        System.out.println("Tempo decorrido: " + Duration.between(inicio,fim).toMillis() + "ms");
    }

    // Adicionar contato
    public static void adicionarContatoArvore(IColecao<Contato> arvore) {
        Instant fim;
        Instant inicio = Instant.now();
        System.out.println("======== Criando novo contato ========");
        String nome = Utilidades.lerString("Nome: ");
        long telefone = Utilidades.lerLong("Telefone: ");

        Contato contato = new Contato(nome, telefone);
        if (!contatoExiste(arvore, contato)) {
            arvore.adicionar(contato);
            System.out.println("Contato adicionado com sucesso!");
            return;
        }
        System.out.println("Este número de telefone já está cadastrado.");

        fim = Instant.now();
        System.out.println("Tempo decorrido: " + Duration.between(inicio,fim).toMillis() + "ms");
    }

    // Pesquisa geral
    public static void pesquisarContatoArvore(IColecao<Contato> arvore, int tipo) {
        Instant fim;
        Instant inicio = Instant.now();
        Contato contato;

        System.out.println("======= Pesquisa de contato (Árvore) =======");
        if (tipo == 0) {
            String nome = Utilidades.lerString("Nome: ");
            contato = pesquisarContatoPorNomeArvore(arvore, nome);
        } else {
            long telefone = Utilidades.lerLong("Telefone: ");
            contato = pesquisarContatoPorTelefoneArvore(arvore, telefone);
        }

        if (contato != null) {
            System.out.println("Correspondência encontrada: " + contato.getNome() + " - " + contato.getTelefone());
        } else {
            System.out.println("Nenhum contato encontrado.");
        }
        fim = Instant.now();
        System.out.println("Tempo decorrido: " + Duration.between(inicio,fim).toMillis() + "ms");
    }
    // Pesquisas auxiliares
    public static Contato pesquisarContatoPorNomeArvore(IColecao<Contato> arvore, String nome) {
        if (arvore instanceof ArvoreBinaria) {
            ArvoreBinaria<Contato> arvoreBinaria = (ArvoreBinaria<Contato>) arvore;
            return arvoreBinaria.pesquisarPorCondicao(c -> c.getNome().equalsIgnoreCase(nome));
        }
        return null;
    }
    public static Contato pesquisarContatoPorTelefoneArvore(IColecao<Contato> arvore, long telefone) {
        if (arvore instanceof ArvoreBinaria) {
            ArvoreBinaria<Contato> aux = (ArvoreBinaria<Contato>) arvore;

            // Se a árvore estiver indexada por telefone, usa a busca binária rápida O(log n)
            // Caso contrário, usa a varredura por condição O(n)
            Contato busca = new Contato("", telefone);
            Contato resultadoBuscaBinaria = arvore.pesquisar(busca);

            if (resultadoBuscaBinaria != null) {
                return resultadoBuscaBinaria;
            }

            // Fallback para varredura completa caso a árvore esteja indexada por nome
            return aux.pesquisarPorCondicao(c -> c.getTelefone() == telefone);
        }
        return null;
    }

    // Remover contato da árvore
    public static Resultado removerContatoArvore(IColecao<Contato> arvore) {
        long telefoneRemover = Utilidades.lerLong("Telefone do contato a ser removido: ");

        Instant inicio = Instant.now();
        Contato contatoEncontrado = pesquisarContatoPorTelefoneArvore(arvore, telefoneRemover);
        boolean removido = false;

        if (contatoEncontrado != null) {
            removido = arvore.remover(contatoEncontrado);
        }
        Instant fim = Instant.now();

        if (removido) {
            System.out.println("Contato removido com sucesso!");
        } else {
            System.out.println("Contato não encontrado ou erro ao remover.");
        }

        return new Resultado(removido, Duration.between(inicio, fim).toMillis());
    }
    public static void removerContatoArvore(IColecao<Contato> arvore, Contato contato) {
        if (contato != null) {
            arvore.remover(contato);
        }
    }

    // Atualizar contato
    public static void atualizarContatoArvore(IColecao<Contato> arvore) {
        System.out.println("====== Atualizando Contato (Árvore) ======");
        long telefoneBusca = Utilidades.lerLong("Telefone do contato a ser atualizado: ");
        Contato contatoRemover = pesquisarContatoPorTelefoneArvore(arvore, telefoneBusca);

        if (contatoRemover != null) {
            boolean sucesso = adicionarContatoAtualizadoLista(arvore);
            if (sucesso) {
                removerContatoArvore(arvore, contatoRemover);
                System.out.println("Contato atualizado com sucesso!");
            } else {
                System.out.println("O número de telefone especificado já consta no sistema.");
            }
            return;
        }
        System.out.println("Contato não encontrado.");
    }

    // Contato mais distante
    public static Contato contatoMaisLonge(IColecao<Contato> arvore) {
        if (arvore instanceof ArvoreBinaria) {
            Instant fim;
            Instant inicio = Instant.now();
            ArvoreBinaria<Contato> arvoreBinaria = (ArvoreBinaria<Contato>) arvore;
            Contato contatoMaisLonge = arvoreBinaria.getNoMaisDistante();
            fim = Instant.now();

            if (contatoMaisLonge != null) {
                System.out.println("Contato mais distante da raiz: " + contatoMaisLonge.getNome() + " - " + contatoMaisLonge.getTelefone());
            } else {
                System.out.println("A árvore está vazia.");
            }
            System.out.println("Tempo decorrido: " + Duration.between(inicio,fim).toMillis() + "ms");
            return contatoMaisLonge;
        } else {
            System.out.println("A coleção fornecida não é uma estrutura de árvore binária.");
            return null;
        }
    }
}