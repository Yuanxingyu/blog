package com.stary.model.vo;

import com.stary.model.po.UserPo;
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
public class UserDesc {
    private int id;
    private String userName;
    private String createTime;
    private String updateTime;

    public UserDesc(@NonNull UserPo userPo) {
        this.id = userPo.getId();
        this.userName = userPo.getUserName();
        this.createTime = userPo.getCreateTime().format(DateTimeFormatter.ISO_LOCAL_DATE);
        this.updateTime = userPo.getUpdateTime().format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
