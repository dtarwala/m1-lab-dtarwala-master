package game2048;

public class Vector2 {

  public int x;
  public int y;

  public Vector2(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public static Vector2 add(Vector2 v0, Vector2 v1) {
    return new Vector2(v0.x + v1.x, v0.y + v1.y);
  }

  public void add(Vector2 other) {
    this.x += other.x;
    this.y += other.y;
  }

  public Vector2 sign() {
    return new Vector2((int) Math.signum(x), (int) Math.signum(y));
  }

  @Override
  public String toString() {
    return "(" + Integer.toString(x) + ", " + Integer.toString(y) + ")";
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Vector2)) {
      return false;
    }
    return x == ((Vector2) o).x && y == ((Vector2) o).y;
  }
}
