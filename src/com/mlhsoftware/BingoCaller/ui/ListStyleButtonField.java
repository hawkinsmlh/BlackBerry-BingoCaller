
package com.mlhsoftware.BingoCaller.ui;

import net.rim.device.api.animation.AnimatedScalar;
import net.rim.device.api.animation.Animation;
import net.rim.device.api.animation.Animator;
import net.rim.device.api.animation.AnimatorListener;
import net.rim.device.api.system.Application;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.Characters;
import net.rim.device.api.ui.DrawStyle;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Ui;

public class ListStyleButtonField extends ListStyleField implements Runnable, AnimatorListener
{

  private Animator  animator = new Animator( 20 );
  AnimatedScalar textOffsetScalar ;
  private Animation textAnimation;
  private int scrollDelay = 500;
  private int scrollTime = 4500;
  
  private Bitmap leftIcon = null;
  private Bitmap actionIcon = null;
  private Bitmap badgeIcon = null;

  private int labelTop;
  private int labelWidth;
  private int labelLeft;
  private int labelHeight;
  private String labelText;
  private Font lableFont = null;
  

  // Progress Animation info
  private Bitmap progressBitmap;
  private int numFrames;
  private int frameWidth;
  private int currentFrame;
  private int timerID = -1;
  private Application application;
  private boolean animate = false;
  private boolean visible = true;
  
  public ListStyleButtonField( String label, Bitmap actionIcon )
  {
    this( null, label, actionIcon, USE_ALL_WIDTH | Field.FOCUSABLE );
  }

  public ListStyleButtonField( Bitmap icon, String label, Bitmap actionIcon )
  {
    this( icon, label, actionIcon, USE_ALL_WIDTH | Field.FOCUSABLE );
  }

  public ListStyleButtonField( Bitmap icon, String label, Bitmap actionIcon, long style )
  {
    super( style );

    this.labelText = label;
    this.actionIcon = actionIcon;
    this.leftIcon = icon;
    this.animate = false;
    this.timerID = -1;
    textOffsetScalar = new AnimatedScalar( 0 );
    initTextAnimation();
  }
  
  public void initTextAnimation()
  {
    animator.setAnimatorListener( this );
    textAnimation = animator.addAnimationFromTo( textOffsetScalar, AnimatedScalar.ANIMATION_PROPERTY_SCALAR, 0, this.labelText.length(), Animation.EASINGCURVE_LINEAR, scrollTime);
  }

  public void setLabelText( String label )
  {
    this.labelText = label;
    animator.removeAllAnimations();
    textAnimation = animator.addAnimationFromTo( textOffsetScalar, AnimatedScalar.ANIMATION_PROPERTY_SCALAR, 0, this.labelText.length(), Animation.EASINGCURVE_LINEAR, scrollTime);
    updateLayout();
  }

  public void setIcon( Bitmap icon )
  {
    this.leftIcon = icon;
    updateLayout();
  }

  public void setBadgeIcon( Bitmap icon )
  {
    this.badgeIcon = icon;
    updateLayout();
  }

  public void setProgressAnimationInfo( Bitmap bitmap, int numFrames )
  {
    this.progressBitmap = bitmap;
    this.numFrames = numFrames;
    this.frameWidth = this.progressBitmap.getWidth() / this.numFrames;
    this.application = Application.getApplication();
    this.animate = false;
    this.visible = true;
    this.timerID = -1;
  }

  public void startAnimation()
  {
    this.animate = true;
    startTimer();
  }

  public void stopAnimation()
  {
    this.animate = false;
    stopTimer();
  }

  private void startTimer()
  {
    if ( this.timerID == -1 && this.animate == true )
    {
      this.timerID = this.application.invokeLater( this, 200, true );
    }
  }

  private void stopTimer()
  {
    if ( this.timerID != -1 )
    {
      this.application.cancelInvokeLater( this.timerID );
      this.timerID = -1;
    }
  }

  public void run()
  {
    if ( this.visible )
    {
      invalidate();
    }
  }

  protected void onDisplay()
  {
    super.onDisplay();
    this.visible = true;
    startTimer();
  }

  protected void onUndisplay()
  {
    super.onUndisplay();
    this.visible = false;
    stopTimer();
  }

  public boolean isFocusable()
  {
    return true;
  }

  //protected void sublayout( int width, int height )
  public void layout( int width, int height )
  {
    int fieldWidth = 0;
    int finalHeight = 0;
    //int topPos = ListStyleField.VPADDING;

    this.labelWidth = width;

    this.labelLeft = ListStyleField.HPADDING;
    if ( this.leftIcon != null )
    {
      this.labelLeft += this.leftIcon.getWidth() + ListStyleField.HPADDING;
      finalHeight = Math.max( finalHeight, this.leftIcon.getHeight() + ListStyleField.VPADDING + ListStyleField.VPADDING );
    }
    fieldWidth = this.labelLeft + ListStyleField.HPADDING;

    int rightOffset = ListStyleField.HPADDING;
    if ( this.actionIcon != null )
    {
      rightOffset += this.actionIcon.getWidth() + ListStyleField.HPADDING;
      finalHeight = Math.max( finalHeight, this.actionIcon.getHeight() + ListStyleField.VPADDING + ListStyleField.VPADDING );
      fieldWidth += this.actionIcon.getWidth() + ListStyleField.HPADDING + ListStyleField.HPADDING;
    }

    this.labelWidth -= this.labelLeft;
    this.labelWidth -= rightOffset;


    int fontSize =  this.fontSize; //sizeFont( this.labelText, this.labelWidth );
    this.lableFont = FONT_LEFT_LABLE.derive( FONT_LEFT_LABLE.isBold() ? Font.BOLD : Font.PLAIN, fontSize, Ui.UNITS_px );

    fieldWidth += this.lableFont.getAdvance( this.labelText );
    if ( !this.isStyle( Field.USE_ALL_WIDTH ) )
    {
      width = fieldWidth;
    }

    finalHeight = Math.max( finalHeight, this.lableFont.getHeight() + ListStyleField.VPADDING + ListStyleField.VPADDING );

    this.labelTop = ( finalHeight - this.lableFont.getHeight() ) / 2;

    setExtent( width, finalHeight );
  }

  protected void paint( Graphics g )
  {
    int oldColor = g.getColor();
    Font oldFont = g.getFont();
    try
    {
      // Left Bitmap
      if ( this.leftIcon != null )
      {
        g.drawBitmap( ListStyleField.HPADDING, ListStyleField.VPADDING, this.leftIcon.getWidth(), this.leftIcon.getHeight(), this.leftIcon, 0, 0 );
      }

      // Animated Bitmap
      if ( this.animate == true && this.progressBitmap != null )
      {
        g.drawBitmap( getWidth() - ( this.frameWidth + ListStyleField.HPADDING ), ( getHeight() - this.actionIcon.getHeight() ) / 2, this.frameWidth, this.progressBitmap.getHeight(), this.progressBitmap, this.frameWidth * this.currentFrame, 0 );
        this.currentFrame++;
        if ( this.currentFrame >= this.numFrames )
        {
          this.currentFrame = 0;
        }
      }

      // Right (Action) Bitmap
      else if ( this.actionIcon != null )
      {
        g.drawBitmap( getWidth() - ( this.actionIcon.getWidth() + ListStyleField.HPADDING ), ( getHeight() - this.actionIcon.getHeight() ) / 2, this.actionIcon.getWidth(), this.actionIcon.getHeight(), this.actionIcon, 0, 0 );
      }

      int labelWidth = this.labelWidth;
      labelHeight = this.lableFont.getHeight();

      if ( this.badgeIcon != null )
      {
        labelWidth -= this.badgeIcon.getWidth();
      }


      // Left Label Text
      int leftPos = this.labelLeft;
      g.setColor( this.fontColor );
      g.setFont( this.lableFont );
      
      if ( labelWidth < this.lableFont.getAdvance( this.labelText ) && g.isDrawingStyleSet( Graphics.DRAWSTYLE_FOCUS ) )
      {
        int currentChar = textOffsetScalar.getInt();
        String currentText = this.labelText;
        if ( currentChar < currentText.length() ) 
        {
          currentText = currentText.substring(currentChar);
        }        
        
        if ( this.lableFont.getAdvance( this.labelText ) < labelWidth )
        {
          this.textAnimation.end( 0 );
        }
        
        g.drawText( currentText, leftPos, this.labelTop, DrawStyle.ELLIPSIS, labelWidth );
      }
      else
      {
        g.drawText( this.labelText, leftPos, this.labelTop, DrawStyle.ELLIPSIS, labelWidth );
      }
      leftPos += labelWidth;

      // Badge icon (to the left of Action bitmap)
      if ( this.badgeIcon != null )
      {
        g.drawBitmap( leftPos, ( getHeight() - this.badgeIcon.getHeight() ) / 2, this.badgeIcon.getWidth(), this.badgeIcon.getHeight(), this.badgeIcon, 0, 0 );
      }
    }
    finally
    {
      g.setColor( oldColor );
      g.setFont( oldFont );
    }
  }

  protected boolean keyControl( char key, int status, int time )
  {
    boolean retVal = false;
    switch ( key )
    {
      case Characters.CONTROL_VOLUME_UP:
      {
        fieldChangeNotify( 1 );
        retVal = true;
        break;
      }

      case Characters.CONTROL_VOLUME_DOWN:
      {
        fieldChangeNotify( 2 );
        retVal = true;
        break;
      }

      default:
      {
        retVal = super.keyControl( key, status, time );
      }
    }
    return retVal;
  }

  protected boolean keyChar( char character, int status, int time )
  {
    boolean retVal = false;
    switch ( character )
    {
      case Characters.ENTER:
      case Characters.SPACE:
      {
        clickButton();
        retVal = true;
        break;
      }

      default:
      {
        retVal = super.keyChar( character, status, time );
      }
    }
    return retVal;
  }

  protected boolean navigationClick( int status, int time )
  {
    clickButton();
    return true;
  }

  protected boolean trackwheelClick( int status, int time )
  {
    clickButton();
    return true;
  }

  //#ifndef VER_4.1.0 | 4.0.0
  protected boolean invokeAction( int action )
  {
    switch ( action )
    {
      case ACTION_INVOKE:
      {
        clickButton();
        return true;
      }
    }
    return super.invokeAction( action );
  }
  //#endif        

  /**
     * A public way to click this button
     */
  public void clickButton()
  {
    fieldChangeNotify( 0 );
  }

  public boolean isDirty()
  {
    return false;
  }

  public boolean isMuddy()
  {
    return false;
  }

  public String toString()
  {
    return this.labelText;
  }

  public void animatorProcessing( boolean processing ) 
  {
  }

  public void animatorUpdate() 
  {
    invalidate();
  }
  
  protected  void onFocus(int direction) 
  {
    super.onFocus( direction );
    textAnimation.begin( scrollDelay );
  }
  
  protected  void  onUnfocus()
  {
    super.onUnfocus();
    textAnimation.end( 0 );
    this.textOffsetScalar.setInt( 0 );
    this.invalidate();
  }  
}



