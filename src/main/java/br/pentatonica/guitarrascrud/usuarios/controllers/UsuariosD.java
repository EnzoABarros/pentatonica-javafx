package br.pentatonica.guitarrascrud.usuarios.controllers;

import br.pentatonica.guitarrascrud.usuarios.Usuario;
import javafx.scene.control.Alert;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class UsuariosD {
    public void deletarUsuario(ArrayList<Usuario> usuarios, Usuario usuario) {
        usuarios.remove(usuario);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("usuarios.dat"));
            oos.writeObject(usuarios);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Sucesso!");
            alert.setHeaderText("Sucesso!");
            alert.setContentText("Usuario deletado com sucesso!");
            alert.showAndWait();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
