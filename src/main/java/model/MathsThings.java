package model;
import java.math.BigInteger;
import java.util.Random;
import java.util.Stack;

public class MathsThings {

    /**
     * Wandelt einen String der in Exp-Darstellung ist, in einen BigInteger um.
     *
     * @param exp ist der Eingabe-String des Users (z.B. "5^1000").
     * @return Das Ergebnis der Exponentialberechnung als BigInteger.
     */
    public BigInteger expStringToBigInt(String exp) {

        String potenz = exp;
        String expression ="";

        if(proofStringOfOps(exp)) {
            if ((proofStringOfOps(exp))) {
                String[] result = splitExpression(exp);
                potenz = result[0];
                expression = result[1];
            }
        }
        String[]expParts = potenz.split("\\^");
        // Basis und Exponent verarbeiten
        BigInteger base = new BigInteger(expParts[0]);
        int exponent = Integer.parseInt(expParts[1]);

        // Berechne die Potenz
        BigInteger result = base.pow(exponent);

        if(!expression.isEmpty()){
            char c = expression.charAt(0);
            if(Character.isDigit(c)){
                result = result.add( new BigInteger(expression.trim()));
            }
        }

        return result;
    }


    public double durchschnittlicheModuloOperationen(int anz, int n) {
        Random random = new Random();
        int gesamtOperationen = 0;

        for (int i = 0; i < anz; i++) {

            int a = random.nextInt(n);
            int b = random.nextInt(n);

            EuklidAlgo euklidAlgo = new EuklidAlgo(a, b);
            euklidAlgo.calcGgt();  // Hier wird die Anzahl der Schritte abgefragt
            int operationen = euklidAlgo.getSteps();
            gesamtOperationen += operationen;
        }

        return (double) gesamtOperationen / anz;
    }

    public int squareAndMultiply(int base, int exponent, int mod) {

        /**
         *
         * 5^21 mod 5
         *
         * 5^1 mod 5 =  r
         * 5^2 = r * r mod 5 = r
         * 5^4 = r * r mod 5
         *
         */
        base = base % mod;
        int result = 1;

        while (exponent > 0) {
            result = (result * base) % mod;
            exponent = exponent >> 1; // Dieser Schritt halbiert den Exponenten
            // Basis quadrieren
            base = (base * base) % mod;
        }
        return result;
    }

    public int potenzieren(int base, int exponent) {
        int result = 1;

        for (int i = 1; i <= exponent; i++) {
            result *= base;
        }

        return result;
    }


    private boolean proofStringOfOps(String exp) {
        if(exp.contains("+") || exp.contains("-") || exp.contains("*") || exp.contains("/")) {
            return true;
        }
        return false;
    }

    public int stringToInteger(String int_) {

        int result = 0;
        try {
            result = Integer.parseInt(int_);
        } catch (NumberFormatException e) {
            return result;
        }
        return result;
    }

    /**
     * @param text der Eingabestring in infix
     * @return der Eingabestring in postfix
     */
    public String parseArithmetikExp(String text) {

        StringBuilder postfix = new StringBuilder();
        Stack<Character> operatorStack = new Stack<>();

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            if (Character.isDigit(c)) {
                postfix.append(c);
            } else if (proofOperator(c)) {
                //Wenn der Operator aufm Stack >= als der current-Char, dann wird der Operator aufm Stack entfernt
                //und dem String Postfix zugefügt.
                while (!operatorStack.isEmpty() && wertigkeitOps(operatorStack.peek()) >=
                        wertigkeitOps(c)) {
                    postfix.append(operatorStack.pop());
                }
                operatorStack.push(c);
            }

        }
        //Restliche Ops auf den PostfixString adden
        while (!operatorStack.isEmpty()) {
            postfix.append(operatorStack.pop());
        }
        String postfixString = postfix.toString();

        return postfixString;

    }


    public int calculatePostfix(String postfix) {
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < postfix.length(); i++) {
            char c = postfix.charAt(i);
            if (Character.isDigit(c)) {
                stack.push(c - '0');
            } else if (proofOperator(c)) {
                int b = stack.pop();
                int a = stack.pop();
                int result = calculateStackOps(a,b,c);
                stack.push(result);

            }

        }

        return stack.pop();
    }


    private int calculateStackOps(int a, int b, char c) {
        switch (c) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) throw new ArithmeticException("Division durch Null");
                return a / b;
            default:
                throw new IllegalArgumentException("Ungültiger Operator: " + c);
        }

    }

    private boolean proofOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }


    private int wertigkeitOps(Character op) {
        if (op == '+' || op == '-') {
            return 1;
        } else if (op == '*' || op == '/') {
            return 2;
        }
        return 0;
 }



    public String[]  splitExpression(String exp) {
        String potenz = "";      // Für den Potenzteil
        String expression = "";  // Für den restlichen Ausdruck

        // Finde die Position des Potenzoperators "^"
        int indexOfPotenz = exp.indexOf('^');

        if (indexOfPotenz != -1) {
            // Suche die Basis der Potenz
            int baseStart = indexOfPotenz - 1;
            while (baseStart >= 0 && Character.isDigit(exp.charAt(baseStart))) {
                baseStart--;
            }
            // Extrahiere die Basis und den Exponenten für den Potenzterm
            potenz = exp.substring(baseStart + 1, exp.length());

            // Der restliche Ausdruck beginnt nach dem Potenzterm
            int endOfPotenz = indexOfPotenz + 1;
            while (endOfPotenz < exp.length() && Character.isDigit(exp.charAt(endOfPotenz))) {
                endOfPotenz++;
            }
            potenz = exp.substring(0, endOfPotenz);

            // Der Rest des Ausdrucks beginnt direkt nach dem Potenzterm
            expression = exp.substring(endOfPotenz).trim();
        }

         expression = expression.replace("+","");

        System.out.println("Potenzteil: " + potenz);
        System.out.println("Restlicher Ausdruck: " + expression);
        return new String[]{potenz, expression};

    }

}