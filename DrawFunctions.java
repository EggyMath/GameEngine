import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

public class DrawFunctions extends JPanel
{
   public static void rectDraw (Graphics g, boolean fill, float x, float y, float w, float l, double rads)
   {
      double ang = Math.atan(l/(double)w);
      double d = Math.sqrt(((w/2)*(w/2)) + (l/2)*(l/2));
      Point[] pts = new Point[4];
      pts[0] = new Point((int)(x + d * Math.cos(Math.PI - ang + rads)), (int)(y + d * Math.sin(Math.PI - ang + rads)));
      pts[1] = new Point((int)(x + d * Math.cos(ang + rads)), (int)(y + d * Math.sin(ang + rads)));
      pts[2] = new Point((int)(x + d * Math.cos(Math.PI + ang + rads)), (int)(y + d * Math.sin(Math.PI + ang + rads)));
      pts[3] = new Point((int)(x + d * Math.cos(rads - ang)), (int)(y + d * Math.sin(rads - ang)));
      Point[] opts = pts;
      int[] xs = new int[]{pts[0].x, pts[1].x, pts[3].x, pts[2].x};
      int[] ys = new int[]{pts[0].y, pts[1].y, pts[3].y, pts[2].y};
      if (fill)
         g.fillPolygon(xs, ys, 4);
      else
         g.drawPolygon(xs, ys, 4);
   }
}