package com.easy.api.domain.vo.request;

import lombok.Data;

@Data
public class AddProjectToGrayEnvRequestVo {

   private String projectName;

   private String projectBranch;

   private String projectCloneUrl;
}
