package com.mlhsoftware.BingoCaller;

import net.rim.device.api.ui.UiApplication;

/**
 * This class extends the UiApplication class, providing a
 * graphical user interface.
 */
public class BingoCallerApp extends UiApplication
{
    /**
     * Entry point for application
     * @param args Command line arguments (not used)
     */ 
    public static void main(String[] args)
    {
        // Create a new instance of the application and make the currently
        // running thread the application's event dispatch thread.
        BingoCallerApp theApp = new BingoCallerApp();       
        theApp.enterEventDispatcher();
    }
    

    /**
     * Creates a new BingoCallerApp object
     */
    public BingoCallerApp()
    {        
      SplashScreen spScreen = new SplashScreen( new BingoCallerScreen() );
    }    
}
