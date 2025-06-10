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

public class UsuariosC{
    private Stage stage;
    private Stage stageOwner;
    private Scene cena;

    public UsuariosC(Stage stageOwner){
        this.stageOwner = stageOwner;
    }

    public void mostrar(){
        criarUI();
        stage.showAndWait();
    }

    private void criarUI(){

        this.stage = new Stage();
        stage.setTitle("Criar usuario");

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(this.stageOwner);

        Label labelMensagem = new Label("Criar usuario");
        labelMensagem.setFont(new Font("Arial",26));

        Label nome = new Label("Nome:");
        TextField nomeI = new TextField();

        Label CPF = new Label("CPF:");
        TextField CPFI = new TextField();

        Label email = new Label("Email:");
        TextField emailI = new TextField();

        Label senha = new Label("Senha:");
        TextField senhaI = new TextField();

        Button btnFechar = new Button("Cancelar");
        Button btnAdd = new Button("Adicionar");

        btnFechar.setOnAction((event) -> {this.stage.close();});
        btnAdd.setOnAction(actionEvent -> {
            Usuario u = new Usuario();
            u.setNome(nomeI.getText());
            u.setEmail(emailI.getText());
            u.setSenha(senhaI.getText());
            while(true){
                try {
                    int num = Integer.parseInt(CPFI.getText());
                    u.setCPF(num);
                    break;
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro");
                    alert.setHeaderText("Erro");
                    alert.setContentText("Tipo de dado incorreto no campo CPF.");
                    alert.showAndWait();

                    return;
                }
            }

            ArrayList<Usuario> usuarios = new ArrayList<>();
            try {
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("usuarios.dat"));
                oos.writeObject(usuarios);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Sucesso!");
                alert.setHeaderText("Sucesso!");
                alert.setContentText("Usuario cadastrado com sucesso!");
                alert.showAndWait();
                oos.close();
                this.stage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");
        layout.getChildren().addAll(labelMensagem, nome, nomeI, CPF, CPFI, email, emailI, senha, senhaI, btnAdd, btnFechar);
        this.cena = new Scene(layout, 500, 700);
        this.stage.setScene(this.cena);
    }
}
