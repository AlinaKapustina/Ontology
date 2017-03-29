package view;

import data.UserSet;
import main_package.Controller;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.UIManager;

public class MainWindow extends JFrame {

    private JMenuBar menuBar;
    private JToolBar toolBar;
    private Controller controller;
    JScrollPane scrollPane;
    
    @Override
    public void paint(Graphics g) {
        super.paintComponents(g);
        scrollPane.updateUI();
    }

    public MainWindow(Controller controller) {

        super("Калькулятор тарифов");
        this.controller = controller;
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception ex) {

        }
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(new Dimension(3*screenSize.width / 4,3* screenSize.height / 4));
        setMinimumSize(new Dimension(3*screenSize.width / 4, 3*screenSize.height / 4));
        setBounds(0, 0,3*screenSize.width / 4, 3*screenSize.height / 4);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        View view = new View();
        scrollPane = new JScrollPane(view);
        add(scrollPane);
        
        toolBar = new JToolBar();
        add(toolBar, BorderLayout.PAGE_START);
        createMenuBar();

    }

    final void createMenuBar() {
        menuBar = new JMenuBar();
        Font font = new Font("Verdana", Font.PLAIN, 11);
        JMenu fileMenu = new JMenu("");
        fileMenu.setFont(font);
        JMenu newMenu = new JMenu("New");
        newMenu.setFont(font);
        fileMenu.add(newMenu);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
        menuBar.setVisible(true);
    }

    final void createView() {

        JPanel panel = new JPanel(null);
        // panel.setLayout(new GridLayout(1,4 ));
        add(panel);
        JLabel mess_label = new JLabel("Количество сообщений");
        mess_label.setSize(new Dimension(100, 50));
        mess_label.setBounds(10, 10, 160, 60);
        JTextField mess = new JTextField();
        mess.setSize(new Dimension(100, 50));
        mess.setBounds(180, 10, 100, 60);
        JLabel call_label = new JLabel("Время звонков");
        call_label.setSize(new Dimension(100, 50));
        call_label.setBounds(10, 75, 160, 60);
        JTextField call = new JTextField();
        call.setSize(new Dimension(100, 50));
        call.setBounds(180, 75, 100, 60);

        JButton count = new JButton("Расчитать");
        count.setSize(new Dimension(100, 100));
        count.setBounds(200, 200, 100, 50);
//        count.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                boolean error = false;
//                String text = call.getText();
//                int means_call = 0;
//                try {
//                    means_call = Integer.parseInt(text);
//                } catch (NumberFormatException ex) {
//                    JOptionPane.showMessageDialog(panel, "Неверный формат данных в поле Время звонков", "Ошибка", JOptionPane.ERROR_MESSAGE);
//                    call.setText("");
//                    error = true;
//                }
//                text = mess.getText();
//                int means_mess = 0;
//                try {
//                    means_mess = Integer.parseInt(text);
//                } catch (NumberFormatException ex) {
//                    JOptionPane.showMessageDialog(panel, "Неверный формат данных в поле Количество сообщений", "Ошибка", JOptionPane.ERROR_MESSAGE);
//                    mess.setText("");
//                    error = true;
//                }
//                if (!error) {
//                    List<String> giveAnswer = controller.giveAnswer(new UserSet(means_mess, means_call));
//                    if (giveAnswer == null) {
//                        JOptionPane.showMessageDialog(panel, "Не найдено тарифов по вашему запросу. Попробуйте ввести другие данные", "Информация", JOptionPane.INFORMATION_MESSAGE);
//                        mess.setText("");
//                        call.setText("");
//                    } else {
//                        JPanel panelAnswer = new JPanel(null);
//                        for (int i = 0; i < giveAnswer.size(); i++) {
//                            JLabel label = new JLabel(giveAnswer.get(i));
//                            JButton button = new JButton("Подробнее");
//                            label.setSize(new Dimension(150, 50));
//                            label.setBounds(10, 10 + i * 50, 150, 50);
//                            button.setSize(new Dimension(150, 50));
//                            button.setBounds(170, 10 + i * 50, 150, 50);
//                            panelAnswer.add(label);
//                            panelAnswer.add(button);
//                        }
//                        add(panelAnswer);
//                        panel.setVisible(false);
//                        panelAnswer.setVisible(true);
//                    }
//                }
//            }
//        });
//        panel.add(call_label);
        panel.add(call);
        panel.add(mess_label);
        panel.add(mess);
        panel.add(count);

    }

   
}
