package com.egrand.cloud.ram.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.egrand.core.model.PageParams;
import com.egrand.core.model.ResultBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import java.util.Arrays;
import java.util.Map;

/**
 *  前端控制器
 *
 * @author ZZH
 * @date 2019-12-12
 */
@Api(value = "", tags = "")
    @RestController
@RequestMapping("group")
    public class GroupController {

    @Autowired
    private GroupService targetService;

    /**
    * 获取分页数据
    *
    * @return
    */
    @ApiOperation(value = "获取分页数据", notes = "获取分页数据")
    @GetMapping(value = "/list")
    public ResultBody<IPage<Group>>list(@RequestParam(required = false) Map map){
        PageParams pageParams = new PageParams(map);
        Group query = pageParams.mapToObject(Group.class);
        QueryWrapper<Group> queryWrapper = new QueryWrapper();
        return ResultBody.ok().data(targetService.page(pageParams,queryWrapper));
    }

    /**
     * 根据ID查找数据
     */
    @ApiOperation(value = "根据ID查找数据", notes = "根据ID查找数据")
    @ResponseBody
    @RequestMapping("/get")
    public ResultBody<Group> get(@RequestParam("id") Long id){
        Group entity = targetService.getById(id);
        return ResultBody.ok().data(entity);
    }

    /**
    * 添加数据
    * @return
    */
    @ApiOperation(value = "添加数据", notes = "添加数据")
    @ApiImplicitParams({
         @ApiImplicitParam(name = "comments", required = true, value = "", paramType = "form"),
         @ApiImplicitParam(name = "createDate", required = true, value = "", paramType = "form"),
         @ApiImplicitParam(name = "groupName", required = true, value = "", paramType = "form"),
         @ApiImplicitParam(name = "status", required = true, value = "", paramType = "form"),
         @ApiImplicitParam(name = "updateDate", required = true, value = "", paramType = "form"),
        @ApiImplicitParam(name = "ouInfoId", required = true, value = "", paramType = "form")
            })
    @PostMapping("/add")
    public ResultBody add(
        @RequestParam(value = "comments") String comments,
        @RequestParam(value = "createDate") Date createDate,
        @RequestParam(value = "groupName") String groupName,
        @RequestParam(value = "status") Integer status,
        @RequestParam(value = "updateDate") Date updateDate,
        @RequestParam(value = "ouInfoId") Long ouInfoId
            ){
        Group entity = new Group();
        entity.setComments(comments);
        entity.setCreateDate(createDate);
        entity.setGroupName(groupName);
        entity.setStatus(status);
        entity.setUpdateDate(updateDate);
        entity.setOuInfoId(ouInfoId);
        targetService.save(entity);
        return ResultBody.ok();
    }

    /**
    * 更新数据
    * @return
    */
    @ApiOperation(value = "更新数据", notes = "更新数据")
    @ApiImplicitParams({
                    @ApiImplicitParam(name = "id", required = true, value = "", paramType = "form"),
                    @ApiImplicitParam(name = "comments", required = true, value = "", paramType = "form"),
                    @ApiImplicitParam(name = "createDate", required = true, value = "", paramType = "form"),
                    @ApiImplicitParam(name = "groupName", required = true, value = "", paramType = "form"),
                    @ApiImplicitParam(name = "status", required = true, value = "", paramType = "form"),
                    @ApiImplicitParam(name = "updateDate", required = true, value = "", paramType = "form"),
                    @ApiImplicitParam(name = "ouInfoId", required = true, value = "", paramType = "form")
        })
        @PostMapping("/update")
        public ResultBody add(
                @RequestParam(value = "id") Long id,
                @RequestParam(value = "comments") String comments,
                @RequestParam(value = "createDate") Date createDate,
                @RequestParam(value = "groupName") String groupName,
                @RequestParam(value = "status") Integer status,
                @RequestParam(value = "updateDate") Date updateDate,
                @RequestParam(value = "ouInfoId") Long ouInfoId
        ){
            Group entity = new Group();
                    entity.setId(id);
                    entity.setComments(comments);
                    entity.setCreateDate(createDate);
                    entity.setGroupName(groupName);
                    entity.setStatus(status);
                    entity.setUpdateDate(updateDate);
                    entity.setOuInfoId(ouInfoId);
                targetService.updateById(entity);
                return ResultBody.ok();
        }

    /**
    * 删除数据
    * @return
    */
    @ApiOperation(value = "删除数据", notes = "删除数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true, value = "id", paramType = "form")
    })
    @PostMapping("/remove")
    public ResultBody remove(
            @RequestParam(value = "id") Long id
    ){
            targetService.removeById(id);
            return ResultBody.ok();
      }

    /**
    * 批量删除数据
    * @return
    */
    @ApiOperation(value = "批量删除数据", notes = "批量删除数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", required = true, value = "多个用,号隔开", paramType = "form")
    })
    @PostMapping("/batch/remove")
    public ResultBody batchRemove(
                @RequestParam(value = "ids") String ids
            ){
            targetService.removeByIds(Arrays.asList(ids.split(",")));
            return ResultBody.ok();
     }

}
