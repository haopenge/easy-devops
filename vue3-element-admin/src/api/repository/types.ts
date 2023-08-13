/**
 * git仓库Vo
 */
export interface GitRepositoryVo {
	id?: number;
	name?: string;
	description?: string;
	isPublic?: boolean;
	defaultBranch?: string;
	httpCloneUrl?: string;
	sshCloneUrl?: string;
}

/**
 * 仓库(新增、修改)弹窗信息Vo
 */
export interface RepositoryPopVo {
	id?: number;
	name?: string;
	description?: string;
	cloneUrl?: string;
	easyCertificateId?: number;
	branch?: string;
}

/**
 * 仓库(新增、修改)表单
 */
export interface RepositoryFormData {
	id?: number;
	name?: string;
	easyCertificateId?: number;
}

/**
 * 仓库Vo
 */
export interface RepositoryVo {
	id?: number;
	name?: string;
	description?: string;
	cloneUrl?: string;
	easyCertificateId?: number;
	easyCertificateName?: string;
	branch?: string;

}
