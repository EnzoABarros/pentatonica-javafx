package br.pentatonica.guitarrascrud.usuarios.controllers;

import br.pentatonica.guitarrascrud.usuarios.Usuario;
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

public class UsuariosMain {
    private Scene cena;
    private Stage stage;
    private Stage stageOwner;
    private TableView<Usuario> table = new TableView();


    public void mostrar(){
        criarUI();
        stage.showAndWait();
    }

    private void criarUI(){
        stage.setTitle("Pentatonica - Usuários");

        VBox layout = new VBox();
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");
        layout.setSpacing(30);
        Label label = new Label("Pentatonica - Usuários");
        label.setFont(new Font("Arial", 26));

        table.setEditable(false);

        TableColumn<Usuario, String> modeloCol = new TableColumn<>("Modelo");
        modeloCol.setCellValueFactory(new PropertyValueFactory<>("modelo"));

        TableColumn<Usuario, String> marcaCol = new TableColumn<>("Marca");
        marcaCol.setCellValueFactory(new PropertyValueFactory<>("marca"));

        TableColumn<Usuario, Double> precoCol = new TableColumn<>("Preço");
        precoCol.setCellValueFactory(new PropertyValueFactory<>("preco"));

        TableColumn<Usuario, String> descricaoCol = new TableColumn<>("Descrição");
        descricaoCol.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        TableColumn<Usuario, String> categoriaCol = new TableColumn<>("Categoria");
        categoriaCol.setCellValueFactory(new PropertyValueFactory<>("categoria"));

        table.getColumns().addAll(modeloCol, marcaCol, precoCol, descricaoCol, categoriaCol);

        ArrayList<Usuario> usuarios = new ArrayList<>();
        File file = new File("usuarios.dat");
        if (file.exists() && file.length() > 0) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                usuarios = (ArrayList<Usuario>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        table.getItems().addAll(guitarras);


        Button adicionar = new Button("Adicionar");
        adicionar.setStyle("-fx-padding: 10; -fx-text-fill: rgb(41, 158, 72)");
        adicionar.setOnAction((e) -> {
            UsuariosC create = new UsuariosC(this.stage);
            create.mostrar();
        });

        Button atualizar = new Button("Atualizar tabela");
        atualizar.setStyle("-fx-padding: 10; -fx-text-fill: rgb(85, 22, 128);");
        atualizar.setOnAction((e) -> {
            if (file.exists() && file.length() > 0) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                    ArrayList<Usuario> novosUsuarios = (ArrayList<Usuario>) ois.readObject();
                    table.getItems().clear();
                    table.getItems().addAll(novosUsuarios);
                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });

        HBox botoes = new HBox();


        Button deletar = new Button("Deletar");
        deletar.setStyle("-fx-padding: 10; -fx-text-fill: rgb(191, 15, 15);");
        ArrayList<Usuario> finalUsuarios = usuarios;
        deletar.setOnAction((e) -> {
            Usuario selection = table.getSelectionModel().getSelectedItem();
            if (selection == null) {
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Aviso");
                alerta.setHeaderText("Nenhum usuário selecionado");
                alerta.setContentText("Por favor, selecione um usuário para deletar.");
                alerta.showAndWait();
                return;
            }
            UsuariosD u = new UsuariosD();
            u.deletarUsuario(finalUsuarios, selection);
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
    }
}
