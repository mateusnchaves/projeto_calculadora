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
 * <p><b>Versão 2.0 (refatoração):</b> a lógica que ficava toda dentro do
 * {@code main} foi dividida em métodos menores com nomes descritivos, e as
 * variáveis {@code sc}, {@code c}, {@code x} e {@code r} foram renomeadas.</p>
 *
 * @author Mateus Novais Chaves
 * @version 2.0
 */
public class Main {

    /** Palavra que encerra o programa quando digitada no primeiro número. */
    private static final String COMANDO_SAIR = "sair";

    /** Localidade usada para formatar o resultado (vírgula decimal). */
    private static final Locale LOCALE_BR = Locale.forLanguageTag("pt-BR");

    /** Leitor da entrada do usuário. */
    private static final Scanner entrada = new Scanner(System.in);

    /** Calculadora usada em todas as operações. */
    private static final Calculadora calculadora = new Calculadora();

    /**
     * Construtor privado: a classe só possui métodos estáticos.
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
        exibirCabecalho();

        boolean continuar = true;
        while (continuar) {
            continuar = executarRodada();
        }

        System.out.println("Calculadora encerrada.");
        entrada.close();
    }

    /**
     * Mostra o título e a instrução de saída.
     */
    private static void exibirCabecalho() {
        System.out.println("=== FinançApp - Calculadora ===");
        System.out.println("Digite '" + COMANDO_SAIR + "' no primeiro número para encerrar.");
    }

    /**
     * Executa uma rodada completa: lê os dados, calcula e exibe o resultado.
     * Erros são exibidos como mensagem e não interrompem o programa.
     *
     * @return {@code false} se o usuário pediu para sair ou a entrada acabou;
     *         {@code true} para continuar
     */
    private static boolean executarRodada() {
        try {
            String primeiraEntrada = lerTexto("\nPrimeiro número: ");
            if (primeiraEntrada.equalsIgnoreCase(COMANDO_SAIR)) {
                return false;
            }
            int primeiroNumero = Integer.parseInt(primeiraEntrada);
            String operacao = lerTexto("Operação (+, -, *, /): ");
            int segundoNumero = Integer.parseInt(lerTexto("Segundo número: "));

            double resultado = calculadora.calc(primeiroNumero, segundoNumero, operacao);
            exibirResultado(primeiroNumero, operacao, segundoNumero, resultado);
        } catch (NumberFormatException e) {
            // Precisa vir antes de IllegalArgumentException (é subclasse dela)
            System.out.println("Erro: digite apenas números inteiros.");
        } catch (ArithmeticException | IllegalArgumentException e) {
            // Divisão por zero ou operação inválida: mostra a mensagem e continua
            System.out.println(e.getMessage());
        } catch (NoSuchElementException e) {
            // Fim da entrada (ex.: Ctrl+D / Ctrl+Z): encerra normalmente
            return false;
        }
        return true;
    }

    /**
     * Exibe uma mensagem e lê uma linha digitada pelo usuário.
     *
     * @param mensagem texto exibido antes da leitura
     * @return a linha digitada, sem espaços nas extremidades
     * @throws NoSuchElementException se não houver mais entrada disponível
     */
    private static String lerTexto(String mensagem) {
        System.out.print(mensagem);
        return entrada.nextLine().trim();
    }

    /**
     * Exibe a conta realizada e o resultado com 2 casas decimais no padrão
     * brasileiro (ex.: {@code 7 / 2 = 3,50}).
     *
     * @param primeiroNumero primeiro operando
     * @param operacao       símbolo da operação
     * @param segundoNumero  segundo operando
     * @param resultado      resultado do cálculo
     */
    private static void exibirResultado(int primeiroNumero, String operacao,
                                        int segundoNumero, double resultado) {
        System.out.println(String.format(LOCALE_BR, "Resultado: %d %s %d = %.2f",
                primeiroNumero, operacao, segundoNumero, resultado));
    }
}
