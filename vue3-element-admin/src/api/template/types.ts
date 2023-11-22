/**
 * 代码编辑控件选项
 */
export interface CodeMirrorCmOptionVo {
	mode?: string;
	theme?: string;
}

/**
 * 代码编辑控件Vo
 */
export interface CodeMirrorVo {

	width?: number;

	height?: number;

	value?: string;

	cmOptions?: CodeMirrorCmOptionVo;
}

/**
 * 新增模板vo
 */
export interface TemplateForm {

	id?: number;

	name?: string;

	type?: number

	content?: string;

}

/**
 * 模板vo
 */
export interface TemplateVo {

	id?: number;

	name?: string;

	createTime?: string;

	updateTime?: string;

	type?: number

	content?: string;

}
