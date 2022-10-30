package com.easy.core.loadbalancer;

import com.easy.core.domain.Constants;
import com.easy.core.util.IpUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Objects;

@Slf4j
public class FeignHeaderIntercept implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        if(Objects.isNull(requestAttributes)){
            return;
        }

        HttpServletRequest request = requestAttributes.getRequest();
        Enumeration<String> headerNames = request.getHeaderNames();

        if(Objects.isNull(headerNames)){
            return ;
        }

        boolean xForwardIpExist = false;
        while (headerNames.hasMoreElements()){
            String name = headerNames.nextElement();
            String values = request.getHeader(name);

            // 获取用户真实IP
            if (name.equalsIgnoreCase(Constants.X_FORWARDED_FOR)) {
                if(IpUtils.isLocalAddress(values)){
                    values = IpUtils.getIpAddr(request);
                }
                template.header(Constants.X_FORWARDED_FOR,values);
                xForwardIpExist = true;
            }

            // 自定义http header 获取
            if(name.startsWith(Constants.POD_ENV)){
                values = request.getHeader(name);
                log.info("http 拦截 header : name = {},values = {}",name,values);
                template.header(name,values);

                EnvHolder.setEnv(Constants.POD_ENV,values);
            }
        }

        if(!xForwardIpExist){
            String remoteAddr = IpUtils.getIpAddr(request);
            log.info("http x-forwarded-for 增加remoteAddr header : {}",remoteAddr);
            template.header(Constants.X_FORWARDED_FOR,remoteAddr);
        }
    }


}
