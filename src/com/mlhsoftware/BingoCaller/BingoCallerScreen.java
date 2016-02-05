package com.mlhsoftware.BingoCaller;

import java.util.Date;
import java.util.Random;

import net.rim.device.api.animation.AnimatedScalar;
import net.rim.device.api.animation.Animation;
import net.rim.device.api.animation.Animator;
import net.rim.device.api.animation.AnimatorListener;
import net.rim.device.api.system.Characters;
import net.rim.device.api.system.Display;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.Menu;
import net.rim.device.api.ui.container.FullScreen;
import net.rim.device.api.util.StringProvider;

import com.mlhsoftware.BingoCaller.ui.AboutScreen;

public final class BingoCallerScreen extends FullScreen implements AnimatorListener
{

  private Animator  animator = new Animator( 20 );
  AnimatedScalar fontSizeScalar;
  private Animation fontAnimation;
  private Animation boardAnimation;
  AnimatedScalar boardFontScalar;
  private int animateTime = 500;
  
  int fontSmall = 10;
  int fontBig = 100;
  
  boolean[] selectedNumbers = new boolean[76];
  Random rndGenerator;
  int currentNumber = 0;

  public BingoCallerScreen()
  {
    super( DEFAULT_MENU | DEFAULT_CLOSE );
    this.rndGenerator = new Random( new Date().getTime() );
    
    this.fontBig = 165;
    Font font = Font.getDefault().derive( Font.PLAIN, this.fontBig );
    int strWidth = font.getAdvance( "XXXX" );
    while ( strWidth > Display.getWidth() )
    {
      font = Font.getDefault().derive( Font.PLAIN, this.fontBig -= 5 );
      strWidth = font.getAdvance( "XXXX" );
    }    
    
    this.animator.setAnimatorListener( this );
    
    this.fontSizeScalar = new AnimatedScalar( 0 );
    this.fontAnimation = this.animator.addAnimationFromTo( this.fontSizeScalar, AnimatedScalar.ANIMATION_PROPERTY_SCALAR, 30, fontBig, Animation.EASINGCURVE_OVERSHOOT_INOUT, animateTime);
    
    this.boardFontScalar = new AnimatedScalar( 16 );
    this.boardAnimation = this.animator.addAnimationFromTo( this.boardFontScalar, AnimatedScalar.ANIMATION_PROPERTY_SCALAR, 24, 16, Animation.EASINGCURVE_OVERSHOOT_INOUT, animateTime);
  }

  void newGame()
  {
    this.currentNumber = 0;
    for ( int i = 0; i < 76; i++ )
    {
      this.selectedNumbers[i] = false;
    }
    invalidate();
  }

  void drawNumber()
  {
    if ( allNumbersCalled() )
    {
      this.currentNumber = -1;
      invalidate();
    }
    else
    {
      while ( !allNumbersCalled() )
      {
        int index = this.rndGenerator.nextInt( 76 );
        if ( index != 0 && this.selectedNumbers[index] == false )
        {
          this.currentNumber = index;
          this.selectedNumbers[index] = true;
          break;
        }
      }
      //invalidate();
      this.fontAnimation.begin( 0 );
      this.boardAnimation.begin( animateTime );
    }
  }

  boolean allNumbersCalled()
  {
    boolean retVal = true;
    for ( int i = 1; i < 76; i++ )
    {
      if ( !this.selectedNumbers[i] )
      {
        retVal = false;
        break;
      }
    }
    return retVal;
  }


  protected void paint( Graphics graphics )
  {
    Font font = null;
    int screenWidth = Display.getWidth();
    int screenHeight = Display.getHeight();
    int strHeight = 0;
    int strWidth = 0;
    String text;

    // paint background
    graphics.setBackgroundColor( Graphics.BLACK );
    graphics.fillRect( 0, 0, screenWidth, screenHeight );

    // add mlhsoftware.com to bottom of screen
    graphics.setColor( 0xFFFFFF );
    font = Font.getDefault().derive( Font.PLAIN, 12 );
    strHeight = font.getHeight();
    graphics.setFont( font );

    int yPos = screenHeight - ( strHeight + 4 );
    text = "MLHSoftware.com";
    strWidth = font.getAdvance( text );
    graphics.drawText( text, ( screenWidth / 2 ) - ( strWidth / 2 ), yPos );

    // paint call board
    int boardHeight = paintCallBoard( graphics, yPos );


    // paint current number
    if ( this.currentNumber > 0 )
    {
      text = "";
      if ( this.currentNumber < 16 )
      {
        text = "B";
      }
      else if ( this.currentNumber < 31 )
      {
        text = "I";
      }
      else if ( this.currentNumber < 46 )
      {
        text = "N";
      }
      else if ( this.currentNumber < 61 )
      {
        text = "G";
      }
      else
      {
        text = "O";
      }
      text += " " + this.currentNumber;


      // font size changes with animation
      font = Font.getDefault().derive( Font.PLAIN, fontSizeScalar.getInt() );
      strWidth = font.getAdvance( text );
      int textHeight = font.getHeight();
      graphics.setColor( 0x00FF00 );
      graphics.setFont( font );
      graphics.drawText( text, ( screenWidth / 2 ) - ( strWidth / 2 ), ((Display.getHeight() - boardHeight) / 2 ) - ( textHeight / 2 ) );
    }
    else
    {
      if ( this.currentNumber == 0 )
      {
        text = "Click to draw number";
      }
      else
      {
        text = "Game Over";
      }
      int fontSize = 60;
      font = Font.getDefault().derive( Font.PLAIN, fontSize );
      strWidth = font.getAdvance( text );
      while ( strWidth > Display.getWidth() )
      {
        font = Font.getDefault().derive( Font.PLAIN, fontSize-=5 );
        strWidth = font.getAdvance( text );
      }      
      
      
      graphics.setColor( 0xFFFFFF );
      graphics.setFont( font );
      int textHeight = font.getHeight();
      graphics.drawText( text, ( screenWidth / 2 ) - ( strWidth / 2 ), ((Display.getHeight() - boardHeight) / 2 ) - ( textHeight / 2 ) );
    }
  }
  
  private int paintCallBoard( Graphics graphics, int yPos )
  {
    

    
    Font font = Font.getDefault().derive( Font.PLAIN, 16 );
    graphics.setColor( 0xFFFFFF );
    graphics.setFont( font );
    int strHeight = font.getHeight();    
    int boardHeight = ( ( strHeight + 3 ) * 5 );
    int y = yPos - boardHeight;
    int w = Display.getWidth() / 16;
    int number = 1;
    int x = 4;
    String text = "";
    for ( int Row = 0; Row < 5; Row++ )
    {
      x = 4;
      switch ( Row )
      {
        case 0:
        {
          text = "B";
          break;
        }
        case 1:
        {
          text = "I";
          break;
        }
        case 2:
        {
          text = "N";
          break;
        }
        case 3:
        {
          text = "G";
          break;
        }
        case 4:
        {
          text = "O";
          break;
        }
      }

      font = Font.getDefault().derive( Font.PLAIN, 16 );
      graphics.setFont( font );      
      graphics.setColor( 0x00FF00 );
      graphics.drawText( text, x, y );

      
      for ( int Column = 1; Column <= 15; Column++ )
      {
        font = Font.getDefault().derive( Font.PLAIN, 16 );
        graphics.setFont( font );   
        graphics.setColor( 0x444444 );
        if ( number == this.currentNumber )
        {
          font = Font.getDefault().derive( Font.PLAIN, boardFontScalar.getInt() );
          graphics.setFont( font );
          graphics.setColor( 0x00FF00 );
        }
        else if ( selectedNumbers[number] )
        {
          graphics.setColor( 0x00FF00 );
        }

        x += w;
        text = "" + number;
        graphics.drawText( text, x, y );
        number++;
      }

      y += ( strHeight + 3 );
    }
    return boardHeight;
  }

  protected boolean navigationClick( int status, int time )
  {
    drawNumber();
    return true;
  }
  
  public boolean keyChar( char key, int status, int time )
  {
    boolean retVal = true;
    switch ( key )
    {
      case Characters.ESCAPE:
      {
        UiApplication.getUiApplication().requestBackground();
        break;
      }
      case ' ':
      {
        drawNumber();
        break;
      } 
      default:
      {
        retVal = super.keyChar( key, status, time );
        break;
      }
    }
    return retVal;
  }  

  private void displayAbout()
  {
    AboutScreen screen = new AboutScreen();
    UiApplication.getUiApplication().pushScreen(screen);
  }
  
  protected void makeMenu( Menu menu, int instance )
  {
    MenuItem newMenu = new MenuItem( new StringProvider( "New Game" ), 50, 50 )
    {
      public void run()
      {
        newGame();
      }
    };

    MenuItem aboutMenu = new MenuItem( new StringProvider( "About" ), 60, 60 )
    {
      public void run()
      {
        displayAbout();
      }
    };    


    menu.add( newMenu );
    menu.add( aboutMenu );

    super.makeMenu( menu, instance );
  }

  public void animatorProcessing( boolean processing ) 
  {
  }

  public void animatorUpdate() 
  {
    invalidate();
  }
}
