
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import javafx.stage.Stage;

public class calculadora extends Application {

    private Label display = new Label("");
    private double previousvalue = 0;
    private String operatorString = "";
    private String currentinput = "";

    @Override
    public void start(Stage primaryStage) throws Exception{

        // configurar stage -> titulo

        primaryStage.setTitle("Calculadora Simples");
        

        // criar layout principal -> VBox 

        VBox root = new VBox();
        root.setPadding(new Insets(20));
        root.setSpacing(10);

        // criar display -> Label

        display.setId("display");
        display.setMinSize(200, 40);
        display.setMaxSize(200, 40);   
        display.setMaxWidth(Double.MAX_VALUE);
        VBox.setVgrow(display, Priority.NEVER);
        root.getChildren().add(display);

        // criar botoes -> GridPane

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        grid.setPadding(new Insets(10));
        String[] buttons = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", "C", "=", "+"
        };
        
        int row = 0;
        int col = 0;

        for (String text : buttons) {
            Button button = new Button(text);
            button.setMinSize(50, 50);
            
            // adicionar evento de clique ao botao
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, e ->  {
                handlebuttonpress(text);
            });

            grid.add(button, col, row);

            col++;
            if (col > 3) {
                col = 0;
                row++;
            }
                
        }
        
        root.getChildren().add(grid);   

        // criar cena e configurar stage -> tamanho, cena e exibir 

        Scene scene = new Scene(root, 300, 400);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    
        
        
    }

    // lógica da calculadora

    private void handlebuttonpress(String value){
        switch (value) {
            case "C":
                currentinput = "";
                operatorString = "";
                previousvalue = 0;
                display.setText(currentinput);

                break;
            case "=":
                if (!currentinput.isEmpty() && !operatorString.isEmpty()) {
                    double currentvalue = Double.parseDouble(currentinput);
                    double result = calculate(previousvalue, currentvalue, operatorString);
                    display.setText(String.valueOf(result));
                    currentinput = String.valueOf(result);
                    operatorString = "";
                    previousvalue = 0; 
                }
                break;
            case "+": case "-": case "*": case "/":
                if (!currentinput.isEmpty()) {
                    operatorString = value;
                    previousvalue = Double.parseDouble(currentinput);
                    currentinput = "";  
                }
                break;
        
            default:
                currentinput += value;
                display.setText(currentinput);
                break;
        }
    }

    // método para realizar cálculos 

    private double calculate(double val1, double val2, String operator) {
        switch (operator) {
            case "+":
                return val1 + val2;
            case "-":
                return val1 - val2;
            case "*":
                return val1 * val2;
            case "/":
                if (val2 != 0) {
                    return val1 / val2;
                } else {
                    display.setText("Erro: Divisão por zero");
                    return 0;
                }
            default:
                return 0;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}