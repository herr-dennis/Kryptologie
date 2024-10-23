package model;
import java.math.BigInteger;

public class MathsThings {

    /**
     * Wandelt einen String der in Exp-Darstellung ist, in einen BigInteger um.
     * @param exp ist der Eingabe-String des Users (z.B. "5^1000").
     * @return Das Ergebnis der Exponentialberechnung als BigInteger.
     */
    public BigInteger expStringToBigInt(String exp) {

        // Teile den String in Basis und Exponent auf
        String[] expParts = exp.split("\\^");

        // Basis und Exponent verarbeiten
        BigInteger base = new BigInteger(expParts[0]);
        int exponent = Integer.parseInt(expParts[1]);

        // Berechne die Potenz
        BigInteger result = base.pow(exponent);

        // Gib das Ergebnis zurück
        return result;
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

    public int stringToInteger(String int_) {

        int result = 0;
        try {
            result = Integer.parseInt(int_);
        }
        catch (NumberFormatException e) {
            return result;
        }
        return result;
    }





}
