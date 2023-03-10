public class Transform
{
   //need smooth collisions for smooth movement
   //public long previousTime;

   public Vector2 position;
   public Vector2 velocity;
   public Vector2 acceleration;
   public double rotation;
   
   public boolean isStatic;
   public GameObject gameObject;
   
   public Transform(GameObject gameObject)
   {
      //previousTime = System.currentTimeMillis();
      this.gameObject = gameObject;
      position = new Vector2();
      velocity = new Vector2();
      acceleration = new Vector2();
   }
   //dt = time passed
   public void Update(double dt)
   {
      //dt = ((float)(System.currentTimeMillis() - previousTime))/1000f;
      //previousTime = System.currentTimeMillis();
      if (gameObject != null)
      {
         if (gameObject.collider.enabled)
         {
         //use 1/att + vt for position to stop momentum loss
            if(gameObject.collider.velocitySet != null)
            {
               velocity = gameObject.collider.velocitySet;
               gameObject.collider.velocitySet = null;
            }
            if(gameObject.collider.positionSet != null)
            {
               position = gameObject.collider.positionSet;
               gameObject.collider.positionSet = null;
            }
         }
      }
      if (!isStatic)
      {
         velocity = velocity.add(acceleration.multiply((float)dt));
         position = position.add(velocity.multiply((float)dt));
      }
   }
   
   public void draw(double scale)
   {
   
   }
}