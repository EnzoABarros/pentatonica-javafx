package br.pentatonica.guitarrascrud.leiloes.controllers;

import br.pentatonica.guitarrascrud.guitarra.Guitarra;
import br.pentatonica.guitarrascrud.leiloes.Leilao;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class LeiloesU {
    private Stage stage;
    private Stage stageOwner;
    private Scene cena;

    public LeiloesU(Stage stageOwner) {
        this.stageOwner = stageOwner;
    }

    public void mostrar(Leilao leilao) {
        criarUI(leilao);
        stage.showAndWait();
    }

    private void criarUI(Leilao leilao){

        this.stage = new Stage();
        stage.setTitle("Editar Leilao");

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(this.stageOwner);

        Label labelMensagem = new Label("Editar Leilao");
        labelMensagem.setFont(new Font("Arial",26));

        Label nome = new Label("Titulo:");
        TextField nomeInput = new TextField();
        nomeInput.setText(leilao.getNome());

        Label descricao = new Label("Descrição:");
        TextField descricaoInput = new TextField();
        descricaoInput.setText(leilao.getDescricao());

        Label lanceInicial = new Label("Lance Inicial:");
        TextField lanceInput = new TextField();
        try {
            String lanceEdit = Double.toString(leilao.getLanceInicial());
            lanceInput.setText(lanceEdit);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        Label data = new Label("Data:");
        DatePicker dataInput = new DatePicker();
        dataInput.setPromptText("Selecione a data");
        dataInput.setValue(leilao.getDataFim());

        Button btnFechar = new Button("Cancelar");
        Button btnEdit = new Button("Atualizar");

        btnFechar.setOnAction((event) -> {this.stage.close();});
        btnEdit.setOnAction(actionEvent -> {

            if (nomeInput.getText().isEmpty() || descricaoInput.getText().isEmpty() || lanceInput.getText().isEmpty() || dataInput.getValue() == null) {
                erro("Por favor, preencha todos os campos.");
                return;
            }
            ArrayList<Leilao> leiloes = new ArrayList<>();
            File file = new File("leiloes.dat");
            if (file.exists() && file.length() > 0) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                    leiloes = (ArrayList<Leilao>) ois.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            leiloes.removeIf(l -> Objects.equals(leilao.getNome(), l.getNome()));


            leilao.setNome(nomeInput.getText());
            leilao.setDescricao(descricaoInput.getText());
            try {
                double num = Double.parseDouble(lanceInput.getText());
                leilao.setLanceInicial(num);
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText("Erro");
                alert.setContentText("Tipo de dado incorreto no campo Lance Inicial.");
                alert.showAndWait();

                return;
            }
            leilao.setDescricao(descricaoInput.getText());
            leilao.setDataFim(LocalDate.parse(dataInput.getValue().toString()));
            leiloes.add(leilao);
            try {
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("leiloes.dat"));
                oos.writeObject(leiloes);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Sucesso!");
                alert.setHeaderText("Sucesso!");
                alert.setContentText("Leilao atualizada com sucesso!");
                alert.showAndWait();
                oos.close();
                this.stage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");
        layout.getChildren().addAll(labelMensagem, nome, nomeInput, descricao, descricaoInput, lanceInicial, lanceInput, data, dataInput, btnEdit, btnFechar);
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