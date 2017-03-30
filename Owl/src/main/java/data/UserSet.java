package data;

public class UserSet {

    private MessageData messangeData;
    private InternetData internetData;
    private CallData callData;

    public UserSet(MessageData messangeData, InternetData internetData, CallData callData) {
        this.messangeData = messangeData;
        this.internetData = internetData;
        this.callData = callData;
    }

    public UserSet(MessageData messangeData) {
        this.messangeData = messangeData;
    }

    public UserSet(InternetData internetData) {
        this.internetData = internetData;
    }

    public UserSet() {
    }

    public UserSet(CallData callData) {
        this.callData = callData;
    }
    
    public MessageData getMessangeData() {
        return messangeData;
    }

    public void setMessangeData(MessageData messangeData) {
        this.messangeData = messangeData;
    }

    public InternetData getInternetData() {
        return internetData;
    }

    public void setInternetData(InternetData internetData) {
        this.internetData = internetData;
    }

    public CallData getCallData() {
        return callData;
    }

    public void setCallData(CallData callData) {
        this.callData = callData;
    }
    
    
    
}
