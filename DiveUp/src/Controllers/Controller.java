package Controllers;


public interface Controller
{
    /**
     * Violations constants
     *
     */

    int id_empty=1;
    int firstName_empty=421;
    int lastName_empty=422;
    int licenseID_empty=423;
    int email_empty=424;
    int phone_empty=425;
    int PASSWORD_EMPTY=426;




    //void init() throws PcapNativeException, IOException;
    void init();
    //void refresh() throws PcapNativeException, IOException;
    void refresh();
}
