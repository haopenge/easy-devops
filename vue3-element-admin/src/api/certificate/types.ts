/**
 * 凭证Vo
 */
export interface CertificateVo {
	name?: string;
	repositoryType?: number;
	typeShow?: string;
	description?: string;
	id?: number;
	username?: string;
	accessToken?: string;
	repositoryTypeShow: string;
}

/**
 * 凭证(新增、修改)表单
 */
export interface CertificateForm {
	id?: number;
	name?: string;
	repositoryType?: number;
	description?: string;
	username?: string;
	accessToken?: string;
}
