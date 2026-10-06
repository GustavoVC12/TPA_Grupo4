import Service.Sistema;
import Util.Utilidades;
import Util.GeradorContatos;
import Util.GeradorMatriculas;
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
                    "3 - Árvore Binária (Matrículas)\n");

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
                            Sistema.carregarDadosListas(lista);
                            jaCarregado = true;
                            break;
                        case 2:
                            Sistema.adicionarContato(lista);
                            break;
                        case 3:
                            Sistema.pesquisarContato(lista, 0);
                            break;
                        case 4:
                            Sistema.pesquisarContato(lista, 1);
                            break;
                        case 5:
                            resultado = Sistema.removerContatoPorTelefone(lista);
                            if (resultado.isConcluido()) {
                                System.out.println("Contato removido com sucesso!");
                            } else {
                                System.out.println("Contato não encontrado. Nada foi removido.");
                            }
                            System.out.println("Tempo decorrido: " + resultado.getTempo() + "ms");
                            break;
                        case 6:
                            Sistema.atualizarContato(lista);
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
                            GeradorContatos.gerar(contatos);
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
                int metIndex = Utilidades.lerInt("Qual método de indexação deseja para a Árvore de Matrículas?\n" +
                        "1 - Número da Matrícula\n" +
                        "2 - Nome do Aluno\n");

                Comparator<Matricula> comparador;
                if (metIndex == 2) {
                    comparador = ComparadorMatricula.POR_NOME;
                    System.out.println("\n========== Árvore de Matrículas indexada por NOME criada! ==========");
                } else {
                    comparador = ComparadorMatricula.POR_NUMERO;
                    System.out.println("\n========== Árvore de Matrículas indexada por NÚMERO criada! ==========");
                }

                IColecao<Matricula> arvore = new ArvoreBinaria<Matricula>(comparador);

                int escolha = 0;
                boolean jaCarregado = false;

                while(escolha != 7){
                    System.out.println("\n====== MENU DE MATRÍCULAS (ÁRVORE) ======\n" +
                            "1 - Carregar dados\n" +
                            "2 - Adicionar matrícula\n" +
                            "3 - Pesquisar matrícula por nome\n" +
                            "4 - Pesquisar matrícula por número\n" +
                            "5 - Remover matrícula por número\n" +
                            "6 - Alterar dados de matrícula\n" +
                            "7 - Sair\n" +
                            "====== Métodos para simplificar os testes ======\n" +
                            "8 - Imprimir árvore\n" +
                            "9 - Gerar dados\n" +
                            "10 - Última matrícula");
                    escolha = Utilidades.lerInt("Sua escolha: ");
                    switch(escolha){
                        case 1:
                            if(jaCarregado){
                                System.out.println("A árvore já foi carregada. Outro carregamento substituirá os dados atuais.");
                                if (Utilidades.lerInt("Deseja continuar? (sim=1 / não=2)") == 2) {
                                    break;
                                }
                                arvore = new ArvoreBinaria<Matricula>(comparador);
                            }
                            Sistema.carregarDadosArvores(arvore);
                            jaCarregado = true;
                            break;
                        case 2:
                            Sistema.adicionarMatricula(arvore);
                            break;
                        case 3:
                            Sistema.pesquisarMatricula(arvore, 0);
                            break;
                        case 4:
                            Sistema.pesquisarMatricula(arvore, 1);
                            break;
                        case 5:
                            Sistema.removerMatriculaPorNumero(arvore);
                            break;
                        case 6:
                            Sistema.atualizarMatricula(arvore);
                            break;
                        case 7:
                            int totalMatriculas = arvore.quantidadeNos();
                            System.out.println("Matrículas salvas: " + totalMatriculas + ". Até a próxima!");
                            break;
                        case 8:
                            System.out.println(arvore);
                            break;
                        case 9:
                            int matriculas = Utilidades.lerInt("Quantidade de matrículas: ");
                            GeradorMatriculas.gerar(matriculas);
                            break;
                        default:
                            System.out.println("Opção ainda não implementada para matrículas ou inválida!");
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