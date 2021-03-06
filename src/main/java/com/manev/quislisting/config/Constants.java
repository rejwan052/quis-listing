package com.manev.quislisting.config;

public class Constants {

    private Constants() {
        // hide the implicit public one
    }

    //Regex for acceptable logins
    public static final String LOGIN_REGEX = "^[_'.@A-Za-z0-9-]*$";

    public static final String SPRING_PROFILE_DEVELOPMENT = "dev";
    public static final String SPRING_PROFILE_TEST = "test";
    public static final String SPRING_PROFILE_PRODUCTION = "prod";

    // Spring profile used to disable running liquibase
    public static final String SPRING_PROFILE_NO_LIQUIBASE = "no-liquibase";

    public static final String SPRING_PROFILE_SWAGGER = "swagger";

    public static final String SYSTEM_ACCOUNT = "system";
}
