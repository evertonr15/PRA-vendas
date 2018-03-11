
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
		return "{Código do produto: " + codigoProduto + ", Valor do produto: " + valorProduto + "}";
	}
}
