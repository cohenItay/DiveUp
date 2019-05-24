package res;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

public class DTable extends JTable {

	public JTable designTable(JTable table)
	{
		table.setForeground(UIConstants.SELECTED_BTN);
		table.setFont(new Font("Tahoma", Font.BOLD, 18));
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		((DefaultTableCellRenderer)table.getDefaultRenderer(String.class)).setHorizontalAlignment(SwingConstants.RIGHT);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setReorderingAllowed(false);
		table.setRowSelectionAllowed(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setGridColor(UIConstants.BAR_DARK);
		table.setFillsViewportHeight(true);
		JTableHeader header = table.getTableHeader();
	     header.setBackground(UIConstants.SELECTED_BTN);
	     header.setForeground(Color.white);
	     header.setFont(new Font("Tahoma", Font.BOLD, 20));
		
		return table;
	}
}
