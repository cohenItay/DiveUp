package res;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

public class DTextField extends JTextField
{
    private Dimension DIM =new Dimension(150, 30);

    public DTextField(int size){
        super(size);
        setBackground(UIConstants.SELECTED_BTN);
        setForeground(UIConstants.BTN_PRIMARY_FONT_DEFUALT);
        //setBorder(BorderFactory.createLineBorder(UIConstants.BORDER_DEFUALT));
        setCaretColor(UIConstants.TXT_BASE_DARK);
        DIM.width+=size;

        addFocusListener(new FocusListener()
        {
            public void focusGained(FocusEvent e)
            {
                setBorder(BorderFactory.createLineBorder(UIConstants.SELECTED_BTN));
            }

            public void focusLost(FocusEvent e)
            {
                setBorder(BorderFactory.createLineBorder(UIConstants.BORDER_DARK));
            }
        });
    }

    public void setViolatedBorder(boolean status){
        if(status) {
            setBorder(BorderFactory.createLineBorder(Color.RED,2));
        }
        else{
            setBorder(BorderFactory.createLineBorder(UIConstants.BORDER_DARK));
        }
    }

    public void setEditableBorder(boolean status){
        if(status) {
            setBorder(BorderFactory.createLineBorder(UIConstants.SELECTED_BTN));
        }
        else{
            setBorder(BorderFactory.createLineBorder(UIConstants.BORDER_DARK));
        }
    }

    public void setDisabled(boolean status){
        if(status){
            setBackground(UIConstants.DESABLED_INPUT);
            setForeground(UIConstants.DESABLED_TEXT);
        }
        else{
            setBackground(UIConstants.HEADER_SIDE_PANELS);
            setForeground(UIConstants.TEXT_COLOR);
        }
        setFocusable(!status);
        setEditable(!status);
    }

    @Override
    public Dimension getPreferredSize() {
        return DIM;
    }

    @Override
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }
}
