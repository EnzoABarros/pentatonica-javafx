package br.pentatonica.guitarrascrud.pagamentos.metodos;

public class Pix extends PagamentoBase {
    public Pix(MetodoPagamento metodo) {
        super(metodo);
    }

    @Override
    public void realizarPagamento(double valor) {
        System.out.println("Iniciando pagamento via Pix...");
        metodo.pagar(valor);
    }
}