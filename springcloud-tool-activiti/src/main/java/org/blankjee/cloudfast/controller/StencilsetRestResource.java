package org.blankjee.cloudfast.controller;

import org.activiti.engine.ActivitiException;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @Author blankjee
 * @Date 2020/7/8 20:53
 */
@RestController
public class StencilsetRestResource {

    @RequestMapping(value = "/editor/stencilset", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public @ResponseBody String getStencilSet() {
        //InputStream stencilsetStream = this.getClass().getClassLoader().getResourceAsStream("stencilset.json");
        String url = Thread.currentThread().getContextClassLoader().getResource("stencilset.json").getFile();
        try {
            InputStream stencilsetStream = new FileInputStream(url);

            return IOUtils.toString(stencilsetStream, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ActivitiException("加载 stencil 集失败");
        }
    }
}
