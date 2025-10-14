package br.pentatonica.guitarrascrud.leiloes;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Leilao implements Serializable{
    private String nome;
    private String descricao;
    private double lanceInicial;
    private LocalDate dataFim;

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

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    @Override
    public String toString() {
        return nome + " - R$" + lanceInicial;
    }
}