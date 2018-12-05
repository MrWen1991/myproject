package top.winkin.aop.spring;

import org.springframework.stereotype.Repository;

/**
 */
@Repository
public class UserDaoImp implements UserDao {
    @Override
    public int addUser(int num) {
        System.out.println("add user ......");
        return 6666;
    }

    @Override
    public void updateUser() {
        System.out.println("update user ......");
    }

    @Override
    public void deleteUser() {
        System.out.println("delete user ......");
    }

    @Override
    public void findUser() {
        System.out.println("find user ......");
        throw new RuntimeException("there is exception on find user method ...");
    }
}
