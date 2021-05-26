package org.example.todo_mvc.api.utils;

import java.util.List;

public class PageInfo<T> {

    private List<T> lists;

    private Integer totalCount = 0;

    private Integer pageSize = 20;

    private Integer currentPage = 0;
    
    public PageInfo(Integer currentPage, Integer pageSize) {
        if (currentPage == null) {
            currentPage = 1;
        }
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public void setLists(List<T> lists) {
        this.lists = lists;
    }

    public List<T> getLists() {
        return lists;
    }

    public Integer getTotalCount() {
        if (totalCount == null) {
            totalCount = 0;
        }
        return totalCount;
    }

    public Integer getCurrentPage() {
        if (currentPage == null || currentPage <= 0) {
            currentPage = 1;
        }
        return currentPage;
    }

    public Integer getPageSize() {
        if (pageSize==null || pageSize <= 0) {
            pageSize = 7;
        }
        return pageSize;
    }

    public Integer getTotalPage() {
        int pageSize = getPageSize();
        if (this.totalCount % pageSize == 0) {
            return (this.totalCount / pageSize) == 0 ? 1 :(this.totalCount / pageSize);
        }
        return (this.totalCount / this.pageSize + 1);
    }
}
