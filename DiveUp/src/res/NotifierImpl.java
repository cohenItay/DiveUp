package res;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import Controllers.Notifier;

public class NotifierImpl implements Notifier
{

    private JPanel notificationHolder;

    public NotifierImpl(JPanel notificationHolder){
        this.notificationHolder=notificationHolder;
        this.notificationHolder.setBackground(UIConstants.HOVER_SELECTED_MAIN_BACKGROUND);
        this.notificationHolder.setVisible(false);
        this.notificationHolder.setLayout(new BorderLayout());
        this.notificationHolder.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    @Override
    public void show(DNotification notification)
    {
        notificationHolder.removeAll();
        notification.setPreferredSize(notificationHolder.getPreferredSize());
        notificationHolder.add(notification,BorderLayout.CENTER);
        notificationHolder.validate();
        notificationHolder.setVisible(true);
    }

    @Override
    public void show(DNotification notification, int seconds)
    {
        //todo add thread with sleep counter
        show(notification);
    }

    @Override
    public void clean()
    {
        notificationHolder.removeAll();
        notificationHolder.validate();
        notificationHolder.setVisible(false);
    }
}