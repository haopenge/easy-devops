import service from "@/utils/request";
import {AxiosPromise} from "axios";
import {CertificateForm, CertificateVo} from "./types";

const $certificate = {
	/**
	 * 查询凭证列表
	 *
	 */
	findAll(): AxiosPromise<CertificateVo[]> {
		return service.get("/certificate/findAll");
	},

	/**
	 * 新增凭证
	 */
	add(data: CertificateForm){
		return service.post("/certificate/add",data);
	},

	/**
	 * 修改凭证
	 */
	edit(data: CertificateForm){
		return service.put("/certificate/edit",data);
	},

	/**
	 * 删除凭证
	 */
	deleteById(ids: string){
		return service.delete("/certificate/deleteById?ids=" + ids);
	}

}

export default $certificate;





