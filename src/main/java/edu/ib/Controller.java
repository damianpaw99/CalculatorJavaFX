package edu.ib;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea textOutput;

    @FXML
    private ListView<String> historyList;

    private ObservableList<String> list = FXCollections.observableArrayList();

    Calculator c = new Calculator();

    /**
     * Method adding operation to expresion, base on what button was pressed
     *
     * @param event Event parameters
     */
    @FXML
    void addExpresion(ActionEvent event) {
        String value = ((Button) event.getSource()).getText();
        if (textOutput.getParagraphs().size() == 2) {
            c.getExpresion().setLength(0);
            textOutput.setText(c.getExpresion().toString());
        }
        switch (value) {
            case "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "(", ")", ".", "+", "-", "/", "*", "pi", "e", ",", "ans", "^", "!" -> c.addExpresion(value);
            case "sin" -> c.addExpresion("sin(");
            case "cos" -> c.addExpresion("cos(");
            case "tg" -> c.addExpresion("tg(");
            case "ctg" -> c.addExpresion("ctg(");
            case "ln" -> c.addExpresion("ln(");
            case "min" -> c.addExpresion("min(");
            case "max" -> c.addExpresion("max(");
            case "sqrt" -> c.addExpresion("sqrt(");
            case "log" -> c.addExpresion("log(");
        }

        textOutput.setText(c.getExpresion().toString());
    }

    /**
     * Method adding value from history list
     *
     * @param event Event parameters
     */
    @FXML
    void addValue(MouseEvent event) {
        if (historyList.getSelectionModel().getSelectedItem() != null) {
            String value = historyList.getSelectionModel().getSelectedItem();
            c.addExpresion(value.substring(value.indexOf('=') + 1));
            textOutput.setText(c.getExpresion().toString());
        }
    }

    /**
     * Method calculating answer and updating history list
     *
     * @param event Event parameters
     */
    @FXML
    void calculate(ActionEvent event) {

        String out = c.calculate(historyList);
        textOutput.setText(c.getExpresion().toString() + "\n" + out);
        if (!out.contains("Syntax error")) {
            if (!Double.isNaN(Double.parseDouble(out)) && !Double.isInfinite(Double.parseDouble(out))) {
                list.add(0, c.getExpresion().toString() + "=" + out);
                if (list.size() > 21) list.remove(21);
                historyList.setItems(list);
            }
        }

    }

    /**
     * Method clearing input text field
     *
     * @param event Event parameters
     */
    @FXML
    void clear(ActionEvent event) {
        c.getExpresion().setLength(0);
        textOutput.clear();
    }

    /**
     * Method clearing input text field and history list
     *
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
     *
     * @param event Event parameters
     */
    @FXML
    void removeExpresion(ActionEvent event) {
        c.removeExpresion();
        textOutput.setText(c.getExpresion().toString());
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
