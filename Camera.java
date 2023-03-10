public class Camera
{
   //add rotation implementation
   public Vector2 screenSize;
   
   public float size;
   public Vector2 position;
   public float rotation;
   public float funkyRotation;
   
   public Camera(Vector2 screenSize, float size, Vector2 position)
   {
      this.screenSize = screenSize;
      this.size = size;
      this.position = position;
      rotation = 0;
   }
   public String toString()
   {
      return size + position.toString();
   }
   public void CenterCamera(Vector2 pos)
   {
      position = new Vector2(pos.x * size - screenSize.x/2f, pos.y * size - screenSize.y/2f);
   }
}