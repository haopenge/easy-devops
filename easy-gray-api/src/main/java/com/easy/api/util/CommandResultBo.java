package com.easy.api.util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @desc 命令行执行结果Bo
 * @author liuph
 * @date 2020/03/06 15:57
 */
@Setter
@Getter
@NoArgsConstructor
public class CommandResultBo {
    private String execResult;

    private String execError;
}
