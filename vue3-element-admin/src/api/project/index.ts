import service from "@/utils/request";
import {AxiosPromise} from "axios";
import {ProjectForm, ProjectVo} from "./types";

const $project = {
	/**
	 * 查询项目列表
	 *
	 */
	findAll(): AxiosPromise<ProjectVo[]> {
		return service.get("/project/findAll");
	},

	/**
	 * 新增项目
	 */
	add(data: ProjectForm){
		return service.post("/project/add",data);
	},

	/**
	 * 修改项目
	 */
	edit(data: ProjectForm){
		return service.put("/project/edit",data);
	},

	/**
	 * 删除项目
	 */
	deleteById(ids: string){
		return service.delete("/project/deleteById?ids=" + ids);
	}

}

export default $project;





