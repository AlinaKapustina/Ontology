package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

public class View extends JPanel {

    private JTextField startTextMessage;
    private JTextField finishTextMessage;
    private JTextField startTextCall;
    private JTextField finishTextCall;
    private JTextField startTextInternet;
    private JTextField finishTextInternet;

    private JTextField numberCallNovObl;
    private JTextField numberMessageNovObl;
    private JTextField numberCallNovOther;
    private JTextField numberMessageNovOther;

    public View() {
        setLayout(new BorderLayout(70, 50));
        JPanel messangePanel = createMessangePanel();
        JPanel callPanel = createCallPanel();
        JPanel internetPanel = createInternetPanel();

        JPanel panel = new JPanel(new GridLayout(1, 3, 20, 20));
        panel.setPreferredSize(new Dimension(800, 400));

        panel.add(messangePanel);
        panel.add(callPanel);
        panel.add(internetPanel);
        add(panel, BorderLayout.NORTH);

        JPanel panelButton = new JPanel(new BorderLayout());
        panelButton.setPreferredSize(new Dimension(800, 100));
        panelButton.setBorder(BorderFactory.createEmptyBorder(0, 200, 40, 200));
        JButton analis = new JButton("Подобрать тариф");
        panelButton.add(analis, BorderLayout.NORTH);
        analis.addActionListener(new ControllerView(this));
        add(panelButton, BorderLayout.SOUTH);
        setPreferredSize(new Dimension(800, 500));
    }

    private JPanel createMessangePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1, 15, 10));
        panel.setSize(new Dimension(400, 400));
        panel.setPreferredSize(new Dimension(300, 400));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
                        "Сообщения"),
                BorderFactory.createEmptyBorder(30, 30, 30, 30)));
        JLabel name = new JLabel("     Количество сообщений в месяц");
        name.setSize(new Dimension(20, 150));
        name.setBounds(0, 0, 20, 150);
        JPanel data = new JPanel();
        data.setLayout(new GridLayout(0, 2, 30, 50));
        data.setBounds(50, 50, 100, 50);

        JPanel startPanel = new JPanel(new GridLayout(1, 2, 0, 0));

        JLabel start = new JLabel("от");
        startTextMessage = new JTextField("10");
        startPanel.add(start);
        startPanel.add(startTextMessage);

        JPanel finishPanel = new JPanel(new GridLayout(1, 2, 0, 0));
        JLabel finish = new JLabel("до");
        finishTextMessage = new JTextField("100");
        finishPanel.add(finish);
        finishPanel.add(finishTextMessage);

        start.setSize(new Dimension(20, 40));
        start.setBounds(0, 0, 20, 40);
        finish.setSize(new Dimension(20, 40));
        finish.setBounds(0, 0, 20, 40);
        startTextMessage.setSize(new Dimension(20, 40));
        startTextMessage.setBounds(0, 0, 20, 40);
        finishTextMessage.setSize(new Dimension(20, 40));
        finishTextMessage.setBounds(0, 0, 20, 40);
        data.add(startPanel);
        data.add(finishPanel);
        JPanel oblNov = new JPanel(new GridLayout(2, 1));
        JLabel messageNumberLabel = new JLabel("     абонентам Новосибирской области");
        numberMessageNovObl = new JTextField();
        oblNov.add(messageNumberLabel);
        oblNov.add(numberMessageNovObl);
        JPanel oblOther = new JPanel(new GridLayout(2, 1));
        JLabel messageNumberLabelOther = new JLabel("     абонентам другого региона");
        numberMessageNovOther = new JTextField();
        oblOther.add(messageNumberLabelOther);
        oblOther.add(numberMessageNovOther);
        JButton hideObl = new JButton("Подробнее" + " ⇓");
        panel.add(hideObl);
        JPanel union = new JPanel(new GridLayout(3, 1, 20, 20));
        union.add(name);
        union.add(data);
        union.add(hideObl);
        panel.add(union);
        JPanel obl = new JPanel(new GridLayout(2, 0, 10, 10));
        obl.add(oblNov);
        obl.add(oblOther);
        panel.add(obl);
        obl.setVisible(false);
        hideObl.addActionListener(new ActionListener() {
            private boolean flag = false;

            @Override
            public void actionPerformed(ActionEvent e) {

                hideObl.setText("Подробнее" + (flag ? " ⇓" : " ⇑"));
                flag = !flag;
                obl.setVisible(flag);
            }
        });
        return panel;
    }

    private JPanel createCallPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1, 15, 10));
        panel.setSize(new Dimension(400, 400));
        panel.setPreferredSize(new Dimension(400, 400));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
                        "Звонки"),
                BorderFactory.createEmptyBorder(30, 30, 30, 30)));
        JLabel name = new JLabel("     Количество звонков в минутах в месяц");
        name.setSize(new Dimension(20, 150));
        name.setBounds(0, 0, 20, 150);
        JPanel data = new JPanel();
        data.setLayout(new GridLayout(1, 4, 30, 50));
        data.setBounds(50, 50, 100, 50);
        JPanel finishPanel = new JPanel(new GridLayout(0, 2, 0, 0));
        JLabel start = new JLabel("от");
        startTextCall = new JTextField("10");
        JLabel finish = new JLabel("до");
        finishTextCall = new JTextField("100");
        start.setSize(new Dimension(20, 40));
        start.setBounds(0, 0, 20, 40);
        finish.setSize(new Dimension(20, 40));
        finish.setBounds(0, 0, 20, 40);

        JPanel startPanel = new JPanel(new GridLayout(1, 2, 0, 0));
        startTextCall.setSize(new Dimension(20, 40));
        startTextCall.setBounds(0, 0, 20, 40);
        finishTextCall.setSize(new Dimension(20, 40));
        startPanel.add(start);

        finishPanel.add(finish);
        finishPanel.add(finishTextCall);
        startPanel.add(startTextCall);
        data.add(startPanel);
        data.add(finishPanel);
        JPanel oblNov = new JPanel(new GridLayout(2, 1));
        JLabel messageNumberLabel = new JLabel("     абонентам Новосибирской области");
        numberCallNovObl = new JTextField();
        oblNov.add(messageNumberLabel);
        oblNov.add(numberCallNovObl);
        JPanel oblOther = new JPanel(new GridLayout(2, 1));
        JLabel messageNumberLabelOther = new JLabel("     абонентам другого региона");
        numberCallNovOther = new JTextField();
        oblOther.add(messageNumberLabelOther);
        oblOther.add(numberCallNovOther);

        JButton hideObl = new JButton("Подробнее" + " ⇓");

        JPanel union = new JPanel(new GridLayout(3, 1, 20, 20));
        union.add(name);
        union.add(data);
        union.add(hideObl);
        panel.add(union);
        JPanel obl = new JPanel(new GridLayout(2, 0, 10, 10));
        obl.add(oblNov);
        obl.add(oblOther);
        panel.add(obl);
        obl.setVisible(false);
        hideObl.addActionListener(new ActionListener() {
            private boolean flag = false;

            @Override
            public void actionPerformed(ActionEvent e) {

                hideObl.setText("Подробнее" + (flag ? " ⇓" : " ⇑"));
                flag = !flag;
                obl.setVisible(flag);

            }
        });
        return panel;
    }

    private JPanel createInternetPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1, 15, 10));
        panel.setSize(new Dimension(400, 400));
        panel.setPreferredSize(new Dimension(400, 400));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
                        "Интернет"),
                BorderFactory.createEmptyBorder(30, 30, 30, 30)));
        JLabel name = new JLabel("Количество интернета в Гб в месяц");
        name.setSize(new Dimension(20, 150));
        name.setBounds(0, 0, 20, 150);
        JPanel data = new JPanel();
        data.setLayout(new GridLayout(1, 4, 30, 50));
        data.setBounds(50, 50, 100, 50);
        JLabel start = new JLabel("от");
        startTextInternet = new JTextField("1");
        JLabel finish = new JLabel("до");
        finishTextInternet = new JTextField("2");
        start.setSize(new Dimension(20, 40));
        start.setBounds(0, 0, 20, 40);
        finish.setSize(new Dimension(20, 40));
        finish.setBounds(0, 0, 20, 40);
        startTextInternet.setSize(new Dimension(20, 40));
        startTextInternet.setBounds(0, 0, 20, 40);
        finishTextInternet.setSize(new Dimension(20, 40));
        finishTextInternet.setBounds(0, 0, 20, 40);
        JPanel startPanel = new JPanel(new GridLayout(1, 2, 0, 0));
        startPanel.add(start);
        startPanel.add(startTextInternet);
        JPanel finishPanel = new JPanel(new GridLayout(1, 2, 0, 0));
        finishPanel.add(finish);
        finishPanel.add(finishTextInternet);
        data.add(startPanel);
        data.add(finishPanel);
        JPanel union = new JPanel(new GridLayout(3, 1, 20, 20));
        union.add(name);
        union.add(data);
        panel.add(union);
        return panel;
    }

    public JTextField getStartTextMessage() {
        return startTextMessage;
    }

    public void setStartTextMessage(JTextField startTextMessage) {
        this.startTextMessage = startTextMessage;
    }

    public JTextField getFinishTextMessage() {
        return finishTextMessage;
    }

    public void setFinishTextMessage(JTextField finishTextMessage) {
        this.finishTextMessage = finishTextMessage;
    }

    public JTextField getStartTextCall() {
        return startTextCall;
    }

    public void setStartTextCall(JTextField startTextCall) {
        this.startTextCall = startTextCall;
    }

    public JTextField getFinishTextCall() {
        return finishTextCall;
    }

    public void setFinishTextCall(JTextField finishTextCall) {
        this.finishTextCall = finishTextCall;
    }

    public JTextField getStartTextInternet() {
        return startTextInternet;
    }

    public void setStartTextInternet(JTextField startTextInternet) {
        this.startTextInternet = startTextInternet;
    }

    public JTextField getFinishTextInternet() {
        return finishTextInternet;
    }

    public void setFinishTextInternet(JTextField finishTextInternet) {
        this.finishTextInternet = finishTextInternet;
    }

    public JTextField getNumberCallNovObl() {
        return numberCallNovObl;
    }

    public void setNumberCallNovObl(JTextField numberCallNovObl) {
        this.numberCallNovObl = numberCallNovObl;
    }

    public JTextField getNumberMessageNovObl() {
        return numberMessageNovObl;
    }

    public void setNumberMessageNovObl(JTextField numberMessageNovObl) {
        this.numberMessageNovObl = numberMessageNovObl;
    }

    public JTextField getNumberCallNovOther() {
        return numberCallNovOther;
    }

    public void setNumberCallNovOther(JTextField numberCallNovOther) {
        this.numberCallNovOther = numberCallNovOther;
    }

    public JTextField getNumberMessageNovOther() {
        return numberMessageNovOther;
    }

    public void setNumberMessageNovOther(JTextField numberMessageNovOther) {
        this.numberMessageNovOther = numberMessageNovOther;
    }

}
