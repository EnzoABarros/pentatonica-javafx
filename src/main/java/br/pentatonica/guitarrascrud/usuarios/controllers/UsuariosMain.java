package br.pentatonica.guitarrascrud.usuarios.controllers;

import br.pentatonica.guitarrascrud.usuarios.Usuario;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
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

    public UsuariosMain(Stage stage) {
        this.stageOwner = stageOwner;
    }

    public void mostrar(){
        criarUI();
        stage.showAndWait();
    }

    private void criarUI(){

        this.stage = new Stage();

        stage.setTitle("Pentatonica - Usuários");

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(this.stageOwner);

        VBox layout = new VBox();
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");
        layout.setSpacing(30);
        Label label = new Label("Pentatonica - Usuários");
        label.setFont(new Font("Arial", 26));

        table.setEditable(false);

        TableColumn<Usuario, String> nomeCol = new TableColumn<>("Nome");
        nomeCol.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<Usuario, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Usuario, String> senhaCol = new TableColumn<>("Senha");
        senhaCol.setCellValueFactory(new PropertyValueFactory<>("senha"));

        TableColumn<Usuario, String> cpfCol = new TableColumn<>("CPF");
        cpfCol.setCellValueFactory(new PropertyValueFactory<>("CPF"));

        table.getColumns().addAll(nomeCol, emailCol, senhaCol, cpfCol);

        ArrayList<Usuario> usuarios = new ArrayList<>();
        File file = new File("usuarios.dat");
        if (file.exists() && file.length() > 0) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                usuarios = (ArrayList<Usuario>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        table.getItems().addAll(usuarios);


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
            Usuario selecionada = table.getSelectionModel().getSelectedItem();
            UsuariosU edit = new UsuariosU(this.stage);
            edit.mostrar(selecionada);
        });

        botoes.getChildren().addAll(adicionar, deletar, editar);

        layout.getChildren().addAll(label, atualizar, table);
        layout.getChildren().add(botoes);

        this.cena = new Scene(layout, 800, 500);
        this.stage.setScene(this.cena);
    }
}
