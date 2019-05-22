package res;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JToolTip;

public class DTooltip extends JToolTip
{
    private DTextArea tt_text;
    private JPanel ttPanel;

    public DTooltip(String tipText) {
        super();
        ttPanel = new JPanel(new BorderLayout());

        tt_text=new DTextArea(tipText);
        tt_text.setLineWrap(true);
        tt_text.setForeground(Color.WHITE);
        tt_text.setBorder(BorderFactory.createEmptyBorder(10,25,10,25));
        tt_text.setBackground(new Color(0x20,0x20,0x20));
        ttPanel.add(BorderLayout.CENTER, tt_text);

        setBorder(BorderFactory.createEmptyBorder());
        setLayout(new BorderLayout());
        add(ttPanel);
    }

    @Override public Dimension getPreferredSize() {
        return ttPanel.getPreferredSize();
    }

    @Override public void setTipText(String tipText) {
        if (tipText != null && !tipText.isEmpty()) {
            tt_text.setText(tipText);
            //tt_text.validate();
            //
            // tt_text.repaint();
        } else {
            super.setTipText(tipText);
        }
    }
}