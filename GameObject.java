import java.awt.*;
import java.util.*;
public class GameObject
{
   public Transform transform;
   public RectCollider collider;
   
   public Color color;
   public Vector2 size;
   
   public Camera camera;
   
   public GameObject(Camera camera)
   {
      this.camera = camera;
      transform = new Transform(this);
      collider = new RectCollider(this, false);
      collider.sizeDelta = size;
      color = Color.black;
   }
   public void draw(Graphics g)
   {
      //camera position needs to stay centered in space as well
      //so camera point needs to rotate about orgin
      g.setColor(color);
      //not rotating? or not cenetered?
      Vector2 cameraPos = new Vector2(camera.position.x * (float)Math.cos(camera.rotation) + camera.position.y * (float)Math.sin(camera.rotation), -camera.position.x * (float)Math.sin(camera.rotation) + camera.position.y * (float)Math.cos(camera.rotation));
      float dx = transform.position.x * camera.size - camera.position.x - camera.screenSize.x/2f;
      float dy = transform.position.y * camera.size - camera.position.y - camera.screenSize.y/2f;
      //keeps being 0
      System.out.println(cameraPos);
      DrawFunctions.rectDraw(g, true, dx * (float)Math.cos(camera.rotation) + dy * (float)Math.sin(camera.rotation) + camera.screenSize.x/2f, -dx * (float)Math.sin(camera.rotation) + dy * (float)Math.cos(camera.rotation) + camera.screenSize.y/2f, size.x * camera.size, size.y * camera.size, transform.rotation - camera.rotation + camera.funkyRotation);
   }
   public void colorChangeRandom()
   {
      Random r = new Random();
      
   }
}