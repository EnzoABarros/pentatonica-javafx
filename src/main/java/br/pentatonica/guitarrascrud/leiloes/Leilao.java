package br.pentatonica.guitarrascrud.leiloes;

import br.pentatonica.guitarrascrud.guitarra.Guitarra;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Leilao{
    private String nome;
    private String descricao;
    private double lanceInicial;
    private LocalDateTime dataFim;

    public Leilao() {
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

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }

    @Override
    public String toString() {
        return nome + " - R$" + lanceInicial;
    }
}