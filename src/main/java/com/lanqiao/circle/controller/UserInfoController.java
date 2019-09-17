package com.lanqiao.circle.controller;


import com.lanqiao.circle.annotations.UserLoginToken;
import com.lanqiao.circle.entity.Users;
import com.lanqiao.circle.mapper.UsersMapper;
import com.lanqiao.circle.service.RelationShipService;
import com.lanqiao.circle.service.TokenService;
import com.lanqiao.circle.service.UserInfoService;
import com.lanqiao.circle.util.CommentUtil;
import com.lanqiao.circle.util.FileUploadUtil;
import com.lanqiao.circle.util.Result;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/userInfo")
public class UserInfoController {
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    TokenService tokenService;
    @Autowired
    FileUploadUtil fileUploadUtil;
    @Autowired
    UsersMapper usersMapper;
    @Autowired
    RelationShipService relationShipService;
    @Autowired
    CommentUtil commentUtil;

    /**
     * 查询用户基本信息
     *
     * @return Users
     * @Param 头部加token
     */
    @UserLoginToken
    @PostMapping("getUserInfo")
    public Result getUserInfoByUserId(HttpServletRequest httpServletRequest) {
        int userId = Integer.parseInt(tokenService.getUserId(httpServletRequest));
        return userInfoService.getUserInfoByUserId(userId);
    }

    /**
     * 修改用户基本信息
     *
     * @return int
     * @Param 头部加token, Users
     */
    @UserLoginToken
    @PostMapping("updateUserInfo")
    public Result updateUserInfo(HttpServletRequest httpServletRequest, Users users) {
        int userId = Integer.parseInt(tokenService.getUserId(httpServletRequest));
        users.setUserId(userId);
        return userInfoService.updateUserInfo(users);
    }

    /**
     * 自己查询粉丝
     *
     * @return FansList
     * @Param 头部加token
     */
    @UserLoginToken
    @PostMapping("getAllFansSelf")
    public Result getAllFansToken(HttpServletRequest httpServletRequest,@RequestParam(name = "page") int page,@RequestParam(name = "size") int size) {
        int userId = Integer.parseInt(tokenService.getUserId(httpServletRequest));
        return userInfoService.getFansByUserId(userId,page,size);
    }

    /**
     * 查询别人的粉丝
     *
     * @return FansList
     * @Param userId
     */
    @PostMapping("getAllFansOthers")
    public Result getAllFansByUserId(@RequestParam(name = "userId") int userId,@RequestParam(name = "page") int page,@RequestParam(name = "size") int size) {
        return userInfoService.getFansByUserId(userId,page,size);
    }

    /**
     * 自己查询关注
     *
     * @return BloggerList
     * @Param 头部加token
     */
    @UserLoginToken
    @PostMapping("getAllBloggerSelf")
    public Result getAllBloggerByToken(HttpServletRequest httpServletRequest,@RequestParam(name = "page") int page,@RequestParam(name = "size") int size) {
        int userId = Integer.parseInt(tokenService.getUserId(httpServletRequest));
        return userInfoService.getBloggerByUserId(userId,page,size);
    }

    /**
     * 查询别人的关注
     *
     * @return BloggerList
     * @Param 头部加token
     */
    @PostMapping("getAllBloggerOthers")
    public Result getAllBloggerByUserId(@RequestParam(name = "userId") int userId,@RequestParam(name = "page") int page,@RequestParam(name = "size") int size) {
        return userInfoService.getBloggerByUserId(userId,page,size);
    }

    /**
     * 查看自己主页除微博外的东西
     * @param httpServletRequest
     * @return
     */
    @UserLoginToken
    @PostMapping("getUserAvatarAndRelation")
    public Result getUserIndex(HttpServletRequest httpServletRequest) {
        int userId = Integer.parseInt(tokenService.getUserId(httpServletRequest));
        return userInfoService.getUserAvatarAndRelation(userId);
    }

    /**
     * 查看别人首页除微博外的东西
     * @param userId
     * @return
     */
    @PostMapping("getOtherInfo")
    public Result getOtherInfo(@RequestParam(name = "userId") String userId){
        return userInfoService.getOthersInfo(Integer.parseInt(userId));
    }

    /**
     * 查看自己主页所有微博
     * @param httpServletRequest
     * @return
     */

    @UserLoginToken
    @PostMapping("getUserAllBlog")
    public Result getUserAllBlog(HttpServletRequest httpServletRequest,@RequestParam(name = "page") int page,@RequestParam(name = "size") int size){
        int userId = Integer.parseInt(tokenService.getUserId(httpServletRequest));
        return userInfoService.getUserAllBlog(userId,page,size);
    }

    /**
     * 查看别人首页微博
     * @param userId
     * @param page
     * @param size
     * @return
     */
    @PostMapping("getOtherBlog")
    public Result getOtherBlog(@RequestParam(name = "userId") Integer userId,@RequestParam(name = "page") int page,@RequestParam(name = "size") int size){
        return userInfoService.getUserAllBlog(userId,page,size);
    }

    /**
     * 修改用户头像
     * @param httpServletRequest
     * @param file
     * @return
     */
    @UserLoginToken
    @PostMapping("changeAvatar")
    public Result changeAvatar(HttpServletRequest httpServletRequest,@RequestParam(name = "file") MultipartFile file){
        int userId = Integer.parseInt(tokenService.getUserId(httpServletRequest));
        String newUrl = "//" + fileUploadUtil.fileUpload(file,3);
        Users users = new Users();
        users.setUserId(userId);
        users.setAvatarUrl(newUrl);
        usersMapper.updateByPrimaryKeySelective(users);
        return Result.createSuccessResult();
    }

    /**
     * 判断是否关注
     * @param httpServletRequest
     * @param userId
     * @return
     */
    @UserLoginToken
    @PostMapping("ifConcern")
    public Result ifConcern(HttpServletRequest httpServletRequest,@RequestParam(name = "userId") int userId){
        int loginUserId = Integer.parseInt(tokenService.getUserId(httpServletRequest));
        return relationShipService.ifConcern(loginUserId,userId);
    }

    /**
     * 加关注或者取消关注
     * @param httpServletRequest
     * @param userId
     * @return
     */
    @UserLoginToken
    @PostMapping("addConcernOrSub")
    public Result addConcernOrSub(HttpServletRequest httpServletRequest,@RequestParam(name = "userId") int userId){
        int loginUserId = Integer.parseInt(tokenService.getUserId(httpServletRequest));
        return relationShipService.addConcernOrSub(loginUserId,userId);
    }

    /**
     * 用户名与手机匹配
     * @param userName
     * @param phone
     * @return
     */
    @PostMapping("matchNameAndPhone")
    public Result matchNameAndPhone(@RequestParam(name = "userName") String userName,@RequestParam(name = "phone") String phone){
        return userInfoService.matchNameAndPhone(userName,phone);
    }

    /**
     * 密码重置
     * @param users
     * @return
     */
    @PostMapping("newPassword")
    public Result newPassword(Users users){
        String newPassword = users.getPassword();
        String password = commentUtil.getMd5(newPassword);
        users.setPassword(password);
        usersMapper.updateByPrimaryKeySelective(users);
        return Result.createSuccessResult();
    }
    /**
     * 管理员查询用户信息
     */
    @PostMapping("/getNormalUser")
    public Result normalUsers(String userName, int page, int limit){
        return userInfoService.normalUsers(userName,page,limit);
    }
    /**
     * 管理员删除用户
     */
    @PostMapping("/delete")
    public Result deleteUsers(Integer userId){
        return userInfoService.deleteUsers(userId);
    }
    /**
     * 管理员封禁用户
     */
    @PostMapping("/banned")
    public Result bannedUsers(Integer userId){
        return userInfoService.bannedUsers(userId);
    }
    /**
     *
     */
    @PostMapping("/unblock")
    public Result unblockUsers(Integer userId){
        return userInfoService.unblockUsers(userId);
    }
    /**
     * 管理员查询用户
     */
    @PostMapping("/find")
    public Result findUsers(String userName){
        return userInfoService.findUsers(userName);
    }
}