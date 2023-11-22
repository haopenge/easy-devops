import service from "@/utils/request";
import {AxiosPromise} from "axios";
import {TemplateForm, TemplateVo} from "@/api/template/types";

const $template = {

	/**
	 * 查询模板列表
	 *
	 */
	findAll(): AxiosPromise<TemplateVo[]> {
		return service.get("/template/findAll");
	},

	/**
	 * 新增模板
	 */
	add(data: TemplateForm) {
		return service.post("/template/add", data);
	},

	/**
	 * 修改模板
	 */
	edit(data: TemplateForm) {
		return service.put("/template/edit", data);
	},

	/**
	 * 删除模板
	 */
	deleteById(ids: string) {
		return service.delete("/template/deleteById?ids=" + ids);
	},
}

export default $template;
