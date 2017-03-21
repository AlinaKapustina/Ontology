package main_package;
import com.owl.ControllerOntology;
import java.io.IOException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import view.MainWindow;

public class Owl {
 public static void main(String[] args) throws OWLOntologyStorageException, IOException  {
     ControllerOntology controllerOntology = new ControllerOntology();
     Controller controller = new Controller();
     controller.setControllerOntology(controllerOntology);
     MainWindow mainWindow = new MainWindow(controller);
     mainWindow.setVisible(true);
 }
   
}
