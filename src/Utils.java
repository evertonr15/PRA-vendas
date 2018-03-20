import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public abstract class Utils {

	public static List<Produtos> criaProdutos() { // método que cria produtos
		List<Produtos> produtos = new ArrayList<>(); // cria uma lista de produtos

		do {
			Produtos produto = new Produtos(aleatorioInt(100, 999), aleatorioFloat(1000)); // código do produto varia entre 100 a 999 e o valor é um número aleatório multiplicado por 1000

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
		} while (produtos.size() < 100); // define uma lista de 100 produtos

		return produtos;
	}

	public static List<Cliente> criaClientes() { // método que cria clientes
		List<Cliente> clientes = new ArrayList<>(); // cria uma lista de clientes

		while (clientes.size() < 100) { // define uma lista de 100 clientes
			Cliente cliente = new Cliente(aleatorioInt(1, 999)); // código do cliente varia entre 1 a 999
			clientes.add(cliente);
		}

		return clientes;
	}

	public static List<Vendedor> criaVendedor() { // método que cria vendedores
		List<Vendedor> vendedores = new ArrayList<>(); // cria uma lista de vendedores

		int codigoDoVendedor = 1;
		while (vendedores.size() < 10) { // define uma lista de 10 vendedores
			Vendedor vendedor = new Vendedor(codigoDoVendedor); 
			vendedores.add(vendedor);
			codigoDoVendedor++;
		}

		return vendedores;
	}

	public static int aleatorioInt(int nrInicial, int nrFinal) { // método que gera números aleatórios inteiros

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

	public static float aleatorioFloat(int mult) { // método que gera números aleatórios float
		Random gerador = new Random();
		return (gerador.nextFloat() * gerador.nextInt(mult + 1) + 1);
	}

	public static Pedido geraUmPedido(int nPedidos, List<Cliente> clientes, List<Produtos> produtos, // método que gera aleatoriamente um pedido
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