package calculadora;

public class Calculadora {

    // realiza a operacao basica entre a e b
    public double calc(int a, int b, String op) {
        double r = 0;
        if ("+".equals(op)) {
            r = (double) a + b;
        } else if ("-".equals(op)) {
            r = (double) a - b;
        } else if ("*".equals(op)) {
            r = (double) a * b;
        } else if ("/".equals(op)) {
            r = (double) a / b;
        }
        return r;
    }
}
