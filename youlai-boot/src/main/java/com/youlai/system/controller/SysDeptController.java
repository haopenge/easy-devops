package com.youlai.system.controller;

import com.youlai.system.common.annotation.PreventDuplicateSubmit;
import com.youlai.system.common.model.Option;
import com.youlai.system.common.result.Result;
import com.youlai.system.model.form.DeptForm;
import com.youlai.system.model.query.DeptQuery;
import com.youlai.system.model.vo.DeptVO;
import com.youlai.system.service.SysDeptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 部门控制器
 *
 * @author haoxr
 * @since 2020/11/6
 */
@Tag(name = "05.部门接口")
@RestController
@RequestMapping("/api/v1/dept")
@RequiredArgsConstructor
public class SysDeptController {
    private final SysDeptService deptService;
    @Operation(summary = "获取部门列表", security = {@SecurityRequirement(name = "Authorization")})
    @GetMapping
    public Result<List<DeptVO>> listDepartments(
            DeptQuery queryParams
    ) {
        List<DeptVO> list = deptService.listDepartments(queryParams);
        return Result.success(list);
    }

    @Operation(summary = "获取部门下拉选项", security = {@SecurityRequirement(name = "Authorization")})
    @GetMapping("/options")
    public Result<List<Option>> listDeptOptions() {
        List<Option> list = deptService.listDeptOptions();
        return Result.success(list);
    }

    @Operation(summary = "获取部门表单数据", security = {@SecurityRequirement(name = "Authorization")})
    @GetMapping("/{deptId}/form")
    public Result<DeptForm> getDeptForm(
            @Parameter(description ="部门ID") @PathVariable Long deptId
    ) {
        DeptForm deptForm = deptService.getDeptForm(deptId);
        return Result.success(deptForm);
    }

    @Operation(summary = "新增部门", security = {@SecurityRequirement(name = "Authorization")})
    @PostMapping
    @PreAuthorize("@ss.hasPerm('sys:dept:add')")
    @PreventDuplicateSubmit
    public Result saveDept(
            @Valid @RequestBody DeptForm formData
    ) {
        Long id = deptService.saveDept(formData);
        return Result.success(id);
    }

    @Operation(summary = "修改部门", security = {@SecurityRequirement(name = "Authorization")})
    @PutMapping(value = "/{deptId}")
    @PreAuthorize("@ss.hasPerm('sys:dept:edit')")
    public Result updateDept(
            @PathVariable Long deptId,
            @Valid @RequestBody DeptForm formData
    ) {
        deptId = deptService.updateDept(deptId, formData);
        return Result.success(deptId);
    }

    @Operation(summary = "删除部门", security = {@SecurityRequirement(name = "Authorization")})
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPerm('sys:dept:delete')")
    public Result deleteDepartments(
            @Parameter(description ="部门ID，多个以英文逗号(,)分割") @PathVariable("ids") String ids
    ) {
        boolean result = deptService.deleteByIds(ids);
        return Result.judge(result);
    }

}
