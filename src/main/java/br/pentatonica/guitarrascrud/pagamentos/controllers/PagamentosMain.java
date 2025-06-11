package br.pentatonica.guitarrascrud.pagamentos.controllers;

import br.pentatonica.guitarrascrud.pagamentos.Pagamento;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.Modality;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class PagamentosMain {
    private Stage stage;
    private Stage stageOwner;
    private Scene cena;
    private TableView<Pagamento> table = new TableView<>();

    public PagamentosMain(Stage stageOwner) {
        this.stageOwner = stageOwner;
    }

    public void mostrar() {
        criarUI();
        stage.showAndWait();
    }

    private void criarUI() {
        this.stage = new Stage();
        stage.setTitle("Pentatonica - Pagamentos");

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(this.stageOwner);

        VBox layout = new VBox();
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");
        layout.setSpacing(20);

        Label label = new Label("Pentatonica - Pagamentos");
        label.setFont(new Font("Arial", 26));

        table.setEditable(false);

        TableColumn<Pagamento, String> tituloCol = new TableColumn<>("Título");
        tituloCol.setCellValueFactory(new PropertyValueFactory<>("titulo"));

        TableColumn<Pagamento, Double> precoCol = new TableColumn<>("Preço");
        precoCol.setCellValueFactory(new PropertyValueFactory<>("preco"));

        TableColumn<Pagamento, String> emailCol = new TableColumn<>("E-mail");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Pagamento, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        TableColumn<Pagamento, String> dataCol = new TableColumn<>("Data");
        dataCol.setCellValueFactory(new PropertyValueFactory<>("data"));

        table.getColumns().addAll(tituloCol, precoCol, emailCol, statusCol, dataCol);

        ArrayList<Pagamento> pagamentos = new ArrayList<>();
        File file = new File("pagamentos.dat");
        if (file.exists() && file.length() > 0) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                pagamentos = (ArrayList<Pagamento>) ois.readObject();
            } catch (FileNotFoundException e) {
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        table.getItems().addAll(pagamentos);

        Button adicionar = new Button("Adicionar");
        adicionar.setStyle("-fx-padding: 10; -fx-text-fill: rgb(41, 158, 72);");
        adicionar.setOnAction((e) -> {
            PagamentosC create = new PagamentosC(this.stage);
            create.mostrar();
            atualizarTabela(file);
        });

        Button atualizar = new Button("Atualizar tabela");
        atualizar.setStyle("-fx-padding: 10; -fx-text-fill: rgb(85, 22, 128);");
        atualizar.setOnAction((e) -> atualizarTabela(file));

        HBox botoes = new HBox();
        botoes.setSpacing(10);

        Button deletar = new Button("Deletar");
        deletar.setStyle("-fx-padding: 10; -fx-text-fill: rgb(191, 15, 15);");
        deletar.setOnAction((e) -> {
            Pagamento selecionado = table.getSelectionModel().getSelectedItem();
            if (selecionado == null) {
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Aviso");
                alerta.setHeaderText("Nenhum pagamento selecionado");
                alerta.setContentText("Por favor, selecione um pagamento para deletar.");
                alerta.showAndWait();
                return;
            }
            PagamentosD d = new PagamentosD();
            ArrayList<Pagamento> listaAtual = atualizarLista(file);
            d.deletarPagamento(listaAtual, selecionado);
            atualizarTabela(file);
        });

        Button editar = new Button("Editar");
        editar.setStyle("-fx-padding: 10; -fx-text-fill: rgb(36, 15, 128);");
        editar.setOnAction((e) -> {
            Pagamento selecionado = table.getSelectionModel().getSelectedItem();
            if (selecionado == null) {
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Aviso");
                alerta.setHeaderText("Nenhum pagamento selecionado");
                alerta.setContentText("Por favor, selecione um pagamento para editar.");
                alerta.showAndWait();
                return;
            }
            PagamentosU edit = new PagamentosU(this.stage);
            edit.mostrar(selecionado);
            atualizarTabela(file);
        });

        botoes.getChildren().addAll(adicionar, deletar, editar, atualizar);

        layout.getChildren().addAll(label, table, botoes);
        this.cena = new Scene(layout, 800, 500);
        this.stage.setScene(this.cena);
    }

    private void atualizarTabela(File file) {
        ArrayList<Pagamento> lista = atualizarLista(file);
        table.getItems().clear();
        table.getItems().addAll(lista);
    }


    private static ArrayList<Pagamento> atualizarLista(File file) {
        ArrayList<Pagamento> lista = new ArrayList<>();
        if (file.exists() && file.length() > 0) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                lista = (ArrayList<Pagamento>) ois.readObject();
            } catch (FileNotFoundException e) {
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        return lista;
    }
}
