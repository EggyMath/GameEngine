import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import java.util.*;
import java.lang.*;


/**
   To Do:
   Add camera class that implements rotation and size difference and can center
   Change updates to be based off of timechange not expected time change to ensure momentum smooth
   Make collision class to hold collision info to help solve collisions and multiple collisions at same time
      Ensure that collisions are consistent with self
   A superscript class for onCollision() and others things (Just a regular class to inherit from)
**/

public class CameraRenderer extends JPanel
{
   static JFrame frame = new JFrame("Display");
   //private Vector2 camera = new Vector2();
   private int ticks = 100;
   public int delayTime;
   public KeyInput keyHandler = new KeyInput();
   public ArrayList<GameObject> hierarchy = new ArrayList<GameObject>();
   
   Camera camera = new Camera(new Vector2(1000, 700), 1f, new Vector2(10f, 10f));
   Transform transform = new Transform(null);
   
   public static void main(String[] args)
   {
      System.out.println("Hi Eddie");
      CameraRenderer r = new CameraRenderer();
      frame.add(r);
      frame.pack();
      frame.setSize(1000, 700);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
      r.init();
   }
   public void init()
   {
      /*GameObject go = new GameObject();
      go.transform.position = new Vector2(100, 100);
      go.size = new Vector2(30, 30);
      go.transform.acceleration = new Vector2(100, 500);
      //loses velocity on collisions somehow with high accel
      go.transform.velocity = new Vector2(0, 0);
      go.collider.enabled = true;
      go.color = Color.green;
      go.collider.bounciness = 1.0f;
      //go.collider.continuous = true;
      hierarchy.add(go);
      GameObject go10 = new GameObject();
      go10.transform.position = new Vector2(200, 100);
      go10.size = new Vector2(30, 30);
      go10.transform.acceleration = new Vector2(100, 500);
      //loses velocity on collisions somehow with high accel
      go10.transform.velocity = new Vector2(0, 0);
      go10.collider.enabled = true;
      go10.color = Color.green;
      go10.collider.bounciness = 1.0f;
      //go10.collider.continuous = true;
      hierarchy.add(go10);*/
      Color[] colors = new Color[]{Color.green, Color.blue, Color.red, Color.magenta, Color.yellow, Color.orange, Color.green, Color.blue, Color.red, Color.magenta, Color.yellow, Color.orange};
      for(int i = 0; i < 12; i++)
      {
         GameObject go = new GameObject(camera);
         go.transform.position = new Vector2(80 + 44 * i, 50 + 12 * i);
         go.size = new Vector2(20 + i * 2, 20 + i * 2);
         go.collider.sizeDelta = new Vector2(20 + i * 2, 20 + i * 2);
         go.transform.acceleration = new Vector2(100, 500);
         //loses velocity on collisions somehow with high accel
         go.transform.velocity = new Vector2(i * 20, i * 5);
         go.collider.enabled = true;
         go.color = colors[i];
         go.collider.bounciness = 1.0f;
         //go.collider.continuous = true;
         hierarchy.add(go);
      }
      transform.position = new Vector2(500f, 350f);
      GameObject go1 = new GameObject(camera);
      go1.transform.position = new Vector2(500, 650);
      go1.transform.isStatic = true;
      go1.size = new Vector2(1000, 60);
      go1.collider.sizeDelta = new Vector2(1000, 60);
      go1.collider.enabled = true;
      hierarchy.add(go1);
      GameObject go2 = new GameObject(camera);
      go2.transform.position = new Vector2(0, 350);
      go2.transform.isStatic = true;
      go2.size = new Vector2(60, 700);
      go2.collider.sizeDelta = new Vector2(60, 700);
      go2.collider.enabled = true;
      hierarchy.add(go2);
      GameObject go3 = new GameObject(camera);
      go3.transform.position = new Vector2(1000, 350);
      go3.transform.isStatic = true;
      go3.size = new Vector2(60, 700);
      go3.collider.sizeDelta = new Vector2(60, 700);
      go3.collider.enabled = true;
      hierarchy.add(go3);
      GameObject go4 = new GameObject(camera);
      go4.transform.position = new Vector2(500, 0);
      go4.transform.isStatic = true;
      go4.size = new Vector2(1000, 60);
      go4.collider.sizeDelta = new Vector2(1000, 60);
      go4.collider.enabled = true;
      hierarchy.add(go4);
      GameObject go5 = new GameObject(camera);
      go5.transform.position = new Vector2(200, 420);
      go5.transform.isStatic = true;
      go5.size = new Vector2(100, 40);
      go5.collider.sizeDelta = new Vector2(100, 40);
      go5.collider.enabled = true;
      hierarchy.add(go5);
      GameObject go6 = new GameObject(camera);
      go6.transform.position = new Vector2(500, 500);
      go6.transform.isStatic = true;
      go6.size = new Vector2(120, 60);
      go6.collider.sizeDelta = new Vector2(120, 60);
      go6.collider.enabled = true;
      hierarchy.add(go6);
      GameObject go7 = new GameObject(camera);
      go7.transform.position = new Vector2(800, 400);
      go7.transform.isStatic = true;
      go7.size = new Vector2(30, 200);
      go7.collider.sizeDelta = new Vector2(30, 200);
      go7.collider.enabled = true;
      hierarchy.add(go7);
      frame.addKeyListener(keyHandler);
      delayTime = (int)Math.round(1000/ticks);
      repaint();
   }
   public Color getColor(long c)
   {
      int r = (int)(c / 65025);
      int g = (int)((c / 255) - (r * 255));
      int b = (int)(c - (g * 255) - (r * 65025));
      
      return new Color(r,g,b);
   } 
   @Override
   protected void paintComponent(Graphics g)
   {
      super.paintComponent(g);
      delay(delayTime);
      //hierarchy.get(0).color = new Color((int)(255 * (cc/255.0)), 255, 126);//new Color((int)Math.round(Math.random() * 255),(int)Math.round(Math.random() * 255),(int)Math.round(Math.random() * 255));
      camera.CenterCamera(transform.position);
      if(keyHandler.getKey(KeyEvent.VK_EQUALS))
         camera.size += 0.005f;
      if(keyHandler.getKey(KeyEvent.VK_MINUS))
         if(camera.size > 0.005f)
            camera.size -= 0.005f;
      transform.velocity.x = 0;
      //camera controls should be based off rotation
      if(keyHandler.getKey(KeyEvent.VK_D))
         transform.velocity.x += 200f * camera.size;
      if(keyHandler.getKey(KeyEvent.VK_A))
         transform.velocity.x -= 200f * camera.size;
      transform.velocity.y = 0;
      if(keyHandler.getKey(KeyEvent.VK_S))
         transform.velocity.y += 200f * camera.size;
      if(keyHandler.getKey(KeyEvent.VK_W))
         transform.velocity.y -= 200f * camera.size;
      System.out.println(transform.velocity);
      transform.Update(delayTime/1000.0);
      if(keyHandler.getKey(KeyEvent.VK_SHIFT))
         camera.rotation += 0.01f;
      for(int i = 0; i < hierarchy.size(); i++)
      {
         if(!hierarchy.get(i).collider.continuous || !hierarchy.get(i).collider.enabled)
         {
            if(keyHandler.getKey(KeyEvent.VK_SPACE))
               hierarchy.get(i).transform.Update(-delayTime/1000.0);
            else
               hierarchy.get(i).transform.Update(delayTime/1000.0);
         }
         hierarchy.get(i).draw(g);
      }
      for(int i = 0; i < hierarchy.size(); i++)
      {
         if(hierarchy.get(i).collider.enabled)
         {
            for(int j = i + 1; j < hierarchy.size(); j++)
            {
               if(hierarchy.get(j).collider.enabled)
               {
                  if(!hierarchy.get(i).collider.continuous || !hierarchy.get(i).collider.enabled)
                     hierarchy.get(i).collider.boxCollision(hierarchy.get(j).collider);
               }
            }
         }
         if(hierarchy.get(i).collider.continuous)
            hierarchy.get(i).collider.continuousBoxCollisionUpdate(hierarchy, (float)(delayTime/1000.0));
      }
      repaint();
   }
   public static void delay(int n)
   {
      long start = System.currentTimeMillis();
      long endDelay = 0;
      while (endDelay - start < n)
         endDelay = System.currentTimeMillis();
   }
}