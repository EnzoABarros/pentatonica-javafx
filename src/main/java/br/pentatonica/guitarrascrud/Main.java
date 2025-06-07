package br.pentatonica.guitarrascrud;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{

    public static void main(String[] args){
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Pentatonica p = new Pentatonica(stage);
        p.mostrar();
    }
}
