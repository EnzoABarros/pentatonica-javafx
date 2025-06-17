package br.pentatonica.guitarrascrud.pagamentos.controllers;

import br.pentatonica.guitarrascrud.pagamentos.Pagamento;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class PagamentosD {

    public void deletarPagamento(ArrayList<Pagamento> pagamentos, Pagamento pagamento) {
        String titulo = pagamento.getTitulo();
        pagamentos.removeIf(p -> Objects.equals(titulo, p.getTitulo()));
        File file = new File("pagamentos.dat");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(pagamentos);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Sucesso!");
            alert.setHeaderText("Sucesso!");
            alert.setContentText("Pagamento deletado com sucesso!");
            alert.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro");
            alert.setContentText("Erro ao deletar o pagamento.");
            alert.showAndWait();
        }
    }
}
