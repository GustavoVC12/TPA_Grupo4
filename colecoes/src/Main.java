import Service.Sistema;
import Util.Utilidades;
import Util.GeradorContatos;
import arvorebinaria.ArvoreAVL;
import colecao.IColecao;
import dominio.*;
import listaencadeada.ListaEncadeada;
import arvorebinaria.ArvoreBinaria;

import java.util.Comparator;

public class Main {
    public static void main(String[] args) {
        int estrutura;
        do {
            estrutura = Utilidades.lerInt("Qual tipo de estrutura deseja utilizar?\n" +
                    "1 - Lista Ordenada (Contatos)\n" +
                    "2 - Lista Não Ordenada (Contatos)\n" +
                    "3 - Árvore Binária (Contatos)\n" +
                    "4 - Árvore Binária AVL (Contatos)");

            if (estrutura == 1 || estrutura == 2) {
                IColecao<Contato> lista = new ListaEncadeada<Contato>(new ComparadorContato(), estrutura == 1);
                if (estrutura == 1) {
                    System.out.println("\n========== Lista ordenada de contatos criada! ==========");
                } else {
                    System.out.println("\n========== Lista não ordenada de contatos criada! ==========");
                }

                int escolha = 0;
                boolean jaCarregado = false;
                Resultado resultado;

                while(escolha != 7){
                    System.out.println("\n====== MENU DE CONTATOS ======\n" +
                            "1 - Carregar dados\n" +
                            "2 - Adicionar contato\n" +
                            "3 - Pesquisar contato por nome\n" +
                            "4 - Pesquisar contato por telefone\n" +
                            "5 - Remover contato por telefone\n" +
                            "6 - Alterar dados de contato\n" +
                            "7 - Sair\n" +
                            "====== Métodos para simplificar os testes ======\n" +
                            "8 - Imprimir lista\n" +
                            "9 - Gerar dados\n" +
                            "10 - Último contato");
                    escolha = Utilidades.lerInt("Sua escolha: ");
                    switch(escolha){
                        case 1:
                            if(jaCarregado){
                                System.out.println("A lista já foi carregada. Outro carregamento substituirá os dados atuais.");
                                if (Utilidades.lerInt("Deseja continuar? (sim=1 / não=2)") == 2) {
                                    break;
                                }
                                lista = new ListaEncadeada<Contato>(new ComparadorContato(), estrutura == 1);
                            }
                            Sistema.carregarContatosLista(lista);
                            jaCarregado = true;
                            break;
                        case 2:
                            Sistema.adicionarContatoLista(lista);
                            break;
                        case 3:
                            Sistema.pesquisarContatoLista(lista, 0);
                            break;
                        case 4:
                            Sistema.pesquisarContatoLista(lista, 1);
                            break;
                        case 5:
                            resultado = Sistema.removerContatoPorTelefoneLista(lista);
                            if (resultado.isConcluido()) {
                                System.out.println("Contato removido com sucesso!");
                            } else {
                                System.out.println("Contato não encontrado. Nada foi removido.");
                            }
                            System.out.println("Tempo decorrido: " + resultado.getTempo() + "ms");
                            break;
                        case 6:
                            Sistema.atualizarContatoLista(lista);
                            break;
                        case 7:
                            int totalContatos = lista.quantidadeNos();
                            System.out.println("Contatos salvos: " + totalContatos + ". Até a próxima!");
                            break;
                        case 8:
                            System.out.println(lista);
                            break;
                        case 9:
                            int contatos = Utilidades.lerInt("Quantidade de contatos: ");
                            GeradorContatos.gerarAleatorios(contatos);
                            break;
                        case 10:
                            Sistema.ultimoContato(lista);
                            break;
                        default:
                            System.out.println("Escolha uma das opções!");
                    }
                }
                return;
            }
            else if (estrutura == 3) {
                int metIndex = Utilidades.lerInt("Qual método de indexação?\n" +
                        "1 - Número do Contato\n" +
                        "2 - Nome do Contato\n");

                Comparator<Contato> comparador;
                if (metIndex == 1) {
                    comparador = ComparadorContatoArvore.POR_TELEFONE;
                    System.out.println("\n========== Árvore de Contatos indexada por NÚMERO criada! ==========");
                } else {
                    comparador = ComparadorContatoArvore.POR_NOME;
                    System.out.println("\n========== Árvore de Contatos indexada por NOME criada! ==========");
                }

                IColecao<Contato> arvore = new ArvoreBinaria<Contato>(comparador);

                int escolha = 0;
                boolean jaCarregado = false;

                while(escolha != 7){
                    System.out.println("\n====== MENU DE CONTATOS (ÁRVORE) ======\n" +
                            "1 - Carregar dados\n" +
                            "2 - Adicionar contato\n" +
                            "3 - Pesquisar contato por nome\n" +
                            "4 - Pesquisar contato por número\n" +
                            "5 - Remover contato por número\n" +
                            "6 - Alterar dados de contato\n" +
                            "7 - Sair\n" +
                            "====== Métodos para simplificar os testes ======\n" +
                            "8 - Imprimir árvore\n" +
                            "9 - Gerar dados\n" +
                            "10 - Último contato");
                    escolha = Utilidades.lerInt("Sua escolha: ");
                    switch(escolha){
                        case 1:
                            if(jaCarregado){
                                System.out.println("A árvore já foi carregada. Outro carregamento substituirá os dados atuais.");
                                if (Utilidades.lerInt("Deseja continuar? (sim=1 / não=2)") == 2) {
                                    break;
                                }
                                arvore = new ArvoreBinaria<Contato>(comparador);
                            }
                            Sistema.carregarContatosArvore(arvore);
                            jaCarregado = true;
                            break;
                        case 2:
                            Sistema.adicionarContatoArvore(arvore);
                            break;
                        case 3:
                            Sistema.pesquisarContatoArvore(arvore, 0);
                            break;
                        case 4:
                            Sistema.pesquisarContatoArvore(arvore, 1);
                            break;
                        case 5:
                            Sistema.removerContatoArvore(arvore);
                            break;
                        case 6:
                            Sistema.atualizarContatoArvore(arvore);
                            break;
                        case 7:
                            System.out.println("Contatos salvos: " + arvore.quantidadeNos() + ". Até a próxima!");
                            break;
                        case 8:
                            System.out.println(arvore);
                            break;
                        case 9:
                            int tipo = Utilidades.lerInt("Escolha uma opção: \n" +
                                    "1 - Contatos aleatórios\n" +
                                    "2 - Contatos ordenados\n" +
                                    "3 - Contatos balanceados");
                            int contatos = Utilidades.lerInt("Quantidade de contatos: ");
                            if (tipo == 1) GeradorContatos.gerarAleatorios(contatos);
                            else if (tipo == 2) GeradorContatos.gerarOrdenados(contatos);
                            else GeradorContatos.gerarBalanceados(contatos);
                            System.out.println(contatos + " contatos gerados com sucesso.");
                            break;
                        case 10:
                            Sistema.ultimoContatoArvore(arvore);
                            break;
                        default:
                            System.out.println("Opção inválida!");
                    }
                }
                return;
            }
            else if (estrutura == 4) {
                int metIndex = Utilidades.lerInt("Qual método de indexação?\n" +
                        "1 - Número do Contato\n" +
                        "2 - Nome do Contato\n");

                Comparator<Contato> comparador;
                if (metIndex == 1) {
                    comparador = ComparadorContatoArvore.POR_TELEFONE;
                    System.out.println("\n========== Árvore AVL de Contatos indexada por NÚMERO criada! ==========");
                } else {
                    comparador = ComparadorContatoArvore.POR_NOME;
                    System.out.println("\n========== Árvore AVL de Contatos indexada por NOME criada! ==========");
                }

                IColecao<Contato> arvore = new ArvoreAVL<Contato>(comparador);

                int escolha = 0;
                boolean jaCarregado = false;

                while(escolha != 7){
                    System.out.println("\n====== MENU DE CONTATOS (ÁRVORE) ======\n" +
                            "1 - Carregar dados\n" +
                            "2 - Adicionar contato\n" +
                            "3 - Pesquisar contato por nome\n" +
                            "4 - Pesquisar contato por número\n" +
                            "5 - Remover contato por número\n" +
                            "6 - Alterar dados de contato\n" +
                            "7 - Sair\n" +
                            "====== Métodos para simplificar os testes ======\n" +
                            "8 - Imprimir árvore\n" +
                            "9 - Gerar dados\n" +
                            "10 - Último contato");
                    escolha = Utilidades.lerInt("Sua escolha: ");
                    switch(escolha){
                        case 1:
                            if(jaCarregado){
                                System.out.println("A árvore já foi carregada. Outro carregamento substituirá os dados atuais.");
                                if (Utilidades.lerInt("Deseja continuar? (sim=1 / não=2)") == 2) {
                                    break;
                                }
                                arvore = new ArvoreBinaria<Contato>(comparador);
                            }
                            Sistema.carregarContatosArvore(arvore);
                            jaCarregado = true;
                            break;
                        case 2:
                            Sistema.adicionarContatoArvore(arvore);
                            break;
                        case 3:
                            Sistema.pesquisarContatoArvore(arvore, 0);
                            break;
                        case 4:
                            Sistema.pesquisarContatoArvore(arvore, 1);
                            break;
                        case 5:
                            Sistema.removerContatoArvore(arvore);
                            break;
                        case 6:
                            Sistema.atualizarContatoArvore(arvore);
                            break;
                        case 7:
                            System.out.println("Contatos salvos: " + arvore.quantidadeNos() + ". Até a próxima!");
                            break;
                        case 8:
                            System.out.println(arvore);
                            break;
                        case 9:
                            int tipo = Utilidades.lerInt("Escolha uma opção: \n" +
                                    "1 - Contatos aleatórios\n" +
                                    "2 - Contatos ordenados\n" +
                                    "3 - Contatos balanceados");
                            int contatos = Utilidades.lerInt("Quantidade de contatos: ");
                            if (tipo == 1) GeradorContatos.gerarAleatorios(contatos);
                            else if (tipo == 2) GeradorContatos.gerarOrdenados(contatos);
                            else GeradorContatos.gerarBalanceados(contatos);
                            System.out.println(contatos + " contatos gerados com sucesso.");
                            break;
                        case 10:
                            Sistema.ultimoContatoArvore(arvore);
                            break;
                        default:
                            System.out.println("Opção inválida!");
                    }
                }
                return;
            }
            else {
                System.out.println("Insira uma opção válida.");
            }

        } while(true);
    }
}