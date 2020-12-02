package estradio.player;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/** Representa o slider que vai permitir ao utilizador classificar uma músca/artista/género.
 * Este slider assume valores entre 0 e 5.
 * 
 * NÃO ALTERAR ESTA CLASSE!!
 * NÃO ALTERAR ESTA CLASSE!!
 * NÃO ALTERAR ESTA CLASSE!!
 * 
 * @author F. Sergio Barbosa
 */
public class Slider5Niveis extends AbstractCellEditor
						   implements TableCellEditor, TableCellRenderer {
	/** Nº de Série  */
	private static final long serialVersionUID = 1L;

	private JSlider sliderEdit, sliderRend;

	public Slider5Niveis( ) {
		sliderEdit = new JSlider( 0, 5 );
		sliderEdit.addChangeListener( new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if( !sliderEdit.getValueIsAdjusting() ) {
					fireEditingStopped();
				}
			}
		});
		sliderRend = new JSlider( 0, 5 );
	}

	@Override
	public Object getCellEditorValue() {
		return sliderEdit.getValue();
	}

	@Override
	public Component getTableCellEditorComponent(JTable t, Object value, boolean sel, int row, int column) {
		sliderEdit.setValue( (Integer)value );
		return sliderEdit;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		sliderRend.setValue( (Integer)value );
		return sliderRend;
	}

 }
