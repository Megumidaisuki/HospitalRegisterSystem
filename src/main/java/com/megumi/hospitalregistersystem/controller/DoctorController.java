package com.megumi.hospitalregistersystem.controller;

import com.megumi.hospitalregistersystem.common.Result;
import com.megumi.hospitalregistersystem.controller.dto.LoginDTO;
import com.megumi.hospitalregistersystem.controller.request.LoginRequest;
import com.megumi.hospitalregistersystem.controller.request.NewPassRequest;
import com.megumi.hospitalregistersystem.domain.*;
import com.megumi.hospitalregistersystem.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping("/doctor")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;
    //注册
    @PutMapping("/save")
    public Result save(@RequestBody Doctor doctor){
        doctorService.save(doctor);
        return Result.success();
    }

    //登录
    @PostMapping("/login")
    public Result login(@RequestBody LoginRequest loginRequest) {
        LoginDTO loginDTO = doctorService.login(loginRequest);
        return Result.success(loginDTO);
    }
    //退出登录
    @PostMapping("/logout")
    public Result logout(){
        doctorService.logout();
        return Result.success();
    }
    //得到医生挂号类别
    @GetMapping("/getTypeMessage")
    public Result getTypeMessage(){
        return Result.success(doctorService.getTypeMessage());
    }
    //验证修改密码





    //修改密码
    @PutMapping("/newPass")
    public Result newPass(@RequestBody NewPassRequest newPassRequest){
        doctorService.newPass(newPassRequest);
        return Result.success();
    }

    //得到当前输入的医生信息
    @GetMapping("/getDoctor")
    public Result getDoctor(){
        return Result.success(doctorService.getDoctor());
    }

    //修改医生信息
    @PutMapping("/update")
    public Result update(@RequestBody Doctor doctor){
        doctorService.update(doctor);
        return Result.success();
    }

    //医生新建排班模板
    @PutMapping("/newTemplate")
    public Result newTemplate(String doctorName,String typeName,String registerAmounts,String timeScope){
        doctorService.newTemplate(doctorName,typeName,registerAmounts,timeScope);
        return Result.success();
    }

    //修改排班模板
    @PutMapping("/updateTemplate")
    public Result updateTemplate(@RequestBody ArrangementTemplate arrangementType){
        doctorService.updateTemplate(arrangementType);
        return Result.success();
    }
    //删除排班模板
    @DeleteMapping("/deleteTemplate")
    public Result deleteTemplate(ArrangementTemplate arrangementTemplate){
        doctorService.deleteTemplate(arrangementTemplate);
        return Result.success();
    }
    //查询医生所有挂号类别
    @GetMapping("/getAllRegisterType")
    public Result getAllRegisterMessage(){
        return Result.success(doctorService.getAllRegisterType());
    }

    //医生添加挂号类别
    @PutMapping("/addRegisterType")
    public Result addRegisterType(String name,String date,String registerAmounts,String typeName,String timeScope){
        doctorService.addRegisterType(name,date,registerAmounts,typeName,timeScope);
        return Result.success();
    }
    //医生删除挂号类别
    @DeleteMapping("/deleteRegisterType")
    public Result deleteRegisterType(Integer id){
        doctorService.deleteRegisterType(id);
        return Result.success();
    }

    //根据日期返回挂号类别
    @GetMapping("/getRegisterTypeByDate")
    public Result getRegisterTypeByDate(String date){
        return Result.success(doctorService.getRegisterTypeByDate(date));
    }


    //删除类别信息
    @DeleteMapping("/deleteTypeMessage")
    public Result deleteTypeMessage(Integer id){
        doctorService.deleteTypeMessage(id);
        return Result.success();
    }
    //显示未预约的挂号类别
    @GetMapping("/getFirstStatusRegisterType")
    public Result getFirstStatusRegisterType(){
        return Result.success(doctorService.getFirstStatusRegisterType());
    }

    //显示已预约的挂号类别
    @GetMapping("/getSecondStatusRegisterType")
    public Result getSecondStatusRegisterType(){
        return Result.success(doctorService.getSecondStatusRegisterType());
    }

    //显示已完成的挂号类别
    @GetMapping("/getThirdStatusRegisterType")
    public Result getThirdStatusRegisterType(){
        return Result.success(doctorService.getThirdStatusRegisterType());
    }
    //得到已预约和已完成的订单信息
    @GetMapping("/getFullRegisterMessage")
    public Result getFullRegisterMessage(){
        return Result.success(doctorService.getFullRegisterMessage());
    }

    //查看未支付患者信息
    @GetMapping("/getRegisterMessageOne")
    public Result getRegisterMessageOne(){
        return Result.success(doctorService.getRegisterMessageOne());
    }

    //修改订单信息（传订单信息对象）
    @PutMapping("/updateRegisterMessage")
    public Result updateRegisterMessage(@RequestBody RegisterMessage registerMessage){
        doctorService.updateRegisterMessage(registerMessage);
        return Result.success();
    }
    //删除订单信息（传id）
    @DeleteMapping("/deleteRegisterMessage")
    private Result deleteRegisterMessage(Integer id){
        doctorService.deleteRegisterMessage(id);
        return Result.success();
    }

    //查看已完成患者信息
    @GetMapping("/getRegisterMessageTwo")
    public Result getRegisterMessageTwo(){
        return Result.success(doctorService.getRegisterMessageTwo());
    }


    //查看患者信息
    @GetMapping("/getPatientMessage")
    public Result getPatientMessage(){
        return Result.success(doctorService.getPatientMessage());
    }


    //修改患者信息(修改所有数据)
    @PutMapping("/updatePatientMessage")
    public Result updatePatientMessage(@RequestBody PatientMessage patientMessage){
        doctorService.updatePatientMessage(patientMessage);
        return Result.success();
    }

    //查看患者历史预约信息
    @GetMapping("/getHistoryRegisterMessage")
    public Result getHistoryRegisterMessage(String patientName){
        return Result.success(doctorService.getHistoryRegisterMessage(patientName));
    }

    //显示订单信息（日期，时间段，挂号名称，挂号科室，挂号状态）
    @GetMapping("/getSpecialRegisterMessage")
    public Result getSpecialRegisterMessage(){
        return Result.success(doctorService.getSpecialRegisterMessage());
    }




//    //根据id查医生信息
//    @GetMapping("/{id}")
//    public Result getById(@PathVariable Integer id) {
//        return Result.success(doctorService.getById(id));
//    }


//    //医生信息的分页查询
//    @GetMapping("/page")
//    public Result page(DoctorPageRequest pageRequest){
//        return Result.success(doctorService.page(pageRequest));
//    }

//    //查询所有医生信息
//    @GetMapping("/findAll")
//    public Result findAll(){
//        return Result.success(doctorService.findAll());
//    }

//    //修改患者信息(失约次数加一)
//    @PutMapping("/updatePatientMessageStatus")
//    public Result updatePatientMessageStatus(@RequestBody Patient patient){
//        doctorService.updatePatientMessage(patient);
//        return Result.success();
//    }




    //展现医生的排班信息(通过医生的名字)
    @GetMapping("/showSchedule")
    public Result showSchedule(Doctor doctor){
        return Result.success(doctorService.showSchedule(doctor));
    }

    //展现排班模板信息(通用，此信息并不属于特一医生)
    @GetMapping("/showTemplate")
    public Result showSchedule(){
        return Result.success(doctorService.showTemplate());
    }


    //修改排班信息,同时更新挂号类型表
    @PutMapping("/updateSchedule")
        public Result updateSchedule(@RequestBody ArrangementMessage arrangementMessage){
        doctorService.updateSchedule(arrangementMessage);
        return Result.success();
    }
    //删除排班信息
    @DeleteMapping("/deleteSchedule")
    public Result deleteSchedule(ArrangementMessage arrangementMessage){
        doctorService.deleteSchedule(arrangementMessage);
        return Result.success();
    }


    //选择排班模板，将当日排班设置为模板排班时间，同时插入新的挂号信息数据
    @PutMapping("/setMessageByTemplate")
    public Result setMessageByTemplate(ArrangementTemplate arrangementTemplate,Doctor doctor) {
        doctorService.setMessageByTemplate(arrangementTemplate,doctor);
        return Result.success();
    }
    //将排班信息按日复制
    @PutMapping("/copyByDay")
    public Result copyByDay(String date,String targetDate){
        doctorService.copyByDay(date,targetDate);
        return Result.success();
    }
    //将排班信息按周复制
    @PutMapping("/copyByWeek")
    public Result copyByWeek(int year,int week,int targetWeek){
        doctorService.copyByWeek(year,week,targetWeek);
        return Result.success();
    }

    //提供一个查看历史挂号信息和订单状态（已预约，已付款）的接口
    @GetMapping("/history")
    public Result getHistory() {
        return Result.success(doctorService.getHistory());
    }
    //提供一个修改订单信息的接口(将status字段从“已预约0”改成“已付款1”)
    @PutMapping("/updateStatus")
    public Result updateStatus(RegisterMessage registerMessage) {
        doctorService.updateStatus(registerMessage);
        return Result.success();
    }











}
