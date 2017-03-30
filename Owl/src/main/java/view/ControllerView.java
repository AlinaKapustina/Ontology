package view;

import data.CallData;
import data.InternetData;
import data.MessageData;
import data.UserSet;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import main_package.Controller;

public class ControllerView implements ActionListener {

    private final Controller controller = new Controller();
    private final UserSet userSet = new UserSet();
    private final View view;

    public ControllerView(View view) {
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (getDataMessage()
                && getDataCall()
                && getDataInternet()) {
            controller.giveAnswer(userSet);
        }
    }

    private boolean getDataMessage() {
        int startTextMessage = checkField(view.getStartTextMessage().getText());
        int finishTextMessage = checkField(view.getFinishTextMessage().getText());
        int numberMessageNovObl = checkField(view.getNumberMessageNovObl().getText());
        int numberMessageNovOther = checkField(view.getNumberMessageNovOther().getText());

        if (startTextMessage == -2) {
            view.getStartTextMessage().setText("10");
            startTextMessage = 10;
        }
        if (finishTextMessage == -2) {
            view.getFinishTextMessage().setText("100");
            finishTextMessage = 100;
        }
        if (numberMessageNovObl == -2) {
            view.getNumberMessageNovObl().setText("0");
            numberMessageNovObl = 0;
        }
        if (numberMessageNovOther == -2) {
            view.getNumberMessageNovOther().setText("0");
            numberMessageNovOther = 0;
        }

        if (startTextMessage != -1 && finishTextMessage != -1 && numberMessageNovObl != -1 && numberMessageNovOther != -1) {
            MessageData messageData = new MessageData();
            messageData.setMinNumberMessage(startTextMessage);
            messageData.setMaxNumberMessage(finishTextMessage);
            messageData.setNumberHomeAreaMessage(numberMessageNovObl);
            messageData.setNumberOtherAreaMessage(numberMessageNovOther);
            userSet.setMessangeData(messageData);
        } else {
            JOptionPane.showMessageDialog(view, "Неверный формат данных в блоке Сообщения.\n"
                    + "Проверьте правильность введенных данных.", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean getDataInternet() {
        int startTextInternet = checkField(view.getStartTextInternet().getText());
        int finishTextInternet = checkField(view.getFinishTextInternet().getText());
        if (startTextInternet == -2) {
            view.getStartTextInternet().setText("1");
            startTextInternet = 1;
        }
        if (finishTextInternet == -2) {
            view.getFinishTextInternet().setText("2");
            finishTextInternet = 2;
        }

        if (startTextInternet != -1 && finishTextInternet != -1) {
            InternetData internetData = new InternetData();
            internetData.setMaxNumberInternet(finishTextInternet);
            internetData.setMinNumberInternet(startTextInternet);
            userSet.setInternetData(internetData);
        } else {
            JOptionPane.showMessageDialog(view, "Неверный формат данных в блоке Интернет.\n"
                    + "Проверьте правильность введенных данных.", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean getDataCall() {
        int startTextСall = checkField(view.getStartTextCall().getText());
        int finishTextCall = checkField(view.getFinishTextCall().getText());
        int numberCallNovObl = checkField(view.getNumberCallNovObl().getText());
        int numberCallNovOther = checkField(view.getNumberCallNovOther().getText());
        if (startTextСall == -2) {
            view.getStartTextCall().setText("10");
            startTextСall = 10;
        }
        if (finishTextCall == -2) {
            view.getFinishTextCall().setText("100");
            finishTextCall = 100;
        }
        if (numberCallNovObl == -2) {
            view.getNumberCallNovObl().setText("0");
            numberCallNovObl = 0;
        }

        if (numberCallNovOther == -2) {
            view.getNumberCallNovOther().setText("0");
            numberCallNovOther = 0;
        }

        if (startTextСall != -1 && finishTextCall != -1 && numberCallNovObl != -1 && numberCallNovOther
                != -1) {
            CallData callData = new CallData();
            callData.setMinNumberCall(startTextСall);
            callData.setMaxNumberCall(finishTextCall);
            callData.setNumberHomeAreaCall(numberCallNovObl);
            callData.setNumberOtherAreaCall(numberCallNovOther);
            userSet.setCallData(callData);
        } else {
            JOptionPane.showMessageDialog(view, "Неверный формат данных в блоке Звонки.\n"
                    + "Проверьте правильность введенных данных.", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private int checkField(String field) {
        int means;
        if ("".equals(field)) {
            return -2;
        }
        try {
            means = Integer.parseInt(field);
        } catch (NumberFormatException ex) {
            return -1;
        }
        if (means < 0) {
            return -1;
        } else {
            return means;
        }
    }

}
