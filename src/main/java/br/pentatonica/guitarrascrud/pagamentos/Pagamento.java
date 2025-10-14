package br.pentatonica.guitarrascrud.pagamentos;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Pagamento implements Serializable {
    private static final long serialVersionUID = 1L;

    private String titulo;
    private double preco;
    private String email;
    private String status;
    private String tipoPagamento;
    private LocalDateTime data;

    public Pagamento() {}

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getTipoPagamento() { return tipoPagamento; }
    public void setTipoPagamento(String tipoPagamento) { this.tipoPagamento = tipoPagamento; }

    public LocalDateTime getData() { return data; }
    public void setData(LocalDateTime data) { this.data = data; }


    @Override
    public String toString() {
        return "Pagamento{" +
                "titulo='" + titulo + '\'' +
                ", preco=" + preco +
                ", email='" + email + '\'' +
                ", status='" + status + '\'' +
                ", tipoPagamento='" + tipoPagamento + '\'' +
                ", data=" + data +
                '}';
    }
}
