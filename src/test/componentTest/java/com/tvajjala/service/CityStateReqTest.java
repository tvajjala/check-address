package com.tvajjala.service;

import com.tvajjala.address.client.model.request.CityStateReq;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;

public class CityStateReqTest {


    @Test
    public void simpleTest() throws Exception{
        CityStateReq cityStateReq = new CityStateReq();
        Assert.assertNotNull(cityStateReq);
        Assert.assertTrue(new ClassPathResource("application.yml").exists());

        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource("application.yml"));

        Assert.assertEquals("check-address", yaml.getObject().getProperty("application.name"));

        Thread.sleep(5000);
    }


}
