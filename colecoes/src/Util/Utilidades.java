package Util;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Utilidades {
    private static final Scanner s = new Scanner(System.in);

    public static String lerString(String msg){
        System.out.print(msg);
        return s.nextLine();
    }

    public static char lerChar(String msg){
        while(true){
            System.out.print(msg);
            String entrada = s.nextLine().trim();
            if(entrada.length()==1) return entrada.charAt(0);
            System.out.println("Digite apenas um caractere válido.");
        }
    }

    public static int lerInt(String msg){
        while(true){
            try{
                System.out.print(msg);
                int valor = s.nextInt();
                s.nextLine();
                return valor;
            }
            catch(InputMismatchException e){
                System.out.println("Digite um número inteiro válido.");
                s.nextLine();
            }
        }
    }

    public static long lerLong(String msg){
        while(true){
            try{
                System.out.print(msg);
                long valor = s.nextLong();
                s.nextLine();
                return valor;
            }
            catch(InputMismatchException e){
                System.out.println("Digite um número inteiro válido.");
                s.nextLine();
            }
        }
    }

    public static double lerDouble(String msg){
        while(true){
            try{
                System.out.print(msg);
                double valor = s.nextDouble();
                s.nextLine();
                return valor;
            }
            catch(InputMismatchException e){
                System.out.println("Digite um número decimal válido.");
                s.nextLine();
            }
        }
    }


    public static void confirmar() {
        System.out.print("Confirmar [Enter] ");
        s.nextLine();
    }


}

