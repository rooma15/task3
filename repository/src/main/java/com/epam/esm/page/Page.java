package com.epam.esm.page;

import java.util.List;

public class Page <T>{
    private final Integer size;
    private final Integer page;
    private List<T> content;

    private boolean hasNextPage;
    private boolean hasPrevPage;

    public Page(Integer size, Integer page) {
        this.size = size;
        this.page = page;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public List<T> getContent() {
        return content;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public boolean isHasPrevPage() {
        return hasPrevPage;
    }

    public void setHasPrevPage(boolean hasPrevPage) {
        this.hasPrevPage = hasPrevPage;
    }

    public Integer getSize() {
        return size;
    }

    public Integer getPage() {
        return page;
    }
}
