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

public class GuitarraC{
    private Stage stage;
    private Stage stageOwner;
    private Scene cena;

    public GuitarraC(Stage stageOwner){
        this.stageOwner = stageOwner;
    }

    public void mostrar(){
        criarUI();
        stage.showAndWait();
    }

    private void criarUI(){

        this.stage = new Stage();
        stage.setTitle("Adicionar guitarra");

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(this.stageOwner);

        Label labelMensagem = new Label("Adicionar Guitarra");
        labelMensagem.setFont(new Font("Arial",26));

        Label modelo = new Label("Modelo:");
        TextField modeloI = new TextField();

        Label marca = new Label("Marca:");
        TextField marcaI = new TextField();

        Label preco = new Label("Preço:");
        TextField precoI = new TextField();

        Label descricao = new Label("Descrição:");
        TextField descricaoI = new TextField();

        Label categoria = new Label("Categoria:");
        ChoiceBox categoriaI = new ChoiceBox(FXCollections.observableArrayList("Elétrica", "Acústica"));

        Button btnFechar = new Button("Cancelar");
        Button btnAdd = new Button("Adicionar");

        btnFechar.setOnAction((event) -> {this.stage.close();});
        btnAdd.setOnAction(actionEvent -> {
            Guitarra g = new Guitarra();
            if(modeloI.getText() != "")
                g.setModelo(modeloI.getText());
            else {
                erro("O campo Modelo não pode estar vazio!");
                return;
            }
            if(marcaI.getText() != "") {
                g.setMarca(marcaI.getText());
            }
            else {
                erro("O campo Marca não pode estar vazio!");
                return;
            }
            try {
                double num = Double.parseDouble(precoI.getText());
                g.setPreco(num);
            } catch (NumberFormatException e) {
                erro("Tipo de dado incorreto no campo Preço!");
                return;
            }
            if(descricaoI.getText() != "") {
                g.setDescricao(descricaoI.getText());
            }
            else {
                erro("O campo Descrição não pode estar vazio!");
                return;
            }
            if(categoriaI.getValue() != null) {
                g.setCategoria(categoriaI.getValue().toString());
            }
            else {
                erro("Selecione uma categoria!");
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

            guitarras.add(g);

            try {
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("guitarras.dat"));
                oos.writeObject(guitarras);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Sucesso!");
                alert.setHeaderText("Sucesso!");
                alert.setContentText("Guitarra cadastrada com sucesso!");
                alert.showAndWait();
                oos.close();
                this.stage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");
        layout.getChildren().addAll(labelMensagem, modelo, modeloI, marca, marcaI, preco, precoI, descricao, descricaoI, categoria, categoriaI, btnAdd, btnFechar);
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
