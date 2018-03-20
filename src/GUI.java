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

	static JTextArea campoDeInformacoesGerais = new JTextArea(35, 90);
	
	static JTextArea campoDeRetornoPaginacao = new JTextArea(20, 70);
	
	static JTextField campoDeEntradaDoUsuario = new JTextField();
	
	static JTextField campoDeEntradaDoUsuarioPaginacao = new JTextField();

	static GerenciadorDeArquivo arquivoDeVendas;
	
	static boolean fimPaginacao = false;
	
	static int paginaAtual = 0;
	
	static int totalPaginacao = 0;
	
	public static void main(String[] args) {
		final List<Cliente> clientes = Utils.criaClientes();
		final List<Produtos> produtos = Utils.criaProdutos();
		final List<Vendedor> vendedor = Utils.criaVendedor();
		
		arquivoDeVendas = new GerenciadorDeArquivo();

		final JScrollPane outScrollInformacoesGerais = new JScrollPane(campoDeInformacoesGerais);
		campoDeInformacoesGerais.setEditable(false);
		final JScrollPane outScrollInformacoesDeRetorno = new JScrollPane(campoDeRetornoPaginacao);
		campoDeRetornoPaginacao.setEditable(false);

		JButton btnCriaRegQtd = new JButton("Criar pedidos por quantidade");
		btnCriaRegQtd.setToolTipText("Cria os pedidos de acordo com a quantidade digitada");
		btnCriaRegQtd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				campoDeEntradaDoUsuarioPaginacao.setText("0");
				List<Pedido> paginaPedidos = new ArrayList<>();
				Object[] entradaDoUsu�rio = {
					    "Quantidade de registros por p�gina (0 - Sem pagina��o): ", campoDeEntradaDoUsuarioPaginacao,
					    "Quantidade de registros a serem criados: ", campoDeEntradaDoUsuario
				};

				Integer opt = JOptionPane.showOptionDialog(null, entradaDoUsu�rio, "Cria��o por quantidade de registros", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
				if (opt == 0) {
					int qtdRegistroPorPagina = Integer.parseInt(campoDeEntradaDoUsuarioPaginacao.getText().trim());
					int qtdRegistros = Integer.parseInt(campoDeEntradaDoUsuario.getText().trim());
					
					long tempoInicial = Calendar.getInstance().getTimeInMillis();
					arquivoDeVendas.criarEAbrirArquivoParaEscrita();
					int nPedido = 1;
					
					if(qtdRegistroPorPagina > 0){
						while (nPedido <= qtdRegistros) {
							if(paginaPedidos.size() == qtdRegistroPorPagina){
								arquivoDeVendas.salvaPedidoPaginacao(paginaPedidos);
								paginaPedidos.clear();
							}
							paginaPedidos.add(Utils.geraUmPedido(nPedido, clientes, produtos, vendedor));
							nPedido++;
						}
						if(paginaPedidos.size() > 0){
							arquivoDeVendas.salvaPedidoPaginacao(paginaPedidos);
							paginaPedidos.clear();
						}
					}else{
						while (nPedido <= qtdRegistros) {
							arquivoDeVendas.salvaPedido(Utils.geraUmPedido(nPedido, clientes, produtos, vendedor));
							nPedido++;
						}
					}
					
					arquivoDeVendas.fecharArquivoParaEscrita();
					long tempoExecucao = Calendar.getInstance().getTimeInMillis() - tempoInicial;
					GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\n###########################################################################################################################");
					GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\nOp��o selecionada: Cria��o de arquivos por quantidade");
					GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\nPagina��o selecionada: "+qtdRegistroPorPagina);
					GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\nQuantidade de registros selecionada: "+qtdRegistros);
					GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\nTempo de execu��o: "+tempoExecucao+" milissegundos");
					GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\n###########################################################################################################################\n");
				}
				campoDeEntradaDoUsuario.setText(null);
			}
		});

		JButton btnCriaRegTamanho = new JButton("Criar pedidos por tamanho (KB)");
		btnCriaRegTamanho.setToolTipText("Cria os pedidos de acordo com o tamanho digitado (KB)");
		btnCriaRegTamanho.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				campoDeEntradaDoUsuarioPaginacao.setText("0");
				List<Pedido> paginaPedidos = new ArrayList<>();
				Object[] entradaDoUsu�rio = {
					    "Quantidade de registros por p�gina (0 - Sem pagina��o): ", campoDeEntradaDoUsuarioPaginacao,
					    "Tamanho do arquivo: ", campoDeEntradaDoUsuario
				};
				
				Integer opt = JOptionPane.showOptionDialog(null, entradaDoUsu�rio, "Digite o tamanho desejado (KB)", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
				if (opt == 0) {
					long tamSolicitado;

					int qtdRegistroPorPagina = Integer.parseInt(campoDeEntradaDoUsuarioPaginacao.getText().trim());
					tamSolicitado = Integer.valueOf(campoDeEntradaDoUsuario.getText().trim()) * 1024;

					long tempoInicial = Calendar.getInstance().getTimeInMillis();
					arquivoDeVendas.criarEAbrirArquivoParaEscrita();
					int nPedido = 1;
					
					if(qtdRegistroPorPagina > 0){
						while (arquivoDeVendas.tamanhoDoArquivo() < tamSolicitado) {
							if(paginaPedidos.size() == qtdRegistroPorPagina){
								arquivoDeVendas.salvaPedidoPaginacao(paginaPedidos);
								paginaPedidos.clear();
							}
							paginaPedidos.add(Utils.geraUmPedido(nPedido, clientes, produtos, vendedor));
							nPedido++;
						}
					}else{
						while (arquivoDeVendas.tamanhoDoArquivo() < tamSolicitado) {
							arquivoDeVendas.salvaPedido(Utils.geraUmPedido(nPedido, clientes, produtos, vendedor));
							nPedido++;
						}
					}
					
					arquivoDeVendas.fecharArquivoParaEscrita();
					long tempoExecucao = Calendar.getInstance().getTimeInMillis() - tempoInicial;
					GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\n###########################################################################################################################");
					GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\nOp��o selecionada: Cria��o de arquivos por tamanho (KB)");
					GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\nTamanho de arquivo selecionado: "+tamSolicitado/1024+ " KB");
					GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\nTamanho de arquivo gerado: " + (arquivoDeVendas.tamanhoDoArquivo() / 1024) + " KB");
					GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\nTempo de execu��o: "+tempoExecucao+" milissegundos");
					GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\n###########################################################################################################################\n");
				}
				campoDeEntradaDoUsuario.setText(null);
			}
		});

		JButton btnConsultaGeral = new JButton("Consultar base");
		btnConsultaGeral.setToolTipText("Exibe os registros do arquivo gerado");
		btnConsultaGeral.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				campoDeEntradaDoUsuarioPaginacao.setText("0");
				paginaAtual = 0;
				Object[] entradaDoUsu�rio = {
					    "Quantidade de registros por p�gina (0 - Sem pagina��o): ", campoDeEntradaDoUsuarioPaginacao
				};

				Integer opt = JOptionPane.showOptionDialog(null, entradaDoUsu�rio, "Consulta", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
				if (opt == 0) {
					
					final int qtdRegistroPorPagina = Integer.parseInt(campoDeEntradaDoUsuarioPaginacao.getText().trim());
					
					final JButton btnAnterior = new JButton("Anterior");
					btnAnterior.setToolTipText("");
					btnAnterior.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							if(paginaAtual > 0){
								GUI.paginaAtual--;
								GUI.campoDeRetornoPaginacao.setText(null);
								arquivoDeVendas.abrirArquivoParaLeitura();
								arquivoDeVendas.recuperarArquivoPaginacaoGUI2(qtdRegistroPorPagina);
								arquivoDeVendas.fecharArquivoParaLeitura();
								GUI.campoDeRetornoPaginacao.setText(GUI.campoDeRetornoPaginacao.getText() + "\n   " + "P�gina: " + (GUI.paginaAtual+1)+"/"+GUI.totalPaginacao);
							}
						}
					});
					
					final JButton btnProximo = new JButton("Pr�ximo");
					btnProximo.setToolTipText("");
					btnProximo.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
								GUI.paginaAtual++;
								GUI.campoDeRetornoPaginacao.setText(null);
								arquivoDeVendas.abrirArquivoParaLeitura();
								arquivoDeVendas.recuperarArquivoPaginacaoGUI2(qtdRegistroPorPagina);
								if(GUI.fimPaginacao){
									GUI.paginaAtual--;
									arquivoDeVendas.fecharArquivoParaLeitura();
									arquivoDeVendas.abrirArquivoParaLeitura();
									arquivoDeVendas.recuperarArquivoPaginacaoGUI2(qtdRegistroPorPagina);
								}
								arquivoDeVendas.fecharArquivoParaLeitura();
								GUI.campoDeRetornoPaginacao.setText(GUI.campoDeRetornoPaginacao.getText() + "\n   " + "P�gina: " + (GUI.paginaAtual+1)+"/"+GUI.totalPaginacao);
						}
					});
					
					long tempoInicial = Calendar.getInstance().getTimeInMillis();
					
					arquivoDeVendas.abrirArquivoParaLeitura();
					if(qtdRegistroPorPagina > 0){
						long quantidadeTotalDeLinhas = arquivoDeVendas.quantidadeDeLinhasDoArquivo();
						totalPaginacao = (int) (quantidadeTotalDeLinhas / qtdRegistroPorPagina);
						if(quantidadeTotalDeLinhas % qtdRegistroPorPagina  != 0) {
							totalPaginacao++;
						}
						arquivoDeVendas.recuperarArquivoPaginacaoGUI2(qtdRegistroPorPagina);
						arquivoDeVendas.fecharArquivoParaLeitura();
						GUI.campoDeRetornoPaginacao.setText(GUI.campoDeRetornoPaginacao.getText() + "\n   " + "P�gina: " + (GUI.paginaAtual+1)+"/"+GUI.totalPaginacao);
						JOptionPane.showOptionDialog(null, outScrollInformacoesDeRetorno, "Leitura - Pagina��o", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[] {btnAnterior, btnProximo}, null);
					}else{
						GUI.campoDeRetornoPaginacao.setText(GUI.campoDeRetornoPaginacao.getText() + "\n###########################################################################################################################");
						GUI.campoDeRetornoPaginacao.setText(GUI.campoDeRetornoPaginacao.getText() + "\nOp��o selecionada: Consultar base");
						GUI.campoDeRetornoPaginacao.setText(GUI.campoDeRetornoPaginacao.getText() + "\n\nRegistros: ");
						arquivoDeVendas.recuperarArquivoGUI();
						arquivoDeVendas.fecharArquivoParaLeitura();
						long tempoExecucao = Calendar.getInstance().getTimeInMillis() - tempoInicial;
						GUI.campoDeRetornoPaginacao.setText(GUI.campoDeRetornoPaginacao.getText() + "\n\nTempo de Execu��o: "+tempoExecucao+" milissegundos");
						GUI.campoDeRetornoPaginacao.setText(GUI.campoDeRetornoPaginacao.getText() + "\n###########################################################################################################################\n");
						JOptionPane.showOptionDialog(null, outScrollInformacoesDeRetorno, "Leitura - Total", JOptionPane.CLOSED_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
					}
				}
			}
		});

		JOptionPane.showOptionDialog(null, outScrollInformacoesGerais, "PRA - Vendas", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[] { btnCriaRegQtd, btnCriaRegTamanho, btnConsultaGeral }, btnCriaRegQtd);
		
	}
	
	
}
