import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class Pedido {

	public Pedido(int codigoPedido, Vendedor vendedor, Cliente cliente, Calendar dataPedido, List<Produtos> produtos, GerenciadorDeArquivo gerenciadorDeArquivo) {
		String dataDoPedido = new SimpleDateFormat("dd/MM/yyyy").format(dataPedido.getTime());
		float totalDaVenda = 0f;
		
		for (Produtos produto : produtos) {
			totalDaVenda += produto.getValorProduto();
		}
		DecimalFormat decimalFormater = new DecimalFormat("#,###.00");
		
		gerenciadorDeArquivo.salvaPedido(
				"Pedido: " + codigoPedido
				+ "; Cliente: " + cliente.getCodigoCliente() 
				+ "; Vendedor: " + vendedor.getCodigoVendedor()
				+ "; Data da venda: " + dataDoPedido 
				+ "; Valor da venda: R$ " + decimalFormater.format(totalDaVenda) 
				+ "; Produtos: " + produtos.toString()
		);
	}
}