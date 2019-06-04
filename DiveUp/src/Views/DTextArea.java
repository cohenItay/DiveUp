package Views;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;
import javax.swing.JToolTip;

public class DTextArea extends JTextArea
{
    private Font font;
    private String text;
    private DTooltip toolTip;
    private int lenLimit =20;

    public DTextArea(String text){
        super(text);

        this.text=text;
        //this.font=new Font("Honeywell Sans Web",Font.PLAIN,13);
        setBackground(UIConstants.HOVER_SELECTED_MAIN_BACKGROUND);
        setForeground(UIConstants.TEXT_COLOR);
        setBorder(BorderFactory.createEmptyBorder(15,5,5,5));
        //setFont(this.font);
        setEditable(false);

        if(getText().length()>lenLimit){
            setToolTipText(getText());
        }

        //setLineWrap(true);
        //setWrapStyleWord(true);
    }

    private void setToolTip(int lenLimit){
        if(text.length()>lenLimit){
            setText(text.substring(0,lenLimit-3)+"...");
            this.setToolTipText(text);
        }
    }
    public void setToolTip(String text, int lenLimit){
        if(text.length()>lenLimit){
            setText(text.substring(0,lenLimit-3)+"...");
            this.setToolTipText(text);
        }
    }

    @Override
    public JToolTip createToolTip() {
        toolTip = new DTooltip(this.text);
        toolTip.setComponent(this);
        toolTip.validate();
        toolTip.repaint();
        return toolTip;
    }


    @Override
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

}
