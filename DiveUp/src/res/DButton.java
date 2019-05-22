package res;

import javax.swing.*;
import java.awt.*;

public class DButton extends JButton{

    public enum Mode{
        PRIMARY,
        SECONDERY,
        INLINE,
        INLINE_SECONDRY
}

    private Mode mode;
    //todo add hand cursor

    public DButton(String text, Mode mode)
    {
      
        setFocusPainted(false);
        setText(text);
        this.mode=mode;
        setStyleByMode();
    }

    private void setStyleByMode(){
        switch (mode)
        {
            case INLINE:
                setBackground(UIConstants.INVERTED);
                setForeground(UIConstants.HOVER_SELECTED_MAIN_BACKGROUND);
                break;
            case INLINE_SECONDRY:
                setForeground(UIConstants.INVERTED);
                setBorder(BorderFactory.createLineBorder(UIConstants.INVERTED));
                setContentAreaFilled(false);
                break;
            case PRIMARY:
                setBackground(UIConstants.SELECTED_BTN);
                setForeground(UIConstants.BTN_PRIMARY_FONT_DEFUALT);
                setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
                break;
            case SECONDERY:
                setForeground(UIConstants.SELECTED_BTN);
                setBorder(BorderFactory.createLineBorder(UIConstants.SELECTED_BTN));
                setContentAreaFilled(false);
                break;
        }
    }

    @Override
    public void setEnabled(boolean b)
    {
        setForeground(UIConstants.TXT_BASE_DARK);
        super.setEnabled(b);

    }

    public void setDisabled(boolean status){
        if(status){

            setEnabled(false);

            if(mode == Mode.SECONDERY || mode == Mode.INLINE_SECONDRY){
                setBorder(BorderFactory.createLineBorder(UIConstants.DESABLED_BTN));
            }
            else{
                setBackground(UIConstants.BTN_INLINE_DESABLED_DEFUALT);
            }
            //setText("<html><font color = rgb(0,0,0)>"+getText()+"</font></html>");

        }
        else{

            setEnabled(true);
            setStyleByMode();
        }
    }

    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(100, 30);
    }

    @Override
    public Dimension getMinimumSize()
    {
        return getPreferredSize();
    }
}