
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
