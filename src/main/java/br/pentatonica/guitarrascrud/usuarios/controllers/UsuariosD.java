package br.pentatonica.guitarrascrud.usuarios.controllers;

import br.pentatonica.guitarrascrud.usuarios.Usuario;
import javafx.scene.control.Alert;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Objects;

public class UsuariosD {
    public void deletarUsuario(ArrayList<Usuario> usuarios, Usuario usuario) {
        String cpf = usuario.getCPF();
        usuarios.removeIf(u -> Objects.equals(cpf, u.getCPF()));
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
