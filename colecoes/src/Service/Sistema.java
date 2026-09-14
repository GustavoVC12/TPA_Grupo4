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

public class Sistema {
    // Carregar dados do arquivo texto
    public static void carregarDados(IColecao<Contato> lista) {
        // definir temporizadores
        Instant fim;
        Instant inicio = Instant.now();

        //abrir arquivo
        try {
            File arquivo = new File("entrada.txt");
            if (arquivo.createNewFile()) {
                System.out.println("Arquivo criado: " + arquivo.getName() + "  ME REMOVA!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            } else {
                System.out.println("Arquivo já existe.  ME REMOVA!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            }
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
                if (!contatoExiste(lista, contato)){
                    lista.adicionar(contato);
                }
            }
            System.out.println("Lista carregada!");
        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao carregar a lista.");
        }
        fim = Instant.now();
        System.out.println("Tempo decorrido: " + Duration.between(inicio,fim).toMillis() + "ms");
    }
    // public static void excluirDados(IColecao<Contato> lista)

    // Adicionar contato à lista
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


    // Pesquisa de contato por nome ou telefone
    // pesquisarContato lida com a resposta ao usuário, permitindo que os métodos específicos
    // sejam utilizados em outras partes do sistema quando necessário
    public static void pesquisarContato(IColecao<Contato> lista, int tipo) {
        long inicio, fim;
        Contato contato;

        System.out.println("======= Pesquisa de contato =======");
        if (tipo == 0) {
            String nome = Utilidades.lerString("Nome: ");
            inicio = System.nanoTime(); // Marcação em nanossegundos
            contato = pesquisaPorNome(lista, nome);
            fim = System.nanoTime();
        } else {
            long telefone = Utilidades.lerLong("Telefone: ");
            inicio = System.nanoTime();
            contato = pesquisaPorTelefone(lista, telefone);
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
    public static Contato pesquisaPorNome(IColecao<Contato> lista, String nome) {
        // elaborado com auxílio de IA
        // lista e nó auxiliares para busca
        ListaEncadeada<Contato> listaAux = (ListaEncadeada<Contato>) lista;
        No<Contato> aux = listaAux.getPrim();

        while (aux != null){
            if (aux.getValor().getNome().equalsIgnoreCase(nome)){
                return aux.getValor(); // correspondência encontrada
            }
            aux = aux.getProx();
        }
        return null;
    }
    public static Contato pesquisaPorTelefone(IColecao<Contato> lista, long telefone) {
        Contato contato = new Contato("", telefone);
        return lista.pesquisar(contato);
    }

    // Remover contato da lista
    public static Resultado removerContatoPorTelefone(IColecao<Contato> lista) {
        // AVALIAR TEMPO DE REMOÇÃO
        // SE PRECISAR,MUDAR MEDIDA PARA NANO!!!

        long telefoneRemover = Utilidades.lerLong("Telefone do contato a ser removido: ");
        Instant inicio = Instant.now();
        boolean removido = lista.remover(pesquisaPorTelefone(lista, telefoneRemover));
        Instant fim = Instant.now();
        return new Resultado(removido, Duration.between(inicio,fim).toMillis());
    }
    public static void removerContatoPorTelefone(IColecao<Contato> lista, Contato contato) {
        lista.remover(contato);
    }

    // Atualizar contato da lista
    public static void atualizarContato(IColecao<Contato> lista) {
        // remove o contato antigo e substitui por um novo
        System.out.println("====== Atualizando Contato ======");
        // busca contato a ser removido
        Contato contatoRemover = pesquisaPorNome(lista, Utilidades.lerString("Nome do contato a ser atualizado: "));
        if (contatoRemover != null) {
            // confere se o telefone novo já foi cadastrado e o contato novo pode ser adicionado
            boolean sucesso = adicionarContatoAtualizado(lista);
            if (sucesso) {
                // operação só é concluída se um contato puder ser removido E outro puder ser adicionado
                removerContatoPorTelefone(lista, contatoRemover);
                System.out.println("Contato atualizado com sucesso!");
            }
            else {
                System.out.println("O número especificado já consta no sistema.");
            }
            return;
        }
        // caso o contato a ser removido não exista OU o número novo já conste no sistema, a operação falha
        System.out.println("Contato não encontrado.");
    }

    public static boolean contatoExiste(IColecao<Contato> l, Contato c) {
        return l.pesquisar(c) != null;
    }

    public static void ultimoContato(IColecao<Contato> l) {
        ListaEncadeada<Contato> lAux = (ListaEncadeada<Contato>) l;
        Contato ult = lAux.getUlt().getValor();
        System.out.println("Último contato: " + ult.getNome() + " - " + ult.getTelefone());
    }
}
