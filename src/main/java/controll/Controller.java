package controll;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.EuklidAlgo;
import model.EuklidAlgoErweiterter;
import model.MathsThings;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.concurrent.atomic.AtomicInteger;

public class Controller {
    @FXML
    private TextField inputA;
    @FXML
    private TextField inputB;
    @FXML
    private Button calcButton;
    @FXML
    private Label warningLabel;
    @FXML
    private Label resultLabel;
    @FXML
    private Label stepsLabel;
    @FXML
    private Label resultAverLabel;
    @FXML
    private TextField inputN;
    @FXML
    private TextField inputAnz;
    @FXML
    private TextField inputC;
    @FXML
    private TextField inputD;
    @FXML
    private TextField inputM;
    @FXML
    private Label resultD;
    @FXML
    private Label resultX;
    @FXML
    private Label resultY;
    @FXML
    private Button calculateAver;
    @FXML
    private Button solveEqButton;


    int a = 0;
    int b = 0;
    BigInteger aBig = null;
    BigInteger bBig = null;
    boolean type = true; // true ->  int, false -> bigInt

    @FXML
    protected void onCalcButtonClicked() {

        MathsThings mathsThings = new MathsThings();
        String a_string = getInputA();
        String b_string = getInputB();

        //Prüft ob a_string und b_string bigInt sind
        if (proofExp(a_string) && proofExp(b_string)) {
            aBig = mathsThings.expStringToBigInt(a_string);
            bBig = mathsThings.expStringToBigInt(b_string);
            type = false;
            if (!proofGreaterLess(aBig, bBig)) {
                aBig = null;
                bBig = null;
            }
            //Ganze normale Integer
        } else {
            a = mathsThings.stringToInteger(a_string);
            b = mathsThings.stringToInteger(b_string);
            type = true;
            if (!proofGreaterLess(a, b)) {
                a = 0;
                b = 0;
            }
        }

        if (type) {
            startEuklidInt();
        } else {
            startEuklidBigInt();
        }
    }

    @FXML
    protected void onSolveButtonClicked() {

        // c*y + m*y = d
        String cString = getInputC();
        String mString = getInputM();
        String dString = getInputD();

        boolean isBigNumber = false;

        BigInteger cBig = null;
        BigInteger mBig = null;
        BigInteger dBig = null;
        int c =0;
        int m=0;
        int d=0;
         MathsThings mathsThings = new MathsThings();

        //Prüft ob a_string und b_string bigInt sind
        if (proofExp(cString) && proofExp(mString)&&proofExp(dString)) {
            cBig = mathsThings.expStringToBigInt(cString);
            mBig  = mathsThings.expStringToBigInt(mString);
            dBig = mathsThings.expStringToBigInt(dString);

            isBigNumber = true;

            //Ganze normale Integer
        } else {
            c = mathsThings.stringToInteger(cString);
            m = mathsThings.stringToInteger(mString);
            d = mathsThings.stringToInteger(dString);
            isBigNumber = false;

        }

        if(isBigNumber){
            EuklidAlgoErweiterter euklidAlgoErweiterter = new EuklidAlgoErweiterter(cBig,mBig);
            try {

                BigInteger[] results = euklidAlgoErweiterter.calculateEuklidAlgoErweiterter(dBig);
                BigInteger resultX = results[1];
                BigInteger resultY = results[2];

                setResultD((dBig.toString()));
                setResultX((resultX.toString()));
                setResultY((resultY.toString()));


            } catch (Exception e) {
                setResultD("Keine Lösung - BigInteger");
                setResultX("Keine Lösung - BigInteger ");
                setResultY("Keine Lösung - BigInteger ");
            }
        }else{

            try {
               EuklidAlgoErweiterter euklidAlgoErweiterter = new EuklidAlgoErweiterter(c,m);
                int[] results = euklidAlgoErweiterter.calculateEuklidAlgoErweiterter(d);
                int resultX = results[1];
                int resultY = results[2];
                setResultD(Integer.toString(d));
                setResultX(Integer.toString(resultX));
                setResultY(Integer.toString(resultY));

            } catch (Exception e) {
                setResultD("Keine Lösung");
                setResultX("Keine Lösung");
                setResultY("Keine Lösung");
            }

        }





    }

    private void setStepsLabel(int steps) {
        stepsLabel.setText("Durchläufe: " + String.valueOf(steps));
    }

    private void startEuklidInt() {
        //Mutex
        AtomicInteger result = new AtomicInteger();
        AtomicInteger steps = new AtomicInteger();
        if (!(a == 0 || b == 0)) {
            setWarningLabel("Alles in Ordnung!");
            try {
                Thread t1 = new Thread(() -> {
                    EuklidAlgo euklidAlgo = new EuklidAlgo(a, b);
                    result.set(euklidAlgo.calcGgt());
                    steps.set(euklidAlgo.getSteps());
                });
                t1.start();
                t1.join();
                setresultLabel("Der GGT ist:  " + String.valueOf(Integer.parseInt(String.valueOf(result.get()))));
                setStepsLabel(steps.get());
            } catch (Exception e) {
                setWarningLabel("Da ist etwas schief gelaufen. Fehlercode 0x01");
                resetGui();
            }
        } else {
            setWarningLabel("Da ist etwas schief gelaufen. Fehlercode 0x02");
            resetGui();
        }

    }

    /**
     * Startet die Berechnung des GGT von BigInteger
     */
    private void startEuklidBigInt() {
        // Mutex (Initialisierung von result mit 0, um NullPointerException zu vermeiden)
        BigInteger[] results = {BigInteger.ZERO};
        AtomicInteger steps = new AtomicInteger();

        if (aBig != null && bBig != null) {
            setWarningLabel("Alles in Ordnung!");
            try {
                // Thread zur Berechnung des GGT
                Thread t1 = new Thread(() -> {
                    EuklidAlgo euklidAlgo = new EuklidAlgo(aBig, bBig);
                    results[0] = euklidAlgo.calcGgtBigInteger();
                    steps.set(euklidAlgo.getSteps());
                });
                t1.start();   // Thread starten
                t1.join();    // Hauptthread wartet, bis t1 fertig ist

                setStepsLabel(steps.get());
                setresultLabel("Der GGT ist: " + results[0].toString());
            } catch (Exception e) {
                setWarningLabel("Da ist etwas schief gelaufen. Fehlercode 0x05");
                resetGui();
            }
        } else {
            setWarningLabel("Da ist etwas schief gelaufen. Fehlercode 0x06");
            resetGui();
        }
    }

    /**
     * Getter für TextFeld A
     * @return den Wert der eingabe
     */
    String getInputB() {

        try {
            return inputB.getText();
        } catch (Exception e) {
            setWarningLabel("Da ist etwas schief gelaufen. Fehlercode 0x04");
            resetGui();
            return "Fehlercode 0x04";
        }
    }

    /**
     * Getter für Textfeld B
     * @return den Wert der eingabe
     */
    String getInputA() {
        try {
            return inputA.getText();
        } catch (Exception e) {
            setWarningLabel("Da ist etwas schief gelaufen. Fehlercode 0x03");
            resetGui();
            return "Fehlercode 0x03";
        }
    }

    boolean proofExp(String exp) {

        if (exp == null || exp.isEmpty()) {
            return false;
        }
        if (exp.contains("^")) {
            return true;
        }
        return false;
    }

    boolean proofGreaterLess(int x, int y) {
        if (x > y) {
            return true;
        } else {
            return false;
        }
    }

    boolean proofGreaterLess(BigInteger x, BigInteger y) {
        // x > y dann true!
        if (x.compareTo(y) > 0) { // Vergleich, ob x größer als y ist
            return true;
        } else {
            return false;
        }
    }

     @FXML
    private void actionOnAverageCalcButtonClicked() {

        int anz = getInputAnz();
        int n = getInputN();
        double ops =0;
        MathsThings mathsThings = new MathsThings();
        ops = mathsThings.durchschnittlicheModuloOperationen(anz,n);
        double gerundet = Math.round(ops*100.0)/100.0;
        String opsString = String.valueOf(gerundet);
        setResultAverLabel(opsString);

    }

    private void resetGui() {
        setStepsLabel(0);
        setresultLabel("Fehler");
    }

    @FXML
    void clearInputA() {
        inputA.clear();
    }

    @FXML
    void clearInputB() {
        inputB.clear();
    }

    void setWarningLabel(String warning) {
        warningLabel.setText(warning);
    }

    void setresultLabel(String result) {
        resultLabel.setText(result);
    }

    void setTextFieldB(int text) {
        inputB.setText(Integer.toString(text));
    }

    void setTextFieldB(String text) {
        inputB.setText(text);
    }

    void setTextFieldA(int text) {
        inputA.setText(Integer.toString(text));
    }

    void setTextFieldA(String text) {
        inputA.setText(text);
    }

    void setResultAverLabel(String result) {
        resultAverLabel.setText("Ergebnis ist: "+result);
    }

    int getInputAnz(){

        try {
            return Integer.parseInt(inputAnz.getText());
        }catch (NumberFormatException e) {
           setWarningLabel("Etwas ist schief gelaufen. Fehlercode 0x07");
           return 1;
        }

    }

    int getInputN(){

        try {
            return Integer.parseInt(inputN.getText());
        }catch (NumberFormatException e) {
            setWarningLabel("Etwas ist schief gelaufen. Fehlercode 0x08");
            return 1;
        }

    }

    String getInputC(){
        try {
            return  inputC.getText();
        }
        catch (NumberFormatException e) {
            setWarningLabel("Etwas ist schief gelaufen. Fehlercode 0x09");
              return "";
        }
    }

    String getInputD(){
            try {
                return  inputD.getText();
            }
            catch (NumberFormatException e) {
                    setWarningLabel("Etwas ist schief gelaufen. Fehlercode 0x0a");
                    return "";
            }
    }

    String getInputM(){
        try {
            return  inputM.getText();
        }
        catch (NumberFormatException e) {
                setWarningLabel("Etwas ist schief gelaufen. Fehlercode 0x0b");
                return "";
        }
    }

    private void setResultD(String result) {
        resultD.setText(result);
    }
    private  void  setResultY(String result) {
        resultY.setText(result);
    }
    private  void setResultX(String result) {
        resultX.setText(result);
    }



}