public class Collider
{
   public boolean isTrigger;
   public boolean enabled;
   public boolean continuous;
   public GameObject gameObject;
   
   public Vector2 positionSet;
   public Vector2 velocitySet;
   
   public Collider(GameObject gameObject, boolean enabled)
   {
      this.gameObject = gameObject;
      this.enabled = enabled;
   }
   
   public void OnCollision()
   {
   
   }
   
   public void boxCollision(RectCollider col)
   {
      //check if it passed a box side and set velocity to 0 and reset position
   }
   public boolean continuousBoxCollision(RectCollider col, float dt)
   {
      return false;
   }
   public void circleCollision(CircleCollider col)
   {
      
   }
}