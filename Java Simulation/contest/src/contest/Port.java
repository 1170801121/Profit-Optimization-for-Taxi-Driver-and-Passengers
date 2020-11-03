package contest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Port {
  
  private List<Double> calculateList = new ArrayList<Double>();
  private CarPosition carPositions = new CarPosition(this);
  
  public void constructSystem() {
    File file = new File("input.txt");
    String line;
    BufferedReader br = null;
    try {
      br =  new BufferedReader(new FileReader(file));
      while ((line = br.readLine()) != null) {
        String[] stringArray = line.split("\\s");
        calculateList.add(Double.parseDouble(stringArray[8]));
//        System.out.println(calculateList.get(0));
      }
    }
    catch (Exception e) {
      try {
        br.close();
      } catch (IOException e1) {
        e1.printStackTrace();
      }
    }
  }
  
  public List<Double> getList() {
    return calculateList;
  }
  
  public void simulate(int x) {//x 为单车道车位数量
    int waitingCar = 8000;
    long useTime = 0;
    long onceTime = 0;
    List<Double> busyTimePath_1 = new ArrayList<Double>();
    List<Double> busyTimePath_2 = new ArrayList<Double>();
    List<Double> busyRatio_1 = new ArrayList<Double>();
    List<Double> busyRatio_2 = new ArrayList<Double>();
    for (int i = 0; i < x; i++) {
      busyRatio_1.add(0.0);
      busyRatio_2.add(0.0);
      busyTimePath_1.add(0.0);
      busyTimePath_2.add(0.0);
    }
    for (int i = 0; i < 1000; i++) {
    
      while(waitingCar > 0) {
        waitingCar -= carPositions.getPositions();
        carPositions.construct(x);
        carPositions.generateRandomTime();
        
        for (int j = 0; j < carPositions.getPath1BusyTime().size(); j++) {
          Map<Integer, Double> map = carPositions.getPath1BusyTime();
          busyTimePath_1.set(j, busyTimePath_1.get(j) + map.get(j));
        }
        for (int j = 0; j < carPositions.getPath2BusyTime().size(); j++) {
          Map<Integer, Double> map = carPositions.getPath2BusyTime();
          busyTimePath_2.set(j, busyTimePath_2.get(j) + map.get(j));
        }
        
        useTime += carPositions.getUsedTime();
        onceTime += carPositions.getUsedTime();
      }
      for (int j = 0; j < x; j++) {
        busyRatio_1.set(j, busyRatio_1.get(j) +  busyTimePath_1.get(j)/onceTime);
        busyRatio_2.set(j, busyRatio_2.get(j) +  busyTimePath_2.get(j)/onceTime);
      }
      waitingCar = 8000;
      onceTime = 0;
      for (int j = 0; j < x; j++) {
        busyTimePath_1.set(j, 0.0);
        busyTimePath_2.set(j, 0.0);
      }
    }
    BufferedWriter br = null;
    File file = new File("output.txt");
    try {
       br = new BufferedWriter(new FileWriter(file));
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    System.out.println("平均时间：" + useTime/1000.0);
    System.out.println("1车道各车位忙闲率：");
    for (int i = 0; i < x; i++) {
//        Map<Integer, Double> map = carPositions.getPath1BusyTime();
      System.out.println("1车道" + i +"号 " + busyRatio_1.get(i)/1000.0);
    }
    System.out.println("2车道各车位忙闲率：");
    for (int i = 0; i < x; i++) {
//        Map<Integer, Double> map = carPositions.getPath2BusyTime();
      System.out.println("2车道" + i +"号 " + busyRatio_2.get(i)/1000.0);
    }
    try {
      br.write(useTime/1000.0 + "\n");
      br.newLine();
      for (int i = 0; i < x; i++) {
        br.write(busyRatio_1.get(i)/1000.0 + "\n");
        br.newLine();
      }
      for (int i = 0; i < x; i++) {
        br.write(busyRatio_2.get(i)/1000.0 + "\n");
        br.newLine();
      }
      br.newLine();
      
      
      
    } catch (IOException e) {
      e.printStackTrace();
    } finally{
      try {
        br.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    };
  }
  
  public static void main(String[] args) {
    Port port = new Port();
    port.constructSystem();
    port.simulate(4);
  }
  
}
