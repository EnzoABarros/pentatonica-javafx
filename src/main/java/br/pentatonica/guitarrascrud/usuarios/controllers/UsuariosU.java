package br.pentatonica.guitarrascrud.usuarios.controllers;

import br.pentatonica.guitarrascrud.usuarios.Usuario;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class UsuariosU {
    private Stage stage;
    private Stage stageOwner;
    private Scene cena;

    public UsuariosU(Stage stageOwner){
        this.stageOwner = stageOwner;
    }

    public void mostrar(Usuario usuario){
        criarUI(usuario);
        stage.showAndWait();
    }

    private void criarUI(Usuario usuario){

        this.stage = new Stage();
        stage.setTitle("Editar usuario");

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(this.stageOwner);

        Label labelMensagem = new Label("Editar Usuario");
        labelMensagem.setFont(new Font("Arial",26));

        Label nome = new Label("Nome:");
        TextField nomeI = new TextField();
        nomeI.setText(usuario.getNome());

        Label email = new Label("Email:");
        TextField emailI = new TextField();
        emailI.setText(usuario.getEmail());

        Label senha = new Label("Senha:");
        TextField senhaI = new TextField();
        senhaI.setText(usuario.getSenha());

        Label CPF = new Label("CPF:");
        TextField CPFI = new TextField();
        CPFI.setText(usuario.getCPF());

        Button btnFechar = new Button("Cancelar");
        Button btnEdit = new Button("Atualizar");

        btnFechar.setOnAction((event) -> {this.stage.close();});
        btnEdit.setOnAction(actionEvent -> {
            ArrayList<Usuario> usuarios = new ArrayList<>();
            File file = new File("usuarios.dat");
            if (file.exists() && file.length() > 0) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                    usuarios = (ArrayList<Usuario>) ois.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

            usuarios.removeIf(u -> Objects.equals(u.getCPF(), usuario.getCPF()));

            usuario.setNome(nomeI.getText());
            usuario.setEmail(emailI.getText());
            usuario.setSenha(senhaI.getText());
            usuario.setSenha(CPFI.getText());
            usuarios.add(usuario);

            try {
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("usuarios.dat"));
                oos.writeObject(usuarios);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Sucesso!");
                alert.setHeaderText("Sucesso!");
                alert.setContentText("Usuario atualizado com sucesso!");
                alert.showAndWait();
                oos.close();
                this.stage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");
        layout.getChildren().addAll(labelMensagem, nome, nomeI, email, emailI, senha, senhaI, CPF, CPFI, btnEdit, btnFechar);
        this.cena = new Scene(layout, 500, 700);
        this.stage.setScene(this.cena);
    }
}
