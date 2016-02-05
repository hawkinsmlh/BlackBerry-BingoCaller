

package com.mlhsoftware.BingoCaller.ui;

import java.util.Enumeration;
import java.util.Vector;

import com.mlhsoftware.BingoCaller.AppInfo;
import com.mlhsoftware.BingoCaller.util.Logger;

import net.rim.blackberry.api.browser.Browser;
import net.rim.blackberry.api.invoke.Invoke;
import net.rim.blackberry.api.invoke.MessageArguments;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.CodeModuleManager;
import net.rim.device.api.system.ControlledAccessException;
import net.rim.device.api.system.DeviceInfo;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.StandardTitleBar;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;
import net.rim.device.api.ui.decor.Background;
import net.rim.device.api.ui.decor.BackgroundFactory;
import net.rim.device.api.util.StringProvider;

public class AboutScreen extends MainScreen 
{
  private static final Bitmap ICON = Bitmap.getBitmapResource( "icon.png" );

  private Vector actionListeners = new Vector();
  
  public AboutScreen() 
  {
    super( DEFAULT_MENU | DEFAULT_CLOSE | Manager.NO_VERTICAL_SCROLL );


    try
    {
      StandardTitleBar myTitleBar = new StandardTitleBar();
      if ( myTitleBar != null )
      {
        //myTitleBar.addIcon("icon.png");
        myTitleBar.addTitle( AppInfo.APP_NAME + "     "  );
        myTitleBar.addClock();
        myTitleBar.addNotifications();
        myTitleBar.addSignalIndicator();
        
        myTitleBar.setPropertyValue(StandardTitleBar.PROPERTY_BATTERY_VISIBILITY, StandardTitleBar.BATTERY_VISIBLE_LOW_OR_CHARGING);
        try
        {
          setTitleBar(myTitleBar);
        }
        catch ( ControlledAccessException e2 )
        {
          
        }
        catch ( SecurityException e1 )
        {
          
        }
      }
    }
    catch( Exception e )
    {
    } 


    //ForegroundManager foreground = new ForegroundManager();
    VerticalFieldManager foreground = new VerticalFieldManager( Manager.VERTICAL_SCROLL | Manager.VERTICAL_SCROLLBAR );
    foreground.setMargin( 5, 5, 5, 5 );
  //  foreground.setBackgroundTransparent( true );
    

    //Bitmap bitmap = Bitmap.getBitmapResource( "background.jpg" );
    //Background bg = BackgroundFactory.createBitmapBackground( bitmap, 0, 0, Background.REPEAT_BOTH );
    Background bg = BackgroundFactory.createSolidBackground( AppInfo.BACKGROUND_COLOR );
    this.getMainManager().setBackground( bg );    
    
    // About bar - App Icon, App Name, App Version, Vendor
    String appVersion = AppInfo.APP_VERSION;
    if ( AppInfo.APPWORLD )
    {
      appVersion += " a";
    }

    String extraLine = "";

    AboutbarManager aboutBar = new AboutbarManager( ICON, AppInfo.APP_NAME, appVersion, extraLine );
    aboutBar.setFontColor( AppInfo.FONT_COLOR );
    foreground.add( aboutBar );


    // Action buttons
    Bitmap caret = Bitmap.getBitmapResource( "greenArrow.png" );
    Bitmap tellIcon = Bitmap.getBitmapResource( "users_two_24.png" );
    Bitmap feedbackIcon = Bitmap.getBitmapResource( "comment_add_24.png" );
    Bitmap webIcon = Bitmap.getBitmapResource( "mlhIcon.png" );
    Bitmap twitterIcon = Bitmap.getBitmapResource( "twitter_24.png" );
    Bitmap systemIcon = Bitmap.getBitmapResource( "spanner_24.png" );
    Bitmap crackberryIcon = Bitmap.getBitmapResource( "crackberry_24.png" );
    Bitmap facebookIcon = Bitmap.getBitmapResource( "facebook_24.png" );
    Bitmap helpIcon = Bitmap.getBitmapResource( "helpIconBlack.png" );
    Bitmap appIcon = Bitmap.getBitmapResource( "appWorld.png" );



    // Info Section
    ListStyleFieldSet moreInfoSet = new ListStyleFieldSet();
    

    ListStyleLabelField info = new ListStyleLabelField( "More Info" );
    moreInfoSet.add( info );

    ListStyleButtonField link = null;
    
    
    link = new ListStyleButtonField( appIcon, "Get More Apps", caret );
    link.setChangeListener( new FieldChangeListener()
    {
      public void fieldChanged( Field field, int context )
      {
        moreApps();
      }
    } );
    moreInfoSet.add( link );    
    
    link = new ListStyleButtonField( webIcon, "MLHSoftware.com", caret );
    link.setChangeListener( new FieldChangeListener()
    {
      public void fieldChanged( Field field, int context )
      {
        onMoreInfo();
      }
    } );
    moreInfoSet.add( link );    
    
    link = new ListStyleButtonField( systemIcon, "System info", caret );
    link.setChangeListener( new FieldChangeListener()
    {
      public void fieldChanged( Field field, int context )
      {
        onSystemInfo();
      }
    } );
    moreInfoSet.add( link );

    /*
    link = new ListStyleButtonField( helpIcon, "Help", caret );
    link.setChangeListener( new FieldChangeListener()
    {
      public void fieldChanged( Field field, int context )
      {
        onHelp();
      }
    } );
    buttonSet.add( link );
    */


    


    
        
    
    // Share
    ListStyleFieldSet shareSet = new ListStyleFieldSet();

    info = new ListStyleLabelField( "Share" );
    shareSet.add( info );

    link = new ListStyleButtonField( tellIcon, "Tell a friend", caret );
    link.setChangeListener( new FieldChangeListener()
    {
      public void fieldChanged( Field field, int context )
      {
        onTellAFriend();
      }
    } );
    shareSet.add( link );

    link = new ListStyleButtonField( twitterIcon, "Follow us on twitter", caret );
    link.setChangeListener( new FieldChangeListener()
    {
      public void fieldChanged( Field field, int context )
      {
        onTwitter();
      }
    } );
    shareSet.add( link );

    link = new ListStyleButtonField( facebookIcon, "Like us on Facebook", caret );
    link.setChangeListener( new FieldChangeListener()
    {
      public void fieldChanged( Field field, int context )
      {
        onFacebook();
      }
    } );
    shareSet.add( link );
    



    // Feedback
    ListStyleFieldSet feedbackSet = new ListStyleFieldSet();
    

    info = new ListStyleLabelField( "Feedback" );
    feedbackSet.add( info );

    link = new ListStyleButtonField( feedbackIcon, "Email us", caret );
    link.setChangeListener( new FieldChangeListener()
    {
      public void fieldChanged( Field field, int context )
      {
        onFeedback();
      }
    } );
    feedbackSet.add( link );

    link = new ListStyleButtonField( crackberryIcon, "User forum on CrackBerry", caret );
    link.setChangeListener( new FieldChangeListener()
    {
      public void fieldChanged( Field field, int context )
      {
        onCrackBerry();
      }
    } );
    feedbackSet.add( link );


    foreground.add( moreInfoSet );
    foreground.add( shareSet );
    foreground.add( feedbackSet );
    


    add( foreground );

    //addMenuItem( _debug );
    //addMenuItem( _showLog );
    //addMenuItem( _clearKey );
  }

  private void moreApps() 
  {
    Browser.getDefaultSession().displayPage( "http://appworld.blackberry.com/webstore/vendor/870" );
  }
  
  private void onSystemInfo()
  {
    SystemInfoScreen dlg = new SystemInfoScreen( AppInfo.APP_NAME, AppInfo.APP_VERSION );
    UiApplication.getUiApplication().pushScreen( dlg );
  }

  private void onTellAFriend()
  {
    String arg = MessageArguments.ARG_NEW;
    String to = "";
    String subject = "Check out this Blackberry application: " + AppInfo.APP_NAME;
    String body = AppInfo.TELL_A_FRIEND;

    MessageArguments msgArg = new MessageArguments( arg, to, subject, body );
    Invoke.invokeApplication( Invoke.APP_TYPE_MESSAGES, msgArg );
  }

  private void onFeedback()
  {
    String beta = "";
    if ( AppInfo.BETA )
    {
      beta = "\r\n Beta: true\r\n";
    }
    
    String key = "Not Required";
    //ActivationKeyStore keyStore = ActivationKeyStore.GetInstance();
    //if ( keyStore != null )
    //{
    //  key = keyStore.getKey();
    //}
    int deviceId = DeviceInfo.getDeviceId();
    String deviceIdText = java.lang.Integer.toHexString( deviceId );
    String pin = deviceIdText.toUpperCase();

    String arg = MessageArguments.ARG_NEW;
    String to = "feedback@mlhsoftware.com";
    String subject = "Feedback: " + AppInfo.APP_NAME;
    String body = "Tell us what you think about " + AppInfo.APP_NAME + ". \r\n" +
                  "\r\n App: " + AppInfo.APP_NAME + 
                  "\r\n Key: " + key + 
                  "\r\n PIN: " + pin +
                  "\r\n Version: " + AppInfo.APP_VERSION + 
                  "\r\n Device: " + DeviceInfo.getDeviceName() + 
                  "\r\n OS Version: " + CodeModuleManager.getModuleVersion( CodeModuleManager.getModuleHandleForObject( "" ) ) +
                  beta +
                  "\r\n\r\n";

    MessageArguments msgArg = new MessageArguments( arg, to, subject, body );
    Invoke.invokeApplication( Invoke.APP_TYPE_MESSAGES, msgArg ); 
  }

  private void onMoreInfo()
  {
    Browser.getDefaultSession().displayPage( AppInfo.APP_URL );
  }

  private void onHelp()
  {
    //TODO fireAction( Controller.ACTION_DISPLAY_HELP );
    close();
  }

  private void onTwitter()
  {
    Browser.getDefaultSession().displayPage( "http://m.twitter.com/mlhsoftware" );
  }

  private void onFacebook()
  {
    Browser.getDefaultSession().displayPage( "http://m.facebook.com/pages/MLH-Software/137362215699" );
  }

  private void onCrackBerry()
  {
    Browser.getDefaultSession().displayPage( "http://forums.crackberry.com/f188/" );
  }

  private MenuItem debugMenu = new MenuItem( new StringProvider( "Enable Debug Logging" ), 3001, 1003 )
  {
    public void run()
    {
      Logger.enableDebugLogging();
      Dialog.alert( "Debug Logging has been enabled" );
    }
  };
  
  
  private MenuItem clearKeyMenu = new MenuItem( new StringProvider( "Clear Activation Key" ), 3001, 1003 )
  {
    public void run()
    {
      //ActivationKeyStore.reset();
      Logger.logDebug( "Activation key cleared." );
      Dialog.alert( "Activation key cleared." );
    }
  };

  private MenuItem showLogMenu = new MenuItem( new StringProvider( "Open Event Logs" ), 3002, 1004 )
  {
    public void run()
    {
      Logger.openEventLogViewer();
    }
  };

  public void addActionListener( ActionListener actionListener )
  {
    if ( actionListener != null )
    {
      actionListeners.addElement( actionListener );
    }
  }

  protected void fireAction( String action )
  {
    fireAction( action, null );
  }

  protected void fireAction( String action, Object data )
  {
    Enumeration listenersEnum = actionListeners.elements();
    while ( listenersEnum.hasMoreElements() )
    {
      ( (ActionListener)listenersEnum.nextElement() ).onAction( new Action( this, action, data ) );
    }
  }   
  
}
