package calculadora;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Testes unitários da classe {@link Calculadora}, escritos com <b>JUnit 5</b>.
 *
 * <p>Cada teste verifica o resultado com as asserções do JUnit
 * ({@code assertEquals}, {@code assertThrows}) e também imprime no console as
 * <b>entradas utilizadas</b>, o <b>resultado esperado</b> e o
 * <b>resultado obtido</b>.</p>
 *
 * <p>Cobertura: soma, subtração, multiplicação, divisão, divisão por zero e
 * operações inválidas.</p>
 *
 * @author Mateus Novais Chaves
 * @version 1.0
 */
@DisplayName("Testes da Calculadora FinançApp")
public class TesteCalculadora {

    /** Diferença máxima aceita ao comparar dois valores {@code double}. */
    private static final double TOLERANCIA = 1e-9;

    /** Calculadora testada; recriada antes de cada teste. */
    private Calculadora calculadora;

    /**
     * Cria uma classe de testes. Usado pelo JUnit para executar cada teste.
     */
    public TesteCalculadora() {
    }

    /**
     * Cria uma nova calculadora antes de cada teste, para que um teste não
     * interfira no outro.
     */
    @BeforeEach
    public void prepararCalculadora() {
        calculadora = new Calculadora();
    }

    // ---------------------------------------------------------------
    // Soma
    // ---------------------------------------------------------------

    /** Soma de dois positivos: 5 + 3 = 8. */
    @Test
    @DisplayName("Soma: 5 + 3 = 8")
    public void deveSomarDoisNumeros() {
        verificarResultado(5, 3, "+", 8.0);
    }

    /** Soma com número negativo: -4 + 10 = 6. */
    @Test
    @DisplayName("Soma com negativo: -4 + 10 = 6")
    public void deveSomarComNumeroNegativo() {
        verificarResultado(-4, 10, "+", 6.0);
    }

    /** Soma que ultrapassa o limite de {@code int} não deve "estourar". */
    @Test
    @DisplayName("Soma sem estouro de int: 2147483647 + 1 = 2147483648")
    public void deveSomarSemEstourarInt() {
        verificarResultado(Integer.MAX_VALUE, 1, "+", 2147483648.0);
    }

    // ---------------------------------------------------------------
    // Subtração
    // ---------------------------------------------------------------

    /** Subtração simples: 10 - 4 = 6. */
    @Test
    @DisplayName("Subtração: 10 - 4 = 6")
    public void deveSubtrairDoisNumeros() {
        verificarResultado(10, 4, "-", 6.0);
    }

    /** Subtração com resultado negativo: 3 - 8 = -5. */
    @Test
    @DisplayName("Subtração com resultado negativo: 3 - 8 = -5")
    public void deveSubtrairComResultadoNegativo() {
        verificarResultado(3, 8, "-", -5.0);
    }

    // ---------------------------------------------------------------
    // Multiplicação
    // ---------------------------------------------------------------

    /** Multiplicação simples: 6 * 7 = 42. */
    @Test
    @DisplayName("Multiplicação: 6 * 7 = 42")
    public void deveMultiplicarDoisNumeros() {
        verificarResultado(6, 7, "*", 42.0);
    }

    /** Multiplicação por zero: 9 * 0 = 0. */
    @Test
    @DisplayName("Multiplicação por zero: 9 * 0 = 0")
    public void deveMultiplicarPorZero() {
        verificarResultado(9, 0, "*", 0.0);
    }

    // ---------------------------------------------------------------
    // Divisão
    // ---------------------------------------------------------------

    /** Divisão exata: 20 / 4 = 5. */
    @Test
    @DisplayName("Divisão exata: 20 / 4 = 5")
    public void deveDividirDoisNumeros() {
        verificarResultado(20, 4, "/", 5.0);
    }

    /** Divisão não exata mantém a parte decimal: 7 / 2 = 3.5. */
    @Test
    @DisplayName("Divisão com decimal: 7 / 2 = 3.5")
    public void deveDividirMantendoDecimais() {
        verificarResultado(7, 2, "/", 3.5);
    }

    // ---------------------------------------------------------------
    // Divisão por zero
    // ---------------------------------------------------------------

    /** Dividir por zero deve lançar {@link ArithmeticException}. */
    @Test
    @DisplayName("Divisão por zero: 10 / 0 lança ArithmeticException")
    public void deveImpedirDivisaoPorZero() {
        verificarExcecao(10, 0, "/", ArithmeticException.class, Calculadora.MSG_DIVISAO_ZERO);
    }

    // ---------------------------------------------------------------
    // Operações inválidas
    // ---------------------------------------------------------------

    /** Símbolo não suportado deve lançar {@link IllegalArgumentException}. */
    @Test
    @DisplayName("Operação inválida: \"%\" lança IllegalArgumentException")
    public void deveRejeitarSimboloInvalido() {
        verificarExcecao(5, 3, "%", IllegalArgumentException.class, Calculadora.MSG_OP_INVALIDA);
    }

    /** Texto no lugar do símbolo deve ser rejeitado. */
    @Test
    @DisplayName("Operação inválida: \"soma\" lança IllegalArgumentException")
    public void deveRejeitarOperacaoPorExtenso() {
        verificarExcecao(5, 3, "soma", IllegalArgumentException.class, Calculadora.MSG_OP_INVALIDA);
    }

    /** Operação vazia deve ser rejeitada. */
    @Test
    @DisplayName("Operação vazia lança IllegalArgumentException")
    public void deveRejeitarOperacaoVazia() {
        verificarExcecao(5, 3, "", IllegalArgumentException.class, Calculadora.MSG_OP_INVALIDA);
    }

    /** Operação nula deve ser rejeitada sem {@code NullPointerException}. */
    @Test
    @DisplayName("Operação nula lança IllegalArgumentException")
    public void deveRejeitarOperacaoNula() {
        verificarExcecao(5, 3, null, IllegalArgumentException.class, Calculadora.MSG_OP_INVALIDA);
    }

    // ---------------------------------------------------------------
    // Métodos auxiliares
    // ---------------------------------------------------------------

    /**
     * Executa o cálculo, exibe entradas/esperado/obtido e confere o resultado.
     *
     * @param a        primeiro operando
     * @param b        segundo operando
     * @param op       operação testada
     * @param esperado resultado esperado
     */
    private void verificarResultado(int a, int b, String op, double esperado) {
        double obtido = calculadora.calc(a, b, op);
        exibir(a, b, op, String.valueOf(esperado), String.valueOf(obtido));
        assertEquals(esperado, obtido, TOLERANCIA);
    }

    /**
     * Confere se o cálculo lança a exceção esperada, com a mensagem correta,
     * e exibe entradas/esperado/obtido.
     *
     * @param a                primeiro operando
     * @param b                segundo operando
     * @param op               operação testada
     * @param tipoEsperado     tipo de exceção esperado
     * @param mensagemEsperada início esperado da mensagem da exceção
     */
    private void verificarExcecao(int a, int b, String op,
                                  Class<? extends RuntimeException> tipoEsperado,
                                  String mensagemEsperada) {
        RuntimeException excecao = assertThrows(tipoEsperado, () -> calculadora.calc(a, b, op));
        exibir(a, b, op,
                tipoEsperado.getSimpleName() + " (\"" + mensagemEsperada + "\")",
                excecao.getClass().getSimpleName() + " (\"" + excecao.getMessage() + "\")");
        assertTrue(excecao.getMessage().startsWith(mensagemEsperada),
                "Mensagem inesperada: " + excecao.getMessage());
    }

    /**
     * Imprime no console as entradas, o resultado esperado e o obtido.
     *
     * @param a        primeiro operando
     * @param b        segundo operando
     * @param op       operação
     * @param esperado resultado esperado (texto)
     * @param obtido   resultado obtido (texto)
     */
    private void exibir(int a, int b, String op, String esperado, String obtido) {
        String opExibida = (op == null) ? "null" : "\"" + op + "\"";
        System.out.println("Entradas: a = " + a + ", b = " + b + ", op = " + opExibida);
        System.out.println("Esperado: " + esperado);
        System.out.println("Obtido  : " + obtido);
        System.out.println("-----------------------------------------------");
    }
}
