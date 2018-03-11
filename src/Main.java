import java.util.Calendar;
import java.util.List;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Cliente> clientes = Utils.criaCliente();
		List<Produtos> produtos = Utils.criaProdutos();
		List<Vendedor> vendedor = Utils.criaVendedor();
		/*
		 * System.out.println("Tamanho " + produtos.size()); for (int i = 0; i <
		 * produtos.size(); i++) { System.out.printf("cdProd: " +
		 * produtos.get(i).getCodigoProduto() + "; Valor: %.2f\n",
		 * produtos.get(i).getValorProduto()); } System.out.println();
		 * System.out.printf("cdProd: " + produtos.get(4).getCodigoProduto() +
		 * "; Valor: %.2f\n", produtos.get(4).getValorProduto());
		 */
		int count = 0;
		while (count < 100) {
			int aleatorioSuubListProdutosMin = Utils.aleatorioInt(0, produtos.size() - 1);
			int aleatorioSuubListProdutosMax = Utils.aleatorioInt(aleatorioSuubListProdutosMin, produtos.size() - 1);
			if (aleatorioSuubListProdutosMin == aleatorioSuubListProdutosMax) {
				if (aleatorioSuubListProdutosMin == 0 && aleatorioSuubListProdutosMax < produtos.size() - 1) {
					aleatorioSuubListProdutosMax++;
				} else {
					aleatorioSuubListProdutosMin--;
				}
			}
			Calendar dataDoPedido = Calendar.getInstance();
			dataDoPedido.add(Calendar.DAY_OF_YEAR, Utils.aleatorioInt(1, 200));
			new Pedido(vendedor.get(Utils.aleatorioInt(0, vendedor.size() - 1)),
					clientes.get(Utils.aleatorioInt(0, clientes.size() - 1)), dataDoPedido,
					produtos.subList(aleatorioSuubListProdutosMin, aleatorioSuubListProdutosMax));
			count++;
		}
	}

}
