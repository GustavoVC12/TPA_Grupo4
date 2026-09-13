import Service.Sistema;
import Util.Utilidades;
import Util.GeradorDados;
import colecao.IColecao;
import dominio.ComparadorContato;
import dominio.Contato;
import dominio.Resultado;
import listaencadeada.ListaEncadeada;

public class Main {
    public static void main(String[] args) {
        IColecao<Contato> lista = null;
        int ord = 0;

        //criar lista escolhida
        do {
            ord = Utilidades.lerInt("Qual tipo de lista deseja utilizar?\n" +
                    "1 - Ordenada\n" +
                    "2 - Não ordenada\n");

            if (ord == 1) {
                lista = new ListaEncadeada<Contato>(new ComparadorContato(), true);
                System.out.println("\n========== Lista ordenada criada! ==========");
            }
            else if (ord == 2) {
                lista = new ListaEncadeada<Contato>(new ComparadorContato(), false);
                System.out.println("\n========== Lista não ordenada criada! ==========");
            }
            else {
                System.out.println("Insira uma opção válida.");
            }

        } while(ord != 1 && ord != 2);

        //exibir menu
        int escolha = 0;
        boolean jaCarregado = false;
        Resultado resultado;
        while(escolha != 7){
            System.out.println("Selecione uma ação.\n" +
                    "1 - Carregar dados\n" +
                    "2 - Adicionar contato\n" +
                    "3 - Pesquisar contato por nome\n" +
                    "4 - Pesquisar contato por telefone\n" +
                    "5 - Remover contato por telefone\n" +
                    "6 - Alterar dados de contato\n" +
                    "7 - Sair\n" +
                    "8 - Imprimir lista\n" +
                    "9 - Gerar dados");
            escolha = Utilidades.lerInt("Sua escolha: ");
            switch(escolha){
                case 1: // Carregar dados
                    if(jaCarregado){
                        System.out.println("Os dados do arquivo já foram carregados.");
                    }
                    Sistema.carregarDados(lista);
                    jaCarregado = true;
                    break;
                case 2: // Adicionar contato
                    Sistema.adicionarContato(lista);
                    break;
                case 3: // Pesquisar contato por nome
                    Sistema.pesquisarContato(lista,0);
                    break;
                case 4: // Pesquisar contato por telefone
                    Sistema.pesquisarContato(lista,1);
                    break;
                case 5: // Remover contato
                    resultado = Sistema.removerContatoPorTelefone(lista);
                    if (resultado.isConcluido()) {
                        System.out.println("Contato removido com sucesso!");
                    }
                    else {
                        System.out.println("Contato não encontrado. Nada foi removido.");
                    }
                    System.out.println("Tempo decorrido: " + resultado.getTempo() + "ms");
                    break;
                case 6: // Alterar contato
                    Sistema.atualizarContato(lista);
                    break;
                case 7: // Sair
                    int totalContatos = lista.quantidadeNos();
                    System.out.println("Contatos salvos: " + totalContatos + ". Até a próxima!");
                    break;
                case 8:
                    System.out.println(lista);
                    break;
                case 9:
                    int contatos = Utilidades.lerInt("Quantidade de contatos: ");
                    GeradorDados.gerar(contatos);
                    break;
                default:
                    System.out.println("Escolha uma das opções!");
            }
        }
    }
}
