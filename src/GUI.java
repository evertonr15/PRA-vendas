import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GUI {

	static JTextArea campoDeInformacoesGerais = new JTextArea(35, 90);
	
	static JTextField campoDeEntradaDoUsuario = new JTextField();
	
	static JTextField campoDeEntradaDoUsuarioPaginacao = new JTextField();

	static GerenciadorDeArquivo arquivoDeVendas;
	
	public static void main(String[] args) {
		final List<Cliente> clientes = Utils.criaClientes();
		final List<Produtos> produtos = Utils.criaProdutos();
		final List<Vendedor> vendedor = Utils.criaVendedor();
		
		arquivoDeVendas = new GerenciadorDeArquivo();

		JScrollPane outScrollInformacoesGerais = new JScrollPane(campoDeInformacoesGerais);
		campoDeInformacoesGerais.setEditable(false);

		JButton btnCriaRegQtd = new JButton("Criar pedidos por quantidade");
		btnCriaRegQtd.setToolTipText("Cria os pedidos de acordo com a quantidade digitada");
		btnCriaRegQtd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				campoDeEntradaDoUsuarioPaginacao.setText("0");
				List<Pedido> paginaPedidos = new ArrayList<>();
				Object[] entradaDoUsuário = {
					    "Quantidade de registros por página (0 - Sem paginação): ", campoDeEntradaDoUsuarioPaginacao,
					    "Quantidade de registros a serem criados: ", campoDeEntradaDoUsuario
				};

				Integer opt = JOptionPane.showOptionDialog(null, entradaDoUsuário, "Criação por quantidade de registros", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
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
					GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\nOpção selecionada: Criação de arquivos por quantidade");
					GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\nPaginação selecionada: "+qtdRegistroPorPagina);
					GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\nQuantidade de registros selecionada: "+qtdRegistros);
					GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\nTempo de execução: "+tempoExecucao+" milissegundos");
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
				Object[] entradaDoUsuário = {
					    "Quantidade de registros por página (0 - Sem paginação): ", campoDeEntradaDoUsuarioPaginacao,
					    "Tamanho do arquivo: ", campoDeEntradaDoUsuario
				};
				
				Integer opt = JOptionPane.showOptionDialog(null, entradaDoUsuário, "Digite o tamanho desejado (KB)", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
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
							Utils.geraUmPedido(nPedido, clientes, produtos, vendedor);
							nPedido++;
						}
					}
					
					arquivoDeVendas.fecharArquivoParaEscrita();
					long tempoExecucao = Calendar.getInstance().getTimeInMillis() - tempoInicial;
					GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\n###########################################################################################################################");
					GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\nOpção selecionada: Criação de arquivos por tamanho (KB)");
					GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\nTamanho de arquivo selecionado: "+tamSolicitado+ " KB");
					GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\nTamanho de arquivo gerado: " + (arquivoDeVendas.tamanhoDoArquivo() / 1024) + " KB");
					GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\nTempo de execução: "+tempoExecucao+" milissegundos");
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
				long tempoInicial = Calendar.getInstance().getTimeInMillis();
				GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\n###########################################################################################################################");
				GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\nOpção selecionada: Consultar base");
				GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\n\nRegistros: ");
				arquivoDeVendas.abrirArquivoParaLeitura();
				arquivoDeVendas.recuperarArquivoGUI();
				arquivoDeVendas.fecharArquivoParaLeitura();
				long tempoExecucao = Calendar.getInstance().getTimeInMillis() - tempoInicial;
				GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\n\nTempo de Execução: "+tempoExecucao+" milissegundos");
				GUI.campoDeInformacoesGerais.setText(GUI.campoDeInformacoesGerais.getText() + "\n###########################################################################################################################\n");
			}
		});

		JOptionPane.showOptionDialog(null, outScrollInformacoesGerais, "PRA - Vendas", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[] { btnCriaRegQtd, btnCriaRegTamanho, btnConsultaGeral }, btnCriaRegQtd);
	}
	
	
}
