package org.sid.billingservice.web;

import org.sid.billingservice.config.MyConsulConfig;
import org.sid.billingservice.config.MyVaultConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ConsulConfigRestController {

//    @Value("${token.accessTokenTimeOut}")
//    private Long accessTokenTimeOut;
//    @Value("${token.refreshTokenTimeOut}")
//    private Long refreshTokenTimeOut;

    @Autowired
    private MyConsulConfig myConsulConfig;

    @Autowired
    private MyVaultConfig myVaultConfig;


    @GetMapping("/myConfig")
    public Map<String,Object> getConfig(){
        return Map.of("ConsulConfig",myConsulConfig,"VaultConfig",myVaultConfig);
    }
}
