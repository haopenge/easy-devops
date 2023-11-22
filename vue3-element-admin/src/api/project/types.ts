/**
 * 项目Vo
 */
export interface ProjectVo {
	id?: number;
	name?: string;
	easyRepositoryId?: number;
	easyRepositoryName?: string;
	easyCertificateName?: string;
	branch?: string;
}

/**
 * 项目(新增、修改)表单
 */
export interface ProjectForm {
	id?: number;
	envId?: number;
	name?: string;
	easyRepositoryId?: number;
	branch?: string;

}

/**
 * 新增项目弹窗vo
 */
export interface ProjectPopVo {
	id?: number;
	name?: string;
	easyRepositoryId?: number;
	easyRepositoryName?: string;
	branch?: string;
	dockerTemplateId?: number;
}

/**
 * 项目配置Vo
 */
export interface ProjectConfigVo {
	id?: number;
	easyTemplateIds?: string;
	dockerScript?: string;
	pushScript?: string;
}

/**
 * 项目模板选项
 */
export interface ProjectTemplateOption {
	id?: number;

	name?: string;

	type?: string;
}
