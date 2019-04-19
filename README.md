## xUtilsOrm简介
[xUtils](https://github.com/wyouflf/xUtils)，只保留orm部分，相比GreenDao更迷你轻量，对于一些提前打包基础数据库进apk的情况，字段名更好控制。

## 功能介绍：
* android中的orm框架，一行代码就可以进行增删改查；
* 支持事务，默认关闭；
* 可通过注解自定义表名，列名，外键，唯一性约束，NOT NULL约束，CHECK约束等（需要混淆的时候请注解表名和列名）；
* 支持绑定外键，保存实体时外键关联实体自动保存或更新；
* 自动加载外键关联实体，支持延时加载；
* 支持链式表达查询，更直观的查询语义，参考下面的介绍或sample中的例子。

## 需要权限：

```xml
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
```

## 混淆时注意事项：

 * 添加Android默认混淆配置${sdk.dir}/tools/proguard/proguard-android.txt
 * 不要混淆xUtils中的注解类型，添加混淆配置：-keep class * extends java.lang.annotation.Annotation { *; }
 * 对使用DbUtils模块持久化的实体类不要混淆，或者注解所有表和列名称@Table(name="xxx")，@Id(column="xxx")，@Column(column="xxx"),@Foreign(column="xxx",foreign="xxx")；

## DbUtils使用方法：

```java
DbUtils db = DbUtils.create(this);
User user = new User(); //这里需要注意的是User对象必须有id属性，或者有通过@ID注解的属性
user.setEmail("wyouflf@qq.com");
user.setName("wyouflf");
db.save(user); // 使用saveBindingId保存实体时会为实体的id赋值

...
// 查找
Parent entity = db.findById(Parent.class, parent.getId());
List<Parent> list = db.findAll(Parent.class);//通过类型查找

Parent Parent = db.findFirst(Selector.from(Parent.class).where("name","=","test"));

// IS NULL
Parent Parent = db.findFirst(Selector.from(Parent.class).where("name","=", null));
// IS NOT NULL
Parent Parent = db.findFirst(Selector.from(Parent.class).where("name","!=", null));

// WHERE id<54 AND (age>20 OR age<30) ORDER BY id LIMIT pageSize OFFSET pageOffset
List<Parent> list = db.findAll(Selector.from(Parent.class)
                                   .where("id" ,"<", 54)
                                   .and(WhereBuilder.b("age", ">", 20).or("age", " < ", 30))
                                   .orderBy("id")
                                   .limit(pageSize)
                                   .offset(pageSize * pageIndex));

// op为"in"时，最后一个参数必须是数组或Iterable的实现类(例如List等)
Parent test = db.findFirst(Selector.from(Parent.class).where("id", "in", new int[]{1, 2, 3}));
// op为"between"时，最后一个参数必须是数组或Iterable的实现类(例如List等)
Parent test = db.findFirst(Selector.from(Parent.class).where("id", "between", new String[]{"1", "5"}));

DbModel dbModel = db.findDbModelAll(Selector.from(Parent.class).select("name"));//select("name")只取出name列
List<DbModel> dbModels = db.findDbModelAll(Selector.from(Parent.class).groupBy("name").select("name", "count(name)"));
...

List<DbModel> dbModels = db.findDbModelAll(sql); // 自定义sql查询
db.execNonQuery(sql) // 执行自定义sql
...

```