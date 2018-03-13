import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class Pedido {
	
	public Pedido(int codigoPedido, Vendedor vendedor, Cliente cliente, Calendar dataPedido, List<Produtos> produtos) {

		float totalDaVenda = 0f;
		for (Produtos produto : produtos) {
			totalDaVenda += produto.getValorProduto();
		}

		File file = new File("vendas.txt");
		try {
			String dataDoPedido = new SimpleDateFormat("dd/MM/yyyy").format(dataPedido.getTime());
			
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter writer = new FileWriter(file, true);
			writer.write("Pedido: " + codigoPedido // PENSAR EM UMA LÓGICA CORRETA
				+"; Cliente: " + cliente.getCodigoCliente()
				+"; Vendedor: " + vendedor.getCodigoVendedor()
				+"; Data da venda: " + dataDoPedido
				+"; Valor da venda: " + totalDaVenda
				+"; Produtos: " + produtos.toString());
			writer.write("\n");
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}