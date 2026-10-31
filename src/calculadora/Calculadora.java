package calculadora;

/**
 * Módulo básico de cálculos matemáticos do aplicativo <b>FinançApp</b>.
 *
 * <p>Esta classe é utilizada por funcionalidades como cálculo de orçamento
 * mensal, divisão de despesas e projeção de economia. Ela realiza as quatro
 * operações básicas (soma, subtração, multiplicação e divisão) entre dois
 * números inteiros e retorna o resultado como {@code double}, para que
 * divisões não exatas (ex.: 7 / 2 = 3.5) não percam a parte decimal.</p>
 *
 * <p>Situações de erro (divisão por zero e operação inválida) são sinalizadas
 * por exceções com mensagens claras, permitindo que a camada que chama a
 * calculadora trate o erro e continue funcionando.</p>
 *
 * @author Mateus Novais Chaves
 * @version 1.0
 */
public class Calculadora {

    /**
     * Mensagem de erro usada quando há tentativa de divisão por zero.
     */
    public static final String MSG_DIVISAO_ZERO = "Erro: não é possível dividir por zero.";

    /**
     * Mensagem de erro usada quando a operação informada não é
     * {@code +}, {@code -}, {@code *} ou {@code /}.
     */
    public static final String MSG_OP_INVALIDA = "Erro: operação inválida. Use +, -, * ou /.";

    /**
     * Cria uma nova calculadora. A classe não guarda estado entre os
     * cálculos, então a mesma instância pode ser reutilizada.
     */
    public Calculadora() {
        // Nenhuma inicialização necessária.
    }

    /**
     * Realiza uma operação matemática básica entre dois números inteiros.
     *
     * <p>Os operandos são convertidos para {@code double} antes do cálculo,
     * o que evita estouro de {@code int} (ex.: {@code Integer.MAX_VALUE + 1})
     * e preserva casas decimais na divisão.</p>
     *
     * @param a  primeiro operando
     * @param b  segundo operando (na divisão, é o divisor)
     * @param op operação desejada: {@code "+"}, {@code "-"}, {@code "*"} ou {@code "/"}
     * @return o resultado da operação
     * @throws ArithmeticException      se {@code op} for {@code "/"} e {@code b} for zero
     * @throws IllegalArgumentException se {@code op} for nula ou não for uma operação suportada
     */
    public double calc(int a, int b, String op) {
        double r;
        // "+".equals(op) evita NullPointerException quando op é null
        if ("+".equals(op)) {
            r = (double) a + b;
        } else if ("-".equals(op)) {
            r = (double) a - b;
        } else if ("*".equals(op)) {
            r = (double) a * b;
        } else if ("/".equals(op)) {
            // Divisão por zero é bloqueada antes do cálculo
            if (b == 0) {
                throw new ArithmeticException(MSG_DIVISAO_ZERO);
            }
            r = (double) a / b;
        } else {
            // Qualquer outro símbolo (ou null) é considerado inválido
            throw new IllegalArgumentException(MSG_OP_INVALIDA + " Recebido: " + op);
        }
        return r;
    }
}
