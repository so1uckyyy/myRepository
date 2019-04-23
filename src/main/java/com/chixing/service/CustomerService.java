package com.chixing.service;

import com.chixing.entity.Customer;

public interface CustomerService {

    /**
     *  根据手机号查看用户是否存在过
     * @param telno 手机号
     * @return 返回查询出来的用户对象
     */
    public Customer getByTelno(long telno);

    /**
     * 添加新用户
     * @param customer  新账户对象
     * @return  返回是否添加成功
     */
    public boolean save(Customer customer);

    public Customer getById(int custId);

}
