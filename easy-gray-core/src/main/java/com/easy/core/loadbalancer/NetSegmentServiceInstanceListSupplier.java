package com.easy.core.loadbalancer;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.easy.core.domain.Constants;
import com.easy.core.util.IPV4Util;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.commons.util.InetUtilsProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.loadbalancer.core.DelegatingServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Flux;

import java.net.InetAddress;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Setter
@Getter
@RefreshScope
@ConfigurationProperties(prefix = "gray")
public class NetSegmentServiceInstanceListSupplier extends DelegatingServiceInstanceListSupplier {

    private InetUtils inetUtils;

    @Value("${gray.env-list:}")
    private String envListStr;

    @Value("${env:}")
    private String env;

    @Value(value = "${gray.enable:false}")
    private boolean grayEnable;

    public NetSegmentServiceInstanceListSupplier(ServiceInstanceListSupplier delegate) {
        super(delegate);
        inetUtils = new InetUtils(new InetUtilsProperties());
    }

    @Override
    public Flux<List<ServiceInstance>> get() {
        return getDelegate().get().map(this::filterByNetSegment);
    }

    private List<ServiceInstance> filterByNetSegment(List<ServiceInstance> instances) {
        InetAddress host = inetUtils.findFirstNonLoopbackAddress();
        if(host == null){
            return instances;
        }
        String resourceIp = host.getHostAddress();
        List<String> envList = new ArrayList<>();
        if(!StringUtils.isEmpty(envListStr)){
            envList = Arrays.asList(envListStr.split(","));
        }

        // 灰度环境标识
        String podEnv = EnvHolder.getEnv(Constants.POD_ENV);
        EnvHolder.clear();
        if(Objects.equals(grayEnable,Boolean.FALSE)
                ||CollectionUtils.isEmpty(envList)
                || !envList.contains(env)
                || StringUtils.isBlank(podEnv)
                || envList.contains(podEnv)
        ){ // 校验此数据环境是否开放灰度
            return getNetSegmentServiceInstances(instances, resourceIp);
        }

        // 查询是否有此灰度环境的服务
        List<ServiceInstance> grayEnvInstanceList = instances.stream().filter(loopInstance -> {
            Map<String, String> metadataMap = loopInstance.getMetadata();
            return Objects.equals(metadataMap.get(Constants.POD_ENV), podEnv);
        }).collect(Collectors.toList());

        if(CollectionUtils.isEmpty(grayEnvInstanceList)){
            return getNetSegmentServiceInstances(instances, resourceIp);
        }

        return getNetSegmentServiceInstances(grayEnvInstanceList, resourceIp);
    }

    private static List<ServiceInstance> getNetSegmentServiceInstances(List<ServiceInstance> instances, String resourceIp) {
        List<ServiceInstance> targetList = new ArrayList<>();
        for(ServiceInstance instance : instances){
            if(IPV4Util.isSameAddress(resourceIp, instance.getHost())){
                targetList.add(instance);
            }
        }
        if(CollectionUtils.isEmpty(targetList)){
            return instances;
        }
        return targetList;
    }
}
