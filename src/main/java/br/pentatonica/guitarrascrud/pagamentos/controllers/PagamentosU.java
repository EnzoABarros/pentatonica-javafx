package br.pentatonica.guitarrascrud.pagamentos.controllers;

import br.pentatonica.guitarrascrud.pagamentos.Pagamento;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class PagamentosU {
    private Stage stage;
    private Stage stageOwner;
    private Scene cena;

    public PagamentosU(Stage stageOwner) {
        this.stageOwner = stageOwner;
    }

    public void mostrar(Pagamento pagamento) {
        if (pagamento == null) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Aviso");
            alerta.setHeaderText("Nenhum pagamento selecionado");
            alerta.setContentText("Por favor, selecione um pagamento para editar.");
            alerta.showAndWait();
            return;
        }
        criarUI(pagamento);
        stage.showAndWait();
    }

    private void criarUI(Pagamento pagamento) {
        this.stage = new Stage();
        stage.setTitle("Editar Pagamento");

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(this.stageOwner);

        Label labelMensagem = new Label("Editar Pagamento");
        labelMensagem.setFont(new Font("Arial", 26));

        Label tituloLabel = new Label("Título:");
        TextField tituloI = new TextField(pagamento.getTitulo());

        Label precoLabel = new Label("Preço:");
        TextField precoI = new TextField(Double.toString(pagamento.getPreco()));

        Label emailLabel = new Label("E-mail:");
        TextField emailI = new TextField(pagamento.getEmail());

        Label statusLabel = new Label("Status:");
        TextField statusI = new TextField(pagamento.getStatus());

        Label dataLabel = new Label("Data:");
        DatePicker dataPicker = new DatePicker();
        try {
            LocalDateTime dt = pagamento.getData();
            if (dt != null) {
                dataPicker.setValue(dt.toLocalDate());
            }
        } catch (Exception e) {
        }

        Button btnFechar = new Button("Cancelar");
        Button btnEdit = new Button("Atualizar");

        btnFechar.setOnAction((event) -> this.stage.close());

        btnEdit.setOnAction(actionEvent -> {
            ArrayList<Pagamento> pagamentos = new ArrayList<>();
            File file = new File("pagamentos.dat");
            if (file.exists() && file.length() > 0) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                    pagamentos = (ArrayList<Pagamento>) ois.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            pagamentos.removeIf(p -> Objects.equals(pagamento.getTitulo(), p.getTitulo()));

            String novoTitulo = tituloI.getText();
            if (novoTitulo == null || novoTitulo.trim().isEmpty()) {
                erro("O campo Título não pode estar vazio!");
                return;
            }
            pagamento.setTitulo(novoTitulo);

            double novoPreco;
            try {
                novoPreco = Double.parseDouble(precoI.getText());
                pagamento.setPreco(novoPreco);
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText("Erro");
                alert.setContentText("Tipo de dado incorreto no campo Preço.");
                alert.showAndWait();
                return;
            }

            String novoEmail = emailI.getText();
            if (novoEmail == null || novoEmail.trim().isEmpty()) {
                erro("O campo E-mail não pode estar vazio!");
                return;
            }
            pagamento.setEmail(novoEmail);

            String novoStatus = statusI.getText();
            if (novoStatus == null || novoStatus.trim().isEmpty()) {
                erro("O campo Status não pode estar vazio!");
                return;
            }
            pagamento.setStatus(novoStatus);

            LocalDate dataSel = dataPicker.getValue();
            if (dataSel != null) {
                pagamento.setData(dataSel.atStartOfDay());
            }

            pagamentos.add(pagamento);
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(pagamentos);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Sucesso!");
                alert.setHeaderText("Sucesso!");
                alert.setContentText("Pagamento atualizado com sucesso!");
                alert.showAndWait();
                this.stage.close();
            } catch (IOException e) {
                e.printStackTrace();
                erro("Erro ao atualizar o pagamento.");
            }
        });

        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");
        layout.getChildren().addAll(
                labelMensagem,
                tituloLabel, tituloI,
                precoLabel, precoI,
                emailLabel, emailI,
                statusLabel, statusI,
                dataLabel, dataPicker,
                btnEdit, btnFechar
        );
        this.cena = new Scene(layout, 500, 600);
        this.stage.setScene(this.cena);
    }

    private static void erro(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText("Erro");
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
