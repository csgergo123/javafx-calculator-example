package calculator;

import calculator.model.Calculator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculatorController {

    @FXML
    private TextField display;

    private Calculator calculator;
    private boolean startNumber = true;
    private double number1;
    private String operator = "";

    @FXML
    private void initialize() {
        calculator = new Calculator();
    }

    @FXML
    public void processDigit(ActionEvent event) {
        String digitPressed = ((Button) event.getSource()).getText();
        System.out.println(digitPressed);
        if (startNumber || display.getText().equals("0")) {
            display.setText(digitPressed);
        } else {
            display.setText(display.getText() + digitPressed);
        }
        startNumber = false;
    }

    @FXML
    public void processOperator(ActionEvent event) {
        String operatorPressed = ((Button) event.getSource()).getText();
        System.out.println(operatorPressed);
        if (operatorPressed.equals("=")) {
            if (operator.isEmpty()) {
                return;
            }
            double number2 = Double.parseDouble(display.getText());
            double result = calculator.calculate(number1, number2, operator);
            BigDecimal decimal = new BigDecimal(result).setScale(15, RoundingMode.HALF_UP).stripTrailingZeros();
            display.setText(decimal.toPlainString());
            operator = "";
        } else {
            if (! operator.isEmpty()) {
                return;
            }
            number1 = Double.parseDouble(display.getText());
            operator = operatorPressed;
            startNumber = true;
        }
    }

    @FXML
    public void clear() {
        number1 = 0;
        operator = "";
        display.setText("0");
        startNumber = true;
    }

    @FXML
    public void decimalPoint() {
        String textNumber = display.getText();
        if(textNumber.contains(".")) {
            return;
        }
        display.setText(textNumber + ".");
    }

    @FXML
    public void negation() {
        String textNumber = display.getText();
        if(textNumber.contains("-")) {
            textNumber = textNumber.replace("-", "");
        } else {
            textNumber = "-" + textNumber;
        }
        display.setText(textNumber);
    }
}