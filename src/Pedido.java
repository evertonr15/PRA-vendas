import java.io.File;
import java.util.Calendar;
import java.util.List;


public class Pedido {
	int codigoPedido;
	
	
	public Pedido(Vendedor vendedor, Cliente cliente, Calendar dataPedido, List<Produtos> produtos){
		
		float totalDaVenda = 0f;
		for(Produtos produto : produtos){
			totalDaVenda += produto.getValorProduto();
		}
		
		
		File file = new File("");
		
	}
}
