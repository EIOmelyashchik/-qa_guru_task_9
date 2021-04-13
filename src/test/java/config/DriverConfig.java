package config;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:config/driver.properties"})
public interface DriverConfig extends Config {

    @DefaultValue("https://%s:%s@%s/wd/hub/")
    @Key("remote.web.url")
    String remoteWebUrl();

    @Key("remote.web.user")
    String remoteWebUser();

    @Key("remote.web.password")
    String remoteWebPassword();
}