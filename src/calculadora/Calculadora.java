package calculadora;

import java.util.Set;

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
 * <p><b>Versão 2.0 (refatoração):</b> cada operação foi extraída para um
 * método próprio, os símbolos viraram constantes, a cadeia de
 * {@code if/else} foi substituída por um {@code switch} e a validação da
 * operação e do divisor ganhou métodos dedicados. O comportamento externo
 * é o mesmo da versão 1.0.</p>
 *
 * @author Mateus Novais Chaves
 * @version 2.0
 */
public class Calculadora {

    /** Símbolo da operação de soma. */
    public static final String SOMA = "+";

    /** Símbolo da operação de subtração. */
    public static final String SUBTRACAO = "-";

    /** Símbolo da operação de multiplicação. */
    public static final String MULTIPLICACAO = "*";

    /** Símbolo da operação de divisão. */
    public static final String DIVISAO = "/";

    /** Conjunto com todas as operações aceitas pelo método {@link #calc(int, int, String)}. */
    private static final Set<String> OPERACOES_SUPORTADAS =
            Set.of(SOMA, SUBTRACAO, MULTIPLICACAO, DIVISAO);

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
     * <p>Primeiro a operação é validada; depois o cálculo é delegado ao
     * método específico ({@link #somar}, {@link #subtrair},
     * {@link #multiplicar} ou {@link #dividir}).</p>
     *
     * @param a  primeiro operando
     * @param b  segundo operando (na divisão, é o divisor)
     * @param op operação desejada: {@code "+"}, {@code "-"}, {@code "*"} ou {@code "/"}
     * @return o resultado da operação
     * @throws ArithmeticException      se {@code op} for {@code "/"} e {@code b} for zero
     * @throws IllegalArgumentException se {@code op} for nula ou não for uma operação suportada
     */
    public double calc(int a, int b, String op) {
        validarOperacao(op);

        // Após a validação, op é garantidamente um dos quatro símbolos
        return switch (op) {
            case SOMA -> somar(a, b);
            case SUBTRACAO -> subtrair(a, b);
            case MULTIPLICACAO -> multiplicar(a, b);
            case DIVISAO -> dividir(a, b);
            default -> throw new IllegalStateException("Operação não mapeada: " + op);
        };
    }

    /**
     * Soma dois números.
     *
     * @param primeiraParcela primeira parcela
     * @param segundaParcela  segunda parcela
     * @return a soma das parcelas
     */
    public double somar(int primeiraParcela, int segundaParcela) {
        // Conversão para double antes da conta evita estouro de int
        return (double) primeiraParcela + segundaParcela;
    }

    /**
     * Subtrai o subtraendo do minuendo.
     *
     * @param minuendo   valor do qual se subtrai
     * @param subtraendo valor a ser subtraído
     * @return a diferença {@code minuendo - subtraendo}
     */
    public double subtrair(int minuendo, int subtraendo) {
        return (double) minuendo - subtraendo;
    }

    /**
     * Multiplica dois números.
     *
     * @param multiplicando valor a ser multiplicado
     * @param multiplicador quantidade de vezes
     * @return o produto dos dois valores
     */
    public double multiplicar(int multiplicando, int multiplicador) {
        return (double) multiplicando * multiplicador;
    }

    /**
     * Divide o dividendo pelo divisor, mantendo as casas decimais.
     *
     * @param dividendo valor a ser dividido
     * @param divisor   valor pelo qual se divide (não pode ser zero)
     * @return o quociente {@code dividendo / divisor}
     * @throws ArithmeticException se {@code divisor} for zero
     */
    public double dividir(int dividendo, int divisor) {
        validarDivisor(divisor);
        return (double) dividendo / divisor;
    }

    /**
     * Verifica se a operação informada é suportada pela calculadora.
     *
     * @param op operação a verificar (pode ser {@code null})
     * @return {@code true} se {@code op} for {@code +}, {@code -}, {@code *} ou {@code /}
     */
    public static boolean isOperacaoSuportada(String op) {
        // Set.of(...).contains(null) lança exceção, por isso o teste de null vem antes
        return op != null && OPERACOES_SUPORTADAS.contains(op);
    }

    /**
     * Garante que a operação é suportada.
     *
     * @param op operação a validar
     * @throws IllegalArgumentException se a operação for nula ou inválida
     */
    private void validarOperacao(String op) {
        if (!isOperacaoSuportada(op)) {
            throw new IllegalArgumentException(MSG_OP_INVALIDA + " Recebido: " + op);
        }
    }

    /**
     * Garante que o divisor não é zero.
     *
     * @param divisor divisor a validar
     * @throws ArithmeticException se o divisor for zero
     */
    private void validarDivisor(int divisor) {
        if (divisor == 0) {
            throw new ArithmeticException(MSG_DIVISAO_ZERO);
        }
    }
}
