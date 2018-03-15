import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class GUI2 {

	static JTextArea campoDeInformacoesGerais = new JTextArea(35, 90);

	static JTextArea campoDeEntradaDoUsuario = new JTextArea(1, 20);

	static GerenciadorDeArquivo arquivoDeVendas;
	
	public static void main(String[] args) {
		final List<Cliente> clientes = Utils.criaClientes();
		final List<Produtos> produtos = Utils.criaProdutos();
		final List<Vendedor> vendedor = Utils.criaVendedor();
		
		arquivoDeVendas = new GerenciadorDeArquivo();

		JScrollPane outScrollInformacoesGerais = new JScrollPane(campoDeInformacoesGerais);
		campoDeInformacoesGerais.setEditable(false);
		final JScrollPane outScrollEntradaDoUsuario = new JScrollPane(campoDeEntradaDoUsuario);

		JButton btnCriaRegQtd = new JButton("Criar pedidos por quantidade");
		btnCriaRegQtd.setToolTipText("Cria os pedidos de acordo com a quantidade digitada");
		btnCriaRegQtd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Integer opt = JOptionPane.showOptionDialog(null, outScrollEntradaDoUsuario, "Digite a quantidade de registros", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
				if (opt == 0) {
					int qtdVendas = Integer.parseInt(campoDeEntradaDoUsuario.getText().trim());
					long tempoInicial = Calendar.getInstance().getTimeInMillis();
					arquivoDeVendas.criarEAbrirArquivoParaEscrita();
					int nPedido = 1;
					while (nPedido <= qtdVendas) {
						Utils.geraUmPedido(nPedido, clientes, produtos, vendedor, arquivoDeVendas);
						nPedido++;
					}
					arquivoDeVendas.fecharArquivoParaEscrita();
					long tempoExecucao = Calendar.getInstance().getTimeInMillis() - tempoInicial;
					GUI2.campoDeInformacoesGerais.setText(GUI2.campoDeInformacoesGerais.getText() + "\n###########################################################################################################################");
					GUI2.campoDeInformacoesGerais.setText(GUI2.campoDeInformacoesGerais.getText() + "\nOpção selecionada: Criação de arquivos por quantidade");
					GUI2.campoDeInformacoesGerais.setText(GUI2.campoDeInformacoesGerais.getText() + "\nQuantidade selecionada: "+qtdVendas);
					GUI2.campoDeInformacoesGerais.setText(GUI2.campoDeInformacoesGerais.getText() + "\nTempo de execução: "+tempoExecucao+" milissegundos");
					GUI2.campoDeInformacoesGerais.setText(GUI2.campoDeInformacoesGerais.getText() + "\n###########################################################################################################################\n");
				}
				campoDeEntradaDoUsuario.setText(null);
			}
		});

		JButton btnCriaRegTamanho = new JButton("Criar pedidos por tamanho (KB)");
		btnCriaRegTamanho.setToolTipText("Cria os pedidos de acordo com o tamanho digitado (KB)");
		btnCriaRegTamanho.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Integer opt = JOptionPane.showOptionDialog(null, outScrollEntradaDoUsuario, "Digite o tamanho desejado (KB)", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
				if (opt == 0) {
					long tamSolicitado;

					tamSolicitado = Integer.valueOf(campoDeEntradaDoUsuario.getText().trim()) * 1024;

					long tempoInicial = Calendar.getInstance().getTimeInMillis();
					arquivoDeVendas.criarEAbrirArquivoParaEscrita();
					int nPedido = 1;
					while (arquivoDeVendas.tamanhoDoArquivo() < tamSolicitado) {
						Utils.geraUmPedido(nPedido, clientes, produtos, vendedor, arquivoDeVendas);
						nPedido++;
					}
					arquivoDeVendas.fecharArquivoParaEscrita();
					long tempoExecucao = Calendar.getInstance().getTimeInMillis() - tempoInicial;
					GUI2.campoDeInformacoesGerais.setText(GUI2.campoDeInformacoesGerais.getText() + "\n###########################################################################################################################");
					GUI2.campoDeInformacoesGerais.setText(GUI2.campoDeInformacoesGerais.getText() + "\nOpção selecionada: Criação de arquivos por tamanho (KB)");
					GUI2.campoDeInformacoesGerais.setText(GUI2.campoDeInformacoesGerais.getText() + "\nTamanho de arquivo selecionado: "+tamSolicitado+ " KB");
					GUI2.campoDeInformacoesGerais.setText(GUI2.campoDeInformacoesGerais.getText() + "\nTamanho de arquivo gerado: " + (arquivoDeVendas.tamanhoDoArquivo() / 1024) + " KB");
					GUI2.campoDeInformacoesGerais.setText(GUI2.campoDeInformacoesGerais.getText() + "\nTempo de execução: "+tempoExecucao+" milissegundos");
					GUI2.campoDeInformacoesGerais.setText(GUI2.campoDeInformacoesGerais.getText() + "\n###########################################################################################################################\n");
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
				GUI2.campoDeInformacoesGerais.setText(GUI2.campoDeInformacoesGerais.getText() + "\n###########################################################################################################################");
				GUI2.campoDeInformacoesGerais.setText(GUI2.campoDeInformacoesGerais.getText() + "\nOpção selecionada: Consultar base");
				GUI2.campoDeInformacoesGerais.setText(GUI2.campoDeInformacoesGerais.getText() + "\n\nRegistros: ");
				arquivoDeVendas.abrirArquivoParaLeitura();
				arquivoDeVendas.recuperarArquivoGUI();
				arquivoDeVendas.fecharArquivoParaLeitura();
				long tempoExecucao = Calendar.getInstance().getTimeInMillis() - tempoInicial;
				GUI2.campoDeInformacoesGerais.setText(GUI2.campoDeInformacoesGerais.getText() + "\n\nTempo de Execução: "+tempoExecucao+" milissegundos");
				GUI2.campoDeInformacoesGerais.setText(GUI2.campoDeInformacoesGerais.getText() + "\n###########################################################################################################################\n");
			}
		});

		JOptionPane.showOptionDialog(null, outScrollInformacoesGerais, "PRA - Vendas", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[] { btnCriaRegQtd, btnCriaRegTamanho, btnConsultaGeral }, btnCriaRegQtd);
	}
	
	
}
