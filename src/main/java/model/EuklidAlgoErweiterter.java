package model;

import java.math.BigInteger;

public class EuklidAlgoErweiterter extends EuklidAlgo  {

    public EuklidAlgoErweiterter (int a, int b){
        super(a,b   );
    }
    public EuklidAlgoErweiterter (BigInteger a, BigInteger b){
        super(a,b);
    }

    public int[] calculateEuklidAlgoErweiterter(int d) throws Exception {


        /**
         * Hier wird einfach die Tabelle erstellt
         *      old_r     old_x    old_y
         *        r        x         y
         *
         *
         * Initialisierung wie im Script
         */

        int old_r = a;
        int r = b;
        int old_x = 1, x = 0;
        int old_y = 0, y = 1;

        while (r != 0) {
            //q_k+1 = r_k-1 div r_K
            int q = old_r / r;

            // Update r old_r mod r
            int temp = r;
            r = old_r % r;
            old_r = temp;

            //x_k+1 =x_k-1 - x_k * q_k+1
            temp = x;
            x = old_x - q * x;
            old_x = temp;

            // Update y
            //y_k+1 = y_k-1 - y_k * q_k+1
            temp = y;
            y = old_y - q * y;
            old_y = temp;
        }

        if(d%old_r==0){
            int faktor = d/old_r;
            old_x = old_x*faktor;
            old_y = old_y*faktor;
        }else
        {
            throw new Exception("Keine Lösung!");
        }

        // Am Ende von old_r ist der GGT, old_x und old_t sind die Koeffizienten
        return new int[]{old_r, old_x, old_y};
    }


    public BigInteger[] calculateEuklidAlgoErweiterter(BigInteger d) throws Exception {

        /**
         * Hier wird einfach die Tabelle erstellt
         *      old_r     old_x    old_y
         *        r        x         y
         *
         * Initialisierung wie im Script
         */

        // Stelle sicher, dass bigA und bigB initialisiert sind
        if (bigA == null || bigB == null) {
            throw new IllegalArgumentException("bigA und bigB müssen initialisiert werden.");
        }

        BigInteger old_r = bigA;
        BigInteger r = bigB;
        BigInteger old_x = BigInteger.ONE;
        BigInteger x = BigInteger.ZERO;
        BigInteger old_y = BigInteger.ZERO;
        BigInteger y = BigInteger.ONE;

        while (!r.equals(BigInteger.ZERO)) {
            // q_k+1 = old_r div r
            BigInteger q = old_r.divide(r);

            // Update r: old_r mod r
            BigInteger temp = r;
            r = old_r.mod(r);
            old_r = temp;

            // x_k+1 = old_x - q * x
            temp = x;
            x = old_x.subtract(q.multiply(x));
            old_x = temp;

            // Update y
            // y_k+1 = old_y - q * y
            temp = y;
            y = old_y.subtract(q.multiply(y));
            old_y = temp;
        }

        // Überprüfung, ob eine Lösung existiert
        if (!old_r.equals(BigInteger.ZERO) && d.mod(old_r).equals(BigInteger.ZERO)) {
            BigInteger faktor = d.divide(old_r);
            old_x = old_x.multiply(faktor);
            old_y = old_y.multiply(faktor);
        } else {
            throw new Exception("Keine Lösung!");
        }

        // Am Ende von old_r ist der GGT, old_x und old_y sind die Koeffizienten
        return new BigInteger[]{old_r, old_x, old_y};
    }

}
