package contest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Path {
  private List<Position> positions = new ArrayList<Position>();
  private Map<Integer, Double> busyTime = new HashMap<Integer, Double>();
  private Port port;
  private double usedTime = 0;//�ó�����ʱ
  private double run = 5.0;//����ʱ��
  private double t0 = 3.6;//����һ����λʱ��
  private double C1 = 3.5;//������
  private double C2 = 5;//ͣ��λ
  private double V0 = 1.5;//��ƽ������
  private int index;//�����ţ�1�ſ�������
  double k = positions.size() + 1; //�ó�����λ��
  
  public Path(Port port, int x) {
    this.port = port;
    this.index = x;
  }
  
  public Map<Integer, Double> getBusyTime() {
    return busyTime;
  }
  
  public void generateRandomTime() {
    for (Position position : positions) {
      position.calculate();
      position.getIn();
      int positionIndex = position.getNumber();
      double humanTime = Math.sqrt(positionIndex*positionIndex*C2*C2 + (index)*(index) * C1*C1)/V0;
      busyTime.put(positionIndex, /*busyTime.get(positionIndex) +*/ position.getTime() + humanTime + run + (k - positionIndex) * t0 + (positionIndex + 1)*t0);
    }
  }
  
  public void build(int x) {
    if (positions.size() == 0)
      for (int i = 0; i < x; i++) {
        positions.add(new Position(port, i));
        busyTime.put(i, 0.0);
      }
  }
  
  //һ�ֵ�ʱ��
  public double getUsedTime() {
    
    double human1 = Math.sqrt(C2*C2 + (index)*(index) * C1*C1)/V0;//���ߵ���1�ų���·����ʱ
    double human0 = Math.sqrt((index)*(index) * C1*C1)/V0;
    if (positions.size() == 1) {
      usedTime = human0 + 1*t0 + positions.get(0).getTime() + (k)*t0;
    } else {
      if (human1 + 2*t0 + positions.get(1).getTime() < human0 + t0 + positions.get(0).getTime() + run) {
        //k*t0 + (k-1) * t0��ǰ���ų����뵽��λ��ʱ��
        usedTime = k*t0 + (k-1) * t0 +  human0 +  positions.get(0).getTime() + run + run;
      } else {
        usedTime = k*t0 + (k-1) * t0 +  human1 + 2*t0 + positions.get(1).getTime() + run;
      }
    }
    
    if (positions.size() > 2) 
      for (int i = 2; i < positions.size(); i++) {
//        double human_i = C * Math.sqrt(1 + (index - 1)*3 + i * i);
//        double human_i1 = C * Math.sqrt(1 + (index - 1)*3 + (i-1)*(i-1));
        double human_i = Math.sqrt(i*i*C2*C2 + (index)*(index) * C1*C1)/V0;
        double human_i1 = Math.sqrt((i - 1)*(i - 1)*C2*C2 + (index)*(index) * C1*C1)/V0;
        if (human_i + (i + 1) * t0 + positions.get(i).getTime() < human_i1 + i * t0 + positions.get(i - 1).getTime() + run) {
          //(k - i) * t0 �ǽ��뵽��λʱ��
          usedTime += run; //(i + 1) * t0 �Ǵӳ�λ����ʱ��
        } else {
          usedTime += human_i + (i + 1) * t0 + positions.get(i).getTime() - (human_i1 + i * t0 + positions.get(i - 1).getTime() + run) + run;
        }
      }
    return usedTime;
  }
}
