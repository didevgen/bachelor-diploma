package ua.nure.providence.dtos;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Providence Team on 17.05.2017.
 */
public class BaseListDTO<T> {

    private long limit;
    private long offset;
    private long count;
    private List<T> data = new ArrayList<>();

    public BaseListDTO(List<T> data, long limit, long offset, long total) {
        this.data = data;
        this.limit = limit;
        this.offset = offset;
        this.count = total;
    }

    public long getLimit() {
        return limit;
    }

    public void setLimit(long limit) {
        this.limit = limit;
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
