package data;
public class UserSet {
    private int numberMessage;
    private  int TimeCall;

    public UserSet(int numberMessage, int TimeCall) {
        this.numberMessage = numberMessage;
        this.TimeCall = TimeCall;
    }

    public int getNumberMessage() {
        return numberMessage;
    }

    public void setNumberMessage(int numberMessage) {
        this.numberMessage = numberMessage;
    }

    public int getTimeCall() {
        return TimeCall;
    }

    public void setTimeCall(int TimeCall) {
        this.TimeCall = TimeCall;
    }
    
}
