package com.raw.nowcoder.entity;

import java.util.List;

public class PageResult{

    //当前页码
    private int PageNum;


    //页大小
    private int Pagesize;

    //总页数
    private  int totalPage;

    //总记录数
    private long totalSize;
    //记录的数据
    private List<?> content;

    @Override
    public String toString() {
        return "PageResult{" +
                "PageNum=" + PageNum +
                ", Pagesize=" + Pagesize +
                ", totalPage=" + totalPage +
                ", totalSize=" + totalSize +
                ", content=" + content +
                '}';
    }

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }


    public List<?> getContent() {
        return content;
    }

    public void setContent(List<?> content) {
        this.content = content;
    }


    public PageResult() {
    }

    public int getPageNum() {
        return PageNum;
    }

    public void setPageNum(int pageNum) {
        PageNum = pageNum;
    }

    public int getPagesize() {
        return Pagesize;
    }

    public void setPagesize(int pagesize) {
        Pagesize = pagesize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }



}
