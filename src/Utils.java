import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public abstract class Utils {

	public static List<Produtos> criaProdutos() {
		List<Produtos> produtos = new ArrayList<>();

		do {
			Produtos produto = new Produtos(aleatorioInt(100, 999), aleatorioFloat(1000));

			int repete = 0;
			for (int i = 0; i < produtos.size(); i++) {
				if (produto.getCodigoProduto() == produtos.get(i).getCodigoProduto()) {
					repete = 1;
					break;
				}
			}

			if (repete == 0) {
				produtos.add(produto);
			}
		} while (produtos.size() < 100);

		return produtos;
	}

	public static List<Cliente> criaClientes() {
		List<Cliente> clientes = new ArrayList<>();

		while (clientes.size() < 100) {
			Cliente cliente = new Cliente(aleatorioInt(1, 999));
			clientes.add(cliente);
		}

		return clientes;
	}

	public static List<Vendedor> criaVendedor() {
		List<Vendedor> vendedores = new ArrayList<>();

		int codigoDoVendedor = 1;
		while (vendedores.size() < 10) {
			Vendedor vendedor = new Vendedor(codigoDoVendedor);
			vendedores.add(vendedor);
			codigoDoVendedor++;
		}

		return vendedores;
	}

	public static int aleatorioInt(int nrInicial, int nrFinal) {

		int numeroGerado = 0;

		if (nrInicial == nrFinal) {
			numeroGerado = nrFinal;
		} else if (nrInicial > nrFinal) {
			numeroGerado = nrInicial;
		} else {
			Random gerador = new Random();

			numeroGerado = gerador.nextInt(nrFinal + 1);

			while (numeroGerado < nrInicial) {
				numeroGerado = aleatorioInt(nrInicial, nrFinal);
			}
		}
		return numeroGerado;
	}

	public static float aleatorioFloat(int mult) {
		Random gerador = new Random();
		return (gerador.nextFloat() * gerador.nextInt(mult + 1) + 1);
	}

	public static Pedido geraUmPedido(int nPedidos, List<Cliente> clientes, List<Produtos> produtos,
			List<Vendedor> vendedor) {
		int aleatorioSuubListProdutosMin = aleatorioInt(0, produtos.size() - 1);
		int aleatorioSuubListProdutosMax = aleatorioInt(aleatorioSuubListProdutosMin, produtos.size() - 1);

		if (aleatorioSuubListProdutosMin == aleatorioSuubListProdutosMax) {
			if (aleatorioSuubListProdutosMin == 0 && aleatorioSuubListProdutosMax < produtos.size() - 1) {
				aleatorioSuubListProdutosMax++;
			} else {
				aleatorioSuubListProdutosMin--;
			}
		}

		Calendar dataDoPedido = Calendar.getInstance();
		dataDoPedido.add(Calendar.DAY_OF_YEAR, aleatorioInt(1, 200));

		return new Pedido(nPedidos, vendedor.get(aleatorioInt(0, vendedor.size() - 1)),
				clientes.get(aleatorioInt(0, clientes.size() - 1)), dataDoPedido,
				produtos.subList(aleatorioSuubListProdutosMin, aleatorioSuubListProdutosMax));
	}
}