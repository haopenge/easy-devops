package com.easy.api.domain.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Form-data 格式请求外部资源，文件model
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormDataFileBo {

    private String name;

    private String fileName;

    private String filePath;
}
