package res;

import java.awt.*;
import java.net.URL;


import javax.swing.*;

public interface UIConstants
{
	String reportsPath = System.getProperty("user.dir")+"/Reports";
	
	
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	int height = (int)screenSize.getHeight();
	int width =  (int)screenSize.getWidth();
	
	int miniScreenWidth =width/2+250;
	int miniScreenHeight = height/2+150;
	int miniScreenx = width/5;
	int miniScreeny = height /6;
    /**
    Available colors
*/
	
    //Components Colors
    Color BAR_DEFUALT  =new Color(0x20, 0x20,0x20);
    Color BAR_DARK  =new Color(0x00, 0x00,0x00);
    Color CONTENT_DEFUALT  =new Color(0xf7, 0xf7,0xf7);
    Color CONTENT_DARK  =new Color(0x30, 0x30,0x30);
    Color BORDER_DEFUALT  =new Color(0xd0, 0xd0,0xd0);
    Color BORDER_DARK  =new Color(0x70, 0x70,0x70);
    Color GRID_PANEL_ZEBRA  =new Color(0xf0, 0xf0,0xf0);


    //Notification Colors
    Color SUCCESS_DEFUALT  =new Color(0x7e, 0xb3,0x38);
    Color SUCCESS_DARK =new Color(0x7e, 0xb3,0x38);
    Color INFO_DEFUALT =new Color(0x17, 0x92,0xe5);
    Color INFO_DARK =new Color(0x17, 0x92,0xe5);
    Color IMPORTANT_DEFUALT =new Color(0xff, 0xc6,0x27);
    Color IMPORTANT_DARK =new Color(0xff, 0xc6,0x27);
    Color ALERT_DEFUALT =new Color(0xf3, 0x70,0x21);
    Color ALERT_DARK =new Color(0xf3, 0x70,0x21);
    Color ERROR_DEFUALT =new Color(0xbe, 0x27,0x1c);
    Color ERROR_DARK =new Color(0xf1, 0x5a,0x4f);
    Color CRITICAL_DEFUALT =new Color(0xf1, 0x5a,0x4f);
    Color CRITICAL_DARK=new Color(0xf1, 0x5a,0x4f);

    //Button Colors
    Color BTN_PRIMARY_DEFUALT =new Color(0x12, 0x74,0xb7);
    Color BTN_PRIMARY_DARK =new Color(0x45, 0xa7,0xea);
    Color BTN_PRIMARY_DESABLED_DEFUALT =new Color(0xa0, 0xa0,0xa0);
    Color BTN_PRIMARY_DESABLED_DARK =new Color(0x50, 0x50,0x50);
    Color BTN_PRIMARY_FOCUS_DEFUALT =new Color(0x0b, 0x48,0x72);
    Color BTN_PRIMARY_FOCUS_DARK =new Color(0x8b, 0xc8,0xf2);
    Color BTN_PRIMARY_HOVER_DEFUALT =new Color(0x10, 0x65,0x9f);
    Color BTN_PRIMARY_HOVER_DARK =new Color(0x5d, 0xb2,0xec);
    Color BTN_PRIMARY_FONT_DEFUALT =new Color(0xff, 0xff,0xff);
    Color BTN_PRIMARY_FONT_DARK =new Color(0x00, 0x00,0x00);

    Color BTN_INLINE_DEFUALT =new Color(0x30, 0x30,0x30);
    Color BTN_INLINE_DARK =new Color(0xe0, 0xe0,0xe0);
    Color BTN_INLINE_DESABLED_DEFUALT =new Color(0xf1, 0xf1,0xf1);
    Color BTN_INLINE_DESABLED_DARK =new Color(0x50, 0x50,0x50);
    Color BTN_INLINE_FOCUS_DEFUALT =new Color(0x70, 0x70,0x70);
    Color BTN_INLINE_FOCUS_DARK =new Color(0xb0, 0xb0,0xb0);
    Color BTN_INLINE_HOVER_DEFUALT =new Color(0x40, 0x40,0x40);
    Color BTN_INLINE_HOVER_DARK =new Color(0xd0, 0xd0,0xd0);
    Color BTN_INLINE_FONT_DEFUALT =new Color(0xff, 0xff,0xff);
    Color BTN_INLINE_FONT_DARK =new Color(0x00, 0x00,0x00);

    //Text Colors
    Color TXT_BASE_DEFUALT =new Color(0x30, 0x30,0x30);
    Color TXT_BASE_DARK =new Color(0xe0, 0xe0,0xe0);
    Color TXT_HIGH_CONTRAST_DEFUALT =new Color(0x40, 0x40,0x40);
    Color TXT_HIGH_CONTRAST_DARK =new Color(0xc0, 0xc0,0xc0);
    Color TXT_MEDIUM_CONTRAST_DEFUALT =new Color(0x60, 0x60,0x60);
    Color TXT_MEDIUM_CONTRAST_DARK =new Color(0xa0, 0xa0,0xa0);

    /**
      Colors configuration
     */

    Color HOVER_SELECTED_MAIN_BACKGROUND =new Color(0x40, 0x40,0x40);
    Color TEXT_COLOR                     =TXT_BASE_DARK;
    Color HEADER_SIDE_PANELS             =BAR_DEFUALT;
    Color SELECTED_BTN                   = new Color(0x17, 0x92,0xe5);
    Color DESABLED_TEXT                  = new Color(0xa0, 0xa0,0xa0);
    Color DESABLED_BTN                   = new Color(0xb0, 0xb0,0xb0);
    Color INVERTED                       =  new Color(0xe0, 0xe0,0xe0);
    Color DESABLED_INPUT                 =  new Color(0x50, 0x50, 0x50);


        }
