package com.easy.api.domain.vo.request;

/**
 * @author liupenghao
 */

public class EditProjectRequestVo {

   /**
    * 项目id
    */
   private Integer id;

   /**
    * 分支名
    */
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