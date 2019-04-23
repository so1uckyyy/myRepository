package com.chixing.controller;

import com.chixing.common.JsonResult;
import com.chixing.entity.Customer;
import com.chixing.entity.Note;
import com.chixing.service.CustomerService;
import com.chixing.service.NoteService;
import com.chixing.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/note")
public class NoteController {

    @Autowired
    private NoteService noteService;
    @Autowired
    private CustomerService customerService;

    //首页中获得最新的前10条游记对象
    @RequestMapping("getLast")
    @ResponseBody
    public JsonResult getall(){
        List<Note> noteList =  noteService.getLastTen();
        Map<String,Object> data = new HashMap<>();
        data.put("noteList",noteList);
        return JsonResult.createSuccessJsonResult(data);
    }

    @RequestMapping("/goToadd")
    public String goToadd(){
        return "note/add";
    }

    //游记头图上传
    @RequestMapping("headImgUpload")
    @ResponseBody
    //文件上传操作处理
    public JsonResult upload(HttpServletRequest request, MultipartFile upload){
        String path = request.getServletContext().getRealPath("/upload");
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }

        String fileName =   upload.getOriginalFilename();
        String uuid =    UUID.randomUUID().toString();

        fileName = uuid+"_"+fileName;
        try {
            File uploadFile = new File(file,fileName);
            upload.transferTo(uploadFile);
            // 返回json :当前上传文件在tomcat服务器中的相对路径
            Map<String ,Object> data = new HashMap<>();
            data.put("filePath","upload/"+fileName);
            return JsonResult.createSuccessJsonResult(data);
        } catch (IOException e) {
            e.printStackTrace();
            return JsonResult.createFailJsonResult(null);
        }
    }

    //添加游记
    @RequestMapping("add")
    @ResponseBody
    public JsonResult save(Note note, HttpServletRequest request){
        //注意添加游记要关联作者id,即当前登录用户的id,还有当前游记创建的时间
        int custId = (int)request.getSession().getAttribute("customerId");
        note.setCustId(custId);
        note.setNoteCreateTime(new Date());
        if (this.noteService.save(note)){
            return JsonResult.createSuccessJsonResult(null);
        }else{
            Map<String,Object> data = new HashMap<>();
            data.put("reason","添加游记失败");
            return JsonResult.createFailJsonResult(data);
        }
    }

    // 跳转到游记的详情页中
    @RequestMapping("goToDetail")
    public String goToDetail(){
        return "note/detail";
    }
    //查看我最近的游记（刚写的游记）
    @RequestMapping("myLastNote")
    @ResponseBody
    public JsonResult getMyLastNote(HttpServletRequest request){
        int custId = (int)request.getSession().getAttribute("customerId");
        Note myLastNote=this.noteService.getMyLastNote(custId);
        Map<String,Object> data = new HashMap<>();
        data.put("myLastNote",myLastNote);
        return JsonResult.createSuccessJsonResult(data);
    }
    //分页查询
    @RequestMapping("getall/{pageNum}")
    @ResponseBody
    public JsonResult getByPage(@PathVariable("pageNum") int pageNum){
        //5条游记（cust_id）
        List<Note> noteList=this.noteService.getAll(pageNum);
        int totalCount = this.noteService.getCount();//游记总记录数
        int totalPage =  (int)  Math.ceil(totalCount% PageUtil.PAGE_SIZE==0?totalCount/ PageUtil.PAGE_SIZE:totalCount/ PageUtil.PAGE_SIZE+1);//共几页
        //
        //游记作者的信息


        for(Note note:noteList){
            Customer customer =  this.customerService.getById(note.getCustId());
            note.setOthers1(customer.getCustName());
        }



        Map<String,Object> data = new HashMap<>();
        data.put("noteList",noteList);        // 当前页码的游记作者对象
        data.put("totalCount",totalCount);    //游记总记录数
        data.put("totalPage",totalPage);      //共几页
        return   JsonResult.createSuccessJsonResult(data);
    }

    @RequestMapping("goToMyNoteList")
    public String goToMyNoteList(){
        return "note/mylist";
    }


    @RequestMapping("goToDescriptionById/{noteId}")
    public String goToDescription(@PathVariable("noteId") int noteId){
        System.out.println("noteId:"+noteId);
        return "note/description.html?noteid="+noteId;
    }
    @RequestMapping("goIndex")
    public String goIndex(){
        return "index";
    }
}
