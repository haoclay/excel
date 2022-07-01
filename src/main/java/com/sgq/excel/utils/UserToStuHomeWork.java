package com.sgq.excel.utils;

import com.sgq.excel.pojo.StuHomeWork;
import com.sgq.excel.pojo.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author : SGQ
 * @date : 2022-05-11 22:09
 **/
public class UserToStuHomeWork {

   public static List<StuHomeWork> getStuHomeWorkList(List<List<User>> userList){
       List<StuHomeWork> stuHomeWorkList = new ArrayList();
        for (List<User> users : userList) {
            User temp = users.get(0);
            String clazz = temp.getClazz();
            String createDate = temp.getCreateDate();
            System.out.println("createDate = " + createDate);
            Integer dayIndex = temp.getDayIndex();

            /*if(createDate.contains("day")) {
                String[] str = createDate.split("day");
                createDate  = "day" + str[1].substring(0, 2);
            }*/
            for (User user : users) {
                StuHomeWork stuHomeWork = new StuHomeWork();
                stuHomeWork.setName(user.getUsername());
                stuHomeWork.setClazz(clazz);
                stuHomeWork.setCreateDate(createDate);
                stuHomeWork.setState(user.getGrade().equals("未交")?"0":"1");
                stuHomeWork.setRemark(new Date());
                stuHomeWork.setDayIndex(dayIndex);
                stuHomeWork.setUuid(user.getId());
                stuHomeWork.setUpdateTime(user.getUpdateTime());

                stuHomeWorkList.add(stuHomeWork);
            }

        }
       System.out.println("--->"+stuHomeWorkList);
       return stuHomeWorkList;
    }
}