package Util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeradorContatos {

    private static final String[] PRIMEIROS_NOMES = {
            "Ana", "Bruno", "Carla", "Daniel", "Eduarda", "Felipe", "Gabriela", "Henrique",
            "Isabela", "João", "Kauan", "Larissa", "Lucas", "Mariana", "Matheus", "Natalia",
            "Otavio", "Paula", "Rafael", "Sofia", "Thiago", "Vanessa", "Vinicius", "Aleksandr",
            "Dmitry", "Elena", "Ivan", "Olga", "Sergei", "Vladimir", "Peter", "Abigail", "Clint",
            "Boris", "Erika", "Marcus", "Oleg", "Jacqueline", "Gabrielle", "Ishtar", "Belle"
    };

    private static final String[] SOBRENOMES = {
            "Silva", "Santos", "Oliveira", "Souza", "Rodrigues", "Ferreira", "Alves",
            "Pereira", "Lima", "Gomez", "Ivanov", "Petrov", "Sidorov", "Smirnov", "Popov", "Saxon",
            "Wright", "Cunningham", "Whatever"
    };

    public static void gerarAleatorios(int quantidadeContatos) { // Altere para 400000 se quiser o máximo
        String arquivo = "entrada.txt";
        List<Long> telefonesExistentes = new ArrayList<>();
        Random random = new Random();

        long inicio = System.currentTimeMillis();
        System.out.println("Gerando " + quantidadeContatos + " contatos em " + arquivo + "...");

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(arquivo))) {
            for (int i = 0; i < quantidadeContatos;) {
                String nome = PRIMEIROS_NOMES[random.nextInt(PRIMEIROS_NOMES.length)] + " " +
                        SOBRENOMES[random.nextInt(SOBRENOMES.length)];

                // Gera um número de telefone único de 11 dígitos no formato 55XXXXXXXXX
                long telefone = 55000000000L + i * (int)(Math.random() * 10001);

                if (!telefonesExistentes.contains(telefone)) {
                    escritor.write(nome + ";" + telefone);
                    escritor.newLine();
                    telefonesExistentes.add(telefone);
                    i++;
                }
            }
            long fim = System.currentTimeMillis();
            System.out.println("Arquivo gerado com sucesso em " + (fim - inicio) + " ms!");

        } catch (IOException e) {
            System.err.println("Erro ao gerar arquivo de teste: " + e.getMessage());
        }
    }

    public static void gerarOrdenados(int quantidadeContatos) {
        String arquivo = "entrada.txt";
        Random random = new Random();

        long inicio = System.currentTimeMillis();
        System.out.println("Gerando " + quantidadeContatos + " contatos ordenados em " + arquivo + "...");

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(arquivo))) {
            long telefoneBase = 5511900000000L;

            for (int i = 0; i < quantidadeContatos; i++) {
                String nome = PRIMEIROS_NOMES[random.nextInt(PRIMEIROS_NOMES.length)] + " " +
                        SOBRENOMES[random.nextInt(SOBRENOMES.length)];

                // Garante números de telefone estritamente crescentes
                long telefone = telefoneBase + i;

                escritor.write(nome + ";" + telefone);
                escritor.newLine();
            }
            long fim = System.currentTimeMillis();
            System.out.println("Arquivo de contatos ordenados gerado com sucesso em " + (fim - inicio) + " ms!");

        } catch (IOException e) {
            System.err.println("Erro ao gerar arquivo ordenado: " + e.getMessage());
        }
    }

    public static void gerarBalanceados(int quantidadeContatos) {
        String arquivo = "entrada.txt";
        Random random = new Random();

        long inicio = System.currentTimeMillis();
        System.out.println("Gerando " + quantidadeContatos + " contatos balanceados em " + arquivo + "...");

        // Gera os índices organizados na ordem de pré-ordem da árvore balanceada
        List<Integer> indices = new ArrayList<>(quantidadeContatos);
        gerarIndicesBalanceados(indices, 0, quantidadeContatos - 1);

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(arquivo))) {
            long telefoneBase = 5511900000000L;

            for (int i = 0; i < quantidadeContatos; i++) {
                int indice = indices.get(i);
                String nome = PRIMEIROS_NOMES[random.nextInt(PRIMEIROS_NOMES.length)] + " " +
                        SOBRENOMES[random.nextInt(SOBRENOMES.length)];

                // O telefone é gerado utilizando o índice previamente ordenado para o balanceamento
                long telefone = telefoneBase + indice;

                escritor.write(nome + ";" + telefone);
                escritor.newLine();
            }
            long fim = System.currentTimeMillis();
            System.out.println("Arquivo de contatos balanceados gerado com sucesso em " + (fim - inicio) + " ms!");

        } catch (IOException e) {
            System.err.println("Erro ao gerar arquivo balanceado: " + e.getMessage());
        }
    }

    private static void gerarIndicesBalanceados(List<Integer> indices, int inicio, int fim) {
        if (inicio > fim) return;

        int meio = (inicio + fim) / 2;
        indices.add(meio); // A raiz do subintervalo entra primeiro
        gerarIndicesBalanceados(indices, inicio, meio - 1); // Subárvore esquerda
        gerarIndicesBalanceados(indices, meio + 1, fim);    // Subárvore direita
    }
}