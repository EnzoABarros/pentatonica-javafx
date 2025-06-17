package br.pentatonica.guitarrascrud.leiloes;

import br.pentatonica.guitarrascrud.guitarra.Guitarra;

import java.util.ArrayList;

public class Leilao{
    private String nome;
    private String descricao;
    private double lanceInicial;
    private ArrayList<Guitarra> guitarras;

    public Leilao() {
        this.guitarras = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getLanceInicial() {
        return lanceInicial;
    }

    public void setLanceInicial(double lanceInicial) {
        this.lanceInicial = lanceInicial;
    }

    public ArrayList<Guitarra> getGuitarras() {
        return guitarras;
    }

    public void setGuitarras(ArrayList<Guitarra> guitarras) {
        this.guitarras = guitarras;
    }

    @Override
    public String toString() {
        return nome + " - R$" + lanceInicial;
    }
}