package com.youlai.system.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.youlai.system.common.annotation.PreventDuplicateSubmit;
import com.youlai.system.common.constant.ExcelConstants;
import com.youlai.system.common.result.PageResult;
import com.youlai.system.common.result.Result;
import com.youlai.system.common.util.ExcelUtils;
import com.youlai.system.listener.easyexcel.UserImportListener;
import com.youlai.system.model.entity.SysUser;
import com.youlai.system.model.form.UserForm;
import com.youlai.system.model.query.UserPageQuery;
import com.youlai.system.model.vo.UserExportVO;
import com.youlai.system.model.vo.UserImportVO;
import com.youlai.system.model.vo.UserInfoVO;
import com.youlai.system.model.vo.UserPageVO;
import com.youlai.system.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * 用户控制器
 *
 * @author haoxr
 * @since 2022/10/16
 */
@Tag(name = "02.用户接口")
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class SysUserController {

    private final SysUserService userService;

    @Operation(summary = "用户分页列表", security = {@SecurityRequirement(name = "Authorization")})
    @GetMapping("/page")
    public PageResult<UserPageVO> getUserPage(UserPageQuery queryParams
    ) {
        IPage<UserPageVO> result = userService.getUserPage(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "新增用户", security = {@SecurityRequirement(name = "Authorization")})
    @PostMapping
    @PreAuthorize("@ss.hasPerm('sys:user:add')")
    @PreventDuplicateSubmit
    public Result saveUser(
            @RequestBody @Valid UserForm userForm
    ) {
        boolean result = userService.saveUser(userForm);
        return Result.judge(result);
    }

    @Operation(summary = "用户表单数据", security = {@SecurityRequirement(name = "Authorization")})
    @GetMapping("/{userId}/form")
    public Result<UserForm> getUserForm(
            @Parameter(description = "用户ID") @PathVariable Long userId
    ) {
        UserForm formData = userService.getUserFormData(userId);
        return Result.success(formData);
    }

    @Operation(summary = "修改用户", security = {@SecurityRequirement(name = "Authorization")})
    @PutMapping(value = "/{userId}")
    @PreAuthorize("@ss.hasPerm('sys:user:edit')")
    public Result updateUser(
            @Parameter(description = "用户ID") @PathVariable Long userId,
            @RequestBody @Validated UserForm userForm) {
        boolean result = userService.updateUser(userId, userForm);
        return Result.judge(result);
    }

    @Operation(summary = "删除用户", security = {@SecurityRequirement(name = "Authorization")})
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPerm('sys:user:delete')")
    public Result deleteUsers(
            @Parameter(description = "用户ID，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean result = userService.deleteUsers(ids);
        return Result.judge(result);
    }

    @Operation(summary = "修改用户密码", security = {@SecurityRequirement(name = "Authorization")})
    @PatchMapping(value = "/{userId}/password")
    @PreAuthorize("@ss.hasPerm('sys:user:reset_pwd')")
    public Result updatePassword(
            @Parameter(description = "用户ID") @PathVariable Long userId,
            @RequestParam String password
    ) {
        boolean result = userService.updatePassword(userId, password);
        return Result.judge(result);
    }

    @Operation(summary = "修改用户状态", security = {@SecurityRequirement(name = "Authorization")})
    @PatchMapping(value = "/{userId}/status")
    public Result updateUserStatus(
            @Parameter(description = "用户ID") @PathVariable Long userId,
            @Parameter(description = "用户状态(1:启用;0:禁用)") @RequestParam Integer status
    ) {
        boolean result = userService.update(new LambdaUpdateWrapper<SysUser>()
                .eq(SysUser::getId, userId)
                .set(SysUser::getStatus, status)
        );
        return Result.judge(result);
    }

    @Operation(summary = "获取当前登录用户信息", security = {@SecurityRequirement(name = "Authorization")})
    @GetMapping("/me")
    public Result<UserInfoVO> getUserLoginInfo() {
        UserInfoVO userInfoVO = userService.getUserLoginInfo();
        return Result.success(userInfoVO);
    }

    @Operation(summary = "用户导入模板下载", security = {@SecurityRequirement(name = "Authorization")})
    @GetMapping("/template")
    public void downloadTemplate(HttpServletResponse response) throws IOException {
        String fileName = "用户导入模板.xlsx";
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));

        String fileClassPath = ExcelConstants.EXCEL_TEMPLATE_DIR + File.separator + fileName;
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(fileClassPath);

        ServletOutputStream outputStream = response.getOutputStream();
        ExcelWriter excelWriter = EasyExcel.write(outputStream).withTemplate(inputStream).build();

        excelWriter.finish();
    }

    @Operation(summary = "导入用户", security = {@SecurityRequirement(name = "Authorization")})
    @PostMapping("/_import")
    public Result importUsers(@Parameter(description = "部门ID") Long deptId, MultipartFile file) throws IOException {
        UserImportListener listener = new UserImportListener(deptId);
        String msg = ExcelUtils.importExcel(file.getInputStream(), UserImportVO.class, listener);
        return Result.success(msg);
    }

    @Operation(summary = "导出用户", security = {@SecurityRequirement(name = "Authorization")})
    @GetMapping("/_export")
    public void exportUsers(UserPageQuery queryParams, HttpServletResponse response) throws IOException {
        String fileName = "用户列表.xlsx";
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));

        List<UserExportVO> exportUserList = userService.listExportUsers(queryParams);
        EasyExcel.write(response.getOutputStream(), UserExportVO.class).sheet("用户列表")
                .doWrite(exportUserList);
    }
}
