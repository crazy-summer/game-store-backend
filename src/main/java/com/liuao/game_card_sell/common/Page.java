package com.liuao.game_card_sell.common;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 分页结果类，替代 MyBatis-Plus 的 IPage
 */
public class Page<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 当前页码
     */
    private long current;

    /**
     * 每页显示条数
     */
    private long size;

    /**
     * 总记录数
     */
    private long total;

    /**
     * 总页数
     */
    private long pages;

    /**
     * 数据列表
     */
    private List<T> records;

    public Page() {
        this.records = Collections.emptyList();
    }

    public Page(long current, long size) {
        this(current, size, 0);
    }

    public Page(long current, long size, long total) {
        this(current, size, total, true);
    }

    public Page(long current, long size, long total, boolean searchCount) {
        this.current = current;
        this.size = size;
        this.total = total;
        if (searchCount) {
            this.pages = this.total / this.size;
            if (this.total % this.size != 0) {
                this.pages++;
            }
            // 处理页码越界问题
            if (this.current > this.pages) {
                this.current = this.pages;
            }
        }
        this.records = Collections.emptyList();
    }

    // 判断当前页是否有上一页
    public boolean hasPrevious() {
        return this.current > 1;
    }

    // 判断当前页是否有下一页
    public boolean hasNext() {
        return this.current < this.pages;
    }

    // 计算偏移量
    public long offset() {
        return (this.current - 1) * this.size;
    }

    // Getters and Setters
    public long getCurrent() {
        return current;
    }

    public Page<T> setCurrent(long current) {
        this.current = current;
        return this;
    }

    public long getSize() {
        return size;
    }

    public Page<T> setSize(long size) {
        this.size = size;
        return this;
    }

    public long getTotal() {
        return total;
    }

    public Page<T> setTotal(long total) {
        this.total = total;
        return this;
    }

    public long getPages() {
        return pages;
    }

    public Page<T> setPages(long pages) {
        this.pages = pages;
        return this;
    }

    public List<T> getRecords() {
        return records;
    }

    public Page<T> setRecords(List<T> records) {
        this.records = records;
        return this;
    }

    @Override
    public String toString() {
        return "Page{" +
                "current=" + current +
                ", size=" + size +
                ", total=" + total +
                ", pages=" + pages +
                ", records=" + records +
                '}';
    }
}