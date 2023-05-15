package com.easy.api.domain.vo.request;

import com.sun.istack.internal.NotNull;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddProjectToGrayEnvRequestVo {

   /**
    * 灰度环境id
    */
   @NotNull
   private Integer envId;

   /**
    * 项目
    */
   @NotNull
   private String fullName;

   /**
    * 分支名
    */
   @NotBlank
   private String branchName;

}
