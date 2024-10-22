package model;

public class EuklidAlgo {
    private int a;
    private int b;

    public EuklidAlgo(int a, int b) {
        // Falls negative Zahlen übergeben werden, konvertiere sie zu positiven
        this.a = Math.abs(a);
        this.b = Math.abs(b);
    }

    /*
       ggt(a,b)
       bsp: ggt (64, 45)

               a    q    b    r
               64 = 1 * 45 + 19
               45 = 2 * 19 + 7
               19 = 2 * 7 + 6
               7  = 1 * 6 + 1
               6  = 6 * 1 + 0 <---

               ggt wäre 1, also teilerfremd
     */

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
        return ggt; // GGT ist, wenn der Rest 0 ist, der aktuelle Wert von a
    }
}


