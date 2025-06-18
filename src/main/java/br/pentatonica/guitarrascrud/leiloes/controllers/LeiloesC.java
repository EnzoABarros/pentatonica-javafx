
package br.pentatonica.guitarrascrud.leiloes.controllers;

import br.pentatonica.guitarrascrud.guitarra.Guitarra;
import br.pentatonica.guitarrascrud.leiloes.Leilao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class LeiloesC {
    private Stage stage;
    private Stage stageOwner;
    private Scene cena;

    public LeiloesC(Stage stageOwner) {
        this.stageOwner = stageOwner;
    }

    public void mostrar() {
        criarUI();
        stage.showAndWait();
    }

    private void criarUI() {
        this.stage = new Stage();
        stage.setTitle("Criar Leilão");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(this.stageOwner);

        Label label = new Label("Novo Leilão");
        label.setFont(new Font("Arial", 26));

        Label titulo = new Label("Título:");
        TextField nomeInput = new TextField();

        Label lance = new Label("Lance inicial:");
        TextField lanceInput = new TextField();

        Label descricao = new Label("Descrição:");
        TextField descricaoInput = new TextField();

        Label data = new Label("Data");
        DatePicker dataPicker = new DatePicker();
        dataPicker.setPromptText("Selecione a data");

        Button btnCriar = new Button("Criar");
        Button btnFechar = new Button("Cancelar");

        btnFechar.setOnAction((event) -> this.stage.close());

        btnCriar.setOnAction(e -> {
            Leilao leilao = new Leilao();
            leilao.setNome(nomeInput.getText());
            try {
                double num = Double.parseDouble(lanceInput.getText());
                leilao.setLanceInicial(num);
            } catch (NumberFormatException ex) {
                erro("Tipo de dado incorreto no campo Lance Inicial");
            }
            leilao.setDescricao(descricaoInput.getText());
            LocalDate dataSel = dataPicker.getValue();
            leilao.setDataFim(dataSel.atStartOfDay());
            ArrayList<Leilao> leiloes = new ArrayList<>();
            File file = new File("leiloes.dat");
            if (file.exists() && file.length() > 0) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                    leiloes = (ArrayList<Leilao>) ois.readObject();
                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }

            leiloes.add(leilao);

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(leiloes);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Sucesso");
                alert.setContentText("Leilão salvo com sucesso.");
                alert.showAndWait();
                this.stage.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");
        layout.getChildren().addAll(label, titulo, nomeInput, lance, lanceInput, descricao, descricaoInput, data, dataPicker, btnCriar, btnFechar);

        this.cena = new Scene(layout, 500, 700);
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


