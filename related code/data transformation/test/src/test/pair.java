package test;

public class pair implements Comparable<pair>{
  private time time = new time();
  private int number;
  @Override
  public int compareTo(pair o) {
    if(this.time.getHour() > o.time.getHour() || (this.time.getHour() == o.time.getHour()&&this.time.getMinute() >= o.time.getMinute())){
        return 1;
    }
    return -1;
  }
  
  public pair(int hour, int minute, int number) {
    time.setHour(hour);
    time.setMinute(minute);
    this.setNumber(number);
  }

  public int getNumber() {
    return number;
  }

  public time getTime() {
    return time;
  }
  
  public void setNumber(int number) {
    this.number = number;
  }
}
