package ua.nure.providence.dtos;

/**
 * Created by Providence Team on 21.05.2017.
 */
public class BaseDataDTO<T>  {

    T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
