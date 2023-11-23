/**
 * 环境Vo
 */
export interface EnvVo {
	id?: number;
	name?: string;
	description?: string;
	expireTime?: string;
	createTime?: string;
	updateTime?: string;
}

/**
 * 环境(新增、修改)表单
 */
export interface EnvForm {
	id?: number;
	name?: string;
	description?: string;
	expireTime?: string;
}
