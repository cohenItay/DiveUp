package Controllers;

import res.DNotification;

public interface Notifier
{
    void show(DNotification notification);
    void show(DNotification notification,int seconds);
    void clean();
}
