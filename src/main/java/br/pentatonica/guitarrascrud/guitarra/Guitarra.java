package br.pentatonica.guitarrascrud.guitarra;

import java.io.Serializable;

public class Guitarra implements Serializable{
    private String modelo;
    private String marca;
    private double preco;
    private String descricao;
    private String categoria;

    public Guitarra() {}

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        String a = "Marca: " + marca + "\n";
        String b = "Modelo: " + modelo + "\n";
        String c = "Descrição: " + descricao + "\n";
        String d = "Categoria: " + categoria + "\n";
        String e = "Preço: " + preco;
        return a + b + c + d + e;
    }



}
