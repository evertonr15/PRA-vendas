import java.text.DecimalFormat;

public class Produtos {

	float valorProduto;
	int codigoProduto;

	public Produtos(final int codigo, final float valor) {
		this.codigoProduto = codigo;
		this.valorProduto = valor;
	}

	public float getValorProduto() {
		return this.valorProduto;
	}

	public int getCodigoProduto() {
		return this.codigoProduto;
	}

	@Override
	public String toString() {
		DecimalFormat decimalFormater = new DecimalFormat("#,###.00");
		return "{Código: " + codigoProduto + ", Valor: R$ " + decimalFormater.format(valorProduto) + "}";
	}
}
