package com.example.incidentmanagement.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.incidentmanagement.annotation.PrintLog;
import com.example.incidentmanagement.application.service.IncidentService;
import com.example.incidentmanagement.common.ApiConstant;
import com.example.incidentmanagement.common.Page;
import com.example.incidentmanagement.domain.valueobject.IncidentRequest;
import com.example.incidentmanagement.domain.valueobject.IncidentResult;
import com.example.incidentmanagement.model.ResponseObject;
import com.example.incidentmanagement.param.IncidentParam;
import com.example.incidentmanagement.param.PageParam;
import com.example.incidentmanagement.persisitence.entity.Incident;
import com.example.incidentmanagement.sso.SSOUtil;
import com.example.incidentmanagement.vo.IncidentDetail;
import com.example.incidentmanagement.vo.IncidentResultVO;

/**
 * @author wuyaqi <wuyaqi_2014@qq.com>
 * Created on 2024-08-14
 */
@RestController
@RequestMapping(ApiConstant.INCIDENT_API_PREFIX)
public class IncidentController {

    @Autowired
    private IncidentService incidentService;

    @PostMapping("/create_incident")
    @PrintLog
    public ResponseObject<IncidentResultVO> createIncident(@RequestBody IncidentParam param) {
        IncidentRequest incidentRequest = param.convertToIncidentRequest();
        IncidentResult incidentResult = incidentService.createIncident(incidentRequest, SSOUtil.getUserName());
        return ResponseObject.ofOk(new IncidentResultVO(incidentResult));
    }


    @PutMapping("/update_incident/{id}")
    @PrintLog
    public ResponseObject<IncidentResultVO> updateCreative(@PathVariable(value = "id") Long incidentId,
                                                           @RequestBody IncidentParam param) {
        IncidentRequest incidentRequest = param.convertToIncidentRequest();
        IncidentResult incidentResult = incidentService.updateIncident(incidentId, incidentRequest, SSOUtil.getUserName());
        return ResponseObject.ofOk(new IncidentResultVO(incidentResult));
    }

    @PutMapping("/delete_incident/{id}")
    @PrintLog
    public ResponseObject<Boolean> deleteCreative(@PathVariable(value = "id") Long incidentId) {
        return ResponseObject.ofOk(incidentService.deleteIncident(incidentId, SSOUtil.getUserName()));
    }


    @PostMapping("/incident_list_all")
    @PrintLog
    public ResponseObject<Page<IncidentDetail>> listAllIncident(@RequestBody PageParam param){
        Page<IncidentDetail> ans = new Page<>(param.getPage(), param.getPageSize());
        Page<Incident> page = incidentService.listAll(param.convert(), SSOUtil.getUserName());
        if (CollectionUtils.isEmpty(page.getData())) {
            return ResponseObject.ofOk(ans);
        }
        List<IncidentDetail> list = new ArrayList<>();
        for (Incident incident : page.getData()) {
            list.add(new IncidentDetail(incident));
        }
        ans.setTotalCount(page.getTotalCount());
        ans.setData(list);
        return ResponseObject.ofOk(ans);
    }

    @GetMapping("/test")
    public ResponseObject<String> test() {
        return ResponseObject.ofOk("test");
    }
}
