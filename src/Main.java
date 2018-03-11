import java.util.Calendar;
import java.util.List;

import org.w3c.dom.ls.LSInput;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//List<Cliente> clientes = Utils.criaCliente();
		List<Produtos> produtos = Utils.criaProdutos();
		//List<Vendedor> vendedor = Utils.criaVendedor();
		
		System.out.println("Tamanho "+ produtos.size());
		
		
		for (int i = 0; i < produtos.size(); i++) {
			
			System.out.printf("cdProd: "+ produtos.get(i).getCodigoProduto()
					+ "; Valor: %.2f\n", produtos.get(i).getValorProduto());
			
		}
		
		System.out.println();
		System.out.printf("cdProd: "+ produtos.get(4).getCodigoProduto()
				+ "; Valor: %.2f\n", produtos.get(4).getValorProduto());
		
		
		
		
		
		//Pedido pedido = new Pedido(vendedor, cliente, dataPedido, produtos)
	}

}
