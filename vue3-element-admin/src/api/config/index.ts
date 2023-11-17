import service from "@/utils/request";
import {AxiosPromise} from "axios";
import {GitRepositoryVo, RepositoryFormData, RepositoryVo} from "./types";

const $repository = {

    /**
     * 获取git仓库列表
     *
     */
    findGitRepositories(certificateId: number): AxiosPromise<GitRepositoryVo[]> {
        return service.get("/repository/findGitRepositories",
            {
                params: {
                    certificateId: certificateId
                }
            }
        );
    },

    /**
     * 查询凭证列表
     *
     */
    findAll(): AxiosPromise<RepositoryVo[]> {
        return service.get("/repository/findAll");
    },

    /**
     * 新增凭证
     */
    add(data: RepositoryFormData) {
        return service.post("/repository/add", data);
    },

    /**
     * 修改凭证
     */
    edit(data: RepositoryFormData) {
        return service.put("/repository/edit", data);
    },

    /**
     * 删除凭证
     */
    deleteById(ids: string) {
        return service.delete("/repository/deleteById?ids=" + ids);
    }

}

export default $repository;





