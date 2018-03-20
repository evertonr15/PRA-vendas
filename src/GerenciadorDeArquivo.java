import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Stream;

public class GerenciadorDeArquivo {

	private String nomeDoArquivo = "PRA-vendas.txt";

	private File arquivo = null;

	FileWriter fWriter = null;

	BufferedWriter buffWriter = null;

	FileReader fReader = null;

	BufferedReader buffReader = null;

	public void criarEAbrirArquivoParaEscrita() {
		try {
			if (arquivo == null) {
				arquivo = new File(nomeDoArquivo);
			}

			if (!arquivo.createNewFile()) {
				arquivo.delete();
				arquivo.createNewFile();
			}
			fWriter = new FileWriter(arquivo, true);
			buffWriter = new BufferedWriter(fWriter);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void fecharArquivoParaEscrita() {
		try {
			if (buffWriter != null) {
				buffWriter.close();
			}
			if (fWriter != null) {
				fWriter.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void salvaPedido(Pedido pedido) {
		try {
			if (buffWriter != null) {
				String informacoesDoPedido = "" + pedido.getCodigoPedido();
				informacoesDoPedido += ";" + pedido.getCliente().getCodigoCliente();
				informacoesDoPedido += ";" + pedido.getVendedor().getCodigoVendedor();
				informacoesDoPedido += ";" + pedido.getDataPedido();
				informacoesDoPedido += ";" + pedido.getTotalDaVenda();
				informacoesDoPedido += ";" + pedido.getProdutos().toString();

				buffWriter.write(informacoesDoPedido);
				buffWriter.newLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void salvaPedidoPaginacao(List<Pedido> pedidos) {
		try {
			if (buffWriter != null) {
				String informacoesDoPedido = "";
				for (Pedido pedido : pedidos) {
					informacoesDoPedido = "" + pedido.getCodigoPedido();
					informacoesDoPedido += ";" + pedido.getCliente().getCodigoCliente();
					informacoesDoPedido += ";" + pedido.getVendedor().getCodigoVendedor();
					informacoesDoPedido += ";" + pedido.getDataPedido();
					informacoesDoPedido += ";" + pedido.getTotalDaVenda();
					informacoesDoPedido += ";" + pedido.getProdutos().toString();
					buffWriter.write(informacoesDoPedido);
					buffWriter.newLine();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void abrirArquivoParaLeitura() {
		try {
			fReader = new FileReader(nomeDoArquivo);
			buffReader = new BufferedReader(fReader);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void fecharArquivoParaLeitura() {
		try {
			if (fReader != null) {
				fReader.close();
			}
			if (buffReader != null) {
				buffReader.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void recuperarArquivo() {
		String linhaAtual = null;
		try {
			while ((linhaAtual = buffReader.readLine()) != null) {
				System.out.print(linhaAtual);
				System.out.print("\n");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/*
	 * registroDoPedido:
	 * 
	 * 0-codigoPedido 1-cliente.getCodigoCliente() 2-vendedor.getCodigoVendedor()
	 * 3-dataDoPedido 4-totalDaVenda 5-produtos.toString()
	 */
	public void recuperarArquivoGUI() {
		String linhaAtual = null;
		DecimalFormat decimalFormater = new DecimalFormat("#,###.00");
		try {
			while ((linhaAtual = buffReader.readLine()) != null) {
				String[] registroDoPedido = linhaAtual.split(";");

				GUI.campoDeRetornoPaginacao.setText(
						GUI.campoDeRetornoPaginacao.getText() + "\n   " + "Código do pedido: " + registroDoPedido[0]);
				GUI.campoDeRetornoPaginacao.setText(GUI.campoDeRetornoPaginacao.getText() + "\n   "
						+ "Código do vendedor: " + registroDoPedido[2]);
				GUI.campoDeRetornoPaginacao.setText(
						GUI.campoDeRetornoPaginacao.getText() + "\n   " + "Data do pedido: " + registroDoPedido[3]);
				GUI.campoDeRetornoPaginacao.setText(
						GUI.campoDeRetornoPaginacao.getText() + "\n   " + "Código do Cliente: " + registroDoPedido[1]);

				GUI.campoDeRetornoPaginacao.setText(GUI.campoDeRetornoPaginacao.getText() + "\n   " + "Produtos: ");
				String produtosToString = registroDoPedido[5].substring(1, registroDoPedido[5].length() - 1);
				String[] produtos = produtosToString.split("/");
				for (Object produto : produtos) {
					if (!produto.toString().equals("")) {
						String[] produtosSplit = ((String) produto).split(":");
						GUI.campoDeRetornoPaginacao.setText(GUI.campoDeRetornoPaginacao.getText() + "\n   "
								+ "    Código do produto: " + produtosSplit[0] + " Valor do produto: R$ "
								+ decimalFormater.format(Float
										.valueOf(produtosSplit[1].trim().substring(0, produtosSplit[1].length() - 2))));
					}
				}
				GUI.campoDeRetornoPaginacao.setText(GUI.campoDeRetornoPaginacao.getText() + "\n   "
						+ "Total do pedido: R$ " + decimalFormater.format(Float.valueOf(registroDoPedido[4])));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/*
	 * registroDoPedido:
	 * 
	 * 0-codigoPedido 
	 * 1-cliente.getCodigoCliente() 
	 * 2-vendedor.getCodigoVendedor()
	 * 3-dataDoPedido 
	 * 4-totalDaVenda 
	 * 5-produtos.toString()
	 */
	public void recuperarArquivoPaginacaoGUI2(int qtdRegistroPagina) {
		String informacaoLinhaAtual = null;
		DecimalFormat decimalFormater = new DecimalFormat("#,###.00");
		GUI.fimPaginacao = true;
		try {
			int posicaoLinhaDeOrigem = qtdRegistroPagina * GUI.paginaAtual;
			int posicaoLinhaAtual = 0;
			while (posicaoLinhaAtual < posicaoLinhaDeOrigem && (informacaoLinhaAtual = buffReader.readLine()) != null) {
				posicaoLinhaAtual++;
			}
			posicaoLinhaAtual = 0;
			while ((informacaoLinhaAtual = buffReader.readLine()) != null && posicaoLinhaAtual < qtdRegistroPagina) {
				GUI.fimPaginacao = false;
				String[] registroDoPedido = informacaoLinhaAtual.split(";");

				GUI.campoDeRetornoPaginacao.setText(
						GUI.campoDeRetornoPaginacao.getText() + "\n   " + "Código do pedido: " + registroDoPedido[0]);
				GUI.campoDeRetornoPaginacao.setText(
						GUI.campoDeRetornoPaginacao.getText() + "\n   " + "Código do vendedor: " + registroDoPedido[2]);
				GUI.campoDeRetornoPaginacao.setText(
						GUI.campoDeRetornoPaginacao.getText() + "\n   " + "Data do pedido: " + registroDoPedido[3]);
				GUI.campoDeRetornoPaginacao.setText(
						GUI.campoDeRetornoPaginacao.getText() + "\n   " + "Código do Cliente: " + registroDoPedido[1]);

				GUI.campoDeRetornoPaginacao.setText(GUI.campoDeRetornoPaginacao.getText() + "\n   " + "Produtos: ");
				String produtosToString = registroDoPedido[5].substring(1, registroDoPedido[5].length() - 1);
				String[] produtos = produtosToString.split("/");
				for (Object produto : produtos) {
					if (!produto.toString().equals("")) {
						String[] produtosSplit = ((String) produto).split(":");
						GUI.campoDeRetornoPaginacao.setText(GUI.campoDeRetornoPaginacao.getText() + "\n   "
								+ "    Código do produto: " + produtosSplit[0] + " Valor do produto: R$ "
								+ decimalFormater.format(Float
										.valueOf(produtosSplit[1].trim().substring(0, produtosSplit[1].length() - 2))));
					}
				}
				GUI.campoDeRetornoPaginacao.setText(GUI.campoDeRetornoPaginacao.getText() + "\n   "
						+ "Total do pedido: R$ " + decimalFormater.format(Float.valueOf(registroDoPedido[4])) + "\n");
				posicaoLinhaAtual++;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public long tamanhoDoArquivo() {
		return arquivo != null ? arquivo.length() : 0;
	}

	public long quantidadeDeLinhasDoArquivo() {
		long numOfLines = 0;
		if (arquivo != null) {
			try (Stream<String> lines = Files.lines(arquivo.toPath(), Charset.defaultCharset())) {
				numOfLines = lines.count();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return numOfLines;
	}
}