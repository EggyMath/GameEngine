import java.util.*;
import java.awt.event.*;
public class KeyInput extends KeyAdapter
{
   private HashMap<Integer,Boolean> keys = new HashMap<Integer,Boolean>();
   public KeyInput()
   {
      for(int i = 0; i < 256; i++)
         keys.put(i, false); 
   }
   public void keyPressed(KeyEvent e)
   {
      keys.put(e.getKeyCode(), true);
   }
   public void keyReleased(KeyEvent e)
   {
      keys.put(e.getKeyCode(), false);
   }
   public boolean getKey(int key)
   {
      return keys.get(key);
   }
}