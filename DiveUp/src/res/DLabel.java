package res;

import javax.swing.*;
import java.awt.*;

public class DLabel extends JLabel
{
    private Font font;
    private String text;
    private DTooltip toolTip;

    public DLabel(String text){
       
        this.text=text;
        setForeground(UIConstants.TXT_HIGH_CONTRAST_DEFUALT);
        setText(text);
        setHorizontalTextPosition(JLabel.CENTER);
        //setForeground(Color.red);
    }

    

    public void setToolTip(String text, int lenLimit){
        if(text.length()>lenLimit){
            setText(text.substring(0,lenLimit-3)+"...");
            this.setToolTipText(text);
        }
    }

    @Override
    public JToolTip createToolTip() {
        if (toolTip == null) {

            toolTip = new DTooltip(this.text);
            toolTip.setComponent(this);
        }
        return toolTip;
    }

    public void setPadding(int top,int left,int bottom,int right){
        setBorder(BorderFactory.createEmptyBorder(top,left,bottom,right));
    }


}
