package model;

import java.math.BigInteger;

public class EuklidAlgo {
    private int a;
    private int b;
    private BigInteger bigA;
    private BigInteger bigB;

    // Konstruktor für int-Werte
    public EuklidAlgo(int a, int b) {
        this.a = Math.abs(a);
        this.b = Math.abs(b);
    }

    // Konstruktor für BigInteger-Werte
    public EuklidAlgo(BigInteger bigA, BigInteger bigB) {
        this.bigA = bigA.abs();
        this.bigB = bigB.abs();
    }

    public int calcGgt() {
        if (a == 0) return b; // Sonderfall wenn a = 0
        if (b == 0) return a; // Sonderfall wenn b = 0

        int r; // r ist der Rest
        int ggt =0;
        boolean exit = false;

        // Euklidischer Algorithmus
        while (!exit) {
            r = a % b;
            a = b;
            b = r;
            if(r==0){
                exit=true;
            }else{
                ggt=r;
            }
            System.out.println("Aktueller Rest: " + r);
        }
        return ggt; // GGT ist, wenn der Rest 0 ist
    }

    // GGT für BigInteger-Werte berechnen
    public BigInteger calcGgtBigInteger() {
        if (bigA.equals(BigInteger.ZERO)) return bigB;
        if (bigB.equals(BigInteger.ZERO)) return bigA;

        BigInteger r;
        BigInteger ggt = BigInteger.ZERO;
        boolean exit = false;

        // Euklidischer Algorithmus für BigInteger
        while (!exit) {
            r = bigA.mod(bigB);
            bigA = bigB;
            bigB = r;
            if (r.equals(BigInteger.ZERO)) {
                exit = true;
            } else {
                ggt = r;
            }
        }
        return ggt;
    }
}


