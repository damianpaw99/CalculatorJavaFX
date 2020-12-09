package edu.ib;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import org.mariuszgromada.math.mxparser.Function;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea textOutput;

    @FXML
    private ListView<String> historyList;

    private StringBuilder expresion = new StringBuilder();

    private ObservableList<String> list = FXCollections.observableArrayList();

    /**
     * Method adding operation to expresion, base on what button was pressed
     * @param event Event parameters
     */
    @FXML
    void addExpresion(ActionEvent event) {
        String value = ((Button) event.getSource()).getText();
        if(textOutput.getParagraphs().size()==2) {
            expresion.setLength(0);
            textOutput.setText(expresion.toString());
        }
        switch (value) {
            case "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "(", ")", ".", "+", "-", "/", "*", "pi", "e", ",", "ans", "^", "!" -> expresion.append(value);
            case "sin" -> expresion.append("sin(");
            case "cos" -> expresion.append("cos(");
            case "tg" -> expresion.append("tg(");
            case "ctg" -> expresion.append("ctg(");
            case "ln" -> expresion.append("ln(");
            case "min" -> expresion.append("min(");
            case "max" -> expresion.append("max(");
            case "sqrt" -> expresion.append("sqrt(");
            case "log" -> expresion.append("log(");
        }
        textOutput.setText(expresion.toString());
    }
    /**
     * Method adding value from history list
     * @param event Event parameters
     */
    @FXML
    void addValue(MouseEvent event) {
        if (historyList.getSelectionModel().getSelectedItem() != null) {
            String value = historyList.getSelectionModel().getSelectedItem();
            double ans = Double.parseDouble(value.substring(value.indexOf('=') + 1));
            expresion.append(ans);
            textOutput.setText(expresion.toString());
        }
    }
    /**
     * Method calculating answer and updating history list
     * @param event Event parameters
     */
    @FXML
    void calculate(ActionEvent event) {
        String value = "";
        Function f = new Function("f(x)=" + expresion.toString());
        if (!historyList.getItems().isEmpty()) {
            if (historyList.getItems().get(0) != null) {
                value = historyList.getItems().get(0).substring(historyList.getItems().get(0).indexOf('=') + 1);
                String text = expresion.toString().replace("ans", value);
                f = new Function("f(x)=" + text);
            }
        }
        System.out.println(f.getFunctionExpressionString());
        if (f.checkSyntax()) {
            textOutput.setText(expresion.toString() + "\n" + f.calculate());
            if (!Double.isNaN(f.calculate()) && !Double.isInfinite(f.calculate())) {
                list.add(0, expresion.toString() + "=" + f.calculate());
                if (list.size() > 21) list.remove(21);
                historyList.setItems(list);
            }
        } else {
            textOutput.setText(expresion.toString() + "\n" + "Syntax error");
        }
    }

    /**
     * Method clearing input text field
     * @param event Event parameters
     */
    @FXML
    void clear(ActionEvent event) {
        expresion.setLength(0);
        textOutput.clear();
    }
    /**
     * Method clearing input text field and history list
     * @param event Event parameters
     */
    @FXML
    void fullClear(ActionEvent event) {
        list.clear();
        historyList.setItems(list);
        clear(event);
    }
    /**
     * Method removing operation from expresion, base on what button was pressed
     * @param event Event parameters
     */
    @FXML
    void removeExpresion(ActionEvent event) {
        String s5 = expresion.substring(Math.max(0, expresion.length() - 5));
        String s4 = expresion.substring(Math.max(0, expresion.length() - 4));
        String s3 = expresion.substring(Math.max(0, expresion.length() - 3));
        String s2 = expresion.substring(Math.max(0, expresion.length() - 2));
        if (s5.contains("sqrt(")) {
            expresion.setLength(Math.max(0, expresion.length() - 5));
        } else
        if (s4.contains("ctg(") || s4.contains("cos(") || s4.contains("sin(") || s4.contains("log(") || s4.contains("min(") || s4.contains("max(")) {
            expresion.setLength(Math.max(0, expresion.length() - 4));
        }
        else if (s3.contains("tg(") || s3.contains("ln(") || s3.contains("ans")) {
            expresion.setLength(Math.max(0, expresion.length() - 3));
        }
        else if (s2.contains("pi")) {
            expresion.setLength(Math.max(0, expresion.length() - 2));
        } else {
            expresion.setLength(Math.max(0, expresion.length() - 1));
        }
        textOutput.setText(expresion.toString());
    }
    /**
     * Starting method
     */
    @FXML
    void initialize() {
        assert textOutput != null : "fx:id=\"textOutput\" was not injected: check your FXML file 'calculator.fxml'.";
        assert historyList != null : "fx:id=\"historyList\" was not injected: check your FXML file 'calculator.fxml'.";
    }
}
