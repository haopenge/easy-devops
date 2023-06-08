package com.easy.devops.api.util;

import com.easy.devops.api.domain.bo.HostAndUsername;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GitUtil {

    /**
     * 获取用户名 域名,从ssh 克隆地址
     *
     * @param sshCloneUrl ssh 克隆地址
     * @return HostAndUsername
     */
    public static HostAndUsername getGitInfoFromSshCloneUrl(String sshCloneUrl) {
        String regex = "^git@([^:]+):(.+)/(.+)\\.git$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(sshCloneUrl);

        if (matcher.find()) {
            String username = matcher.group(2);
            String host = matcher.group(1);

            HostAndUsername hostAndUsername = new HostAndUsername();
            hostAndUsername.setHost(host);
            hostAndUsername.setUsername(username);
            return hostAndUsername;
        }
        return null;
    }

    /**
     * 获取用户名 域名,从https 克隆地址
     *
     * @param httpCloneUrl https 克隆地址
     * @return HostAndUsername
     */
    public static HostAndUsername getGitInfoFromHttpCloneUrl(String httpCloneUrl) {
        String regex = "^https?://([^/]+)/([^/]+)/(.+)\\.git$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(httpCloneUrl);
        if (matcher.find()) {
            String host = matcher.group(1);
            String username = matcher.group(2);

            HostAndUsername hostAndUsername = new HostAndUsername();
            hostAndUsername.setHost(host);
            hostAndUsername.setUsername(username);
            return hostAndUsername;
        }
        return null;
    }
}
