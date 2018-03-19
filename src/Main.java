import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		List<Cliente> clientes = Utils.criaClientes();
		List<Produtos> produtos = Utils.criaProdutos();
		List<Vendedor> vendedor = Utils.criaVendedor();
		int escolha;
		Scanner s = new Scanner(System.in);
		GerenciadorDeArquivo arquivoDeVendas = new GerenciadorDeArquivo();
		long tempoInicial;
		long tempoExecucao;

		do {
			System.out.println("\n\n################## Sistema de Vendas ##################");
			System.out.println("\n      ======================================================");
			System.out.println("        |     Gerar Base                        |");
			System.out.println("        |       1 - Por número de registros     |");
			System.out.println("        |       2 - Por tamanho do arquivo      |");
			System.out.println("        |                                       |");
			System.out.println("        |     Consultar Base                    |");
			System.out.println("        |       3 - Por paginação               |");
			System.out.println("        |       4 - Todo o arquivo              |");
			System.out.println("        |                                       |");
			System.out.println("        |     0 - Sair                          |");
			System.out.println("        ======================================================\n");

			System.out.print("Opção: ");
			escolha = s.nextInt();
			System.out.println();

			int nPedido = 1;

			switch (escolha) {
			case 0:
				System.out.println("Você decidiu sair!");
				break;

			case 1: // O usuário escolheu "n" vendas
				System.out.print("Informe a qntd. de vendas: ");
				int qtdVendas = s.nextInt();
				System.out.println();
				tempoInicial = Calendar.getInstance().getTimeInMillis();
				arquivoDeVendas.criarEAbrirArquivoParaEscrita();

				while (nPedido <= qtdVendas) {
					Utils.geraUmPedido(nPedido, clientes, produtos, vendedor);
					nPedido++;
				}

				arquivoDeVendas.fecharArquivoParaEscrita();
				tempoExecucao = Calendar.getInstance().getTimeInMillis() - tempoInicial;
				System.out.println("Tempo de escrita: "+tempoExecucao+" milissegundos");
				System.out.println();
				break;

			case 2: // O usuário escolheu "n" (KB) tamanho do arquivo

				long tamSolicitado;

				System.out.println("Informe o tamanho do arquivo (KB): ");
				tamSolicitado = (s.nextLong() * 1024);
				System.out.println();

				tempoInicial = Calendar.getInstance().getTimeInMillis();
				arquivoDeVendas.criarEAbrirArquivoParaEscrita();

				while (arquivoDeVendas.tamanhoDoArquivo() < tamSolicitado) {
					Utils.geraUmPedido(nPedido, clientes, produtos, vendedor);
					nPedido++;
				}
				arquivoDeVendas.fecharArquivoParaEscrita();
				tempoExecucao = Calendar.getInstance().getTimeInMillis() - tempoInicial;
				System.out.println("Tempo de escrita: "+tempoExecucao+" milissegundos");
				System.out.println();
				System.out.println("Tamanho final: " + (arquivoDeVendas.tamanhoDoArquivo() / 1024) + " KB");
				break;

			case 3:
				/*
				 * Consultar Base Pode ser mais um subMenu, com:
				 * 
				 * Recuperar os registros por meio da paginação; Recuperar todos os registros;
				 * Etc
				 * 
				 */
				break;
				
			case 4:
				tempoInicial = Calendar.getInstance().getTimeInMillis();
				arquivoDeVendas.abrirArquivoParaLeitura();
				arquivoDeVendas.recuperarArquivo();
				arquivoDeVendas.fecharArquivoParaLeitura();
				tempoExecucao = Calendar.getInstance().getTimeInMillis() - tempoInicial;
				System.out.println("Tempo de leitura: "+tempoExecucao+" milissegundos");
				break;
				
			default:
				System.out.println("Opção Inválida!");
				break;

			}

		} while (escolha != 0);

		s.close();
	}
}
