package br.pentatonica.guitarrascrud.leiloes.controllers;

import br.pentatonica.guitarrascrud.leiloes.Leilao;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.control.cell.PropertyValueFactory;
import br.pentatonica.guitarrascrud.leiloes.controllers.LeiloesC;
import br.pentatonica.guitarrascrud.leiloes.controllers.LeiloesD;

import java.io.*;
import java.util.ArrayList;

public class LeiloesMain {
    private Stage stage;
    private Stage stageOwner;
    private Scene cena;
    private TableView<Leilao> tabela = new TableView<>();

    public LeiloesMain(Stage stage) {
        this.stageOwner = stageOwner;
    }

    public void mostrar() {
        criarUI();
        stage.show();
    }

    private void criarUI() {
        this.stage = new Stage();

        stage.setTitle("Pentatonica - Leilões");

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(this.stageOwner);

        VBox layout = new VBox();
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");
        layout.setSpacing(20);

        Label label = new Label("Leilões de Guitarras");
        label.setFont(new Font("Arial", 26));

        TableColumn<Leilao, String> nomeCol = new TableColumn<>("Nome");
        nomeCol.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<Leilao, String> descricaoCol = new TableColumn<>("Descrição");
        descricaoCol.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        TableColumn<Leilao, Double> lanceCol = new TableColumn<>("Lance Inicial");
        lanceCol.setCellValueFactory(new PropertyValueFactory<>("lanceInicial"));

        TableColumn<Leilao, String> dataFimCol = new TableColumn<>("Data fim");
        dataFimCol.setCellValueFactory(new PropertyValueFactory<>("dataFim"));

        tabela.setEditable(false);
        tabela.getColumns().addAll(nomeCol, descricaoCol, lanceCol, dataFimCol);

        carregarLeiloes();

        Button btnAdicionar = new Button("Adicionar");
        btnAdicionar.setOnAction(e -> {
            LeiloesC lc = new LeiloesC(stage);
            lc.mostrar();
            carregarLeiloes();
        });

        Button btnEditar = new Button("Editar");
        btnEditar.setOnAction(e -> {
            Leilao selecionado = tabela.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                LeiloesU lu = new LeiloesU(stage, selecionado);
                lu.mostrar();
                carregarLeiloes();
            }
        });

        Button btnDeletar = new Button("Deletar");
        btnDeletar.setOnAction(e -> {
            Leilao selecionado = tabela.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                LeiloesD ld = new LeiloesD(selecionado);
                ld.deletar();
                carregarLeiloes();
            }
        });

        HBox botoes = new HBox(10);
        botoes.getChildren().addAll(btnAdicionar, btnEditar, btnDeletar);
        layout.getChildren().addAll(label, tabela, botoes);

        this.cena = new Scene(layout, 600, 500);
        this.stage.setScene(this.cena);
    }

    private void carregarLeiloes() {
        File file = new File("leiloes.dat");
        ArrayList<Leilao> leiloes = new ArrayList<>();
        if (file.exists() && file.length() > 0) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                leiloes = (ArrayList<Leilao>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        tabela.getItems().clear();
        tabela.getItems().addAll(leiloes);
    }
}