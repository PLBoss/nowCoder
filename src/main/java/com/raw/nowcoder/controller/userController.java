package com.raw.nowcoder.controller;


import com.raw.nowcoder.Util.HostHolder;
import com.raw.nowcoder.Util.StringUtil;
import com.raw.nowcoder.Util.constant;
import com.raw.nowcoder.annotation.LoginRequired;
import com.raw.nowcoder.entity.User;
import com.raw.nowcoder.service.MailService;
import com.raw.nowcoder.service.userService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class userController implements constant {

    public static Logger logger= LoggerFactory.getLogger(userController.class);

    @Autowired
    private userService userService;


    @Autowired
    private MailService mailService;

    @Autowired
    private HostHolder hostHolder;

    @LoginRequired
    @RequestMapping("/profile")
    public String getProfilePage(){
        return "/site/profile";

    }
    @LoginRequired
    @RequestMapping("/setting")
    public String getSetingPage(){
        return "/site/setting";

    }



    @RequestMapping("/register")
    public String getRegistry(){

        return "/site/register";

    }
    @LoginRequired
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public String uploadHeader(MultipartFile headerImage, Model model)  {



        //判空处理

        if (headerImage==null) {
            model.addAttribute("error","上传文件为空");
        }
        //文件格式验证
        String originalFilename = headerImage.getOriginalFilename();

        String suffix =originalFilename.substring(originalFilename.lastIndexOf('.'));

        if(!suffix.equals(".jpg")&&!suffix.equals(".png")&&!suffix.equals(".jpeg")){
            model.addAttribute("error","文件格式错误，请上传jpg/png/jpeg格式的图片");
            return "/site/setting";
        }

        //对文件进行重命令

        String imageName=StringUtil.gennerUUID()+suffix;

        //定位文件存储的路劲


        File realPath=new File(dest+"/"+imageName);

        //判断父目录是否存在，如果不存在就进行创建
        if (!realPath.getParentFile().exists()) {
            realPath.getParentFile().mkdir();
        }

        //将图片文件上传到图片服务器的文件夹
        try {
            headerImage.transferTo(realPath);

            //将数据库中对应用户的图片路劲进行修改
            String  headUrl=ContextPath+"head/"+imageName;

            User user = hostHolder.getUsers();

            System.out.println(user);

            userService.updateHeadUrl(user.getId(),headUrl);


        } catch (IOException e) {
            logger.error("上传失败");
        }

        return "redirect:/index";


    }
    //通过网络映射的地址，将图片从本地文件夹读取上去
    @RequestMapping("/head/{filename}")
    public void getHeader(@PathVariable("filename") String filename, HttpServletResponse response){

        filename=dest+"/"+filename;

        String suffix=filename.substring(filename.lastIndexOf('.'));


        //设置响应的图片格式

        response.setContentType("image/"+suffix);

        //将图片以字节流的方式进行输出



        try (
                //自动关闭流
                ServletOutputStream outputStream = response.getOutputStream();

                FileInputStream fis=new FileInputStream(filename);
                )
        {

            //利用缓冲区将图片写入到响应的输出流中
            byte[] buf=new byte[1024];
            int len=0;

            while ((len=fis.read(buf))!=-1){
                outputStream.write(buf,0,len);
            }


        } catch (IOException e) {
            logger.error("头像读取失败"+e.getMessage());
        }


    }

    @RequestMapping("/activation/{id}/{code}")
    public String acactivate(@PathVariable("id") String id,@PathVariable("code") String code, Model model){


        System.out.println(id+":"+code);



        User user = userService.selectUserById(id);

        //对比传入的激活码和数据库中的
        if(code.equals(user.getActivationCode())){

            //修改用户的状态
            int status = userService.updateStatus(id);

            System.out.println(status);

            model.addAttribute("Msg","激活成功");

        }






        return "/site/operate-result";

    }





    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String register(String username, String password, String email, Model model ){

        System.out.println(username+"|"+password+"|"+email);

        Map<String, Object> map = userService.insertUser(username, password, email);

        if (map.isEmpty()) {

            //跳转到注册成功的页面
            model.addAttribute("Msg","我们已经向你的有邮箱发送了一封激活邮件,请尽快激活！");



            return "/site/operate-result";


        }else{
            //注册失败，将信息返回到注册页面

            model.addAttribute("usernameMsg",map.get("usernameMsg"));
            model.addAttribute("passwordMsg",map.get("passwordMsg"));
            model.addAttribute("emailMsg",map.get("emailMsg"));

            return "/site/register";


        }




    }




}


