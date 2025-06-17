
package br.pentatonica.guitarrascrud.leiloes.controllers;

<<<<<<< Updated upstream
=======
import br.pentatonica.guitarrascrud.guitarra.Guitarra;
>>>>>>> Stashed changes
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
import java.util.ArrayList;

public class LeiloesC {
<<<<<<< Updated upstream
    /*private Stage stage;
=======
    private Stage stage;
>>>>>>> Stashed changes
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

        Label titulo = new Label("Novo Leilão");
        titulo.setFont(new Font("Arial", 26));

        TextField nomeInput = new TextField();
        nomeInput.setPromptText("Nome do Leilão");

        TextField lanceInput = new TextField();
        lanceInput.setPromptText("Lance Inicial");

        TextField descricaoInput = new TextField();
        descricaoInput.setPromptText("Descrição");

<<<<<<< Updated upstream
=======
        ArrayList<Guitarra> guitarrasDisponiveis = new ArrayList<>();
        File fileG = new File("guitarras.dat");
        if (fileG.exists() && fileG.length() > 0) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileG))) {
                guitarrasDisponiveis = (ArrayList<Guitarra>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        ListView<Guitarra> listView = new ListView<>();
        ObservableList<Guitarra> obsList = FXCollections.observableArrayList(guitarrasDisponiveis);
        listView.setItems(obsList);
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

>>>>>>> Stashed changes
        Button btnSalvar = new Button("Salvar");
        btnSalvar.setOnAction(e -> {
            Leilao leilao = new Leilao();
            leilao.setNome(nomeInput.getText());
            leilao.setDescricao(descricaoInput.getText());

            try {
                double preco = Double.parseDouble(lanceInput.getText());
                leilao.setLanceInicial(preco);
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Erro");
                alert.setContentText("Lance inicial inválido.");
                alert.showAndWait();
                return;
            }

            leilao.setGuitarras(new ArrayList<>(listView.getSelectionModel().getSelectedItems()));

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
        layout.getChildren().addAll(titulo, nomeInput, lanceInput, descricaoInput, new Label("Selecionar Guitarras:"), listView, btnSalvar);

        this.cena = new Scene(layout, 500, 600);
        this.stage.setScene(this.cena);
<<<<<<< Updated upstream
    }*/
}
=======
    }
}
>>>>>>> Stashed changes
