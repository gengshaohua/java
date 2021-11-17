package com.example.springboot.transcation;

import com.alibaba.fastjson.JSON;
import com.example.springboot.entity.UserInfo;
import com.example.springboot.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;


/**
 * @author gengshaohua.ext
 * @date 2021/10/28
 * @apiNote
 */
@Service
public class TranscationManual {

    @Autowired
    private DataSourceTransactionManager dataSourceTransactionManager;

    @Autowired
    private TransactionDefinition transactionDefinition;

    @Autowired
    private UserInfoService userInfoService;


    public void insertUserTranscation(){
        //手动开启事务
        TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(transactionDefinition);
        String firstName = "aaa";
        String lastName = "bbb";
        UserInfo exists = userInfoService.getUserInfo(firstName, lastName);
        System.out.println(JSON.toJSONString(exists));
        try {
            UserInfo userInfo = new UserInfo();
            userInfo.setFirstName(firstName);
            userInfo.setLastName(lastName);
            userInfoService.insertUser(userInfo);
            //手动提交事务
            dataSourceTransactionManager.commit(transactionStatus);//提交
        }catch (RuntimeException e){
            //手动回滚事务
            dataSourceTransactionManager.rollback(transactionStatus);//最好是放在catch 里面,防止程序异常而事务一直卡在哪里未提交
            e.printStackTrace();
        }
        throw new RuntimeException("aaaa");
    }

    public void insertUserTranscationRequiresNew(){
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW); // 事物隔离级别，开启新事务，这样会比较安全些。
        TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(def); // 获得事务状态
        String firstName = "aaa";
        String lastName = "bbb";
        try {
            UserInfo userInfo = new UserInfo();
            userInfo.setFirstName(firstName);
            userInfo.setLastName(lastName);
            userInfoService.insertUser(userInfo);
            //手动提交事务
            dataSourceTransactionManager.commit(transactionStatus);//提交
        }catch (RuntimeException e){
            //手动回滚事务
            dataSourceTransactionManager.rollback(transactionStatus);//最好是放在catch 里面,防止程序异常而事务一直卡在哪里未提交
            e.printStackTrace();
        }
        throw new RuntimeException("aaaa");
    }

}
