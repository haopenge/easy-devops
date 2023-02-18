package com.easy.api.domain.vo.request;

import lombok.Data;

@Data
public class AddProjectToGrayEnvRequestVo {

   /**
    * 灰度环境id
    */
   private Integer id;

   /**
    * 项目
    */
   private String fullName;

   /**
    * 项目包路径，项目为子项目时填写
    */
   private String subProjectPath;

   /**
    * 分支名
    */
   private String branchName;

}
