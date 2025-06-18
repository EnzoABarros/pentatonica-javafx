package br.pentatonica.guitarrascrud.guitarra.controllers;

import br.pentatonica.guitarrascrud.guitarra.Guitarra;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class GuitarraU {
    private Stage stage;
    private Stage stageOwner;
    private Scene cena;

    public GuitarraU(Stage stageOwner){
        this.stageOwner = stageOwner;
    }

    public void mostrar(Guitarra guitarra){
        criarUI(guitarra);
        stage.showAndWait();
    }

    private void criarUI(Guitarra guitarra){

        this.stage = new Stage();
        stage.setTitle("Editar guitarra");

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(this.stageOwner);

        Label labelMensagem = new Label("Editar Guitarra");
        labelMensagem.setFont(new Font("Arial",26));

        Label modelo = new Label("Modelo:");
        TextField modeloI = new TextField();
        modeloI.setText(guitarra.getModelo());

        Label marca = new Label("Marca:");
        TextField marcaI = new TextField();
        marcaI.setText(guitarra.getMarca());

        Label preco = new Label("Preço:");
        TextField precoI = new TextField();
        try {
            String precoEdit = Double.toString(guitarra.getPreco());
            precoI.setText(precoEdit);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Label descricao = new Label("Descrição:");
        TextField descricaoI = new TextField();
        descricaoI.setText(guitarra.getDescricao());

        Label categoria = new Label("Categoria:");
        ChoiceBox categoriaI = new ChoiceBox(FXCollections.observableArrayList("Elétrica", "Acústica"));
        categoriaI.setValue(guitarra.getCategoria());

        Button btnFechar = new Button("Cancelar");
        Button btnEdit = new Button("Atualizar");

        btnFechar.setOnAction((event) -> {this.stage.close();});
        btnEdit.setOnAction(actionEvent -> {

            if (modeloI.getText().isEmpty() || marcaI.getText().isEmpty() || precoI.getText().isEmpty() || descricaoI.getText().isEmpty()) {
                erro("Por favor, preencha todos os campos.");
                return;
            }
            ArrayList<Guitarra> guitarras = new ArrayList<>();
            File file = new File("guitarras.dat");
            if (file.exists() && file.length() > 0) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                    guitarras = (ArrayList<Guitarra>) ois.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            guitarras.removeIf(g -> Objects.equals(guitarra.getModelo(), g.getModelo()));


            guitarra.setModelo(modeloI.getText());
            guitarra.setMarca(marcaI.getText());
            try {
                double num = Double.parseDouble(precoI.getText());
                guitarra.setPreco(num);
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText("Erro");
                alert.setContentText("Tipo de dado incorreto no campo Preço.");
                alert.showAndWait();

                return;
            }
            guitarra.setDescricao(descricaoI.getText());
            guitarra.setCategoria(categoriaI.getValue().toString());
            guitarras.add(guitarra);
            try {
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("guitarras.dat"));
                oos.writeObject(guitarras);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Sucesso!");
                alert.setHeaderText("Sucesso!");
                alert.setContentText("Guitarra atualizada com sucesso!");
                alert.showAndWait();
                oos.close();
                this.stage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");
        layout.getChildren().addAll(labelMensagem, modelo, modeloI, marca, marcaI, preco, precoI, descricao, descricaoI, categoria, categoriaI, btnEdit, btnFechar);
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
