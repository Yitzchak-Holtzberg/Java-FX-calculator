import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class GuiCalculator extends Application {

    private TextField display;
    Evaluate evaluator;

    @Override
    public void start(Stage primaryStage) {

        display = new TextField();
        display.setEditable(false);
        display.setMinSize(200, 50);
        display.setFocusTraversable(false);

        GridPane grid = new GridPane();
        grid.getStylesheets().add(getClass().getResource("dark-theme.css").toExternalForm());
        grid.setVgap(5);
        grid.setHgap(5);


        String[] buttonLabels = {
                "7", "8", "9", "/", "C",
                "4", "5", "6", "*", "^",
                "1", "2", "3", "-", ")",
                "0", "=", "+", " ", "("
        };


        grid.add(display, 0, 0, 5, 1);

        int labelIndex = 0;
        for (int i = 1; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                Button btn = new Button(buttonLabels[labelIndex]);
                btn.setMinSize(50, 50);
                btn.setOnAction(e -> buttonPressed(btn.getText()));
                grid.add(btn, j, i);
                labelIndex++;
            }
        }



        Scene scene = new Scene(grid);
        grid.requestFocus();
        scene.setOnKeyPressed(event -> {
            String keyName = event.getText();
            switch (event.getCode()) {
                case ENTER:
                    keyName = "=";
                    break;
                case ESCAPE:
                    keyName = "C";
                    break;
                case SLASH:
                    keyName = "/";
                    break;
                case STAR:
                    keyName = "*";
                    break;
                case PLUS:
                    keyName = "+";
                    break;
                case MINUS:
                    keyName = "-";
                    break;
                default:
                    if (keyName.matches("[0-9]")) break;
                    return;
            }
            buttonPressed(keyName);
            event.consume();
        });
        primaryStage.setTitle("Calculator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void buttonPressed(String label) {
        evaluator = new Evaluate(display.getText());
        if (label.equals("=")) {
            try {
                display.setText(evaluator.eval() + " ");
            } catch (Exception e) {
                display.setText("Error");
            }
        } else if (label.equals("C")) {
            display.setText("");
        } else {
            if (display.getText().endsWith(" ")) {
                display.setText(label);
            } else {
                display.appendText(label);
            }
        }
    }
}



