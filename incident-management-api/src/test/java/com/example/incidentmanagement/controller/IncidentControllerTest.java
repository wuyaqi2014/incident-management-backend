package com.example.incidentmanagement.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.alibaba.fastjson2.JSON;
import com.example.incidentmanagement.application.service.IncidentService;
import com.example.incidentmanagement.common.ApiConstant;
import com.example.incidentmanagement.common.Page;
import com.example.incidentmanagement.common.enums.PlatformErrorCode;
import com.example.incidentmanagement.domain.valueobject.IncidentRequest;
import com.example.incidentmanagement.domain.valueobject.IncidentResult;
import com.example.incidentmanagement.model.ResponseObject;
import com.example.incidentmanagement.param.IncidentParam;
import com.example.incidentmanagement.param.PageParam;
import com.example.incidentmanagement.persisitence.entity.Incident;
import com.example.incidentmanagement.sso.SSOUtil;
import com.example.incidentmanagement.validator.CustomerValidator;
import com.example.incidentmanagement.vo.IncidentDetail;

/**
 * @author wuyaqi <wuyaqi_2014@qq.com>
 * Created on 2024-08-14
 */
@WebMvcTest(controllers = IncidentController.class)
public class IncidentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IncidentService incidentService;

    @MockBean
    private CustomerValidator customerValidator;

    @MockBean
    private SSOUtil ssoUtil;

    @Test
    void testCreateIncidentValid() throws Exception {
        IncidentParam param = new IncidentParam();
        param.setTitle("title1");
        param.setDescription("description1");
        param.setStartTime(System.currentTimeMillis());
        param.setEndTime(System.currentTimeMillis());
        param.setRemark("remark1");
        IncidentResult incidentResult = new IncidentResult();
        incidentResult.setIncidentId(1L);
        incidentResult.setSuccess(true);
        when(incidentService.createIncident(any(IncidentRequest.class), anyString())).thenReturn(incidentResult);

        mockMvc.perform(MockMvcRequestBuilders.post(ApiConstant.INCIDENT_API_PREFIX + "/create_incident")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(param)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(PlatformErrorCode.OK.getCode()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMsg").value(PlatformErrorCode.OK.getMsg()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.incidentId").value(incidentResult.getIncidentId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.success").value(incidentResult.isSuccess()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true));
    }

    @Test
    void testCreateIncidentParamInvalid() throws Exception {
        IncidentParam param = new IncidentParam(); // ALl params are null
        String expectedErrorMsgPart1 = "Title cannot be empty;";
        String expectedErrorMsgPart2 = "Description cannot be empty;";
        String expectStartTimePart3 = "StartTime cannot be empty;";
        String expectStartTimePart4 = "EndTime cannot be empty;";
        String responseBody = mockMvc.perform(MockMvcRequestBuilders.post(ApiConstant.INCIDENT_API_PREFIX + "/create_incident")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(param)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(PlatformErrorCode.PARAM_ERROR.getCode()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMsg").value(expectedErrorMsg))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
                .andReturn()
                .getResponse().getContentAsString();
        String actualErrorMsg = JSON.parseObject(responseBody).getString("errorMsg");
        assertTrue(actualErrorMsg.contains(expectedErrorMsgPart1));
        assertTrue(actualErrorMsg.contains(expectedErrorMsgPart2));
        assertTrue(actualErrorMsg.contains(expectStartTimePart3));
        assertTrue(actualErrorMsg.contains(expectStartTimePart4));
    }

    @Test
    public void testUpdateIncidentValid() throws Exception {
        IncidentParam param = new IncidentParam();
        param.setTitle("title1");
        param.setDescription("description1");
        param.setStartTime(System.currentTimeMillis());
        param.setEndTime(System.currentTimeMillis());
        param.setRemark("remark1");
        IncidentResult incidentResult = new IncidentResult();
        incidentResult.setIncidentId(1L);
        incidentResult.setSuccess(true);

        when(incidentService.updateIncident(anyLong(), any(IncidentRequest.class), anyString())).thenReturn(incidentResult);

        mockMvc.perform(MockMvcRequestBuilders.put(ApiConstant.INCIDENT_API_PREFIX + "/update_incident/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(param)))
                .andExpect(status().isOk())
                .andExpect(content().json(JSON.toJSONString(ResponseObject.ofOk(incidentResult))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(PlatformErrorCode.OK.getCode()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMsg").value(PlatformErrorCode.OK.getMsg()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.incidentId").value(incidentResult.getIncidentId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.success").value(incidentResult.isSuccess()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true));
    }

    @Test
    public void testUpdateIncidentInvalid() throws Exception {
        IncidentParam param = new IncidentParam();
        param.setDescription("description1");
        param.setStartTime(System.currentTimeMillis());
        param.setEndTime(System.currentTimeMillis());
        param.setRemark("remark1");
        IncidentResult incidentResult = new IncidentResult();
        incidentResult.setIncidentId(0L);
        incidentResult.setSuccess(false);

        when(incidentService.updateIncident(anyLong(), any(IncidentRequest.class), anyString())).thenReturn(incidentResult);
        String expectedErrorMsg = "Title cannot be empty;";


        ResponseObject expectResponseObject = ResponseObject.ofErrorCodeWithMessageAndTrace(PlatformErrorCode.PARAM_ERROR, expectedErrorMsg);
        mockMvc.perform(MockMvcRequestBuilders.put(ApiConstant.INCIDENT_API_PREFIX + "/update_incident/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(param)))
                .andExpect(status().isOk())
                .andExpect(content().json(JSON.toJSONString(expectResponseObject)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(PlatformErrorCode.PARAM_ERROR.getCode()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMsg").value(expectedErrorMsg))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false));
    }


    @Test
    void testDeleteIncidentValid() throws Exception {
        Long incidentId = 1L;
        boolean result = true;
        when(incidentService.deleteIncident(incidentId, "username")).thenReturn(result);
        mockMvc.perform(MockMvcRequestBuilders.put(ApiConstant.INCIDENT_API_PREFIX + "/delete_incident/" + incidentId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(PlatformErrorCode.OK.getCode()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMsg").value(PlatformErrorCode.OK.getMsg()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true));
    }

    @Test
    void testDeleteIncidentInvalid() throws Exception {
        Long incidentId = 1L;
        when(incidentService.deleteIncident(incidentId, "wuyaqi"))
                .thenThrow(PlatformErrorCode.SERVER_ERROR.toException("incidentID:{} not exists", incidentId));
        mockMvc.perform(MockMvcRequestBuilders.put(ApiConstant.INCIDENT_API_PREFIX + "/delete_incident/" + incidentId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(PlatformErrorCode.SERVER_ERROR.getCode()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMsg").value("incidentID:1 not exists"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false));


    }

    @Test
    void testListAllIncidentValid() throws Exception {
        PageParam param = new PageParam();
        param.setPage(1);
        param.setPageSize(10);
        Page<IncidentDetail> page = new Page<>(param.getPage(), param.getPageSize());
        List<Incident> incidents = new ArrayList<>();
        // Add some incidents to the list
        Page<Incident> incidentPage = new Page<>(param.getPage(), param.getPageSize());
        incidentPage.setData(incidents);
        incidentPage.setTotalCount(incidents.size());
        when(incidentService.listAll(param.convert(), "wuyaqi")).thenReturn(incidentPage);

        List<IncidentDetail> details = new ArrayList<>();
        for (Incident incident : incidents) {
            details.add(new IncidentDetail(incident));
        }
        page.setData(details);
        page.setTotalCount(incidents.size());

        mockMvc.perform(MockMvcRequestBuilders.post(ApiConstant.INCIDENT_API_PREFIX + "/incident_list_all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(param)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(PlatformErrorCode.OK.getCode()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMsg").value(PlatformErrorCode.OK.getMsg()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.totalCount").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.currentPage").value(1));
    }
}