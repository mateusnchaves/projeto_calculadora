package calculadora;

public class Calculadora {

    public static final String MSG_DIVISAO_ZERO = "Erro: não é possível dividir por zero.";
    public static final String MSG_OP_INVALIDA = "Erro: operação inválida. Use +, -, * ou /.";

    // realiza a operacao basica entre a e b
    public double calc(int a, int b, String op) {
        double r;
        if ("+".equals(op)) {
            r = (double) a + b;
        } else if ("-".equals(op)) {
            r = (double) a - b;
        } else if ("*".equals(op)) {
            r = (double) a * b;
        } else if ("/".equals(op)) {
            if (b == 0) {
                throw new ArithmeticException(MSG_DIVISAO_ZERO);
            }
            r = (double) a / b;
        } else {
            throw new IllegalArgumentException(MSG_OP_INVALIDA + " Recebido: " + op);
        }
        return r;
    }
}
