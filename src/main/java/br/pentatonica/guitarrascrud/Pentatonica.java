package br.pentatonica.guitarrascrud;

import br.pentatonica.guitarrascrud.guitarra.controllers.GuitarrasMain;
import br.pentatonica.guitarrascrud.leiloes.controllers.LeiloesMain;
import br.pentatonica.guitarrascrud.pagamentos.controllers.PagamentosMain;
import br.pentatonica.guitarrascrud.usuarios.controllers.UsuariosMain;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Pentatonica {
    private Stage stage;
    private Scene scene;

    public Pentatonica(Stage stage) { this.stage = stage; }

    public void mostrar() {
        criarUI();
        this.stage.show();
    }

    public void criarUI() {
        stage.setTitle("Pentatonica");

        VBox layout = new VBox();
        HBox botoes = new HBox();
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");
        botoes.setSpacing(30);
        botoes.setAlignment(Pos.BASELINE_CENTER);
        Label label = new Label("Pentatonica - CRUDs");
        label.setFont(new Font("Arial", 26));

        Button guitarras = new Button("Guitarras");
        Button leiloes = new Button("Leilões");
        Button usuarios = new Button("Usuários");
        Button pagamentos = new Button("Pagamentos");

        guitarras.setOnAction((e) -> {
            GuitarrasMain a = new GuitarrasMain(this.stage);
            a.mostrar();
        });

        leiloes.setOnAction((e) -> {
            //LeiloesMain a = new LeiloesMain(this.stage);
            //a.mostrar();
        });
        usuarios.setOnAction((e) -> {
           // UsuariosMain a = new UsuariosMain(this.stage);
           // a.mostrar();
        });
        pagamentos.setOnAction((e) -> {
            //PagamentosMain a = new PagamentosMain(this.stage);
            //a.mostrar();
        });

        botoes.getChildren().addAll(guitarras, leiloes, usuarios, pagamentos);
        layout.getChildren().addAll(label, botoes);

        this.scene = new Scene(layout, 500, 500);
        this.stage.setScene(this.scene);
    }
}

