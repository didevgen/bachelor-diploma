package ua.nure.providence.dtos.auth;

public class Wrapper {

    private Object responseResult;

    public Wrapper(Object responseResult) {
        this.responseResult = responseResult;
    }

    public Object getResponseResult() {
        return responseResult;
    }

    public void setResponseResult(Object responseResult) {
        this.responseResult = responseResult;
    }
}
