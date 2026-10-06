package Service;

import java.io.*;
import java.time.Duration;
import java.time.Instant;

import Util.Utilidades;
import colecao.IColecao;
import dominio.Contato;
import dominio.Matricula;
import dominio.Resultado;
import listaencadeada.ListaEncadeada;
import listaencadeada.No;
import arvorebinaria.ArvoreBinaria;

public class Sistema {
    // Carregar dados do arquivo texto
    public static void carregarDadosListas(IColecao<Contato> lista) {
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

    public static void carregarDadosArvores(IColecao<Matricula> arvore) {
        try {
            File arquivo = new File("entradaMatriculas.txt");
            arquivo.createNewFile();
        } catch (IOException e) {
            System.out.println("Deu pau no carregamento");
        }

        try (BufferedReader leitor = new BufferedReader(new FileReader("entradaMatriculas.txt"))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                String[] atributos = linha.split(";");
                int numero = Integer.parseInt(atributos[0]);
                String nome = atributos[1];
                Matricula matricula = new Matricula(numero, nome);
                arvore.adicionar(matricula);
            }
            System.out.println("Árvore de matrículas carregada!");
        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao carregar as matrículas.");
        }
    }

    public static void adicionarContato(IColecao<Contato> lista) {
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

    public static void adicionarMatricula(IColecao<Matricula> arvore) {
        System.out.println("======== Criando nova matrícula ========");
        int numero = Utilidades.lerInt("Número da matrícula: ");
        String nome = Utilidades.lerString("Nome do aluno: ");

        Matricula matricula = new Matricula(numero, nome);
        if (!matriculaExiste(arvore, matricula)) {
            arvore.adicionar(matricula);
            System.out.println("Matrícula adicionada com sucesso!");
            return;
        }
        System.out.println("Este número de matrícula já está cadastrado.");
    }

    public static boolean adicionarContatoAtualizado(IColecao<Contato> lista) {
        String nome = Utilidades.lerString("Nome atualizado: ");
        long telefone = Utilidades.lerLong("Telefone atualizado: ");
        Contato contato = new Contato(nome, telefone);
        if(!contatoExiste(lista, contato)) {
            lista.adicionar(contato);
            return true;
        }
        return false;
    }

    public static boolean adicionarMatriculaAtualizada(IColecao<Matricula> arvore) {
        int numero = Utilidades.lerInt("Número da matrícula atualizado: ");
        String nome = Utilidades.lerString("Nome atualizado do aluno: ");
        Matricula matricula = new Matricula(numero, nome);
        if (!matriculaExiste(arvore, matricula)) {
            arvore.adicionar(matricula);
            return true;
        }
        return false;
    }

    // pesquisa geral
    public static void pesquisarContato(IColecao<Contato> lista, int tipo) {
        long inicio, fim;
        Contato contato;

        System.out.println("======= Pesquisa de contato =======");
        if (tipo == 0) {
            String nome = Utilidades.lerString("Nome: ");
            inicio = System.nanoTime(); // Marcação em nanossegundos
            contato = pesquisarContatoPorNome(lista, nome);
            fim = System.nanoTime();
        } else {
            long telefone = Utilidades.lerLong("Telefone: ");
            inicio = System.nanoTime();
            contato = pesquisarContatoPorTelefone(lista, telefone);
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

    public static Contato pesquisarContatoPorNome(IColecao<Contato> lista, String nome) {
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

    public static Contato pesquisarContatoPorTelefone(IColecao<Contato> lista, long telefone) {
        Contato contato = new Contato("", telefone);
        return lista.pesquisar(contato);
    }

    // pesquisa geral
    public static void pesquisarMatricula(IColecao<Matricula> arvore, int tipo) {
        Matricula matricula;

        System.out.println("======= Pesquisa de matrícula =======");
        if (tipo == 0) {
            String nome = Utilidades.lerString("Nome do aluno: ");
            matricula = pesquisarMatriculaPorNome(arvore, nome);
        } else {
            int numero = Utilidades.lerInt("Número da matrícula: ");
            matricula = pesquisarMatriculaPorNumero(arvore, numero);
        }

        if (matricula != null) {
            System.out.println("Correspondência encontrada: " + matricula.getNumeroMatricula() + " - " + matricula.getNomeAluno());
        } else {
            System.out.println("Nenhuma matrícula encontrada.");
        }
    }

    public static Matricula pesquisarMatriculaPorNome(IColecao<Matricula> arvore, String nome) {
        if (arvore instanceof ArvoreBinaria) {
            ArvoreBinaria<Matricula> arvoreBinaria = (ArvoreBinaria<Matricula>) arvore;
            return arvoreBinaria.pesquisarPorCondicao(m -> m.getNomeAluno().equalsIgnoreCase(nome));
        }
        return null;
    }

    public static Matricula pesquisarMatriculaPorNumero(IColecao<Matricula> arvore, int numero) {
        if (arvore instanceof ArvoreBinaria) {
            ArvoreBinaria<Matricula> arvoreBinaria = (ArvoreBinaria<Matricula>) arvore;
            return arvoreBinaria.pesquisarPorCondicao(m -> m.getNumeroMatricula() == numero);
        }
        return null;
    }

    // Remover contato da lista
    public static Resultado removerContatoPorTelefone(IColecao<Contato> lista) {
        long telefoneRemover = Utilidades.lerLong("Telefone do contato a ser removido: ");
        Instant inicio = Instant.now();
        boolean removido = lista.remover(pesquisarContatoPorTelefone(lista, telefoneRemover));
        Instant fim = Instant.now();
        return new Resultado(removido, Duration.between(inicio,fim).toMillis());
    }

    public static void removerContatoPorTelefone(IColecao<Contato> lista, Contato contato) {
        lista.remover(contato);
    }

    // Remover matrícula da árvore pelo número
    public static void removerMatriculaPorNumero(IColecao<Matricula> arvore) {
        int numeroRemover = Utilidades.lerInt("Número da matrícula a ser removida: ");
        Matricula matriculaEncontrada = pesquisarMatriculaPorNumero(arvore, numeroRemover);

        if (matriculaEncontrada != null) {
            boolean removido = arvore.remover(matriculaEncontrada);
            if (removido) {
                System.out.println("Matrícula removida com sucesso!");
            } else {
                System.out.println("Erro ao remover a matrícula.");
            }
        } else {
            System.out.println("Matrícula não encontrada. Nada foi removido.");
        }
    }

    // Atualizar contato da lista
    public static void atualizarContato(IColecao<Contato> lista) {
        System.out.println("====== Atualizando Contato ======"); // mantido conforme o original
        Contato contatoRemover = pesquisarContatoPorNome(lista, Utilidades.lerString("Nome do contato a ser atualizado: "));
        if (contatoRemover != null) {
            boolean sucesso = adicionarContatoAtualizado(lista);
            if (sucesso) {
                removerContatoPorTelefone(lista, contatoRemover);
                System.out.println("Contato atualizado com sucesso!");
            }
            else {
                System.out.println("O número especificado já consta no sistema.");
            }
            return;
        }
        System.out.println("Contato não encontrado.");
    }

    // Atualizar matrícula da árvore
    public static void atualizarMatricula(IColecao<Matricula> arvore) {
        System.out.println("====== Atualizando Matrícula ======");
        int numeroBusca = Utilidades.lerInt("Número da matrícula a ser atualizada: ");
        Matricula matriculaRemover = pesquisarMatriculaPorNumero(arvore, numeroBusca);

        if (matriculaRemover != null) {
            boolean sucesso = adicionarMatriculaAtualizada(arvore);
            if (sucesso) {
                arvore.remover(matriculaRemover);
                System.out.println("Matrícula atualizada com sucesso!");
            } else {
                System.out.println("O número de matrícula especificado já consta no sistema.");
            }
            return;
        }
        System.out.println("Matrícula não encontrada.");
    }

    public static boolean contatoExiste(IColecao<Contato> l, Contato c) {
        return l.pesquisar(c) != null;
    }
    public static boolean matriculaExiste(IColecao<Matricula> arvore, Matricula m) {
        return arvore.pesquisar(m) != null;
    }

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

    public static void excluirDados(IColecao<Contato> l) {
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
                removerContatoPorTelefone(l, aux.getValor());
                aux = prox;
            }
            System.out.println("Lista esvaziada.");
        } else {
            System.out.println("Para esvaziar a Árvore, feche e abra o sistema (ou re-instancie a coleção no Main).");
        }
    }
}
