package br.pentatonica.guitarrascrud.pagamentos.controllers;

import br.pentatonica.guitarrascrud.pagamentos.Pagamento;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class PagamentosMain {
    /*private Stage stage;
    private Stage stageOwner;
    private Scene cena;
    private TableView<Pagamento> table = new TableView();

    public PagamentosMain(Stage stageOwner){
        this.stageOwner = stageOwner;
    }

    public void mostrar(){
        criarUI();
        stage.showAndWait();
    }

    private void criarUI(){
        stage.setTitle("Pentatonica - Pagamentos");

        VBox layout = new VBox();
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");
        layout.setSpacing(30);
        Label label = new Label("Pentatonica - Pagamentos");
        label.setFont(new Font("Arial", 26));

        table.setEditable(false);

        TableColumn<Pagamento, String> modeloCol = new TableColumn<>("Modelo");
        modeloCol.setCellValueFactory(new PropertyValueFactory<>("modelo"));

        TableColumn<Pagamento, String> marcaCol = new TableColumn<>("Marca");
        marcaCol.setCellValueFactory(new PropertyValueFactory<>("marca"));

        TableColumn<Pagamento, Double> precoCol = new TableColumn<>("Preço");
        precoCol.setCellValueFactory(new PropertyValueFactory<>("preco"));

        TableColumn<Pagamento, String> descricaoCol = new TableColumn<>("Descrição");
        descricaoCol.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        TableColumn<Pagamento, String> categoriaCol = new TableColumn<>("Categoria");
        categoriaCol.setCellValueFactory(new PropertyValueFactory<>("categoria"));

        table.getColumns().addAll(modeloCol, marcaCol, precoCol, descricaoCol, categoriaCol);

        ArrayList<Pagamento> pagamentos = new ArrayList<>();
        File file = new File("pagamentos.dat");
        if (file.exists() && file.length() > 0) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                pagamentos = (ArrayList<Pagamento>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        table.getItems().addAll(pagamentos);


        Button adicionar = new Button("Adicionar");
        adicionar.setStyle("-fx-padding: 10; -fx-text-fill: rgb(41, 158, 72)");
        adicionar.setOnAction((e) -> {
            PagamentoC create = new PagamentoC(this.stage);
            create.mostrar();
        });

        Button atualizar = new Button("Atualizar tabela");
        atualizar.setStyle("-fx-padding: 10; -fx-text-fill: rgb(85, 22, 128);");
        atualizar.setOnAction((e) -> {
            if (file.exists() && file.length() > 0) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                    ArrayList<Pagamento> novoPagamento = (ArrayList<Pagamento>) ois.readObject();
                    table.getItems().clear();
                    table.getItems().addAll(novoPagamento);
                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });

        HBox botoes = new HBox();


        Button deletar = new Button("Deletar");
        deletar.setStyle("-fx-padding: 10; -fx-text-fill: rgb(191, 15, 15);");
        ArrayList<Pagamento> finalPagamentos = pagamentos;
        deletar.setOnAction((e) -> {
            Pagamento selecionada = table.getSelectionModel().getSelectedItem();
            if (selecionada == null) {
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Aviso");
                alerta.setHeaderText("Nenhum pagamento selecionado");
                alerta.setContentText("Por favor, selecione um pagamento para deletar.");
                alerta.showAndWait();
                return;
            }
            PagamentoD g = new PagamentoD();
            g.deletarPagamento(finalPagamentos, selecionada);
        });

        Button editar = new Button("Editar");
        editar.setStyle("-fx-padding: 10; -fx-text-fill: rgb(36, 15, 128);");
        editar.setOnAction((e) -> {

        });

        botoes.getChildren().addAll(adicionar, deletar, editar);

        layout.getChildren().addAll(label, atualizar, table);
        layout.getChildren().add(botoes);
        this.cena = new Scene(layout, 800, 500);
        this.stage.setScene(this.cena);
    } */
}
