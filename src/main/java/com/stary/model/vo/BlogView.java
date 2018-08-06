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
@Accessors(chain = true)
@NoArgsConstructor
public class BlogView {
    private String title;
    private String showContent;
    private String realContent;
    private String createTime;

    public BlogView(@NonNull BlogPo blogPo) {
        this.title = blogPo.getTitle();
        this.showContent = blogPo.getShowContent();
        this.realContent = blogPo.getRealContent();
        this.createTime = blogPo.getCreateTime().format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
