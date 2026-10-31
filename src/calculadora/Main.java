package calculadora;

import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Calculadora c = new Calculadora();
        Locale br = Locale.forLanguageTag("pt-BR");
        boolean continuar = true;

        System.out.println("=== FinançApp - Calculadora ===");
        System.out.println("Digite 'sair' no primeiro número para encerrar.");

        while (continuar) {
            try {
                System.out.print("\nPrimeiro número: ");
                String x = sc.nextLine().trim();
                if (x.equalsIgnoreCase("sair")) {
                    continuar = false;
                } else {
                    int a = Integer.parseInt(x);
                    System.out.print("Operação (+, -, *, /): ");
                    String op = sc.nextLine().trim();
                    System.out.print("Segundo número: ");
                    int b = Integer.parseInt(sc.nextLine().trim());
                    double r = c.calc(a, b, op);
                    System.out.println(String.format(br, "Resultado: %d %s %d = %.2f", a, op, b, r));
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: digite apenas números inteiros.");
            } catch (ArithmeticException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (NoSuchElementException e) {
                continuar = false;
            }
        }
        System.out.println("Calculadora encerrada.");
        sc.close();
    }
}
