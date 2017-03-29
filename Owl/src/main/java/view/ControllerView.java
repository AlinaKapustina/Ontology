
package view;

import data.UserSet;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import main_package.Controller;

public class ControllerView implements ActionListener{
    
    private final Controller controller;
    private final UserSet userSet;

    public ControllerView(Controller controller, UserSet userSet) {
        this.controller = controller;
        this.userSet = userSet;
    }
    
   

    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
    
}
