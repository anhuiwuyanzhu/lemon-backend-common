package com.lemon.violet.controller;


import com.lemon.violet.annotation.LogField;
import com.lemon.violet.pojo.rto.TestParent;
import com.lemon.violet.pojo.vo.RespVo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

//    @PostMapping("/test")
//    public RespVo getTest(String before, @RequestParam(value = "ids") @LogField("ids") List<Integer> ids, @LogField("欢迎") String hello, @RequestBody TestParent req){
//        RespVo rvo = new RespVo();
//        rvo.setRet("更新成功");
//        return rvo;
//    }

//    @PostMapping("/testArray")
//    public RespVo testArray(){
//
//    }
}
