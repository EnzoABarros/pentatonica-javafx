package br.pentatonica.guitarrascrud.guitarra.controllers;

import br.pentatonica.guitarrascrud.guitarra.Guitarra;
import javafx.scene.control.Alert;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class GuitarraD {
    public void deletarGuitarra(ArrayList<Guitarra> guitarras, Guitarra guitarra) {
        guitarras.remove(guitarra);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("guitarras.dat"));
            oos.writeObject(guitarras);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Sucesso!");
            alert.setHeaderText("Sucesso!");
            alert.setContentText("Guitarra deletada com sucesso!");
            alert.showAndWait();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
