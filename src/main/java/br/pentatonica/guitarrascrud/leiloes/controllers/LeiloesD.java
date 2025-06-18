
package br.pentatonica.guitarrascrud.leiloes.controllers;

import br.pentatonica.guitarrascrud.guitarra.Guitarra;
import br.pentatonica.guitarrascrud.leiloes.Leilao;
import javafx.scene.control.Alert;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class LeiloesD {
    public void deletar(ArrayList<Leilao> leiloes, Leilao leilao) {
        String nome = leilao.getNome();
        leiloes.removeIf(l -> Objects.equals(nome, l.getNome()));
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("leiloes.dat"));
            oos.writeObject(leiloes);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Sucesso!");
            alert.setHeaderText("Sucesso!");
            alert.setContentText("Leilao deletado com sucesso!");
            alert.showAndWait();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
