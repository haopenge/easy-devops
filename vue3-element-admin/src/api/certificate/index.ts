import request from "@/utils/request";
import { AxiosPromise } from "axios";
import { CertificateVo } from "./types";

/**
 * 查询凭证列表
 *
 */
export function findAll(): AxiosPromise<CertificateVo[]> {
  return request({
    url: "/certificate/findAll",
    method: "get",
  });
}
