public class Vector2
{
   float x;
   float y;
   
   public static void main(String[] args)
   {
      Vector2 v = new Vector2(-100, -200);
      v.normalize();
      System.out.println(v.magnitude());
   }
   
   public Vector2()
   {
      x = 0;
      y = 0;
   }
   public Vector2(float x, float y)
   {
      this.x = x;
      this.y = y;
   }
   
   public String toString()
   {
      return "(" + x + ", " + y + ")";
   }
   
   public void normalize()
   {
      double theta = Math.atan2(y,x);
      x = (float)Math.cos(theta);
      y = (float)Math.sin(theta);
   }
   public Vector2 multiply(float m)
   {
      return new Vector2(x * m, y * m);
   }
   public Vector2 add(Vector2 v)
   {
      return new Vector2(x + v.x, y + v.y);
   }
   public float magnitude()
   {
      return (float)Math.sqrt(x*x+y*y);
   }
}