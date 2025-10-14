package br.pentatonica.guitarrascrud.pagamentos.metodos;

public class CartaoCredito extends PagamentoBase {
    public CartaoCredito(MetodoPagamento metodo) {
        super(metodo);
    }

    @Override
    public void realizarPagamento(double valor) {
        System.out.println("Iniciando pagamento via Cartão de Crédito...");
        metodo.pagar(valor);
    }
}