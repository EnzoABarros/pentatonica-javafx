package br.pentatonica.guitarrascrud;

import br.pentatonica.guitarrascrud.guitarra.controllers.GuitarrasMain;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{

    public static void main(String[] args){
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        GuitarrasMain g = new GuitarrasMain(stage);
        g.mostrar();
    }
}
