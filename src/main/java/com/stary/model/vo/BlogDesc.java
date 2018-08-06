package com.stary.model.vo;

import com.stary.model.po.BlogPo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.format.DateTimeFormatter;


@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class BlogDesc {
    private int id;
    private String title;
    private String createTime;
    private String updateTime;

    public BlogDesc(@NonNull BlogPo blogPo) {
        this.id = blogPo.getId();
        this.title = blogPo.getTitle();
        this.createTime = blogPo.getCreateTime().format(DateTimeFormatter.ISO_LOCAL_DATE);
        this.updateTime = blogPo.getUpdateTime().format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
