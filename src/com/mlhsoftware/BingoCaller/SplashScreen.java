package com.mlhsoftware.BingoCaller;

import net.rim.device.api.ui.*;
import net.rim.device.api.ui.component.*;
import net.rim.device.api.ui.container.*;
import net.rim.device.api.system.*;
import java.util.*;

public class SplashScreen extends MainScreen 
{
   private Screen next;
   private int state = 0;
   private boolean bDismissed = false;
   private static final Bitmap SplashScreenImg = Bitmap.getBitmapResource("splashScreen.png");
   
   
   public SplashScreen( Screen next ) 
   {
      super(Field.USE_ALL_HEIGHT | Field.FIELD_LEFT);
      this.next = next;
      UiApplication.getUiApplication().pushScreen(this);
      
      CountDown countDown = new CountDown();
      countDown.start();
   }
   
  protected void paint(Graphics graphics)
  {

    int screenWidth = Display.getWidth();
    int screenHeight = Display.getHeight();
    int bitmapWidth = SplashScreenImg.getWidth();
    int bitmapHeight = SplashScreenImg.getHeight();

    graphics.setBackgroundColor( Graphics.BLACK );
    graphics.fillRect( 0, 0, screenWidth, screenHeight );
    
    int alpha = graphics.getGlobalAlpha();
    graphics.setGlobalAlpha( state );
    int x = (screenWidth - bitmapWidth) / 2;
    int y = (screenHeight - bitmapHeight) / 2;
    
    graphics.drawBitmap(x, y, bitmapWidth, bitmapHeight, SplashScreenImg, 0, 0);
    
    graphics.setGlobalAlpha( alpha );
    state+=10;
  }   
   
   public void dismiss() 
   {
      //timer.cancel();
      if ( !bDismissed )
      {
        UiApplication.getUiApplication().popScreen(this);
        UiApplication.getUiApplication().pushScreen(next);
        bDismissed = true;
      }
   }
   
   private class CountDown extends Thread 
   {
      public void run() 
      {
        setSleep( 100 );
        while ( state < 255 )
        {
          invalidate();
          setSleep( 40 );
        }        
        
        invalidate();
        setSleep( 1000 );

        UiApplication.getUiApplication().invokeLater( new Runnable() { public void run() { dismiss(); } }  );
      }
      
      private void setSleep(int ms)
      {
        try
        {
          sleep(ms);
        }
        catch (InterruptedException e)
        {
        }         
      }
   }
   
   protected boolean navigationClick(int status, int time) 
   {
      dismiss();
      return true;
   }

  public boolean keyChar( char key, int status, int time )
  {
    //intercept the ESC and MENU key - exit the splash screen
    boolean retval = false;
    switch ( key )
    {
      case Characters.ENTER:
      case Characters.CONTROL_MENU:
      case Characters.ESCAPE:
      {
        dismiss();
        retval = true;
        break;
      }
    }
    return retval;
  }
  
  /*
   public static class SplashScreenListener implements KeyListener 
   {
      private SplashScreen screen;
      
      public boolean keyChar(char key, int status, int time) 
      {
         //intercept the ESC and MENU key - exit the splash screen
         boolean retval = false;
         switch (key) 
         {
           case Characters.ENTER:
           case Characters.CONTROL_MENU:
           case Characters.ESCAPE:
           {
             screen.dismiss();
             retval = true;
             break;
           }
         }
         return retval;
      }
      
      public SplashScreenListener(SplashScreen splash) 
      {
         screen = splash;
      }
   }
   * */
} 


