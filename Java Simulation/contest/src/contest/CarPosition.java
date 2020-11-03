package contest;

import java.util.Map;

public class CarPosition {
  private Path path_1;
  private Path path_2;
  private int number = 0; // 总车位数
  private Port port;
  
  public CarPosition(Port port) {
    this.port = port;
    path_1 = new Path(port, 1);
    path_2 = new Path(port, 2);
  }
  
  public int getPositions() {
    return number;
  }
  
  public void construct(int x) {
    path_1.build(x);
    path_2.build(x);
    number = 2 * x;
  }
  
  public void generateRandomTime() {
    path_1.generateRandomTime();
    path_2.generateRandomTime();
  }
  
  public Map<Integer, Double> getPath1BusyTime() {
    return path_1.getBusyTime();
  }
  
  public Map<Integer, Double> getPath2BusyTime() {
    return path_2.getBusyTime();
  }
  
  public double getUsedTime() {
    if (path_1.getUsedTime() > path_2.getUsedTime()) {
//      System.out.print(path_1.getUsedTime());
//      System.out.print(path_1.getUsedTime());
//      System.out.print(path_1.getUsedTime());
      return path_1.getUsedTime();
    } else {
//      System.out.print(path_2.getUsedTime());
//      System.out.print(path_2.getUsedTime());
//      System.out.print(path_2.getUsedTime());
      return path_2.getUsedTime();
    }
  }
}
