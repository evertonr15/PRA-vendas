import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		int escolha, qtdVendas;
		
		List<Cliente> clientes = Utils.criaCliente();
		List<Produtos> produtos = Utils.criaProdutos();
		List<Vendedor> vendedor = Utils.criaVendedor();
		
		Scanner s = new Scanner(System.in);
		
		do {
			
			/*if(qtdVendas == 100) escolha = 0;
			else escolha = 1;*/
			
			escolha = Utils.menu();
			
			switch (escolha) {
			case 0:
				break;
				
			case 1: // O usuário escolheu "n" vendas 
				System.out.print("Informe a qntd. de vendas: ");
				qtdVendas = s.nextInt();
				System.out.println();
				
				int count = 0,  nPedidos=1;
				while (count < qtdVendas) {
					Utils.geraUmPedido(nPedidos, clientes, produtos, vendedor); // Vamos Usar o mesmo código no case 2.
					count++;
				}
				
				break;
				
			case 2: // O usuário escolheu "n" vendas 
				System.out.println("Informe o tamanho do arquivo: ");
				/*
				 * tamArquivo = s.nextInt();
				 * int count = 0,  nPedidos=1;
				 * while (tamanhoDoArquivo < tamArquivo) {
				 * 		Utils.geraUmPedido(nPedidos, clientes, produtos, vendedor);
				 * 		tamanhoDoArquivo = calculaOtamanhoDoArquivo;
				 * }
				*/
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
