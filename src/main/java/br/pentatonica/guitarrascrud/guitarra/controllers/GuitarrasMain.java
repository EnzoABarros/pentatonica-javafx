package br.pentatonica.guitarrascrud.guitarra.controllers;

import br.pentatonica.guitarrascrud.guitarra.Guitarra;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.*;
import java.util.ArrayList;

public class GuitarrasMain {
    private Stage stage;
    private Stage stageOwner;
    private Scene cena;
    private TableView<Guitarra> table = new TableView();

    public GuitarrasMain(Stage stageOwner){
        this.stageOwner = stageOwner;
    }

    public void mostrar(){
        criarUI();
        stage.showAndWait();
    }

    private void criarUI(){

        this.stage = new Stage();

        stage.setTitle("Pentatonica - Guitarras");

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(this.stageOwner);

        VBox layout = new VBox();
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");
        layout.setSpacing(30);
        Label label = new Label("Pentatonica - Guitarras");
        label.setFont(new Font("Arial", 26));

        table.setEditable(false);

        TableColumn<Guitarra, String> modeloCol = new TableColumn<>("Modelo");
        modeloCol.setCellValueFactory(new PropertyValueFactory<>("modelo"));

        TableColumn<Guitarra, String> marcaCol = new TableColumn<>("Marca");
        marcaCol.setCellValueFactory(new PropertyValueFactory<>("marca"));

        TableColumn<Guitarra, Double> precoCol = new TableColumn<>("Preço");
        precoCol.setCellValueFactory(new PropertyValueFactory<>("preco"));

        TableColumn<Guitarra, String> descricaoCol = new TableColumn<>("Descrição");
        descricaoCol.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        TableColumn<Guitarra, String> categoriaCol = new TableColumn<>("Categoria");
        categoriaCol.setCellValueFactory(new PropertyValueFactory<>("categoria"));

        table.getColumns().addAll(modeloCol, marcaCol, precoCol, descricaoCol, categoriaCol);

        ArrayList<Guitarra> guitarras = new ArrayList<>();
        File file = new File("guitarras.dat");
        if (file.exists() && file.length() > 0) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                guitarras = (ArrayList<Guitarra>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        table.getItems().addAll(guitarras);


        Button adicionar = new Button("Adicionar");
        adicionar.setStyle("-fx-padding: 10; -fx-text-fill: rgb(41, 158, 72)");
        adicionar.setOnAction((e) -> {
            GuitarraC create = new GuitarraC(this.stage);
            create.mostrar();
        });

        Button atualizar = new Button("Atualizar tabela");
        atualizar.setStyle("-fx-padding: 10; -fx-text-fill: rgb(85, 22, 128);");
        atualizar.setOnAction((e) -> {
            ArrayList<Guitarra> novasGuitarras = atualizar(file);
            table.getItems().clear();
            table.getItems().addAll(novasGuitarras);
        });

        HBox botoes = new HBox();


        Button deletar = new Button("Deletar");
        deletar.setStyle("-fx-padding: 10; -fx-text-fill: rgb(191, 15, 15);");
        deletar.setOnAction((e) -> {
            Guitarra selecionada = table.getSelectionModel().getSelectedItem();
            if (selecionada == null) {
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Aviso");
                alerta.setHeaderText("Nenhuma guitarra selecionada");
                alerta.setContentText("Por favor, selecione uma guitarra para deletar.");
                alerta.showAndWait();
                return;
            }
            GuitarraD g = new GuitarraD();
            g.deletarGuitarra(atualizar(file), selecionada);
        });

        Button editar = new Button("Editar");
        editar.setStyle("-fx-padding: 10; -fx-text-fill: rgb(36, 15, 128);");
        editar.setOnAction((e) -> {
            Guitarra selecionada = table.getSelectionModel().getSelectedItem();
            GuitarraU edit = new GuitarraU(this.stage);
            edit.mostrar(selecionada);
        });

        botoes.getChildren().addAll(adicionar, deletar, editar);

        layout.getChildren().addAll(label, atualizar, table);
        layout.getChildren().add(botoes);
        this.cena = new Scene(layout, 800, 500);
        this.stage.setScene(this.cena);
    }

    private static ArrayList atualizar(File file) {
        ArrayList<Guitarra> novasGuitarras = new ArrayList<>();
        if (file.exists() && file.length() > 0) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                novasGuitarras = (ArrayList<Guitarra>) ois.readObject();
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        return novasGuitarras;
    }
}
