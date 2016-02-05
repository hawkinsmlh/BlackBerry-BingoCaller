package com.mlhsoftware.BingoCaller.ui;

import com.mlhsoftware.BingoCaller.AppInfo;
import com.mlhsoftware.BingoCaller.util.Logger;

import net.rim.device.api.system.Display;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Ui;


public abstract class ListStyleField extends Field
{
  public static final int DRAWPOSITION_UNKNOWN = -1;
  public static final int DRAWPOSITION_TOP     = 0;
  public static final int DRAWPOSITION_BOTTOM  = 1;
  public static final int DRAWPOSITION_MIDDLE  = 2;
  public static final int DRAWPOSITION_SINGLE  = 3;
  public static final int DRAWPOSITION_HEADER  = 4;
  public static final int DRAWPOSITION_SINGLE_NO_BORDER = 5;

  protected static final int CORNER_RADIUS = 18;

  public static final int VPADDING = Display.getWidth() <= 320 ? 6 : 10;
  public static final int HPADDING = Display.getWidth() <= 320 ? 6 : 10;


  public static final int COLOR_INNER_BACKGROUND = 0xFFFFFF;
  public static final int COLOR_BACKGROUND = 0xFFFFFF;
  public static final int COLOR_BORDER = AppInfo.COLOR_BORDER;
  public static final int COLOR_BACKGROUND_FOCUS = 0x186DEF;
  public static final int COLOR_HEADER_FONT = 0x777777;
  public static final int COLOR_LEFT_LABEL_FONT = Color.BLACK;
  public static final int COLOR_RIGHT_LABEL_FONT = 0x777777;
  public static final Font FONT_LEFT_LABLE = Font.getDefault().derive( Font.PLAIN, 8, Ui.UNITS_pt, Font.ANTIALIAS_STANDARD, 0 );
  public static final Font FONT_RIGHT_LABLE = FONT_LEFT_LABLE; // Font.getDefault().derive( Font.PLAIN, 14, Ui.UNITS_px, Font.ANTIALIAS_STANDARD, 0 );

  protected int COLOR_OUTER_BACKGROUND = AppInfo.BACKGROUND_COLOR;
  protected int fontColor = COLOR_LEFT_LABEL_FONT;
  protected int drawPosition = -1;
  protected int fontSize = FONT_LEFT_LABLE.getHeight();

  protected Object customItem;

  public ListStyleField( long style )
  {
    super( style );
  }

  /**
   * DRAWPOSITION_TOP | DRAWPOSITION_BOTTOM | DRAWPOSITION_MIDDLE
   * Determins how the field is drawn (borders)
   * If none is set, then no borders are drawn
   */
  public void setDrawPosition( int drawPosition )
  {
    this.drawPosition = drawPosition;
  }

  public void setFontColor( int color )
  {
    this.fontColor = color;
  }

  public void setFontSize( int size )
  {
    this.fontSize = size;
  }

  public void setOuterBackgroundColor( int color )
  {
    COLOR_OUTER_BACKGROUND = color;
  }

  public void setCustomeItem( Object item )
  {
    this.customItem = item;
  }

  public Object getCustomeItem()
  {
    return this.customItem;
  }

  protected void paintBackground( Graphics g )
  {
    int oldColour = g.getColor();

    // paint outer background color
    if ( this.COLOR_OUTER_BACKGROUND > -1 )
    {
      g.setColor( this.COLOR_OUTER_BACKGROUND );
      g.fillRect( 0, 0, getWidth(), getHeight() );
    }

    int background = g.isDrawingStyleSet( Graphics.DRAWSTYLE_FOCUS ) ? AppInfo.COLOR_INNER_BACKGROUND_FOCUS : ListStyleField.COLOR_INNER_BACKGROUND;
    try
    {
      switch ( this.drawPosition )
      {
        case ListStyleField.DRAWPOSITION_HEADER:
        {
          g.setColor( background );
          g.fillRoundRect( 0, 0, getWidth(), getHeight() + ListStyleField.CORNER_RADIUS, ListStyleField.CORNER_RADIUS, ListStyleField.CORNER_RADIUS );
          g.setColor( ListStyleField.COLOR_BORDER );
          g.drawRoundRect( 0, 0, getWidth(), getHeight() + ListStyleField.CORNER_RADIUS, ListStyleField.CORNER_RADIUS, ListStyleField.CORNER_RADIUS );
          break;
        }
        case ListStyleField.DRAWPOSITION_TOP:
        {
          g.setColor( background );
          g.fillRoundRect( 0, 0, getWidth(), getHeight() + ListStyleField.CORNER_RADIUS, ListStyleField.CORNER_RADIUS, ListStyleField.CORNER_RADIUS );
          g.setColor( ListStyleField.COLOR_BORDER );
          g.drawRoundRect( 0, 0, getWidth(), getHeight() + ListStyleField.CORNER_RADIUS, ListStyleField.CORNER_RADIUS, ListStyleField.CORNER_RADIUS );
          g.drawLine( 0, getHeight() - 1, getWidth() - 1, getHeight() - 1 );
          break;
        }
        case ListStyleField.DRAWPOSITION_BOTTOM:
        {
          g.setColor( background );
          g.fillRoundRect( 0, -ListStyleField.CORNER_RADIUS, getWidth(), getHeight() + ListStyleField.CORNER_RADIUS, ListStyleField.CORNER_RADIUS, ListStyleField.CORNER_RADIUS );
          g.setColor( ListStyleField.COLOR_BORDER );
          g.drawRoundRect( 0, -ListStyleField.CORNER_RADIUS, getWidth(), getHeight() + ListStyleField.CORNER_RADIUS, ListStyleField.CORNER_RADIUS, ListStyleField.CORNER_RADIUS );
          break;
        }
        case ListStyleField.DRAWPOSITION_MIDDLE:
        {
          g.setColor( background );
          g.fillRect( 0, 0, getWidth(), getHeight() );
          g.setColor( ListStyleField.COLOR_BORDER );
          g.drawLine( 0, 0, 0, getHeight() - 1 ); // left border
          g.drawLine( getWidth() - 1, 0, getWidth() - 1, getHeight() - 1 ); // right border
          g.drawLine( 0, getHeight() - 1, getWidth() - 1, getHeight() - 1 ); // bottom border

          break;
        }
        case DRAWPOSITION_SINGLE_NO_BORDER:
        {
          g.setColor( background );
          g.fillRoundRect( 0, 0, getWidth(), getHeight(), ListStyleField.CORNER_RADIUS, ListStyleField.CORNER_RADIUS );
          break;
        }
        case ListStyleField.DRAWPOSITION_SINGLE:
        {
          g.setColor( background );
          g.fillRoundRect( 0, 0, getWidth(), getHeight(), ListStyleField.CORNER_RADIUS, ListStyleField.CORNER_RADIUS );
          g.setColor( ListStyleField.COLOR_BORDER );
          g.drawRoundRect( 0, 0, getWidth(), getHeight(), ListStyleField.CORNER_RADIUS, ListStyleField.CORNER_RADIUS );
          break;
        }
        default:
        {
          g.setColor( background );
          g.fillRect( 0, 0, getWidth(), getHeight() );
          break;
        }

      }

      //g.setColor( 0x00FF0000 );
      //g.drawRect( 0, 0, getWidth(), getHeight() );

    }
    finally
    {
      g.setColor( oldColour );
    }
  }

  protected void drawFocus( Graphics g, boolean on )
  {
    boolean oldDrawStyleFocus = g.isDrawingStyleSet( Graphics.DRAWSTYLE_FOCUS );
    try
    {
      if ( on )
      {
        g.setDrawingStyle( Graphics.DRAWSTYLE_FOCUS, true );
      }
      paintBackground( g );
      paint( g );
    }
    finally
    {
      g.setDrawingStyle( Graphics.DRAWSTYLE_FOCUS, oldDrawStyleFocus );
    }
  }

  protected int getVPadding()
  {
    int vPadding = 0;
    switch ( this.drawPosition )
    {
      case DRAWPOSITION_TOP:
      {
        vPadding = VPADDING * 3;
        break;
      }
      case DRAWPOSITION_BOTTOM:
      {
        vPadding = VPADDING * 3;
        break;
      }
      case DRAWPOSITION_MIDDLE:
      {
        vPadding = VPADDING * 2;
        break;
      }
      case DRAWPOSITION_SINGLE:
      default:
      {
        vPadding = VPADDING * 4;
        break;
      }
    }
    return vPadding;
  }

  protected int getTopPos()
  {
    int topPos = 0;
    switch ( this.drawPosition )
    {
      case DRAWPOSITION_TOP:
      {
        topPos = VPADDING * 2;
        break;
      }
      case DRAWPOSITION_BOTTOM:
      {
        topPos = VPADDING;
        break;
      }
      case DRAWPOSITION_MIDDLE:
      {
        topPos = VPADDING;
        break;
      }
      case DRAWPOSITION_SINGLE:
      default:
      {
        topPos = VPADDING ;
        break;
      }
    }
    return topPos;
  }

  public void setDirty( boolean dirty ) { }
  public void setMuddy( boolean muddy ) { }

  protected int sizeFont( String labelText, int labelWidth )
  {
    int fontSize = this.fontSize;
    Font lableFont = FONT_LEFT_LABLE.derive( FONT_LEFT_LABLE.isBold() ? Font.BOLD : Font.PLAIN, fontSize, Ui.UNITS_px );
    int textWidth = lableFont.getAdvance( labelText );
    while ( textWidth > labelWidth && fontSize > 2 )
    {
      fontSize--;
      lableFont = FONT_LEFT_LABLE.derive( FONT_LEFT_LABLE.isBold() ? Font.BOLD : Font.PLAIN, fontSize, Ui.UNITS_px );
      textWidth = lableFont.getAdvance( labelText );
    }
    return fontSize;
  }
  
  protected void onFocus( int direction )
  {
    try
    {
      super.onFocus( direction );
      if ( isVisible() )
      {
        invalidate();
      }
    }
    catch ( Exception ex )
    {
      String msg = "ListStyleManager::onFocus failed " + ex.toString();
      Logger.logError(msg);
    }
  }

  protected void onUnfocus()
  {
    try
    {
      super.onUnfocus();
      if ( isVisible() )
      {
        invalidate();
      }
    }
    catch ( Exception ex )
    {
      String msg = "ListStyleManager::onFocus failed " + ex.toString();
      Logger.logError(msg);
    }
  }  
}



