package br.com.babicakesbackend.models.enumerators;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public enum ParameterizationEnvEnum {
    PRODUCTION("env"),
    HOMOLOGATION("hml");

    private String status;


    ParameterizationEnvEnum(String status) {
        this.status = status;
    }

    public List<ParameterizationEnvEnum> list() {
        return List.of(ParameterizationEnvEnum.values());
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static ParameterizationEnvEnum getEnv(String env) {
        return StringUtils.equals(env, PRODUCTION.getStatus()) ? PRODUCTION : HOMOLOGATION;
    }
}
