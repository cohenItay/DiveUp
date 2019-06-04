package Views;

import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

public class DNotification extends JPanel
{
//    private HIconPanel icon;
//    private HIconPanel close_icon;
    private DLabel title;
    private DLabel text;
    

    public enum Type{
        ERROR,
        WARNING,
        INFO,
        SUCCESS
    }

    public DNotification(String title,String text, Type type, boolean closeable){
        this.title = new DLabel(title);
        this.text = new DLabel(text);
        setBackground(UIConstants.HEADER_SIDE_PANELS);
        setLayout(new GridBagLayout());
        

        switch (type)
        {
            case INFO:
                setBorder(new MatteBorder(2, 10, 2, 2, UIConstants.BTN_PRIMARY_DEFUALT));
//                icon=new HIconPanel(UIConstants.INFO_NOTIF_SVG_PATH,16,16);
                break;
            case ERROR:
                setBorder(new MatteBorder(2, 10, 2, 2, UIConstants.ERROR_DEFUALT));
//                icon=new HIconPanel(UIConstants.ERR_NOTIF_SVG_PATH,16,16);
                break;
            case WARNING:
                setBorder(new MatteBorder(2, 10, 2, 2, UIConstants.ALERT_DARK));
//                icon=new HIconPanel(UIConstants.WARN_NOTIF_SVG_PATH,16,16);
                break;
            case SUCCESS:
                setBorder(new MatteBorder(2, 10, 2, 2, UIConstants.SUCCESS_DEFUALT));
//                icon=new HIconPanel(UIConstants.SUCCESS_NOTIF_SVG_PATH,24,24);
                break;
        }



    }
}
