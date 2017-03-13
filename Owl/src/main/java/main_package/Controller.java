package main_package;
import com.owl.ControllerOntology;
import data.UserSet;
import java.util.List;


public class Controller {
    ControllerOntology controllerOntology;

    public Controller() {
    }

    public ControllerOntology getControllerOntology() {
        return controllerOntology;
    }

    public void setControllerOntology(ControllerOntology controllerOntology) {
        this.controllerOntology = controllerOntology;
    }
    

   public List<String> giveAnswer(UserSet userSet) {
        List<String> giveAnswer = controllerOntology.giveAnswer(userSet);
        return giveAnswer;
    }
    
   
}
