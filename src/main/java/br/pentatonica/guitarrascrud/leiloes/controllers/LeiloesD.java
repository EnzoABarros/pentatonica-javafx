
package br.pentatonica.guitarrascrud.leiloes.controllers;

import br.pentatonica.guitarrascrud.leiloes.Leilao;

import java.io.*;
import java.util.ArrayList;

public class LeiloesD {
    private Leilao leilao;

    public LeiloesD(Leilao leilao) {
        this.leilao = leilao;
    }

    public void deletar() {
        File file = new File("leiloes.dat");
        ArrayList<Leilao> leiloes = new ArrayList<>();
        if (file.exists() && file.length() > 0) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                leiloes = (ArrayList<Leilao>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        leiloes.removeIf(l -> l.getNome().equals(leilao.getNome()));

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(leiloes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
