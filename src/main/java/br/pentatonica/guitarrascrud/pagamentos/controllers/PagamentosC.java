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
import java.util.ArrayList;

public class PagamentosC {
    private Stage stage;
    private Stage stageOwner;
    private Scene cena;

    public PagamentosC(Stage stageOwner) {
        this.stageOwner = stageOwner;
    }

    public void mostrar() {
        criarUI();
        stage.showAndWait();
    }

    private void criarUI() {
        this.stage = new Stage();
        stage.setTitle("Adicionar Pagamento");

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(this.stageOwner);

        Label labelMensagem = new Label("Adicionar Pagamento");
        labelMensagem.setFont(new Font("Arial", 26));

        Label tituloLabel = new Label("Título:");
        TextField tituloI = new TextField();

        Label precoLabel = new Label("Preço:");
        TextField precoI = new TextField();

        Label emailLabel = new Label("E-mail:");
        TextField emailI = new TextField();

        Label statusLabel = new Label("Status:");
        ComboBox<String> statusCombo = new ComboBox<>();
        statusCombo.getItems().addAll("Aprovado", "Recusado", "Processando");
        statusCombo.setPromptText("Selecione o status");

        Label dataLabel = new Label("Data (se não preencher, usa agora):");
        DatePicker dataPicker = new DatePicker();
        dataPicker.setPromptText("DD/MM/AAAA");

        Button btnFechar = new Button("Cancelar");
        Button btnAdd = new Button("Adicionar");

        btnFechar.setOnAction((event) -> this.stage.close());

        btnAdd.setOnAction(actionEvent -> {
            String titulo = tituloI.getText();
            if (titulo == null || titulo.trim().isEmpty()) {
                erro("O campo Título não pode estar vazio!");
                return;
            }

            String email = emailI.getText();
            if (email == null || email.trim().isEmpty()) {
                erro("O campo E-mail não pode estar vazio!");
                return;
            }
            if (!email.contains("@")) {
                erro("O campo E-mail deve conter um '@'.");
                return;
            }

            double preco;
            try {
                preco = Double.parseDouble(precoI.getText());
            } catch (NumberFormatException e) {
                erro("Tipo de dado incorreto no campo Preço.");
                return;
            }

            String status = statusCombo.getValue();
            if (status == null || status.trim().isEmpty()) {
                erro("Você deve selecionar um status!");
                return;
            }

            LocalDateTime dataHora;
            String dataTexto = dataPicker.getEditor().getText();

            if (dataTexto != null && !dataTexto.trim().isEmpty()) {
                if (!dataTexto.matches("^\\d{2}/\\d{2}/\\d{4}$")) {
                    erro("Data inválida! O formato deve ser DD/MM/AAAA e não pode conter letras.");
                    return;
                }
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate dataSel = LocalDate.parse(dataTexto, formatter);
                    dataHora = dataSel.atStartOfDay();
                } catch (Exception e) {
                    erro("Data inválida! O formato deve ser DD/MM/AAAA e não pode conter letras.");
                    return;
                }
            } else {
                dataHora = LocalDateTime.now();
            }

            Pagamento p = new Pagamento();
            p.setTitulo(titulo);
            p.setPreco(preco);
            p.setEmail(email);
            p.setStatus(status);
            p.setData(dataHora);

            ArrayList<Pagamento> pagamentos = new ArrayList<>();
            File file = new File("pagamentos.dat");
            if (file.exists() && file.length() > 0) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                    pagamentos = (ArrayList<Pagamento>) ois.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    erro("Erro ao carregar os pagamentos existentes.");
                    return;
                }
            }

            pagamentos.add(p);
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(pagamentos);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Sucesso!");
                alert.setHeaderText("Sucesso!");
                alert.setContentText("Pagamento cadastrado com sucesso!");
                alert.showAndWait();
                this.stage.close();
            } catch (IOException e) {
                e.printStackTrace();
                erro("Erro ao salvar o pagamento.");
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
                btnAdd, btnFechar
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
