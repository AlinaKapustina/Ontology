package data;

public class UserSet {

    private MessangeData messangeData;
    private InternetData internetData;
    private CallData callData;

    public UserSet(MessangeData messangeData, InternetData internetData, CallData callData) {
        this.messangeData = messangeData;
        this.internetData = internetData;
        this.callData = callData;
    }

    public UserSet(MessangeData messangeData) {
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
    
    public MessangeData getMessangeData() {
        return messangeData;
    }

    public void setMessangeData(MessangeData messangeData) {
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
