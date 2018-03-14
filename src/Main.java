import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		int escolha;
		
		List<Cliente> clientes = Utils.criaCliente();
		List<Produtos> produtos = Utils.criaProdutos();
		List<Vendedor> vendedor = Utils.criaVendedor();
		
		Scanner s = new Scanner(System.in);
		
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
			
			switch (escolha) {
			case 0:
				System.out.println("Você decidiu sair!");
				break;
				
			case 1: // O usuário escolheu "n" vendas 
				System.out.print("Informe a qntd. de vendas: ");
				int qtdVendas = s.nextInt();
				System.out.println();
				
				int count = 0,  nPedidos=1;
				while (count < qtdVendas) {
					Utils.geraUmPedido(nPedidos, clientes, produtos, vendedor); // Vamos Usar o mesmo código no case 2.
					count++;
				}
				
				break;
				
			case 2: // O usuário escolheu "n" vendas 
				
				long tamSolicitado, tamAtual;
				int nPedido = 1;
				
				System.out.println("Informe o tamanho do arquivo: ");
				tamSolicitado = s.nextLong();
				System.out.println();
				
				tamAtual = Utils.tamanhoArquivo("vendas.txt");
				
				
				while(tamAtual < tamSolicitado) {
					
					Utils.geraUmPedido(nPedido, clientes, produtos, vendedor);
					tamAtual = Utils.tamanhoArquivo("vendas.txt");
					
				}
					
				System.out.println("Tamanho final: "+tamAtual);

				break;
			
			case 3:
				/*
				 * Consultar Base 
				 * Pode ser mais um subMenu, com:
				 * 
				 * Recuperar os registros por meio da paginação;
				 * Recuperar todos os registros;
				 * Etc
				 * 
				 */
				break;
				
			default:
				System.out.println("Opção Inválida!");
				break;
			
			}
			
		} while(escolha != 0);
		
		s.close();
	}

}
