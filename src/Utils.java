import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Utils {

	public static List<Produtos> criaProdutos() {
		List<Produtos> produtos = new ArrayList<>();

		do {
			Produtos pro = new Produtos(aleatorioInt(100, 999), aleatorioFloat(1000));

			int repete = 0;
			for (int i = 0; i < produtos.size(); i++) {

				if (pro.getCodigoProduto() == produtos.get(i).getCodigoProduto()) {
					repete = 1;
					break;
				}
			}

			if (repete == 0)
				produtos.add(pro);

		} while (produtos.size() < 100);

		return produtos;
	}

	public static List<Cliente> criaCliente() {
		List<Cliente> cliente = new ArrayList<>();

		while (cliente.size() < 100) {
			Cliente cli = new Cliente(aleatorioInt(1, 999));
			cliente.add(cli);
		}

		return cliente;
	}

	public static List<Vendedor> criaVendedor() {
		List<Vendedor> vendedor = new ArrayList<>();

		int i = 1;
		while (vendedor.size() < 10) {
			Vendedor vend = new Vendedor(i);
			vendedor.add(vend);
			i++;
		}

		return vendedor;
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

}
