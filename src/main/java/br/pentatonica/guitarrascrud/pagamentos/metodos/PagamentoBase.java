package br.pentatonica.guitarrascrud.pagamentos.metodos;

public abstract class PagamentoBase {
    protected MetodoPagamento metodo;

    public PagamentoBase(MetodoPagamento metodo) {
        this.metodo = metodo;
    }

    public abstract void realizarPagamento(double valor);
}