package br.pentatonica.guitarrascrud.pagamentos.metodos;

public class PagamentoOnline implements MetodoPagamento {
    @Override
    public void pagar(double valor) {
        System.out.println("Pagamento de R$" + valor + " realizado com sucesso via Pagamento Online!");
    }
}