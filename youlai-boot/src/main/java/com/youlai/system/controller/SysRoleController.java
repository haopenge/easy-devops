package com.youlai.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.system.common.annotation.PreventDuplicateSubmit;
import com.youlai.system.common.model.Option;
import com.youlai.system.common.result.PageResult;
import com.youlai.system.common.result.Result;
import com.youlai.system.model.form.RoleForm;
import com.youlai.system.model.query.RolePageQuery;
import com.youlai.system.model.vo.RolePageVO;
import com.youlai.system.service.SysRoleService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "03.角色接口")
@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class SysRoleController {

    private final SysRoleService roleService;

    @Operation(summary = "角色分页列表",security = {@SecurityRequirement(name = "Authorization")} )
    @GetMapping("/page")
    public PageResult<RolePageVO> getRolePage(
            RolePageQuery queryParams
    ) {
        Page<RolePageVO> result = roleService.getRolePage(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "角色下拉列表",security = {@SecurityRequirement(name = "Authorization")})
    @GetMapping("/options")
    public Result<List<Option>> listRoleOptions() {
        List<Option> list = roleService.listRoleOptions();
        return Result.success(list);
    }

    @Operation(summary = "新增角色",security = {@SecurityRequirement(name = "Authorization")})
    @PostMapping
    @PreAuthorize("@ss.hasPerm('sys:role:add')")
    @PreventDuplicateSubmit
    public Result addRole(@Valid @RequestBody RoleForm roleForm) {
        boolean result = roleService.saveRole(roleForm);
        return Result.judge(result);
    }

    @Operation(summary = "角色表单数据",security = {@SecurityRequirement(name = "Authorization")})
    @GetMapping("/{roleId}/form")
    public Result<RoleForm> getRoleForm(
            @Parameter(description ="角色ID") @PathVariable Long roleId
    ) {
        RoleForm roleForm = roleService.getRoleForm(roleId);
        return Result.success(roleForm);
    }

    @Operation(summary = "修改角色",security = {@SecurityRequirement(name = "Authorization")})
    @PutMapping(value = "/{id}")
    @PreAuthorize("@ss.hasPerm('sys:role:edit')")
    public Result updateRole(@Valid @RequestBody RoleForm roleForm) {
        boolean result = roleService.saveRole(roleForm);
        return Result.judge(result);
    }

    @Operation(summary = "删除角色",security = {@SecurityRequirement(name = "Authorization")})
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPerm('sys:role:delete')")
    public Result deleteRoles(
            @Parameter(description ="删除角色，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean result = roleService.deleteRoles(ids);
        return Result.judge(result);
    }

    @Operation(summary = "修改角色状态",security = {@SecurityRequirement(name = "Authorization")})
    @PutMapping(value = "/{roleId}/status")
    public Result updateRoleStatus(
            @Parameter(description ="角色ID") @PathVariable Long roleId,
            @Parameter(description ="状态(1:启用;0:禁用)") @RequestParam Integer status
    ) {
        boolean result = roleService.updateRoleStatus(roleId, status);
        return Result.judge(result);
    }

    @Operation(summary = "获取角色的菜单ID集合",security = {@SecurityRequirement(name = "Authorization")})
    @GetMapping("/{roleId}/menuIds")
    public Result<List<Long>> getRoleMenuIds(
            @Parameter(description ="角色ID") @PathVariable Long roleId
    ) {
        List<Long> menuIds = roleService.getRoleMenuIds(roleId);
        return Result.success(menuIds);
    }

    @Operation(summary = "分配菜单权限给角色",security = {@SecurityRequirement(name = "Authorization")})
    @PutMapping("/{roleId}/menus")
    public Result updateRoleMenus(
            @PathVariable Long roleId,
            @RequestBody List<Long> menuIds
    ) {
        boolean result = roleService.updateRoleMenus(roleId,menuIds);
        return Result.judge(result);
    }
}
