package com.mlhsoftware.BingoCaller.ui;

import net.rim.device.api.ui.*;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.component.*;

/**
 * 
 */
public class AboutbarManager extends Manager
{
  private static final int ICON_MARGIN = 5;
  private static final int TEXT_MARGIN = 3;
  private static final int LEFT_MARGIN = 4;
  private static final int TOP_MARGIN = 14;
  private static final int BOTTOM_MARGIN = 14;
  private static final int RIGHT_MARGIN = 4;
  
  
  private int fontColor = Color.WHITE; 

  private String extraLine;
  public AboutbarManager( Bitmap icon, String appName, String appVersion, String extraLine )
  {
    super( 0 );
    this.extraLine = extraLine;
    Font boldFont = Font.getDefault().derive( Font.BOLD, 10, Ui.UNITS_pt, Font.ANTIALIAS_STANDARD, 0 );

    int height = ( boldFont.getHeight() * 3 ) + TEXT_MARGIN;

    if ( icon.getHeight() > height )
    {
      icon = bestFit( icon, height, height );
    }
    BitmapField iconImg = new BitmapField( icon );
    add( iconImg );
    
    LabelField label = new LabelField( appName + " ", LabelField.FIELD_HCENTER );
    label.setFont( boldFont );
    add( label );

    label = new LabelField( appVersion, LabelField.FIELD_HCENTER );
    label.setFont( Font.getDefault().derive( Font.PLAIN, 10, Ui.UNITS_pt, Font.ANTIALIAS_STANDARD, 0 ) );
    add( label );

    label = new LabelField( extraLine, LabelField.FIELD_HCENTER );
    label.setFont( Font.getDefault().derive( Font.PLAIN, 8, Ui.UNITS_pt, Font.ANTIALIAS_STANDARD, 0 ) );
    add( label );

    label = new LabelField( "MLH Software", LabelField.FIELD_HCENTER );
    label.setFont( Font.getDefault().derive( Font.PLAIN, 10, Ui.UNITS_pt, Font.ANTIALIAS_STANDARD, 0 ) );
    add( label );
  }

  public AboutbarManager( long style )
  {
    super( USE_ALL_WIDTH | style );
  }
  
  public void setFontColor( int color )
  {
    this.fontColor = color;
  }

  protected void sublayout( int width, int height )
  {
    int availableWidth = width - ( LEFT_MARGIN + RIGHT_MARGIN );
    int availableHeight = height - ( TOP_MARGIN + BOTTOM_MARGIN );

    int numFields = getFieldCount();


    int maxHeight = 0;

    // Now position the fields
    switch ( numFields )
    {
      case 5:
      {
        Field iconField = getField( 0 );
        Field appNameField = getField( 1 );
        Field versionField = getField( 2 );
        Field extraField = getField( 3 );
        Field vendorField = getField( 4 );

        layoutChild( iconField,    availableWidth, availableHeight );
        layoutChild( appNameField, availableWidth, availableHeight );
        layoutChild( versionField, availableWidth, availableHeight );
        layoutChild( vendorField,  availableWidth, availableHeight );
        layoutChild( extraField, availableWidth, availableHeight );

        int iconWidth = iconField.getWidth();
        int iconHeight = iconField.getHeight();
        int appNameWidth = appNameField.getWidth();
        int appNameHeight = appNameField.getHeight();
        int versionWidth = versionField.getWidth();
        int vendorHeight = vendorField.getHeight();
        int extraHeight = extraField.getHeight();
        if ( this.extraLine.length() == 0 )
        {
          extraHeight = 0;
        }

        int extraWidth = extraField.getWidth();

        int finalWidth = Math.max( iconWidth + appNameWidth + versionWidth + ICON_MARGIN, iconWidth + extraWidth + ICON_MARGIN );

        maxHeight = Math.max( iconHeight + ( TOP_MARGIN + BOTTOM_MARGIN ), appNameHeight + vendorHeight + extraHeight + ( TOP_MARGIN + BOTTOM_MARGIN + TEXT_MARGIN ) );

        int lableTop = ( maxHeight / 2 ) - ( appNameHeight + vendorHeight + extraHeight + TEXT_MARGIN ) / 2;

        int leftPos = (availableWidth / 2) - ( finalWidth / 2 );
        setPositionChild( iconField, leftPos, ( maxHeight - iconHeight ) / 2 );
        
        leftPos = leftPos + iconWidth + ICON_MARGIN;
        setPositionChild( appNameField, leftPos, lableTop );
        
        leftPos = leftPos + appNameWidth;
        setPositionChild( versionField, leftPos, lableTop );

        leftPos = leftPos - appNameWidth;
        lableTop = lableTop + appNameHeight + TEXT_MARGIN;
        setPositionChild( extraField, leftPos, lableTop );


        lableTop = lableTop + extraHeight + TEXT_MARGIN;
        setPositionChild( vendorField, leftPos, lableTop );
        
        
        break;
      }

    }
    setExtent( width, maxHeight );
  }


  private static int[] rescaleArray( int[] ini, int x, int y, int x2, int y2 )
  {
    int out[] = new int[x2*y2];
    for ( int yy = 0; yy < y2; yy++ )
    {
      int dy = yy * y / y2;
      for ( int xx = 0; xx < x2; xx++ )
      {
        int dx = xx * x / x2;
        out[( x2 * yy ) + xx] = ini[( x * dy ) + dx];
      }
    }
    return out;
  }


  public static Bitmap resizeBitmap( Bitmap image, int width, int height )
  {
    // Note from DCC:
    // an int being 4 bytes is large enough for Alpha/Red/Green/Blue in an 8-bit plane...
    // my brain was fried for a little while here because I am used to larger plane sizes for each
    // of the color channels....
    //

    //Need an array (for RGB, with the size of original image)
    //
    int rgb[] = new int[image.getWidth()*image.getHeight()];

    //Get the RGB array of image into "rgb"
    //
    image.getARGB( rgb, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight() );

    //Call to our function and obtain RGB2
    //
    int rgb2[] = rescaleArray( rgb, image.getWidth(), image.getHeight(), width, height );

    //Create an image with that RGB array
    //
    Bitmap temp2 = new Bitmap( width, height );

    temp2.setARGB( rgb2, 0, width, 0, 0, width, height );

    return temp2;
  }

  public static Bitmap bestFit( Bitmap image, int maxWidth, int maxHeight )
  {

    // getting image properties
    int w = image.getWidth();
    int h = image.getHeight();

    //  get the ratio
    int ratiow = 100 * maxWidth / w;
    int ratioh = 100 * maxHeight / h;

    // this is to find the best ratio to
    // resize the image without deformations
    int ratio = Math.min( ratiow, ratioh );

    // computing final desired dimensions
    int desiredWidth = w * ratio / 100;
    int desiredHeight = h * ratio / 100;

    //resizing
    return resizeBitmap( image, desiredWidth, desiredHeight );
  }

  protected void paint( Graphics g )
  {
    int oldColor = g.getColor();
    Font oldFont = g.getFont();
    try
    {
      g.setColor( this.fontColor );
      super.paint( g );
    }
    finally
    {
      g.setColor( oldColor );
      g.setFont( oldFont );
    }
  }
}
