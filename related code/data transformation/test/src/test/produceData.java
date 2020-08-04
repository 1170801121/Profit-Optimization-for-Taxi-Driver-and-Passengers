package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class produceData {
  
  public produceData() throws IOException {
    File file = new File("datatxt.txt");
    File outFile = new File("output.txt");
    String line;
    BufferedReader br = null;
    BufferedWriter writer = null;
    Map<String, Integer> countMap = new HashMap<String, Integer>();
    List<pair> timeOrderList = new ArrayList<pair>();
    
    try {
      br =  new BufferedReader(new FileReader(file));
      while ((line = br.readLine()) != null) {
//        System.out.println(line);
        String[] firstStrings = line.split(",");
        String[] endTimeString = firstStrings[1].split(";");
        if (endTimeString.length > 1 && countMap.containsKey(endTimeString[1])) {
          countMap.put(endTimeString[1], countMap.get(endTimeString[1]) + 1);
          System.out.println(endTimeString[1]);
        } 
        if (endTimeString.length > 1 && !countMap.containsKey(endTimeString[1])) {
          countMap.put(endTimeString[1], 1);
          System.out.println(endTimeString[1]);
        }
      }
      
      for(String keyString : countMap.keySet()) {
        String[] keyStrings = keyString.split(":");
//        System.out.print(countMap.get(keyString).intValue());
        pair pair = new pair(Integer.parseInt(keyStrings[0]), Integer.parseInt(keyStrings[1]), countMap.get(keyString).intValue());
        timeOrderList.add(pair);
        
      }
//      static class TimeComparator implements Comparator<Object> {  
//        public int compare(Object object1, Object object2) {// 实现接口中的方法  
//        pair p1 = (pair) object1; // 强制转换  
//        pair p2 = (pair) object2;  
//        return new Double(p1.price).compareTo(new Double(p2.price));  
//        }
//      }
       
      
      Collections.sort(timeOrderList);
      
      writer = new BufferedWriter(new FileWriter(outFile));
//      for (String keyString : countMap.keySet()) {
//        writer.write(keyString + "  " + countMap.get(keyString));
//        writer.newLine();
//      }
      for (int i = 0; i < timeOrderList.size(); i++) {
        writer.write(timeOrderList.get(i).getTime().getHour() + ":" + timeOrderList.get(i).getTime().getMinute() +" "+ timeOrderList.get(i).getNumber());
        writer.newLine();
      }
      
    }
    catch (Exception e) {
    } finally {
      try {
        if (br != null) {
          br.close();
        }
        if (writer != null) {
          writer.close();
        }
      } catch (IOException e1) {
        e1.printStackTrace();
      }
    }
  }
  
  public static void main(String[] args) throws IOException {
    produceData producing = new produceData();
  }
}
