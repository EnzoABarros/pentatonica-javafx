package br.pentatonica.guitarrascrud.pagamentos.controllers;

import br.pentatonica.guitarrascrud.pagamentos.Pagamento;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
        ComboBox<String> statusCombo = new ComboBox<>();
        statusCombo.getItems().addAll("Aprovado", "Recusado", "Processando");
        statusCombo.setValue(pagamento.getStatus());

        Label dataLabel = new Label("Data:");
        DatePicker dataPicker = new DatePicker();
        dataPicker.setPromptText("DD/MM/AAAA");

        try {
            LocalDateTime dt = pagamento.getData();
            if (dt != null) {
                dataPicker.setValue(dt.toLocalDate());
            }
        } catch (Exception e) {
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        dataPicker.getEditor().focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) {
                String texto = dataPicker.getEditor().getText();
                if (texto == null || texto.trim().isEmpty()) {
                    dataPicker.setValue(null);
                    return;
                }
                try {
                    LocalDate data = LocalDate.parse(texto, formatter);
                    dataPicker.setValue(data);
                } catch (DateTimeParseException e) {
                    erro("Data inválida! Use o formato DD/MM/AAAA e não use letras.");
                    dataPicker.getEditor().clear();
                    dataPicker.setValue(null);
                    dataPicker.requestFocus();
                }
            }
        });

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
                erro("Tipo de dado incorreto no campo Preço.");
                return;
            }

            String novoEmail = emailI.getText();
            if (novoEmail == null || novoEmail.trim().isEmpty()) {
                erro("O campo E-mail não pode estar vazio!");
                return;
            }
            if (!novoEmail.contains("@")) {
                erro("O campo E-mail deve conter um '@'.");
                return;
            }
            pagamento.setEmail(novoEmail);

            String novoStatus = statusCombo.getValue();
            if (novoStatus == null || novoStatus.trim().isEmpty()) {
                erro("Você deve selecionar um status!");
                return;
            }
            pagamento.setStatus(novoStatus);

            LocalDate dataSel = dataPicker.getValue();
            if (dataSel != null) {
                pagamento.setData(dataSel.atStartOfDay());
            } else {
                erro("Data inválida ou não preenchida! Use o formato DD/MM/AAAA.");
                return;
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
                statusLabel, statusCombo,
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
