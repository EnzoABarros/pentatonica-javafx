
package br.pentatonica.guitarrascrud.leiloes.controllers;

import br.pentatonica.guitarrascrud.guitarra.Guitarra;
import br.pentatonica.guitarrascrud.leiloes.Leilao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

public class LeiloesU {
    private Stage stage;
    private Stage owner;
    private Scene cena;
    private Leilao leilao;

    public LeiloesU(Stage owner, Leilao leilao) {
        this.owner = owner;
        this.leilao = leilao;
    }

    public void mostrar() {
        criarUI();
        stage.showAndWait();
    }

    private void criarUI() {
        this.stage = new Stage();
        stage.setTitle("Editar Leilão");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(this.owner);

        TextField nomeInput = new TextField(leilao.getNome());
        TextField lanceInput = new TextField(String.valueOf(leilao.getLanceInicial()));
        TextField descricaoInput = new TextField(leilao.getDescricao());


        Button salvar = new Button("Salvar");
        salvar.setOnAction(e -> {
            leilao.setNome(nomeInput.getText());
            leilao.setDescricao(descricaoInput.getText());
            try {
                leilao.setLanceInicial(Double.parseDouble(lanceInput.getText()));
            } catch (NumberFormatException ex) {
                return;
            }

            File file = new File("leiloes.dat");
            if (file.exists() && file.length() > 0) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                    ArrayList<Leilao> leiloes = (ArrayList<Leilao>) ois.readObject();
                    for (int i = 0; i < leiloes.size(); i++) {
                        if (leiloes.get(i).getNome().equals(leilao.getNome())) {
                            leiloes.set(i, leilao);
                            break;
                        }
                    }
                    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                        oos.writeObject(leiloes);
                    }
                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
            this.stage.close();
        });

        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");
        layout.getChildren().addAll(new Label("Nome:"), nomeInput, new Label("Lance Inicial:"), lanceInput, new Label("Descrição:"), descricaoInput, salvar);

        this.cena = new Scene(layout, 500, 600);
        this.stage.setScene(this.cena);
    }
}
