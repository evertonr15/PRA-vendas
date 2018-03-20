import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class Pedido {

	private int codigoPedido;

	private Vendedor vendedor;

	private Cliente cliente;

	private String dataPedido;

	private List<Produtos> produtos;

	private float totalDaVenda;

	public Pedido(int codigoPedido, Vendedor vendedor, Cliente cliente, Calendar dataPedido, List<Produtos> produtos) {

		this.setCodigoPedido(codigoPedido);
		this.setVendedor(vendedor);
		this.setCliente(cliente);
		this.setDataPedido(new SimpleDateFormat("dd/MM/yyyy").format(dataPedido.getTime()));
		this.setProdutos(produtos);

		float totalDaVenda = 0f;
		for (Produtos produto : produtos) {
			totalDaVenda += produto.getValorProduto();
		}
		this.totalDaVenda = totalDaVenda;
	}

	public int getCodigoPedido() {
		return codigoPedido;
	}

	public void setCodigoPedido(int codigoPedido) {
		this.codigoPedido = codigoPedido;
	}

	public Vendedor getVendedor() {
		return vendedor;
	}

	public void setVendedor(Vendedor vendedor) {
		this.vendedor = vendedor;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getDataPedido() {
		return dataPedido;
	}

	public void setDataPedido(String dataPedido) {
		this.dataPedido = dataPedido;
	}

	public List<Produtos> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produtos> produtos) {
		this.produtos = produtos;
	}

	public float getTotalDaVenda() {
		return totalDaVenda;
	}

	public void setTotalDaVenda(float totalDaVenda) {
		this.totalDaVenda = totalDaVenda;
	}

}