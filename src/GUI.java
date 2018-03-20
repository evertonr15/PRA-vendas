import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GUI {
	
	/*
	 * Variáveis necessárias para criação
	 * das janelas de interação com o usuário
	 */

	static JTextArea campoDeInformacoesGerais = new JTextArea(35, 90);
	
	static JTextArea campoDeRetornoPaginacao = new JTextArea(20, 70);
	
	static JTextField campoDeEntradaDoUsuario = new JTextField();
	
	static JTextField campoDeEntradaDoUsuarioPaginacao = new JTextField();

	static GerenciadorDeArquivo arquivoDeVendas;
	
	static boolean fimPaginacao = false;
	
	static int paginaAtual = 0;
	
	static int totalPaginacao = 0;
	
	public static void main(String[] args) {

		final List<Cliente> clientes = Utils.criaClientes(); // instanciando uma lista de clientes
		final List<Produtos> produtos = Utils.criaProdutos(); // instanciando uma lista de produtos
		final List<Vendedor> vendedor = Utils.criaVendedor(); // instanciando uma lista de Vendedores
		arquivoDeVendas = new GerenciadorDeArquivo(); // instanciando um gerenciador de arquivo

		final JScrollPane outScrollInformacoesGerais = new JScrollPane(campoDeInformacoesGerais);
		campoDeInformacoesGerais.setEditable(false);
		final JScrollPane outScrollInformacoesDeRetorno = new JScrollPane(campoDeRetornoPaginacao);
		campoDeRetornoPaginacao.setEditable(false);

		JButton btnCriaRegQtd = new JButton("Criar pedidos por quantidade"); // Botão pedidos por quantidade
		btnCriaRegQtd.setToolTipText("Cria os pedidos de acordo com a quantidade digitada");
		btnCriaRegQtd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				campoDeEntradaDoUsuarioPaginacao.setText("0"); // padrão paginação igual a zero
				List<Pedido> paginaPedidos = new ArrayList<>(); // cria uma lista de pedidos
				Object[] entradaDoUsuário = {
					    "Quantidade de registros por página (0 - Sem paginação): ", campoDeEntradaDoUsuarioPaginacao, // lê o campo de paginação digitado pelo usuário
					    "Quantidade de registros a serem criados: ", campoDeEntradaDoUsuario // lê o campo de quantidade de registros digitado pelo usuário
				}; // acão relacionado ao botão pedidos por quantidade

				Integer opt = JOptionPane.showOptionDialog(null, entradaDoUsuário, "Criação por quantidade de registros", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null); // Caixa de diálogo para inserir a qntde de registros
				if (opt == 0) {
					int qtdRegistroPorPagina = Integer.parseInt(campoDeEntradaDoUsuarioPaginacao.getText().trim()); // qtdRegistroPorPagina: campo que guarda a paginação
					int qtdRegistros = Integer.parseInt(campoDeEntradaDoUsuario.getText().trim()); //qtdRegistros: campo que guarda a quantidade de registros
					
					long tempoInicial = Calendar.getInstance().getTimeInMillis(); // início da contagem de tempo da escrita do arquivo
					arquivoDeVendas.criarEAbrirArquivoParaEscrita(); // abertura do arquivo para escrita
					int nPedido = 1;
					
					if(qtdRegistroPorPagina > 0){ // se possui paginação
						while (nPedido <= qtdRegistros) {
							if(paginaPedidos.size() == qtdRegistroPorPagina){
								arquivoDeVendas.salvaPedidoPaginacao(paginaPedidos); // caso a qntde de pedidos atingiu o limite que o usuário solicitou, salva a página no arquivo.
								paginaPedidos.clear();
							} 
							paginaPedidos.add(Utils.geraUmPedido(nPedido, clientes, produtos, vendedor)); // gera uma linha de pedido e adiciona em paginaPedidos 
							nPedido++;
						}
						if(paginaPedidos.size() > 0){ // Certificação das situações em que o número de pedidos não são múltiplos do número da paginação
							arquivoDeVendas.salvaPedidoPaginacao(paginaPedidos);
							paginaPedidos.clear();
						} 
					}else{ // caso não possui paginação
						while (nPedido <= qtdRegistros) {
							arquivoDeVendas.salvaPedido(Utils.geraUmPedido(nPedido, clientes, produtos, vendedor)); //escreve linha a linha no arquivo
							nPedido++;
						}
					}
					
					arquivoDeVendas.fecharArquivoParaEscrita(); // fecha arquivo
					long tempoExecucao = Calendar.getInstance().getTimeInMillis() - tempoInicial; // cálculo do tempo de escrita do arquivo em ms
					GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\n###########################################################################################################################");
					GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\nOpção selecionada: Criação de arquivos por quantidade");
					GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\nPaginação selecionada: "+qtdRegistroPorPagina);
					GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\nQuantidade de registros selecionada: "+qtdRegistros);
					GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\nTempo de execução: "+tempoExecucao+" milissegundos");
					GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\n###########################################################################################################################\n");
				}
				campoDeEntradaDoUsuario.setText(null);
			}
		});

		JButton btnCriaRegTamanho = new JButton("Criar pedidos por tamanho (KB)"); // Botão pedidos por tamanho de arquivo
		btnCriaRegTamanho.setToolTipText("Cria os pedidos de acordo com o tamanho digitado (KB)");
		btnCriaRegTamanho.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				campoDeEntradaDoUsuarioPaginacao.setText("0");
				List<Pedido> paginaPedidos = new ArrayList<>();
				Object[] entradaDoUsuário = {
					    "Quantidade de registros por página (0 - Sem paginação): ", campoDeEntradaDoUsuarioPaginacao, // qtdRegistroPorPagina: campo que guarda a paginação
					    "Tamanho do arquivo (KB): ", campoDeEntradaDoUsuario // lê o campo de tamanho do arquivo digitado pelo usuário
				};// acção relacionado ao botão pedidos por tamanho de arquivo
				
				Integer opt = JOptionPane.showOptionDialog(null, entradaDoUsuário, "Digite o tamanho desejado (KB)", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null); // Caixa de diálogo para inserir o tamanho do arquivo
				if (opt == 0) {
					long tamSolicitado;

					int qtdRegistroPorPagina = Integer.parseInt(campoDeEntradaDoUsuarioPaginacao.getText().trim());
					tamSolicitado = Integer.valueOf(campoDeEntradaDoUsuario.getText().trim()) * 1024;

					long tempoInicial = Calendar.getInstance().getTimeInMillis(); // início da contagem de tempo da escrita do arquivo
					arquivoDeVendas.criarEAbrirArquivoParaEscrita(); // abertura do arquivo para escrita
					int nPedido = 1;
					
					if(qtdRegistroPorPagina > 0){ // se possui paginação
						while (arquivoDeVendas.tamanhoDoArquivo() < tamSolicitado) { //verifica se o tamanho atual do arquivo 
							if(paginaPedidos.size() == qtdRegistroPorPagina){
								arquivoDeVendas.salvaPedidoPaginacao(paginaPedidos);
								paginaPedidos.clear();
							}
							else if (qtdRegistroPorPagina/(tamSolicitado/1024)>1) { // verifica se a paginação é maior que o tamanho em KB do arquivo
								arquivoDeVendas.salvaPedido(Utils.geraUmPedido(nPedido, clientes, produtos, vendedor));// escreve linha a linha no arquivo
								nPedido++;
							}
							paginaPedidos.add(Utils.geraUmPedido(nPedido, clientes, produtos, vendedor));
							nPedido++;
						}
					}else{ // sem paginação
						while (arquivoDeVendas.tamanhoDoArquivo() < tamSolicitado) {
							arquivoDeVendas.salvaPedido(Utils.geraUmPedido(nPedido, clientes, produtos, vendedor));// escreve linha a linha no arquivo
							nPedido++;
						}
					}
					
					arquivoDeVendas.fecharArquivoParaEscrita();
					long tempoExecucao = Calendar.getInstance().getTimeInMillis() - tempoInicial;
					GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\n###########################################################################################################################");
					GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\nOpção selecionada: Criação de arquivos por tamanho (KB)");
					GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\nTamanho de arquivo selecionado: "+tamSolicitado/1024+ " KB");
					GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\nTamanho de arquivo gerado: " + (arquivoDeVendas.tamanhoDoArquivo() / 1024) + " KB");
					GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\nTempo de execução: "+tempoExecucao+" milissegundos");
					GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\n###########################################################################################################################\n");
				}
				campoDeEntradaDoUsuario.setText(null);
			}
		});

		JButton btnConsultaGeral = new JButton("Consultar base"); // Botão consultar base
		btnConsultaGeral.setToolTipText("Exibe os registros do arquivo gerado");
		btnConsultaGeral.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				campoDeEntradaDoUsuarioPaginacao.setText("0");
				paginaAtual = 0;
				Object[] entradaDoUsuário = {
					    "Quantidade de registros por página (0 - Sem paginação): ", campoDeEntradaDoUsuarioPaginacao
				};// acão relacionado ao botão consultar base

				Integer opt = JOptionPane.showOptionDialog(null, entradaDoUsuário, "Consulta", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null); // Caixa de diálogo para consulta dos dados
				if (opt == 0) {
					
					final int qtdRegistroPorPagina = Integer.parseInt(campoDeEntradaDoUsuarioPaginacao.getText().trim()); // verifica a qntd. de registros por página
					
					final JButton btnAnterior = new JButton("Anterior"); // Criação do botão anterior
					btnAnterior.setToolTipText("");
					btnAnterior.addActionListener(new ActionListener() {// Ação ao usar o botão
						@Override
						public void actionPerformed(ActionEvent e) {
							if(paginaAtual > 0){// Verifica se não é a primeira página 
								GUI.paginaAtual--;
								GUI.campoDeRetornoPaginacao.setText(null);
								arquivoDeVendas.abrirArquivoParaLeitura();
								arquivoDeVendas.recuperarArquivoPaginacaoGUI(qtdRegistroPorPagina);
								arquivoDeVendas.fecharArquivoParaLeitura();
								GUI.campoDeRetornoPaginacao.setText(GUI.campoDeRetornoPaginacao.getText() + "\n   " + "Página: " + (GUI.paginaAtual+1)+"/"+GUI.totalPaginacao);
							}
						}
					});
					
					final JButton btnProximo = new JButton("Próximo");// Criação do botão próximo
					btnProximo.setToolTipText("");
					btnProximo.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
								GUI.paginaAtual++;
								GUI.campoDeRetornoPaginacao.setText(null);
								arquivoDeVendas.abrirArquivoParaLeitura();
								arquivoDeVendas.recuperarArquivoPaginacaoGUI(qtdRegistroPorPagina);
								if(GUI.fimPaginacao){// verifica se não é a última página
									GUI.paginaAtual--;
									arquivoDeVendas.fecharArquivoParaLeitura();
									arquivoDeVendas.abrirArquivoParaLeitura();
									arquivoDeVendas.recuperarArquivoPaginacaoGUI(qtdRegistroPorPagina);
								}
								arquivoDeVendas.fecharArquivoParaLeitura();
								GUI.campoDeRetornoPaginacao.setText(GUI.campoDeRetornoPaginacao.getText() + "\n   " + "Página: " + (GUI.paginaAtual+1)+"/"+GUI.totalPaginacao);
						}
					});
					
					long tempoInicial = Calendar.getInstance().getTimeInMillis();
					
					arquivoDeVendas.abrirArquivoParaLeitura();
					if(qtdRegistroPorPagina > 0){// Leitura com paginação
						long quantidadeTotalDeLinhas = arquivoDeVendas.quantidadeDeLinhasDoArquivo();// Busca quantas linhas tem o arquivo
						
						totalPaginacao = (int) (quantidadeTotalDeLinhas / qtdRegistroPorPagina);// Divide a paginação escolhida pelo usuário pela quantidade total de linhas
						if(quantidadeTotalDeLinhas % qtdRegistroPorPagina  != 0) {// Se a divisão sobrar resto, adiciona uma página
							totalPaginacao++;
						}
						arquivoDeVendas.recuperarArquivoPaginacaoGUI(qtdRegistroPorPagina);// Lê arquivo com a paginação passada por parâmetro
						arquivoDeVendas.fecharArquivoParaLeitura();
						long tempoExecucao = Calendar.getInstance().getTimeInMillis() - tempoInicial;
						GUI.campoDeRetornoPaginacao.setText(GUI.campoDeRetornoPaginacao.getText() + "\n\nTempo de Execução: "+tempoExecucao+" milissegundos");
						GUI.campoDeRetornoPaginacao.setText(GUI.campoDeRetornoPaginacao.getText() + "\n###########################################################################################################################\n");
						
						GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\n###########################################################################################################################");
						GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\nOpção selecionada: Leitura por paginação");
						GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\nTempo de execução: "+tempoExecucao+" milissegundos");
						GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\n###########################################################################################################################\n");
				
						GUI.campoDeRetornoPaginacao.setText(GUI.campoDeRetornoPaginacao.getText() + "\n   " + "Página: " + (GUI.paginaAtual+1)+"/"+GUI.totalPaginacao);
						JOptionPane.showOptionDialog(null, outScrollInformacoesDeRetorno, "Leitura - Paginação", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[] {btnAnterior, btnProximo}, null);
					}else{ // Leitura sem paginação
						GUI.campoDeRetornoPaginacao.setText(GUI.campoDeRetornoPaginacao.getText() + "\n###########################################################################################################################");
						GUI.campoDeRetornoPaginacao.setText(GUI.campoDeRetornoPaginacao.getText() + "\nOpção selecionada: Consultar base");
						GUI.campoDeRetornoPaginacao.setText(GUI.campoDeRetornoPaginacao.getText() + "\n\nRegistros: ");
						arquivoDeVendas.recuperarArquivoGUI();// Lê arquivo todo
						arquivoDeVendas.fecharArquivoParaLeitura();
						long tempoExecucao = Calendar.getInstance().getTimeInMillis() - tempoInicial;
						GUI.campoDeRetornoPaginacao.setText(GUI.campoDeRetornoPaginacao.getText() + "\n\nTempo de Execução: "+tempoExecucao+" milissegundos");
						GUI.campoDeRetornoPaginacao.setText(GUI.campoDeRetornoPaginacao.getText() + "\n###########################################################################################################################\n");
						
						GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\n###########################################################################################################################");
						GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\nOpção selecionada: Leitura completa");
						GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\nTempo de execução: "+tempoExecucao+" milissegundos");
						GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\n###########################################################################################################################\n");
				
						
						JOptionPane.showOptionDialog(null, outScrollInformacoesDeRetorno, "Leitura - Total", JOptionPane.CLOSED_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
					}
				}
			}
		});

		JOptionPane.showOptionDialog(null, outScrollInformacoesGerais, "PRA - Vendas", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[] { btnCriaRegQtd, btnCriaRegTamanho, btnConsultaGeral }, btnCriaRegQtd); // mostrando a página principal
		
	}
	
	
}


