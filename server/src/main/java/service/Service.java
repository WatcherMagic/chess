package service;

public class Service {

    int errorCode = 200;

    Service() {}

    public int getErrorCode() {
        return errorCode;
    }

    public void resetErrorCode() {
        this.errorCode = 200;
    }
}
