package controll;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.EuklidAlgo;
import model.MathsThings;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;

public class Controller {
    @FXML
    private Label welcomeText;
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

    int a =0;
    int b =0;
    BigInteger aBig =null;
    BigInteger bBig =null;

    @FXML
    protected void onCalcButtonClicked(){

        MathsThings mathsThings = new MathsThings();
        String a_string = getInputA();
        String b_string = getInputB();

        //Prüft ob a_string und b_string bigInt sind
        if(proofExp(a_string)&&proofExp(b_string)){
            aBig = mathsThings.expStringToBigInt(a_string);
            bBig = mathsThings.expStringToBigInt(b_string);
            if(!proofGreaterLess(aBig,bBig)){
                aBig=null;
                bBig=null;
            }
        }else {
            a = mathsThings.stringToInteger(a_string);
            b = mathsThings.stringToInteger(b_string);
            if(!proofGreaterLess(a,b)){
                a=0;
                b=0;
            }
        }

        //Mutex
        AtomicInteger result = new AtomicInteger();

        if(!(a==0 || b==0)){
            setWarningLabel("Alles in Ordnung!");
            try {
                Thread t1 = new Thread(() -> {
                    EuklidAlgo euklidAlgo = new EuklidAlgo(a,b);
                    result.set(euklidAlgo.calcGgt());
                });
                t1.start();
                t1.join();
                setresultLabel("Der GGT ist: "+String.valueOf(Integer.parseInt(String.valueOf(result.get()))));
            }
            catch(Exception e){
                setWarningLabel("Da ist etwas schief gelaufen. Fehlercode 0x01");
            }
        }
        else {
           setWarningLabel("Da ist etwas schief gelaufen. Fehlercode 0x02");
        }


    }

    /**
     * Getter für TextFeld A
     * @return den Wert der eingabe
     */
    String getInputB(){

        try{
            return  inputB.getText();
        }
        catch(Exception e){
            setWarningLabel("Da ist etwas schief gelaufen. Fehlercode 0x04");
            return "Fehlercode 0x04";
        }
    }

    /**
     Getter für Textfeld B
     @return den Wert der eingabe
     */
    String getInputA(){
        try{
            return inputA.getText();
        }
        catch(Exception e){
            setWarningLabel("Da ist etwas schief gelaufen. Fehlercode 0x03");
            return "Fehlercode 0x03";
        }
    }

    boolean proofExp(String exp){

        if (exp == null || exp.isEmpty()) {
            return false;
        }
        if(exp.contains("^")){
            return true;
        }
        return false;
    }

    boolean proofGreaterLess(int x , int y){
        if (x > y){
            return true;
        }
        else {
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
    void clearInputA(){
        inputA.clear();
    }

    @FXML
    void clearInputB(){
        inputB.clear();
    }

    void setWarningLabel(String warning){
        warningLabel.setText(warning);
    }
    void setresultLabel(String result){
        resultLabel.setText(result);
    }

    void setTextFieldB(int text){
        inputB.setText(Integer.toString(text));
    }
    void setTextFieldB(String text){
        inputB.setText(text);
    }

    void setTextFieldA(int text){
        inputA.setText(Integer.toString(text));
    }
    void setTextFieldA (String text){
        inputA.setText(text);
    }

}