import service from "@/utils/request";
import {AxiosPromise} from "axios";
import {EnvForm, EnvVo} from "./types";

const $env = {
	/**
	 * 查询凭证列表
	 *
	 */
	findAll(): AxiosPromise<EnvVo[]> {
		return service.get("/env/findAll");
	},

	/**
	 * 新增凭证
	 */
	add(data: EnvForm){
		return service.post("/env/add",data);
	},

	/**
	 * 修改凭证
	 */
	edit(data: EnvForm){
		return service.put("/env/edit",data);
	},

	/**
	 * 删除凭证
	 */
	deleteById(ids: string){
		return service.delete("/env/deleteById?ids=" + ids);
	}

}

export default $env;





