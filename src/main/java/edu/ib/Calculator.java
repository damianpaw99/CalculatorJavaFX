package edu.ib;

import javafx.scene.control.ListView;
import org.mariuszgromada.math.mxparser.Function;

public class Calculator {
    private StringBuilder expresion = new StringBuilder();

    public String calculate(ListView<String> historyList) {
        Function f = new Function("f(x)=" + expresion.toString());

        String outputValue = "";

        //checking ans
        if (!historyList.getItems().isEmpty()) {
            if (historyList.getItems().get(0) != null) {
                String value = "";
                value = historyList.getItems().get(0).substring(historyList.getItems().get(0).indexOf('=') + 1);
                String text = expresion.toString().replace("ans", value);
                f = new Function("f(x)=" + text);
            }
        }

        if (f.checkSyntax()) {
            outputValue = String.valueOf(f.calculate());
        } else {
            outputValue = "Syntax error";
        }
        return outputValue;
    }

    public void removeExpresion() {
        String s5 = expresion.substring(Math.max(0, expresion.length() - 5));
        String s4 = expresion.substring(Math.max(0, expresion.length() - 4));
        String s3 = expresion.substring(Math.max(0, expresion.length() - 3));
        String s2 = expresion.substring(Math.max(0, expresion.length() - 2));
        if (s5.contains("sqrt(")) {
            expresion.setLength(Math.max(0, expresion.length() - 5));
        } else if (s4.contains("ctg(") || s4.contains("cos(") || s4.contains("sin(") || s4.contains("log(") || s4.contains("min(") || s4.contains("max(")) {
            expresion.setLength(Math.max(0, expresion.length() - 4));
        } else if (s3.contains("tg(") || s3.contains("ln(") || s3.contains("ans")) {
            expresion.setLength(Math.max(0, expresion.length() - 3));
        } else if (s2.contains("pi")) {
            expresion.setLength(Math.max(0, expresion.length() - 2));
        } else {
            expresion.setLength(Math.max(0, expresion.length() - 1));
        }
    }

    public void addExpresion(String expresion) {
        this.expresion.append(expresion);
    }

    public StringBuilder getExpresion() {
        return expresion;
    }

    public void setExpresion(StringBuilder expresion) {
        this.expresion = expresion;
    }

    @Override
    public String toString() {
        return expresion.toString();
    }
}
