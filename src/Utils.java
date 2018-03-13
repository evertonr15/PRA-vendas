import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

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

	public static int menu() {
		
		
		int opcao = 0;
		
		Scanner escolhaMenu = new Scanner(System.in);
	
		System.out.println("\n\n################## Sistema de Vendas ##################");
		System.out.println("\n                ===========================");
		System.out.println("                  |     1 - Gerar Base      |");
		System.out.println("                  |     2 - Consultar Base  |");
		System.out.println("                  |     0 - Sair            |");
		System.out.println("                  =========================\n");

		System.out.print("Opção: ");
		opcao = escolhaMenu.nextInt();

		escolhaMenu.close();
		
		switch (opcao) {
		case 1:
			return(subMenu1());
		
		case 2:
			//return(subMenu2());
			return(opcao);
		
		default:
			return(opcao);
		}
		
	}
	
	public static int subMenu1() {
		
		
		int op1;
		
		Scanner escolhaSubMenu = new Scanner(System.in);
	
		System.out.println("\n\n################## Gerar Base de Dados ##################");
		System.out.println("\n       ===========================================");
		System.out.println("         |     1 - Por número de registros     |");
		System.out.println("         |     2 - Pelo tamanho do arquivo     |");
		System.out.println("         |     0 - Sair                        |");
		System.out.println("         ===========================================\n");

		System.out.print("Opção: ");
		op1 = escolhaSubMenu.nextInt();

		escolhaSubMenu.close();
		
		return(op1);
		
	}
	
	public static void geraUmPedido(int nPedidos, List<Cliente> clientes, List<Produtos> produtos, List<Vendedor> vendedor) {
		
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
		
		new Pedido(nPedidos,vendedor.get(aleatorioInt(0, vendedor.size() - 1)),
				clientes.get(aleatorioInt(0, clientes.size() - 1)), dataDoPedido,
				produtos.subList(aleatorioSuubListProdutosMin, aleatorioSuubListProdutosMax));
	}
}
