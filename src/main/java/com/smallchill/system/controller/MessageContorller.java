/**
 * Copyright (c) 2015-2017, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.smallchill.system.controller;

import com.alibaba.fastjson.JSON;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.base.controller.BladeController;
import com.smallchill.core.base.controller.CurdController;
import com.smallchill.core.meta.IMeta;
import com.smallchill.core.meta.MetaIntercept;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.grid.JqGrid;
import com.smallchill.core.toolbox.kit.ClassKit;
import com.smallchill.core.toolbox.kit.StrKit;
import com.smallchill.system.meta.factory.MessageFactory;
import com.smallchill.system.model.Message;
import com.smallchill.system.service.CurdService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

@Controller
@RequestMapping("/message")
public class MessageContorller extends CurdController<Message> {

    @Autowired
    CurdService service;

    private final BladeController ctrl = this;
    /**
     * 自定义拦截器
     **/
    private MetaIntercept intercept = null;
    private IMeta metaFactory;
    private String controllerKey;
    private String paraPrefix;
    private Map<String, Object> switchMap;
    private Map<String, String> renderMap;
    private Map<String, String> sourceMap;

    private void init() {
        this.metaFactory = ClassKit.newInstance(metaFactoryClass());
        this.controllerKey = metaFactory.controllerKey();
        this.paraPrefix = metaFactory.paraPrefix();
        this.switchMap = metaFactory.switchMap();
        this.renderMap = metaFactory.renderMap();
        this.sourceMap = metaFactory.sourceMap();
        this.intercept = ClassKit.newInstance(metaFactory.intercept());
    }

    public MessageContorller() {
        this.init();
    }

    @Override
    protected Class<? extends IMeta> metaFactoryClass() {

        return MessageFactory.class;
    }

    @Json
    @RequestMapping("/search")
    public Object search() throws UnsupportedEncodingException {
        String account = getParameter("account");
        String password = getParameter("password");
        String jsonpCallback = getParameter("jsonpCallback");
        Map<String, Object> result = new HashMap();
        result.put("code", 1);
        result.put("success", false);
        if (StringUtils.isEmpty(account) || StringUtils.isEmpty(password)) {
            result.put("message", "账号密码不能为空");
            return jsonpCallback + "(" + JSON.toJSONString(result) + ")";
        }
        Subject currentUser = ShiroKit.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(account, password.toCharArray());
        token.setRememberMe(true);
        try {
            currentUser.login(token);
        } catch (UnknownAccountException e) {
            result.put("message", "账号不存在");
            return jsonpCallback + "(" + JSON.toJSONString(result) + ")";
        } catch (DisabledAccountException e) {
            result.put("message", "账号未启用");
            return jsonpCallback + "(" + JSON.toJSONString(result) + ")";
        } catch (IncorrectCredentialsException e) {
            result.put("message", "密码错误");
            return jsonpCallback + "(" + JSON.toJSONString(result) + ")";
        } catch (RuntimeException e) {
            result.put("message", "未知错误,请联系管理员");
            return jsonpCallback + "(" + JSON.toJSONString(result) + ")";
        }
        Integer page = getParameterToInt("page", 1);
        Integer rows = getParameterToInt("rows", 10);
        String where = getParameter("where", StrKit.EMPTY);
        String sidx = getParameter("sidx", StrKit.EMPTY);
        String sord = getParameter("sord", StrKit.EMPTY);
        String sort = getParameter("sort", "CREATE_TIME");
        String order = getParameter("order", "desc");
        if (StrKit.notBlank(sidx)) {
            sort = sidx + " " + sord
                    + (StrKit.notBlank(sort) ? ("," + sort) : StrKit.EMPTY);
        }
        if (StringUtils.isEmpty(where)) {
            Map<String, Object> params = new HashMap();
            String month_seq = getParameter("month_seq");
            String content = getParameter("content");
            String mobile = getParameter("mobile");
            String create_time_dategt = getParameter("create_time_dategt");
            String create_time_datelt = getParameter("create_time_datelt");
            Integer report = getParameterToInt("report");
            Integer submit_type = getParameterToInt("submit_type");
            String sp_code = getParameter("sp_code");
            String report2 = getParameter("report2");
            params.put("ACCOUNT", account);
            if (!StringUtils.isEmpty(month_seq)) {
                params.put("MONTH_SEQ", month_seq);
            }
            if (!StringUtils.isEmpty(mobile)) {
                params.put("MOBILE", mobile);
            }
            if (!StringUtils.isEmpty(content)) {
                params.put("CONTENT", content);
            }
            if (!StringUtils.isEmpty(create_time_dategt)) {
                params.put("CREATE_TIME_dategt", create_time_dategt);
            }
            if (!StringUtils.isEmpty(create_time_datelt)) {
                params.put("CREATE_TIME_datelt", create_time_datelt);
            }
            if (null != report) {
                if (report == 1) {
                    params.put("REPORT", "DELIVRD");
                } else if (report == 2) {
                    params.put("REPORT_null", "");
                } else {
                    params.put("REPORT_notequal", "DELIVRD");
                    params.put("REPORT_null", "");
                }
                if (null != submit_type) {
                    params.put("SUBMIT_TYPE", submit_type);
                }
            }
            if (!StringUtils.isEmpty(sp_code)) {
                params.put("SP_CODE", sp_code);
            }
            if (!StringUtils.isEmpty(report2)) {
                params.put("REPORT", report2);
            }
            where = URLEncoder.encode(JSON.toJSONString(params), "UTF-8");
        }
        Object grid = service.paginate(page, rows,
                sourceMap.get(KEY_INDEX), where, sort, order,
                intercept, ctrl);
        result.put("code", 0);
        result.put("success", true);
        result.put("message", "获取短信状态成功");
        result.put("data", grid);
        return jsonpCallback + "(" + JSON.toJSONString(result) + ")";
    }

    @Json
    @RequestMapping("/mo")
    public Object mo() throws UnsupportedEncodingException {
        String account = getParameter("account");
        String password = getParameter("password");
        String jsonpCallback = getParameter("jsonpCallback");
        Map<String, Object> result = new HashMap();
        result.put("code", 1);
        result.put("success", false);
        if (StringUtils.isEmpty(account) || StringUtils.isEmpty(password)) {
            result.put("message", "账号密码不能为空");
            return jsonpCallback + "(" + JSON.toJSONString(result) + ")";
        }
        Subject currentUser = ShiroKit.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(account, password.toCharArray());
        token.setRememberMe(true);
        try {
            currentUser.login(token);
        } catch (UnknownAccountException e) {
            result.put("message", "账号不存在");
            return jsonpCallback + "(" + JSON.toJSONString(result) + ")";
        } catch (DisabledAccountException e) {
            result.put("message", "账号未启用");
            return jsonpCallback + "(" + JSON.toJSONString(result) + ")";
        } catch (IncorrectCredentialsException e) {
            result.put("message", "密码错误");
            return jsonpCallback + "(" + JSON.toJSONString(result) + ")";
        } catch (RuntimeException e) {
            result.put("message", "未知错误,请联系管理员");
            return jsonpCallback + "(" + JSON.toJSONString(result) + ")";
        }
        Integer page = getParameterToInt("page", 1);
        Integer rows = getParameterToInt("rows", 10);
        String where = getParameter("where", StrKit.EMPTY);
        String sidx = getParameter("sidx", StrKit.EMPTY);
        String sord = getParameter("sord", StrKit.EMPTY);
        String sort = getParameter("sort", "CREATE_TIME");
        String order = getParameter("order", "desc");
        if (StrKit.notBlank(sidx)) {
            sort = sidx + " " + sord
                    + (StrKit.notBlank(sort) ? ("," + sort) : StrKit.EMPTY);
        }
        if (StringUtils.isEmpty(where)) {
            Map<String, Object> params = new HashMap();
            String content = getParameter("content");
            String mobile = getParameter("mobile");
            String create_time_dategt = getParameter("create_time_dategt");
            String create_time_datelt = getParameter("create_time_datelt");
            String sp_code = getParameter("sp_code");
            params.put("ACCOUNT", account);
            if (!StringUtils.isEmpty(mobile)) {
                params.put("MOBILE", mobile);
            }
            if (!StringUtils.isEmpty(content)) {
                params.put("CONTENT", content);
            }
            if (!StringUtils.isEmpty(create_time_dategt)) {
                params.put("CREATE_TIME_dategt", create_time_dategt);
            }
            if (StringUtils.isEmpty(create_time_datelt)) {
                params.put("CREATE_TIME_datelt", create_time_datelt);
            }
            if (!StringUtils.isEmpty(sp_code)) {
                params.put("SP_CODE", sp_code);
            }
            where = URLEncoder.encode(JSON.toJSONString(params), "UTF-8");
        }
        Object grid = service.paginate(page, rows,
                sourceMap.get(KEY_ADD), where, sort, order,
                intercept, ctrl);
        result.put("code", 0);
        result.put("success", true);
        result.put("message", "获取短信状态成功");
        result.put("data", grid);
        return jsonpCallback + "(" + JSON.toJSONString(result) + ")";
    }


    @Json
    @RequestMapping("/info")
    public Object info() throws UnsupportedEncodingException {
        Map<String, Object> result = new HashMap();
        result.put("code", 0);
        // 获取用户角色 非管理员直接return
        if(!ShiroKit.getUser().getRoles().equals("1") && !ShiroKit.getUser().getRoles().equals("45")){
            result.put("code", 1);
            return result;
        }
        Object grid;
        if(System.currentTimeMillis()/5000%2 == 0){
            grid = service.paginate(1, 1,
                    sourceMap.get(KEY_LIST), null, "REPORT_DATE", "desc",
                    intercept, ctrl);
            ArrayList record = ((ArrayList) ((JqGrid) grid).getRows());
            if(null == record || record.size() ==0){
                result.put("code", 1);
            }else{
                String message = "用户【";
                LinkedHashMap<String,Object> myMap = (LinkedHashMap<String, Object>) record.get(0);
                for(Iterator iter = myMap.entrySet().iterator(); iter.hasNext();){
                    Map.Entry element = (Map.Entry)iter.next();
                    if(element.getKey().equals("ACCOUNT")){
                        message+=element.getValue();
                        message+="】";
                    }
                    if(element.getKey().equals("REPORT_DATE")){
                        message+=element.getValue();
                        message+="短信发送成功率为:";
                    }
                    if(element.getKey().equals("SUCCESS")){
                        message+=element.getValue();
                        message+=" ";
                    }
                }
                result.put("message",message);
            }
        }else{
            grid = service.paginate(1, 1,
                    sourceMap.get(KEY_VIEW), null, "CREATE_TIME", "desc",
                    intercept, ctrl);
            ArrayList record = ((ArrayList) ((JqGrid) grid).getRows());
            if(null == record || record.size() ==0){
                result.put("code", 1);
            }else{
                result.put("message","AUDIT_SMS");
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
    }

}
