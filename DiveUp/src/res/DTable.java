package res;

import java.awt.Color;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.JTableHeader;

public class DTable extends JTable {

	public JTable designTable(JTable table)
	{
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setReorderingAllowed(false);
		table.setRowSelectionAllowed(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setGridColor(UIConstants.BAR_DARK);
		table.setFillsViewportHeight(true);
		JTableHeader header = table.getTableHeader();
	     header.setBackground(UIConstants.SELECTED_BTN);
	     header.setForeground(Color.white);
		
		return table;
	}
}
