package com.mifa.cloud.voice.server.controller;

import com.google.common.base.Preconditions;
import com.google.common.net.HttpHeaders;
import com.mifa.cloud.voice.server.annotation.AuthScope;
import com.mifa.cloud.voice.server.annotation.Loggable;
import com.mifa.cloud.voice.server.commons.constants.AppConst;
import com.mifa.cloud.voice.server.commons.dto.BlackListDTO;
import com.mifa.cloud.voice.server.commons.dto.BlackListRspDTO;
import com.mifa.cloud.voice.server.commons.dto.CommonResponse;
import com.mifa.cloud.voice.server.commons.dto.PageDTO;
import com.mifa.cloud.voice.server.commons.enums.AuthQRole;
import com.mifa.cloud.voice.server.service.BlackListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author: songxm
 * @date: 2018/5/23 09:48
 * @version: v1.0.0
 */
@RestController
@CrossOrigin
@Api(value = "黑名单管理相关API.", tags = "黑名单管理相关API", description = "可扩展手机号,设备号,身份证号等等黑名单操作")
@RequestMapping(AppConst.BASE_AUTH_PATH )
public class BlackListController {

    @Autowired
    BlackListService blackListService;

    @ApiOperation("黑名单记录添加")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = HttpHeaders.AUTHORIZATION, required = true, value = "service token", dataType = "string", defaultValue = AppConst.SAMPLE_TOKEN)
    })
    @RequestMapping(value = "v1/black-list", method = RequestMethod.POST)
    @Loggable(descp = "拨打任务添加")
    @AuthScope(AuthQRole.MF_SERVICE)
    public CommonResponse<Boolean> addBlackList(@RequestBody @Valid BlackListDTO blackListDTO) {
        return CommonResponse.successCommonResponse(blackListService.addBlackList(blackListDTO));
    }

    @ApiOperation("黑名单记录查询")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = HttpHeaders.AUTHORIZATION, required = true, value = "service token", dataType = "string", defaultValue = AppConst.SAMPLE_TOKEN)
    })
    @RequestMapping(value = "v1/black-list", method = RequestMethod.GET)
    @Loggable(descp = "黑名单记录查询")
    @AuthScope(AuthQRole.MF_SERVICE)
    public CommonResponse<BlackListDTO> getBlackList(@RequestParam String contractNo,@RequestParam String blackListId) {
        return CommonResponse.successCommonResponse(blackListService.getBlackList(contractNo,blackListId));
    }

    @ApiOperation("黑名单记录删除操作")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = HttpHeaders.AUTHORIZATION, required = true, value = "service token", dataType = "string", defaultValue = AppConst.SAMPLE_TOKEN)
    })
    @RequestMapping(value = "v1/black-list", method = RequestMethod.DELETE)
    @Loggable(descp = "黑名单记录删除操作")
    @AuthScope(AuthQRole.MF_SERVICE)
    public CommonResponse<BlackListDTO> delBlackList(@RequestParam String contractNo,@RequestParam String id) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(id),"名单ID号不能为空");
        return CommonResponse.successCommonResponse(blackListService.delBlackList(contractNo,id));
    }

    @ApiOperation(value = "黑名单分页列表查询")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = HttpHeaders.AUTHORIZATION, required = true, value = "service token", dataType = "string", defaultValue = AppConst.SAMPLE_TOKEN)
            , @ApiImplicitParam(paramType = "query", name = "contractNo", required = true, dataType = "string", value = "客户号"),
            @ApiImplicitParam(paramType = "query", name = "pageNum", required = true, dataType = "int", value = "页码"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", required = true, dataType = "int", value = "每页条数"),
    })
    @RequestMapping(value = "v1/black-lists", method = RequestMethod.GET)
    @Loggable(descp = "黑名单分页列表查询")
    @AuthScope(AuthQRole.MF_SERVICE)
    public CommonResponse<PageDTO<BlackListRspDTO>> queryJobList(@RequestParam(required = true) String contractNo,  @RequestParam(required = true, defaultValue = "1") Integer pageNum, @RequestParam(required = true, defaultValue = "10") Integer pageSize) {
        return CommonResponse.successCommonResponse(blackListService.getBlackLists(contractNo, pageNum, pageSize));
    }
//
//    @ApiOperation("黑名单记录修改")
//    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = HttpHeaders.AUTHORIZATION, required = true, value = "service token", dataType = "string", defaultValue = AppConst.SAMPLE_TOKEN)
//    })
//    @RequestMapping(value = "v1/black-list", method = RequestMethod.PUT)
//    @Loggable(descp = "黑名单记录修改")
//    @AuthScope(AuthQRole.MF_SERVICE)
//    public CommonResponse<Boolean> alterBlackList(@RequestParam String contractNo,@RequestParam String blackListId) {
//        return CommonResponse.successCommonResponse(blackListService.alterBlackList(contractNo,blackListId));
//    }
}
