package Util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GeradorMatriculas {

    private static final String[] PRIMEIROS_NOMES = {
            "Ana", "Carlos", "Bruno", "Beatriz", "Daniel", "Eduardo", "Fernanda", "Gabriel",
            "Helena", "Isabela", "João", "Larissa", "Lucas", "Mariana", "Nicolas", "Paula",
            "Rafael", "Sophia", "Thiago", "Valeria", "Dmitri", "Ivan", "Anastasia", "Natalia",
            "Alexei", "Tatiana", "Vladimir", "Olga", "Mikhail", "Svetlana", "Sergei",
            "Ekaterina","Pavel", "Elena", "Daria"
    };

    private static final String[] SOBRENOMES = {
            "Silva", "Santos", "Oliveira", "Souza", "Rodrigues", "Ferreira", "Alves",
            "Pereira", "Lima", "Gomes", "Costa", "Martins", "Almeida", "Carvalho", "Ivanov",
            "Smirnov", "Kuznetsov", "Popov", "Vasiliev", "Petrov", "Sokolov", "Mikhailov",
            "Fedorov", "Morozov", "Volkov", "Alekseev", "Lebedev", "Pavlov"
    };

    public static void gerar(int quantidade) {
        Random random = new Random();

        List<Integer> numerosUnicos = new ArrayList<>();
        for (int i = 1; i <= quantidade; i++) {
            numerosUnicos.add(10000 + i);
        }

        Collections.shuffle(numerosUnicos, random);

        try (FileWriter escritor = new FileWriter("entradaMatriculas.txt")) {
            for (int i = 0; i < quantidade; i++) {
                // nome pode repetir
                String nomeAleatorio = PRIMEIROS_NOMES[random.nextInt(PRIMEIROS_NOMES.length)] + " " +
                        SOBRENOMES[random.nextInt(SOBRENOMES.length)];

                int numeroMatricula = numerosUnicos.get(i);

                // formato: numero;Nome
                escritor.write(numeroMatricula + ";" + nomeAleatorio + "\n");
            }
            System.out.println("\n========== " + quantidade + " matrículas geradas em 'entradaMatriculas.txt'! ==========");
        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao gerar o arquivo de matrículas.");
        }
    }
}