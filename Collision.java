public class Collision
{
   public Collider col1;
   public Collider col2;
   
   public Transform trans1;
   public Transform trans2;
   
   public Collision(Collider col1, Collider col2, Transform trans1, Transform trans2)
   {
      this.col1 = col1;
      this.col2 = col2;
      this.trans1 = trans1;
      this.trans2 = trans2;
   }
}