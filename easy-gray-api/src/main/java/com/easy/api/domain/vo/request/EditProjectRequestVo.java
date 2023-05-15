package com.easy.api.domain.vo.request;

import com.sun.istack.internal.NotNull;

import javax.validation.constraints.NotBlank;

/**
 * @author liupenghao
 */

public class EditProjectRequestVo {

   /**
    * 项目id
    */
   @NotNull
   private Integer id;

   /**
    * 分支名
    */
   @NotBlank
   private String branchName;


   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public String getBranchName() {
      return branchName;
   }

   public void setBranchName(String branchName) {
      this.branchName = branchName;
   }
}
