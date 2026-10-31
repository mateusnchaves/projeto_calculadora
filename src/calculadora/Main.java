package calculadora;

import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Programa interativo (console) para usar a {@link Calculadora}.
 *
 * <p>O usuário informa dois números inteiros e uma operação. Qualquer erro
 * (entrada não numérica, divisão por zero ou operação inválida) é tratado
 * e exibido como mensagem, sem interromper a execução do programa.
 * Para encerrar, basta digitar {@code sair}.</p>
 *
 * @author Mateus Novais Chaves
 * @version 1.0
 */
public class Main {

    /**
     * Construtor privado: a classe só possui o método estático {@link #main(String[])}.
     */
    private Main() {
    }

    /**
     * Ponto de entrada do programa. Executa o laço de leitura e cálculo
     * até o usuário digitar {@code sair}.
     *
     * @param args argumentos de linha de comando (não utilizados)
     */
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
                    // Resultado com 2 casas decimais no padrão brasileiro (ex.: 3,50)
                    System.out.println(String.format(br, "Resultado: %d %s %d = %.2f", a, op, b, r));
                }
            } catch (NumberFormatException e) {
                // Precisa vir antes de IllegalArgumentException (é subclasse dela)
                System.out.println("Erro: digite apenas números inteiros.");
            } catch (ArithmeticException | IllegalArgumentException e) {
                // Divisão por zero ou operação inválida: mostra a mensagem e continua
                System.out.println(e.getMessage());
            } catch (NoSuchElementException e) {
                // Fim da entrada (ex.: Ctrl+D / Ctrl+Z): encerra normalmente
                continuar = false;
            }
        }
        System.out.println("Calculadora encerrada.");
        sc.close();
    }
}
