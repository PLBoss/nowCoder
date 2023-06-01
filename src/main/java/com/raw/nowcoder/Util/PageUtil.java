package com.raw.nowcoder.Util;

import com.github.pagehelper.PageInfo;
import com.raw.nowcoder.entity.PageRequest;
import com.raw.nowcoder.entity.PageResult;

public class PageUtil {
    /*
    * 将插件中的分页信息设置到结果集中
    * */

    public static PageResult getPageResult(PageRequest pageRequest, PageInfo<?> pageInfo) {

        PageResult pageResult=new PageResult();
        pageResult.setPageNum(pageInfo.getPageNum());
        pageResult.setPagesize(pageInfo.getPageSize());
        pageResult.setTotalPage(pageInfo.getPages());
        pageResult.setTotalSize(pageInfo.getTotal());
        pageResult.setContent(pageInfo.getList());
        return  pageResult;

    }
}
