import service from "@/utils/request";
import {AxiosPromise} from "axios";
import {ProjectConfigVo, ProjectForm, ProjectVo} from "./types";

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
	add(data: ProjectForm) {
		return service.post("/project/add", data);
	},

	/**
	 * 修改项目
	 */
	edit(data: ProjectForm) {
		return service.put("/project/edit", data);
	},

	/**
	 * 删除项目
	 */
	deleteById(ids: string) {
		return service.delete("/project/deleteById?ids=" + ids);
	},

	/**
	 * 获取仓库分支
	 * @param easyRepositoryId 仓库id
	 */
	findBranches(easyRepositoryId: number | undefined) {
		return service.get("/project/findBranches?easyRepositoryId=" + easyRepositoryId);
	},

	/**
	 * 更新项目配置
	 */
	config(data: ProjectConfigVo) {
		return service.post("/project/config", data);
	},

	/**
	 * 获取项目配置
	 * @param id 项目id
	 */
	findConfigById(id: number | undefined) {
		return service.get("/project/findConfigById?id=" + id);
	},
}

export default $project;





