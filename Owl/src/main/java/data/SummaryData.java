
package data;

import java.util.ArrayList;
import java.util.List;

public class SummaryData {
    String tarifName = "";
    List<String> ysluga = new ArrayList<>();
    double cost = 0;

    public String getTarifName() {
        return tarifName;
    }

    public void setTarifName(String tarifName) {
        this.tarifName = tarifName;
    }

    public List<String> getYsluga() {
        return ysluga;
    }

    public void setYsluga(List<String> ysluga) {
        this.ysluga = ysluga;
    }
    
    public void addYsluga(String name){
        this.ysluga.add(name);
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
    
}
