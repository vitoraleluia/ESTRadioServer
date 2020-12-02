package estradio.player;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.RowFilter;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import estradio.licenca.LicencaNaoSuportaOperacaoExcpetion;
import estradio.server.ESTRadioServer;
import estradio.server.Genero;
import estradio.server.Utilizador;

/** Apresenta uma listagem com os géneros presentes no sistema,
 * sendo que, se o utilizador lhes atribui uma classificação, aparece a classificação atribuída 
 * @author F. Sergio Barbosa e alunos
 */
public class ListaGeneros extends JDialog {

	/** Nº de Série  */
	private static final long serialVersionUID = 1L;

	/** variáveis para os elementos gráficos, NÃO ALTERAR!*/
	private TableRowSorter<TableModel> sorter;
	private TableModel tableModel;
	private MyTableModlListener tableListener;

	private Utilizador utilizador;
	private ArrayList<Genero> generosAListar = new ArrayList<Genero>();
	private HashMap<Genero,Integer> generosClassificados = new HashMap<Genero,Integer>();

	/** Cria uma lista de Géneros. Pode ser necessário alterar este construtor!!
	 * @param owner  janela que deu origem a esta.
	 * @param titulo título a aparecer nesta janela de diálogo
	 * @param server 
	 */
	public ListaGeneros( JFrame owner, String titulo, ESTRadioServer server, Utilizador u) {
		super( owner, titulo );
		utilizador = u;

		//Vai buscar os generos Classificados e adiciona-os à lista para listar
		generosClassificados = u.getGenerosClassificados();
		for (Genero g : generosClassificados.keySet()) {
			generosAListar.add(g);
		}

		//Vai buscar os restantes generos ao servidor e adiciona à lista
		ArrayList<Genero> generos = server.getGeneros();
		for (Genero g : generos) {
			if(!generosAListar.contains(g))
				generosAListar.add(g);
		}

		initialize( generosAListar.size() ); // TODO colocar aqui o nº de géneros a listar
		setDadosTabela( );
		setTableListener();
		setResizable( false );
		setModalityType( Dialog.ModalityType.DOCUMENT_MODAL );
	}

	/** Método que vai colocar os valores a serem apresentados na tabela
	 * Os parâmetros poderão ser alterados. 
	 */
	private void setDadosTabela(  ) {
		for( int i=0; i < generosAListar.size(); i++ ) {
			// TODO colocar os dados nas variáveis
			Genero atual = generosAListar.get(i);
			String nomeGenero = atual.getNome();
			int nivel =  generosClassificados.getOrDefault(atual, 0);

			// NÃO ALTERAR ESTE CÓDIGO
			tableModel.setValueAt( nomeGenero, i, 0);
			tableModel.setValueAt( nivel, i, 1);
		}
	}

	/** Método chamado quando o utilizador altera a classificação dada a um género
	 * @param linha o índice do género que foi alterado
	 * @param novaClass a nova classificação atríbuida
	 */
	private void alterouNivelTabela( int linha, int novaClass ) {

		try {
			// TODO alterar a classificação dada ao género pelo utilizador
			Genero classificar = generosAListar.get(linha);

			if(novaClass > 0) {		
				if (utilizador.getGenerosClassificados().size() >= utilizador.getLicenca().getPrefGeneros() ) 
					throw new LicencaNaoSuportaOperacaoExcpetion();
				else
					utilizador.addGenerosClassificados(classificar, novaClass);
			}else		
				utilizador.removeGenerosClassificados(classificar);

			System.out.println(utilizador.getGenerosClassificados().size());
			System.out.println("Alterou a classificação do género " + generosAListar.get(linha).getNome() + " para " + novaClass);
		} catch( LicencaNaoSuportaOperacaoExcpetion e ) {
			JOptionPane.showMessageDialog(this, "A sua licença não permite fazer essa operação! Faça upgrade!", "Operação não suportada", JOptionPane.ERROR_MESSAGE);
			reporNivelTabela( linha, 0);
		}
	}

	/** método chamado quando se pretende repor o nível da tabela 
	 * @param linha linha a repor
	 * @param nivel nível a repor
	 */
	private void reporNivelTabela( int linha, int nivel ) {
		tableModel.removeTableModelListener( tableListener );
		tableModel.setValueAt( 0, linha, 1);		
		tableModel.addTableModelListener( tableListener );
	}


	// A PARTIR DAQUI NÃO É NECESSÁRIO ALTERAR QUALQUER CÓDIGO! 
	// A PARTIR DAQUI NÃO É NECESSÁRIO ALTERAR QUALQUER CÓDIGO!
	// A PARTIR DAQUI NÃO É NECESSÁRIO ALTERAR QUALQUER CÓDIGO! 
	// A PARTIR DAQUI NÃO É NECESSÁRIO ALTERAR QUALQUER CÓDIGO! 
	/** inicializa a janela, adicionando os botões e a tabela */
	private void initialize( int nLinhas ) {
		JPanel p = new JPanel( new BorderLayout() );
		p.add( setupBotoesLetras(), BorderLayout.NORTH );
		p.add( setupListaGeneros( nLinhas ), BorderLayout.CENTER );
		setContentPane( p );
		pack();
	}

	/** prepara a tabela dos géneros
	 * @param nLinhas quanto linhas tem a tabela 
	 * @return a tabela preenchida
	 */
	private Component setupListaGeneros( int nLinhas ) {
		String colunas[] = { "Género", "Nível" };
		tableModel = new DefaultTableModel( colunas, nLinhas );
		JTable listaGeneros = new JTable( tableModel );
		TableColumn col = listaGeneros.getColumnModel().getColumn(1);
		sorter = new TableRowSorter<TableModel>( listaGeneros.getModel() );
		listaGeneros.setRowSorter(sorter);
		col.setMaxWidth( 100 );
		col.setMinWidth( 100 );
		Slider5Niveis editCell = new Slider5Niveis(); 
		col.setCellEditor( editCell );
		col.setCellRenderer( editCell );
		JScrollPane scroll = new JScrollPane( listaGeneros );
		return scroll;
	}

	/** prepara os botões com as letras para filtrar os géneros */
	private JPanel setupBotoesLetras() {
		JPanel btPanel = new JPanel( new FlowLayout(FlowLayout.LEFT, 3, 0) );

		ButtonGroup btLetras = new ButtonGroup();

		JToggleButton btTodos = new JToggleButton("Todos");
		btTodos.setBorder( null );
		btTodos.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sorter.setRowFilter( null );
			}			
		});
		btLetras.add( btTodos );
		btPanel.add( btTodos );

		ActionListener botaoHandler = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sorter.setRowFilter(RowFilter.regexFilter("^" + e.getActionCommand(), 0 ) );
			}
		};
		for( char ch = 'A'; ch <= 'Z'; ch++ ) {
			JToggleButton bt = new JToggleButton( " " + ch + " " );
			bt.setActionCommand( "" + ch );
			bt.addActionListener( botaoHandler );
			bt.setBorder( BorderFactory.createEmptyBorder(2, 2, 2, 2) );
			btLetras.add( bt );
			btPanel.add( bt );
		}
		return btPanel;
	}

	private void setTableListener() {
		tableListener = new MyTableModlListener();
		tableModel.addTableModelListener( tableListener );
	}

	class MyTableModlListener implements TableModelListener {
		@Override
		public void tableChanged(TableModelEvent e) {
			if( e.getColumn() == 1 )
				alterouNivelTabela( e.getFirstRow(), (Integer)tableModel.getValueAt(e.getFirstRow(), 1) );
		}
	}

}
