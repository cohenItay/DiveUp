package Views;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

public class DTable extends JTable {

	   public enum Mode{
	        PRIMARY,
	        SECONDERY,
	        INLINE,
	        INLINE_SECONDRY
	}
	   private Mode mode;
	
	
	public JTable designTable(JTable table,Mode mode)
	{
		this.mode = mode;
		table.setForeground(UIConstants.BORDER_DARK);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		((DefaultTableCellRenderer)table.getDefaultRenderer(String.class)).setHorizontalAlignment(SwingConstants.RIGHT);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setReorderingAllowed(false);
		table.setRowSelectionAllowed(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setGridColor(UIConstants.BAR_DARK);
		table.setFillsViewportHeight(true);
		table.setRowHeight(30);
		JTableHeader header = table.getTableHeader();
	     header.setBackground(UIConstants.SELECTED_BTN);
	     header.setForeground(Color.white);
	     switch(mode) {
	     case PRIMARY:
	    	 table.setFont(new Font("Tahoma", Font.BOLD, 20));
		     header.setFont(new Font("Tahoma", Font.BOLD, 24));
		     break;
	     case SECONDERY:
	    	 table.setFont(new Font("Tahoma", Font.BOLD, 14));
		     header.setFont(new Font("Tahoma", Font.BOLD, 18));
			
	     }
	     
		return table;
	}
}
