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
import estradio.server.Artista;
import estradio.server.ESTRadioServer;
import estradio.server.Genero;
import estradio.server.Utilizador;

/** Apresenta uma listagem com os artistas presentes no sistema,
 * sendo que, se o utilizador lhes atribui uma classificação, aparece a classificação atribuída 
 * @author F. Sergio Barbosa e alunos
 */
public class ListaArtistas extends JDialog {

	/** Nº de Série  */
	private static final long serialVersionUID = 1L;

	/** variáveis para os elementos gráficos, NÃO ALTERAR!*/
	private TableRowSorter<TableModel> sorter;
	private TableModel tableModel;
	private MyTableModlListener tableListener;

	private Utilizador utilizador;
	private ArrayList<Artista> artistasAListar = new ArrayList<Artista>();
	private HashMap<Artista,Integer> artistasClassificados = new HashMap<Artista,Integer>();

	/** Cria uma lista de Artistas. Pode ser necessário alterar este construtor!!
	 * @param owner  janela que deu origem a esta.
	 * @param titulo título a aparecer nesta janela de diálogo
	 * @param server 
	 * @param s
	 * @param user
	 * @param dados
	 */
	public ListaArtistas( JFrame owner, String titulo, ESTRadioServer server, Utilizador u ) {
		super( owner, titulo );
		utilizador = u;

		//Vai buscar os artistaClassificados e coloca-os na lista a mostrar
		artistasClassificados = u.getArtistasClassificados();
		for(Artista a: artistasClassificados.keySet())
			artistasAListar.add(a);

		//Coloca os restantes na lista
		ArrayList<Artista> artistas = server.getArtistas();
		for(Artista a: artistas)
			if(!artistasAListar.contains(a))
				artistasAListar.add(a);	

		initialize( artistasAListar.size() ); // TODO colocar aqui o nº de artistas a listar
		setDadosTabela( );
		setTableListener();
		setResizable( false );
		setModalityType( Dialog.ModalityType.DOCUMENT_MODAL );
	}

	/** Método que vai colocar os valores a serem apresentados na tabela
	 * Os parâmetros poderão ser alterados. 
	 * @param dados
	 */
	private void setDadosTabela(  ) {
		for( int i=0; i < artistasAListar.size(); i++ ) {
			// TODO colocar os dados nas variáveis
			Artista aListar = artistasAListar.get(i);
			String nomeArtista = aListar.getNome();
			int nivel = artistasClassificados.getOrDefault(aListar, 0);

			// NÃO ALTERAR ESTE CÓDIGO
			tableModel.setValueAt( nomeArtista, i, 0);
			tableModel.setValueAt( nivel, i, 1);
		}
	}

	/** Método chamado quando o utilizador altera a classificação dada a um artista
	 * @param linha o índice do artista que foi alterado
	 * @param novaClass a nova classificação atríbuida
	 */
	private void alterouNivelTabela( int linha, int novaClass ) {
		try {
			// TODO alterar a classficação do artista para o valor definido pelo utilizador
			Artista classificado =  artistasAListar.get(linha);

			if (utilizador.getArtistasClassificados().size() >= utilizador.getLicenca().getPrefArtistas() ) {
				throw new LicencaNaoSuportaOperacaoExcpetion();
			}

			if(novaClass > 0)
				utilizador.addArtistasClassificados(classificado, novaClass);
			else
				utilizador.removeArtistasClassificados(classificado);

			System.out.println("Alterou a classificação do artista " + classificado.getNome() + " para " + novaClass);
		} catch( LicencaNaoSuportaOperacaoExcpetion e ) {
			JOptionPane.showMessageDialog(this, "A sua licença não permite fazer essa operação! Faça upgrade!",
					"Operação não suportada", JOptionPane.ERROR_MESSAGE);
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
		p.add( setupListaArtistas( nLinhas ), BorderLayout.CENTER );
		setContentPane( p );
		pack();
	}

	/** prepara a tabela dos artistas
	 * @param nLinhas quanto linhas tem a tabela 
	 * @return a tabela preenchida
	 */
	private Component setupListaArtistas( int nLinhas ) {
		String colunas[] = { "Artista", "Class." };
		tableModel = new DefaultTableModel( colunas, nLinhas );
		JTable listaArtistas = new JTable( tableModel );
		TableColumn col = listaArtistas.getColumnModel().getColumn(1);
		sorter = new TableRowSorter<TableModel>( listaArtistas.getModel() );
		listaArtistas.setRowSorter(sorter);
		col.setMaxWidth( 100 );
		col.setMinWidth( 100 );
		Slider5Niveis editCell = new Slider5Niveis(); 
		col.setCellEditor( editCell );
		col.setCellRenderer( editCell );
		JScrollPane scroll = new JScrollPane( listaArtistas );
		return scroll;
	}

	/** prepara os botões com as letras para filtrar os artistas */
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
