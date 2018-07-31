package com.cn.tmall.util;

public class Page {

    private int start; //本页开始的第一个数
    private int count; //每页显示个数
    private int total; //查询出来的总条数
    private String param; //参数

    private static final int DEFAULTCOUNT = 5; //默认每页显示5条

    public Page() {
        count = DEFAULTCOUNT;
    }

    public Page(int start, int count) {
        this.start = start;
        this.count = count;
    }

    public boolean isHasPreviouse() {
        if (start == 0) {
            return false;
        }
        return true;
    }

    public boolean isHasNext() {
        if (start == getLast())
            return false;
        return true;
    }

    public int getTotalPage() {
        int totalPage;

        if (0 == total % count) {
            totalPage = total / count;
        } else {
            totalPage = total / count + 1;
        }

        if (0 == totalPage) {
            totalPage = 1;
        }

        return totalPage;
    }

    /**
     * 获取最后一页的开始数字
     *
     * @return 最后一页的开始数字
     */
    public int getLast() {
        int last;
        if (0 == total % count) {
            last = total - count;
        } else {
            last = total - total % count;
        }
        last = last < 0 ? 0 : last;
        return last;
    }

    @Override
    public String toString() {
        return "Page [start=" + start + ", count=" + count + ", total=" + total + ", getStart()=" + getStart()
                + ", getCount()=" + getCount() + ", isHasPreviouse()=" + isHasPreviouse() + ", isHasNext()="
                + isHasNext() + ", getTotalPage()=" + getTotalPage() + ", getLast()=" + getLast() + "]";
    }


    // ------ getters and setters ------
    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
}
