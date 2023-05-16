package com.easy.api.domain.vo.request;

import lombok.Data;

@Data
public class AddProjectToGrayEnvRequestVo {

   /**
    * 灰度环境id
    */
   private Integer envId;

   /**
    * 项目
    */
   private String fullName;

   /**
    * 分支名
    */
   private String branchName;

}
