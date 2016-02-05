package com.mlhsoftware.BingoCaller.ui;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.CodeModuleManager;
import net.rim.device.api.system.ControlledAccessException;
import net.rim.device.api.system.DeviceInfo;
import net.rim.device.api.system.RadioInfo;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.component.StandardTitleBar;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;
import net.rim.device.api.ui.decor.Background;
import net.rim.device.api.ui.decor.BackgroundFactory;

import com.mlhsoftware.BingoCaller.AppInfo;

public class SystemInfoScreen extends MainScreen 
{
  public SystemInfoScreen( String appName, String  version ) 
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

    VerticalFieldManager foreground = new VerticalFieldManager( Manager.VERTICAL_SCROLL | Manager.VERTICAL_SCROLLBAR );

    Bitmap bitmap = Bitmap.getBitmapResource( "background.jpg" );
    Background bg = BackgroundFactory.createBitmapBackground( bitmap, 0, 0, Background.REPEAT_BOTH );
    this.getMainManager().setBackground( bg );      

    ListStyleFieldSet infoSet = new ListStyleFieldSet();

    String deviceName = DeviceInfo.getDeviceName();
    String deviceOS = CodeModuleManager.getModuleVersion( CodeModuleManager.getModuleHandleForObject( "" ) );

    ListStyleLabelField info = new ListStyleLabelField( "BlackBerry Info" );
    infoSet.add( info );

    info = new ListStyleLabelField( null, "Model", deviceName );
    infoSet.add( info );

    info = new ListStyleLabelField( null, "Version", deviceOS );
    infoSet.add( info );

    int deviceId = DeviceInfo.getDeviceId();
    String deviceIdText = java.lang.Integer.toHexString( deviceId );
    String pin = deviceIdText.toUpperCase();
    info = new ListStyleLabelField( null, "PIN", pin );
    infoSet.add( info );

    String battery = DeviceInfo.getBatteryLevel() + "%";
    info = new ListStyleLabelField( null, "Battery", battery );
    infoSet.add( info );

    String signal = RadioInfo.getSignalLevel() + " dBm";
    info = new ListStyleLabelField( null, "Signal", signal );
    infoSet.add( info );


    foreground.add( infoSet );

    infoSet = new ListStyleFieldSet();
    info = new ListStyleLabelField( appName + " Info" );
    infoSet.add( info );

    info = new ListStyleLabelField( null, "Version", version );
    infoSet.add( info );

    String key = "Not Required";
    //ActivationKeyStore keyStore = ActivationKeyStore.GetInstance();
    //if ( keyStore != null )
    //{
    //  key = keyStore.getKey();
    //}

    info = new ListStyleLabelField( null, "Key", key );
    infoSet.add( info );

    foreground.add( infoSet );

    add( foreground );
  }
}
