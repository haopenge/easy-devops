package com.easy.core.util;

import lombok.Getter;

/**
 * 常用状态枚举: 用于表示各类正反状态.
 * <ul>
 *     <li>是/否</li>
 *     <li>可以/不可以</li>
 *     <li>启用/禁用</li>
 *     <li>成功/失败</li>
 *     <li>真/假</li>
 *     <li>开启/关闭</li>
 *     <li>......</li>
 * </ul>
 */
@Getter
public enum State {
    /**
     * 否、不可以、禁用、失败、假、关闭等
     */
    NO(0, "0", "N", "NO", "failure", "false", "OFF"),
    /**
     * 是、可以、启用、成功、真、开启等
     */
    YES(1, "1", "Y", "YES", "success", "true", "ON");

    /**
     * 1/0
     */
    private Integer id;
    /**
     * '1'/'0'
     */
    private String code;
    /**
     * 'Y'/'N'
     */
    private String name;
    /**
     * 'YES'/'NO'
     */
    private String yesNo;
    /**
     * 'ON'/'OFF'
     */
    private String onOff;
    /**
     * 'success'/'failure'
     */
    private String sucFail;
    /**
     * 'true'/'false'
     */
    private String trueFalse;

    /**
     * 构造方法.
     *
     * @param id        1/0
     * @param code      '1'/'0'
     * @param name      'Y'/'N'
     * @param yesNo     'YES'/'NO'
     * @param onOff     'ON'/'OFF'
     * @param sucFail   'success'/'failure'
     * @param trueFalse 'true'/'false'
     */
    State(Integer id, String code, String name, String yesNo, String onOff, String sucFail, String trueFalse) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.yesNo = yesNo;
        this.onOff = onOff;
        this.sucFail = sucFail;
        this.trueFalse = trueFalse;
    }

    /**
     * 获取布尔状态对应的状态类型.
     * <p>true -> YES</p>
     * <p>false -> NO</p>
     *
     * @param state 布尔状态
     * @return 状态类型
     */
    public static State of(boolean state) {
        return state ? YES : NO;
    }

    /**
     * 反转获取布尔状态对应的状态类型.
     * <p>true -> NO</p>
     * <p>false -> YES</p>
     *
     * @param state 布尔状态
     * @return 状态类型
     */
    public static State ofReverse(boolean state) {
        return of(!state);
    }

    /**
     * 获取状态ID对应的布尔类型值.
     * <p>1-true、0-false、其他-null</p>
     *
     * @param stateId 状态ID
     * @return 对应的布尔类型值
     */
    public static Boolean of(Integer stateId) {
        return YES.is(stateId) ? Boolean.TRUE : NO.is(stateId) ? Boolean.FALSE : null;
    }

    /**
     * 获取状态代码对应的布尔类型值.
     * <p>'1'-true、'0'-false、其他-null</p>
     *
     * @param stateCode 状态代码
     * @return 对应的布尔类型值
     */
    public static Boolean of(String stateCode) {
        return YES.isCode(stateCode) ? Boolean.TRUE : NO.isCode(stateCode) ? Boolean.FALSE : null;
    }

    /**
     * 是否匹配指定的状态ID.
     *
     * @param stateId 待匹配的状态ID
     * @return 匹配结果（true-匹配、false-不匹配）
     */
    public boolean is(Integer stateId) {
        return this.getId().equals(stateId);
    }

    /**
     * 是否不匹配指定的状态ID.
     *
     * @param stateId 待匹配的状态ID
     * @return 匹配结果（true-不匹配、false-匹配）
     */
    public boolean not(Integer stateId) {
        return !is(stateId);
    }

    /**
     * 是否匹配指定的状态代码.
     *
     * @param stateCode 待匹配的状态代码
     * @return 匹配结果（true-匹配、false-不匹配）
     */
    public boolean isCode(String stateCode) {
        return this.getCode().equals(stateCode);
    }

    /**
     * 是否不匹配指定的状态代码.
     *
     * @param stateCode 待匹配的状态代码
     * @return 匹配结果（true-不匹配、false-匹配）
     */
    public boolean notCode(String stateCode) {
        return !isCode(stateCode);
    }

    /**
     * 是否匹配指定的状态名称.
     *
     * @param stateName 待匹配的状态名称
     * @return 匹配结果（true-匹配、false-不匹配）
     */
    public boolean isName(String stateName) {
        return this.getName().equals(stateName);
    }

    /**
     * 是否不匹配指定的状态名称.
     *
     * @param stateName 待匹配的状态名称
     * @return 匹配结果（true-不匹配、false-匹配）
     */
    public boolean notName(String stateName) {
        return !isName(stateName);
    }

    /**
     * 是否匹配指定的是否标识.
     *
     * @param yesNo 待匹配的是否标识
     * @return 匹配结果（true-匹配、false-不匹配）
     */
    public boolean isYesNo(String yesNo) {
        return this.getYesNo().equals(yesNo);
    }

    /**
     * 是否不匹配指定的是否标识.
     *
     * @param yesNo 待匹配的是否标识
     * @return 匹配结果（true-不匹配、false-匹配）
     */
    public boolean notYesNo(String yesNo) {
        return !isYesNo(yesNo);
    }

    /**
     * 是否匹配指定的开启关闭标识.
     *
     * @param onOff 待匹配的开启关闭标识
     * @return 匹配结果（true-匹配、false-不匹配）
     */
    public boolean isOnOff(String onOff) {
        return this.getOnOff().equals(onOff);
    }

    /**
     * 是否不匹配指定的开启关闭标识.
     *
     * @param onOff 待匹配的开启关闭标识
     * @return 匹配结果（true-不匹配、false-匹配）
     */
    public boolean notOnOff(String onOff) {
        return !isOnOff(onOff);
    }

    /**
     * 是否匹配指定的成功失败标识.
     *
     * @param sucFail 待匹配的成功失败标识
     * @return 匹配结果（true-匹配、false-不匹配）
     */
    public boolean isSucFail(String sucFail) {
        return this.getSucFail().equals(sucFail);
    }

    /**
     * 是否不匹配指定的成功失败标识.
     *
     * @param sucFail 待匹配的成功失败标识
     * @return 匹配结果（true-不匹配、false-匹配）
     */
    public boolean notSucFail(String sucFail) {
        return !isSucFail(sucFail);
    }

    /**
     * 是否匹配指定的状态真假标识.
     *
     * @param trueFalse 待匹配的状态真假标识
     * @return 匹配结果（true-匹配、false-不匹配）
     */
    public boolean isTrueFalse(String trueFalse) {
        return this.getTrueFalse().equals(trueFalse);
    }

    /**
     * 是否不匹配指定的状态真假标识.
     *
     * @param trueFalse 待匹配的状态真假标识
     * @return 匹配结果（true-不匹配、false-匹配）
     */
    public boolean notTrueFalse(String trueFalse) {
        return !isTrueFalse(trueFalse);
    }

    /**
     * 是否匹配指定的状态真假标识.
     *
     * @param trueFalse 待匹配的状态真假标识
     * @return 匹配结果（true-匹配、false-不匹配）
     */
    public static boolean isTrue(boolean trueFalse) {
        return trueFalse;
    }

    /**
     * 是否匹配指定的状态真假标识.
     * <p>状态真假标识为null时等价于false.</p>
     *
     * @param trueFalse 状态真假标识
     * @return 判定结果
     */
    public static boolean isTrue(Boolean trueFalse) {
        return trueFalse != null && trueFalse;
    }

    /**
     * 是否匹配指定的状态真假标识.
     *
     * @param trueFalse 待匹配的状态真假标识
     * @return 匹配结果（true-匹配、false-不匹配）
     */
    public static boolean isFalse(boolean trueFalse) {
        return !isTrue(trueFalse);
    }

    /**
     * 是否匹配指定的状态真假标识.
     * <p>状态真假标识为null时等价于false.</p>
     *
     * @param trueFalse 状态真假标识
     * @return 判定结果（true-匹配、false-不匹配）
     */
    public static boolean isFalse(Boolean trueFalse) {
        return !isTrue(trueFalse);
    }
}
