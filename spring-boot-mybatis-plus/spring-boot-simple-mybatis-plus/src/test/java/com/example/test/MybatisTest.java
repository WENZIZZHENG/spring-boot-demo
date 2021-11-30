package com.example.test;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.BaseTest;
import com.example.config.MybatisPlusConfig;
import com.example.entity.User;
import com.example.mapper.UserMapper;
import com.example.service.IUserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * mybatis测试
 * </p>
 *
 * @author MrWen
 **/
public class MybatisTest extends BaseTest {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private IUserService userService;


    //==================================mapper相关的==================================

    /**
     * 新增
     */
    @Test
    public void insert() {
        this.userMapper.insert(User.builder()
                .realName("小黄")
                .age(18)
                .email("xxx@qq.com")
                .managerId(1094592041087729666L)
                .remark("备注")
                .build());
    }

    /**
     * 更新方法
     */
    @Test
    public void update() {
        //根据id修改，推荐
//        User user = User.builder()
//                .id(1088250446457389058L)
//                .age(26)
//                .email("1234@qq.com").build();
//        this.userMapper.updateById(user);

        //修改的值
        User user = User.builder()
                .email("lyw2020@163.com")
                .age(29).build();

        //修改的条件
        //根据updateWrapper修改  不推荐
//        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
//        updateWrapper.eq("age", 18);
//        this.userMapper.update(user, updateWrapper);

        //根据lambdaUpdate修改  推荐
        userMapper.update(user, Wrappers.lambdaUpdate(User.class)
                .eq(User::getAge, 18));
    }

    /**
     * 删除方法
     */
    @Test
    public void delete() {
        //根据id删除，推荐
//        this.userMapper.deleteById(1465675282305024002L);

        //条件删除,使用queryWrapper
        userMapper.delete(Wrappers.lambdaUpdate(User.class)
                .eq(User::getAge, 18));
    }


    /**
     * 基础查询
     */
    @Test
    public void selectBase() {
        //查询所有
        System.out.println("=============================查询所有=============================");
        userMapper.selectList(null).forEach(System.out::println);
        System.out.println("=============================查询所有=============================");

        //根据id查询
        System.out.println("=============================selectById=============================");
        User selectById = this.userMapper.selectById(1087982257332887553L);
        System.out.println(selectById);
        System.out.println("=============================selectById=============================");

        //根据id查询-批量
        System.out.println("=============================selectBatchIds=============================");
        List<User> selectBatchIds = this.userMapper.selectBatchIds(Arrays.asList(1087982257332887553L, 1088250446457389058L, 1094592041087729666L));
        selectBatchIds.forEach(System.out::println);
        System.out.println("=============================selectBatchIds=============================");


        /*
          map条件查询，字段必须是数据库对应的字段！！！不太推荐
         */
        System.out.println("=============================map条件查询=============================");
        Map<String, Object> map = new HashMap<>();
        map.put("age", "28");
        map.put("manager_id", 1088248166370832385L);
        List<User> selectByMap = this.userMapper.selectByMap(map);
        selectByMap.forEach(System.out::println);
        System.out.println("=============================map条件查询=============================");


        System.out.println("=============================queryWrapper条件查询=============================");
        // queryWrapper条件查询，名字中包含雨并且年龄小于40 ，字段必须是数据库对应的字段！！！  不太推荐
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", "雨").lt("age", 40);
        List<User> userList = this.userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
        System.out.println("=============================queryWrapper条件查询=============================");


        System.out.println("=============================lambdaQuery条件查询=============================");
        // lambdaQuery条件查询，名字中包含雨并且年龄大于40  强烈推荐，以后修改直接修改实体类属性即可
        List<User> userList1 = this.userMapper.selectList(Wrappers.lambdaQuery(User.class)
                .like(User::getRealName, "雨")
                .gt(User::getAge, 40)
        );
        userList1.forEach(System.out::println);
        System.out.println("=============================lambdaQuery条件查询=============================");
    }

    /**
     * 分页查询，需要配置分页插件 {@link MybatisPlusConfig}
     */
    @Test
    public void selectPage() {
        LambdaQueryWrapper<User> lambdaQuery = Wrappers.lambdaQuery(User.class);
        lambdaQuery.ge(User::getAge, 26);
        Page<User> page = new Page<>(1, 2);
        //不需要查 总页数 总记录数
        IPage<User> iPage = userMapper.selectPage(page, lambdaQuery);
        System.out.println("总页数" + iPage.getPages());
        System.out.println("总记录数" + iPage.getTotal());
        List<User> userList = iPage.getRecords();
        userList.forEach(System.out::println);
    }


    /**
     * Lambda条件构架器 （实体类属性） 注意：QueryWrapper条件构造器是以数据库中的字段为准
     */
    @Test
    public void selectLambda() {
        LambdaUpdateWrapper<User> lambda = Wrappers.lambdaUpdate(User.class);
        //创建LambdaUpdateWrapper的其它写法
//        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//        LambdaQueryWrapper<User> lambdaQuery = Wrappers.<User>lambdaQuery();

//        lambda.like(User::getRealName, "雨").lt(User::getAge, 40);
//        List<User> userList = this.userMapper.selectList(lambda);
//        userList.forEach(System.out::println);

        //5、名字为王姓并且（年龄小于40或邮箱不为空） ame like '王%' and (age<40 or email is not null)
        lambda.likeRight(User::getRealName, "王").and(qw -> qw.lt(User::getAge, 40).or().isNotNull(User::getEmail));
        List<User> userList = this.userMapper.selectList(lambda);
        userList.forEach(System.out::println);
    }

    /**
     * queryWrapper条件查询
     */
    @Test
    public void selectByWrapper() {
        /**
         * queryWrapper条件查询，字段必须是数据库对应的字段！！！
         */
        // 1、名字中包含雨并且年龄小于40
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.like("name", "雨").lt("age", 40);
//        List<User> userList = this.userMapper.selectList(queryWrapper);
//        userList.forEach(System.out::println);

        //2、名字中包含雨年并且龄大于等于20且小于等于40并且email不为空
//        QueryWrapper<User> queryWrapper2 = new QueryWrapper<>();
//        queryWrapper2.between("age", 20, 40).isNotNull("email");
//        List<User> userList2 = this.userMapper.selectList(queryWrapper2);
//        userList2.forEach(System.out::println);

        //3、名字为王姓或者年龄大于等于25，按照年龄降序排列，年龄相同按照id升序排列
//        QueryWrapper<User> queryWrapper3 = new QueryWrapper<>();
//        queryWrapper3.likeRight("name", "王").or().ge("age", 25).orderByDesc("age").orderByAsc("id");
//        List<User> userList3 = this.userMapper.selectList(queryWrapper3);
//        userList3.forEach(System.out::println);

        //4、创建日期为2019年2月14日并且直属上级为名字为王姓
//        QueryWrapper<User> queryWrapper4 = new QueryWrapper<>();
//        queryWrapper4.apply("date_format(create_time,'%Y-%m-%d')={0}", "2019-02-14")
//                .inSql("manager_id", "select id from t_user where name like '王%'");
//        List<User> userList4 = this.userMapper.selectList(queryWrapper4);
//        userList4.forEach(System.out::println);

        //5、名字为王姓并且（年龄小于40或邮箱不为空）
//        QueryWrapper<User> queryWrapper5 = new QueryWrapper<>();
//        queryWrapper5.likeRight("name", "王").and(wq -> wq.lt("age", 40).or().isNotNull("email"));
//        List<User> userList5 = this.userMapper.selectList(queryWrapper5);
//        userList5.forEach(System.out::println);

        //6、名字为王姓或者（年龄小于40并且年龄大于20并且邮箱不为空）
//        QueryWrapper<User> queryWrapper6 = new QueryWrapper<>();
//        queryWrapper6.likeRight("name", "王").and(qw -> qw.lt("age", 40).gt("age", 20).isNotNull("email"));
//        List<User> userList6 = this.userMapper.selectList(queryWrapper6);
//        userList6.forEach(System.out::println);

        //7、（年龄小于40或邮箱不为空）并且名字为王姓
//        QueryWrapper<User> queryWrapper7 = new QueryWrapper<>();
//        queryWrapper7.and(qw -> qw.lt("age", 40).or().isNotNull("email")).likeRight("name", "王");
//        List<User> userList7 = this.userMapper.selectList(queryWrapper7);
//        userList7.forEach(System.out::println);

        //8、年龄为30、31、34、35
//        QueryWrapper<User> queryWrapper8 = new QueryWrapper<>();
//        queryWrapper8.in("age", Arrays.asList(30, 31, 34, 35));
//        List<User> userList8 = this.userMapper.selectList(queryWrapper8);
//        userList8.forEach(System.out::println);

        //9、只返回满足条件的其中一条语句即可
//        QueryWrapper<User> queryWrapper9 = new QueryWrapper<>();
//        queryWrapper9.in("age", Arrays.asList(30, 31, 34, 35)).last(" limit 1 ");
//        List<User> userList9 = this.userMapper.selectList(queryWrapper9);
//        userList9.forEach(System.out::println);

        //11、按照直属上级分组，查询每组的平均年龄、最大年龄、最小年龄。并且只取年龄总和小于500的组。
        //select avg(age) avg_age,min(age) min_age,max(age) max_age from user group by manager_id having sum(age) <500
        QueryWrapper<User> queryWrapper11 = new QueryWrapper<>();
        queryWrapper11.select("avg(age) avg_age", "min(age) min_age", "max(age) max_age")
                .groupBy("manager_id")
                //{0} 占位符，一定要加上。防止sql注入！！！
                .having("sum(age)<{0}", 500);
        List<User> userList11 = this.userMapper.selectList(queryWrapper11);
        userList11.forEach(System.out::println);
    }

    /**
     * select中字段不全部出现的查询
     */
    @Test
    public void select() {
        //select id,name from user where name like '%雨%' and age<40
        /*
        SELECT id,name FROM sys_user WHERE (name LIKE ? AND age < ?)
        有缺陷，如果实体类名称是 realName，不能对应的映射！
         */
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.like("name", "雨").lt("age", 40).select("id", "name");
//        List<User> users = this.userMapper.selectList(queryWrapper);
//        users.forEach(System.out::println);

         /*
        SELECT id,name AS realName,email,age FROM sys_user WHERE (name LIKE ? AND age < ?)
        排除一部分字段
         */
        QueryWrapper<User> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.like("name", "雨").lt("age", 40)
                .select(User.class, info -> !info.getColumn().equals("create_time") && !info.getColumn().equals("manager_id"));
        List<User> users = this.userMapper.selectList(queryWrapper2);
        users.forEach(System.out::println);

        List<Map<String, Object>> mapList = this.userMapper.selectMaps(queryWrapper2);
        mapList.forEach(System.out::println);


    }

    /**
     * 条件选择查询
     */
    @Test
    public void conditionTest() {
        //SELECT id,name AS realName,create_time,manager_id,email,age FROM sys_user WHERE (name LIKE ?)
//        String name = "王";
//        String email = "";
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.likeRight(StringUtils.isNoneBlank(name), "name", name)
//                .likeRight(StringUtils.isNoneBlank(email), "email", email);
//        List<User> userList = this.userMapper.selectList(queryWrapper);
//        userList.forEach(System.out::println);

        //SELECT id,name AS realName,create_time,manager_id,email,age FROM sys_user WHERE name=? AND email LIKE CONCAT('%',?,'%')
        /**
         *   LIKE CONCAT('%',?,'%')   实体类字段定义
         *  @TableField(condition = SqlCondition.LIKE)
         *   private String email;
         */
        User user = User.builder()
                .realName("王")
                .email("x")
                .build();
        QueryWrapper<User> queryWrapper2 = new QueryWrapper<>(user);
        List<User> userList2 = this.userMapper.selectList(queryWrapper2);
        userList2.forEach(System.out::println);
    }

    /**
     * 全等查询
     */
    @Test
    public void selectByAllEq() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        Map<String, Object> map = new HashMap<>();
        map.put("name", "王天风");
        //全等于 年龄也为null
        map.put("age", null);
        queryWrapper.allEq(map);
        List<User> userList = this.userMapper.selectList(queryWrapper);

        userList.forEach(System.out::println);
    }

    //==================================mapper相关的==================================


    //==================================service相关的==================================

    /**
     * 通用service操作，简单的操作,推荐这样！！！！
     */
    @Test
    public void getOneExample() {
        User userOne = this.userService.getOne(Wrappers.lambdaQuery(
                User.builder()
                        .age(13)
                        .build()));
        System.out.println("userOne = " + userOne);
    }

    /**
     * 通用service操作，大于小于等于复杂的操作，推荐这样!!!
     */
    @Test
    public void getOne() {
        User userOne = this.userService.getOne(Wrappers.lambdaQuery(User.class).le(User::getAge, 24));
        System.out.println(userOne);
    }


    @Test
    public void Batch() {
        User user1 = User.builder()
                .realName("李四")
                .age(12)
                .email("wsz@163.com")
                .build();
        User user2 = User.builder()
                .realName("张三")
                .age(11)
                .build();
        this.userService.saveOrUpdateBatch(Arrays.asList(user1, user2));
    }

    /**
     * 链式操作
     */
    @Test
    public void chain() {
        //查询操作
        List<User> userList = this.userService.list(Wrappers.lambdaQuery(User.class)
                .gt(User::getAge, 18)
                .likeRight(User::getRealName, "王"));
        userList.forEach(System.out::println);

        //修改操作
        this.userService.update(Wrappers.lambdaUpdate(User.class)
                .eq(User::getAge, 31).set(User::getAge, 18));

        //删除操作
        this.userService.remove(Wrappers.lambdaQuery(User.class).eq(User::getAge, 18));
        userService.list().forEach(System.out::println);
    }
    //==================================service相关的==================================
}
