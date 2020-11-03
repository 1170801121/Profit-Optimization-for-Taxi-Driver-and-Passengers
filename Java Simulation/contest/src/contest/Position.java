package contest;

public class Position {
  private double randomTime = 0;
  private Port port;
  private int number;//об╠Й
  
  public Position(Port port, int x) {
    this.port = port;
    this.number = x;
  }
  
  public int getNumber() {
    return number;
  }
  
  public double calculate() {
    double random = Math.random();
    double result = 0; 
//    System.out.println("random  " + random);
    for (int i = 1; i < port.getList().size(); i++) {
      if (random >= port.getList().get(i - 1) && random < port.getList().get(i)) {
//        System.out.println("res " + ((random - port.getList().get(i - 1))/(random - port.getList().get(i)) ));
        result = ((random - port.getList().get(i - 1))/(port.getList().get(i) - port.getList().get(i - 1)) )* 
            3 + 3 * i;
      }
    }
//    System.out.println("result  " + result);
    return result;
  }
  
  public void getIn() {
    randomTime = this.calculate();
  }
  
  public double getTime() {
    return randomTime;
  }
  
}
