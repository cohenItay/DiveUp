package res;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JTextPane;
import javax.swing.UIManager;

public class DTextPane extends JTextPane{

	public DTextPane()
	{
		setBackground(UIManager.getColor("JFrame"));
		setFont(new Font("Tahoma",Font.BOLD,22));
		setSize(UIConstants.miniScreenWidth,UIConstants.miniScreenHeight);
		setPreferredSize(new Dimension(UIConstants.messageWidth,UIConstants.messageHeight));
		setSize(UIConstants.messageWidth,UIConstants.messageHeight);
		
	}
}
