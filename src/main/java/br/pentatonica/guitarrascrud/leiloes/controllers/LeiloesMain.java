package br.pentatonica.guitarrascrud.leiloes.controllers;

import br.pentatonica.guitarrascrud.leiloes.Leilao;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
import java.util.ArrayList;

public class LeiloesMain {
    private Stage stage;
    private Stage stageOwner;
    private Scene cena;
    private TableView<Leilao> tabela = new TableView<>();

    public LeiloesMain(Stage stageOwner) {
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
        layout.setSpacing(30);
        Label label = new Label("Leilões de Guitarras");
        label.setFont(new Font("Arial", 26));

        tabela.setEditable(false);

        TableColumn<Leilao, String> nomeCol = new TableColumn<>("Nome");
        nomeCol.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<Leilao, String> descricaoCol = new TableColumn<>("Descrição");
        descricaoCol.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        TableColumn<Leilao, Double> lanceCol = new TableColumn<>("Lance Inicial");
        lanceCol.setCellValueFactory(new PropertyValueFactory<>("lanceInicial"));

        TableColumn<Leilao, String> dataFimCol = new TableColumn<>("Data fim");
        dataFimCol.setCellValueFactory(new PropertyValueFactory<>("dataFim"));

        tabela.getColumns().addAll(nomeCol, descricaoCol, lanceCol, dataFimCol);

        ArrayList<Leilao> leiloes = new ArrayList<>();
        File file = new File("leiloes.dat");
        if (file.exists() && file.length() > 0) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                leiloes = (ArrayList<Leilao>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        tabela.getItems().addAll(leiloes);


        Button adicionar = new Button("Adicionar");
        adicionar.setStyle("-fx-padding: 10; -fx-text-fill: rgb(41, 158, 72)");
        adicionar.setOnAction((e) -> {
            LeiloesC create = new LeiloesC(this.stage);
            create.mostrar();
            atualizarTabela(file);
        });

        HBox botoes = new HBox();

        Button deletar = new Button("Deletar");
        deletar.setStyle("-fx-padding: 10; -fx-text-fill: rgb(191, 15, 15);");
        deletar.setOnAction((e) -> {
            Leilao selecionado = tabela.getSelectionModel().getSelectedItem();
            if (selecionado == null) {
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Aviso");
                alerta.setHeaderText("Nenhum Leilao selecionado");
                alerta.setContentText("Por favor, selecione um leilao para deletar.");
                alerta.showAndWait();
                return;
            }
            LeiloesD l = new LeiloesD();
            l.deletar(atualizar(file), selecionado);
            atualizarTabela(file);
        });


        Button editar = new Button("Editar");
        editar.setStyle("-fx-padding: 10; -fx-text-fill: rgb(36, 15, 128);");
        editar.setOnAction((e) -> {
            Leilao selecionado = tabela.getSelectionModel().getSelectedItem();
            if (selecionado == null){
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Aviso");
                alerta.setHeaderText("Nenhum Leilao selecionado");
                alerta.setContentText("Por favor, selecione um leilao para editar.");
                alerta.showAndWait();
                return;
            }
            LeiloesU edit = new LeiloesU(this.stage);
            edit.mostrar(selecionado);
            atualizarTabela(file);
        });

        botoes.getChildren().addAll(adicionar, deletar, editar);

        layout.getChildren().addAll(label, tabela);
        layout.getChildren().add(botoes);
        this.cena = new Scene(layout, 800, 500);
        this.stage.setScene(this.cena);
    }

    private static ArrayList atualizar(File file) {
        ArrayList<Leilao> novosLeiloes = new ArrayList<>();
        if (file.exists() && file.length() > 0) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                novosLeiloes = (ArrayList<Leilao>) ois.readObject();
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        return novosLeiloes;
    }

    private void atualizarTabela(File file) {
        ArrayList<Leilao> novosLeiloes = atualizar(file);
        tabela.getItems().clear();
        tabela.getItems().addAll(novosLeiloes);
    }
}