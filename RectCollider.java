
import java.util.*;
public class RectCollider extends Collider
{
   public Vector2 sizeDelta;
   public float bounciness;
   
   public RectCollider(GameObject gameObject, boolean enabled)
   {
      super(gameObject, enabled);
   }
   
   //work continuous detection for static interactions
   //still should collide if not heading towards box but box is heading towards it
   //also not always bounce to reverse direction like in case above ^
   
   //collision class to save collision information
   //come up with way to better solve collisions so it wont break with multiple collisions
   public void boxCollision(RectCollider col)
   {
      Transform tf = gameObject.transform;
      Transform tf1 = col.gameObject.transform;
      if((enabled && col.enabled) && (!tf.isStatic || !tf1.isStatic))
      {
         float minDistance = 1000f;
         int type = -1;;
         if(((tf.position.y > tf1.position.y && tf.position.y - sizeDelta.y/2f < tf1.position.y + col.sizeDelta.y/2f) 
         || (tf.position.y < tf1.position.y && tf.position.y + sizeDelta.y/2f > tf1.position.y - col.sizeDelta.y/2f))
         && ((tf.position.x > tf1.position.x && tf.position.x - sizeDelta.x/2f < tf1.position.x + col.sizeDelta.x/2f) 
         || (tf.position.x < tf1.position.x && tf.position.x + sizeDelta.x/2f > tf1.position.x - col.sizeDelta.x/2f)))
         {
            minDistance = Math.abs(tf.position.x - (tf1.position.x + col.sizeDelta.x/2f + sizeDelta.x/2f));
            type = 0;
            if(minDistance > Math.abs(tf1.position.x - col.sizeDelta.x/2f - sizeDelta.x/2f - tf.position.x))
            {
               minDistance = Math.abs(tf.position.x - (tf1.position.x - col.sizeDelta.x/2f - sizeDelta.x/2f));
               type = 1;
            }
            if(minDistance > Math.abs(tf1.position.y + col.sizeDelta.y/2f + sizeDelta.y/2f - tf.position.y))
            {
               minDistance = Math.abs(tf.position.y - (tf1.position.y + col.sizeDelta.y/2f + sizeDelta.y/2f));
               type = 2;
            }
            if(minDistance > Math.abs(tf1.position.y - col.sizeDelta.y/2f - sizeDelta.y/2f - tf.position.y))
            {
               minDistance = Math.abs(tf.position.y - (tf1.position.y - col.sizeDelta.y/2f - sizeDelta.y/2f));
               type = 3;
            }
         }
         switch(type)
         {
            case 0:
               //tf.position.x = tf1.position.x + col.sizeDelta.x/2f + sizeDelta.x/2f;
               positionSet = new Vector2(tf1.position.x + col.sizeDelta.x/2f + sizeDelta.x/2f, tf.position.y);
               //tf.velocity.x *= -bounciness;
               velocitySet = new Vector2(tf.velocity.x * - bounciness, tf.velocity.y);
               break;
            case 1:
               //tf.position.x = tf1.position.x - col.sizeDelta.x/2f - sizeDelta.x/2f;
               positionSet = new Vector2(tf1.position.x - col.sizeDelta.x/2f - sizeDelta.x/2f, tf.position.y);
               //tf.velocity.x *= -bounciness;
               velocitySet = new Vector2(tf.velocity.x * - bounciness, tf.velocity.y);
               break;
            case 2:
               //tf.position.y = tf1.position.y + col.sizeDelta.y/2f + sizeDelta.y/2f;
               positionSet = new Vector2(tf.position.x, tf1.position.y + col.sizeDelta.y/2f + sizeDelta.y/2f);
               //tf.velocity.y *= -bounciness;
               velocitySet = new Vector2(tf.velocity.x, tf.velocity.y * -bounciness);
               break;
            case 3:
               //tf.position.y = tf1.position.y - col.sizeDelta.y/2f - sizeDelta.y/2f;
               positionSet = new Vector2(tf.position.x, tf1.position.y - col.sizeDelta.y/2f - sizeDelta.y/2f);
               //tf.velocity.y *= -bounciness;
               velocitySet = new Vector2(tf.velocity.x, tf.velocity.y * -bounciness);
               break;
         }
      }
   }
   //only assumes this object is continuous and other is stationary //may need integrals for this
   //cant do multiple continuous collisions unlesss you track dt
   //should replace transform update in renderer if hit
   
   //need to take into account acceleration and not actually change pos and veloc but save time so when this runs mutliple time it wont break
   //not colliding correctly
   //not seeing x collisions
   
   //make collision class to better handle these?
   public void continuousBoxCollisionUpdate(ArrayList<GameObject> cols, float dt)
   {
      if(dt > 0f)
      {
         Vector2 minInfo = new Vector2(1000, 1000);
         for(int i = 0; i < cols.size(); i++)
         {
            RectCollider col = cols.get(i).collider;
            if(col != this)
            {
               Vector2 info = continuousBoxCollisionTime(col, dt);
               if(info.x >= 0f)
               {
                  if(info.x < minInfo.x)
                  {
                     minInfo = info;
                     System.out.println(cols.get(i).transform.position);
                     System.out.println(gameObject.transform.position);
                     //sometimes only 1 coord lines up but it says its fine
                  }
               }
            }
         }
         Transform tf = gameObject.transform;
         if(minInfo.x <= dt)
         {
            //change velocity
            tf.Update(minInfo.x);
            if(minInfo.y == 1f || minInfo.y == 3f)
               tf.velocity.y *= -bounciness;
            if(minInfo.y == 2f || minInfo.y == 3f)
               tf.velocity.x *= -bounciness;
            //rerun
            continuousBoxCollisionUpdate(cols, dt - minInfo.x);
         }
         else
         {
            tf.Update(dt);
         }
      }
   }
   //returns time, collosion type
   public Vector2 continuousBoxCollisionTime(RectCollider col, float maxDt)
   {
      Vector2 info = new Vector2(-1f, -1f);
      Transform tf = gameObject.transform;
      Transform tf1 = col.gameObject.transform;
      if(col.enabled)
      {
         //not colliding right
         //some collisions are very far away
         Vector2 txs = new Vector2();
         if(tf.velocity.x > 0f && tf.position.x < tf1.position.x)
         {
            float dx1 = tf1.position.x - col.sizeDelta.x/2f - (tf.position.x + sizeDelta.x/2f);
            float dx2 = tf1.position.x + col.sizeDelta.x/2f - (tf.position.x - sizeDelta.x/2f);
            txs = new Vector2(dx1/tf.velocity.x, dx2/tf.velocity.x);
         }
         else if(tf.velocity.x < 0f && tf.position.x > tf1.position.x)
         {
            float dx1 = tf1.position.x + col.sizeDelta.x/2f - (tf.position.x - sizeDelta.x/2f);
            float dx2 = tf1.position.x - col.sizeDelta.x/2f - (tf.position.x + sizeDelta.x/2f);
            txs = new Vector2(dx1/tf.velocity.x, dx2/tf.velocity.x);
         }
         Vector2 tys = new Vector2();
         if(tf.velocity.y > 0f && tf.position.y < tf1.position.y)
         {
            float dy1 = tf1.position.y - col.sizeDelta.y/2f - (tf.position.y + sizeDelta.y/2f);
            float dy2 = tf1.position.y + col.sizeDelta.y/2f - (tf.position.y - sizeDelta.y/2f);
            tys = new Vector2(dy1/tf.velocity.y, dy2/tf.velocity.y);
         }
         else if(tf.velocity.y < 0f && tf.position.y > tf1.position.y)
         {
            float dy1 = tf1.position.y + col.sizeDelta.y/2f - (tf.position.y - sizeDelta.y/2f);
            float dy2 = tf1.position.y - col.sizeDelta.y/2f - (tf.position.y + sizeDelta.y/2f);
            tys = new Vector2(dy1/tf.velocity.y, dy2/tf.velocity.y);
         }
         Vector2 ts = new Vector2(Math.max(txs.x, tys.x), Math.min(txs.y, tys.y));
         //fix this part probably, or its reading wrong dx/dy
         if(txs.x <= 0)
            ts = tys;
         if(tys.x <= 0)
            ts = txs;
         if(ts.x > 0f)
         {
            if(ts.x <= maxDt)
            {
               //1 is y, 2 is x, 3 is xy
               if(txs.x < tys.x)
                  info = new Vector2(ts.x, 1);
               if(tys.x < txs.x)
                  info = new Vector2(ts.x, 2);
               if(tys.x == txs.x)
                  info = new Vector2(ts.x, 3);
            }
         }
      }
      return info;
   }
   public boolean continuousBoxCollision(RectCollider col, float dt)
   {
      Transform tf = gameObject.transform;
      Transform tf1 = col.gameObject.transform;
      if(col.enabled)
      {
      //c
      //would moving current distance velocity * time lead to collosion at any point
      
      //get distances to collision
      //check if that distance is possible in time frame
         Vector2 txs = new Vector2();//range times for: minX,maxX
         if(tf.velocity.x > 0f && tf.position.x < tf1.position.x)
         {
            float dx1 = tf1.position.x - col.sizeDelta.x/2f - (tf.position.x + sizeDelta.x/2f);
            float dx2 = tf1.position.x + col.sizeDelta.x/2f - (tf.position.x - sizeDelta.x/2f);
            txs = new Vector2(dx1/tf.velocity.x, dx2/tf.velocity.x);
         }
         else if(tf.velocity.x < 0f && tf.position.x > tf1.position.x)
         {
            float dx1 = tf.position.x + sizeDelta.x/2f - (tf1.position.x - col.sizeDelta.x/2f);
            float dx2 = tf.position.x - sizeDelta.x/2f - (tf1.position.x + col.sizeDelta.x/2f);
            txs = new Vector2(dx1/tf.velocity.x, dx2/tf.velocity.x);
         }
         Vector2 tys = new Vector2();//range times for: minY, maxY
         if(tf.velocity.y > 0f && tf.position.y < tf1.position.y)
         {
            float dy1 = tf1.position.y - col.sizeDelta.y/2f - (tf.position.y + sizeDelta.y/2f);
            float dy2 = tf1.position.y + col.sizeDelta.y/2f - (tf.position.y - sizeDelta.y/2f);
            tys = new Vector2(dy1/tf.velocity.y, dy2/tf.position.y);
         }
         else if(tf.velocity.y < 0f && tf.position.y > tf1.position.y)
         {
            float dy1 = tf.position.y + sizeDelta.y/2f - (tf1.position.y - col.sizeDelta.y/2f);
            float dy2 = tf.position.y - sizeDelta.y/2f - (tf1.position.y + col.sizeDelta.y/2f);
            tys = new Vector2(dy1/tf.velocity.y, dy2/tf.position.y);
         }
         //find where two ranges agree and take the minimum ts.x is min
         //get if x or y collision and go accordingly
         Vector2 ts = new Vector2(Math.max(txs.x, tys.x), Math.min(txs.y, tys.y));
         if(txs.x <= 0)
            ts = tys;
         if(tys.x <= 0)
            ts = tys;
         if(ts.x > 0f)
         {
            //if(ts.x <= ts.y)
            //{
            if(ts.x <= dt)
            {
               System.out.println(dt);
               tf.Update(ts.x);
               ts.x = dt - ts.x;
               //change velocity based on collision type
               if(txs.x <= tys.x)
                  tf.velocity.y *= -bounciness;
               if(tys.x <= txs.x)
                  tf.velocity.x *= -bounciness;
               //rerun to check if it collides again
               //if(!continuousBoxCollision(col, ts.x))
               tf.Update(ts.x);
               return true;
            }
            //}
         }
      }
      return false;
      //find actual time taken to get there
      //get new velocity and use that for new position-
   }
}