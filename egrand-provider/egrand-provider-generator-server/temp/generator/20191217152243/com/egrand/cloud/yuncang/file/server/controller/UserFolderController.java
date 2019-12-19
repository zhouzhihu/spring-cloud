package com.egrand.cloud.yuncang.file.server.controller;

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
 * @date 2019-12-17
 */
@Api(value = "", tags = "")
    @RestController
@RequestMapping("userFolder")
    public class UserFolderController {

    @Autowired
    private UserFolderService targetService;

    /**
    * 获取分页数据
    *
    * @return
    */
    @ApiOperation(value = "获取分页数据", notes = "获取分页数据")
    @GetMapping(value = "/list")
    public ResultBody<IPage<UserFolder>>list(@RequestParam(required = false) Map map){
        PageParams pageParams = new PageParams(map);
        UserFolder query = pageParams.mapToObject(UserFolder.class);
        QueryWrapper<UserFolder> queryWrapper = new QueryWrapper();
        return ResultBody.ok().data(targetService.page(pageParams,queryWrapper));
    }

    /**
     * 根据ID查找数据
     */
    @ApiOperation(value = "根据ID查找数据", notes = "根据ID查找数据")
    @ResponseBody
    @RequestMapping("/get")
    public ResultBody<UserFolder> get(@RequestParam("id") Long id){
        UserFolder entity = targetService.getById(id);
        return ResultBody.ok().data(entity);
    }

    /**
    * 添加数据
    * @return
    */
    @ApiOperation(value = "添加数据", notes = "添加数据")
    @ApiImplicitParams({
         @ApiImplicitParam(name = "userId", required = true, value = "用户ID", paramType = "form"),
         @ApiImplicitParam(name = "folderId", required = true, value = "文件夹ID", paramType = "form"),
        @ApiImplicitParam(name = "folderLocation", required = true, value = "文件夹位置", paramType = "form")
            })
    @PostMapping("/add")
    public ResultBody add(
        @RequestParam(value = "userId") Long userId,
        @RequestParam(value = "folderId") Long folderId,
        @RequestParam(value = "folderLocation") String folderLocation
            ){
        UserFolder entity = new UserFolder();
        entity.setUserId(userId);
        entity.setFolderId(folderId);
        entity.setFolderLocation(folderLocation);
        targetService.save(entity);
        return ResultBody.ok();
    }

    /**
    * 更新数据
    * @return
    */
    @ApiOperation(value = "更新数据", notes = "更新数据")
    @ApiImplicitParams({
                    @ApiImplicitParam(name = "id", required = true, value = "ID", paramType = "form"),
                    @ApiImplicitParam(name = "userId", required = true, value = "用户ID", paramType = "form"),
                    @ApiImplicitParam(name = "folderId", required = true, value = "文件夹ID", paramType = "form"),
                    @ApiImplicitParam(name = "folderLocation", required = true, value = "文件夹位置", paramType = "form")
        })
        @PostMapping("/update")
        public ResultBody add(
                @RequestParam(value = "id") Long id,
                @RequestParam(value = "userId") Long userId,
                @RequestParam(value = "folderId") Long folderId,
                @RequestParam(value = "folderLocation") String folderLocation
        ){
            UserFolder entity = new UserFolder();
                    entity.setId(id);
                    entity.setUserId(userId);
                    entity.setFolderId(folderId);
                    entity.setFolderLocation(folderLocation);
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
