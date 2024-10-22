package controll;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.EuklidAlgo;

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

    @FXML
    protected void onCalcButtonClicked(){

        int a = getInputA();
        int b = getInputB();
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
     * @return den geparsten Wert der eingabe
     */
    int getInputB(){

        try{
            return Integer.parseInt(inputB.getText());
        }
        catch(Exception e){
            setWarningLabel("Da ist etwas schief gelaufen. Fehlercode 0x04");
            return 0;
        }
    }

    /**
     Getter für Textfeld B
     @return den geparsten Wert der eingabe
     */
    int getInputA(){
        try{
            return Integer.parseInt(inputA.getText());
        }
        catch(Exception e){
            setWarningLabel("Da ist etwas schief gelaufen. Fehlercode 0x03");
            return 0;
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