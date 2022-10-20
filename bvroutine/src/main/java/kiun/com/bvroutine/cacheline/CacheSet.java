package kiun.com.bvroutine.cacheline;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RUNTIME)
public @interface CacheSet {

    /**
     * 关联表格名称.
     * 可以多表关联例如:
     * 主表名称={子字段=子表名称}
     * @return 配置管理表格名称.
     */
    String Name();

    /**
     * 关联的主键.
     * @return 配置主键.
     */
    String Key();

    /**
     * 过滤器,设置过滤条件,对比参数中的对应值.
     * 上行处理时作为插入条件.
     * @return 过滤条件.
     */
    String Filter() default "";

    /**
     * 参数映射.下行查询使用
     * 例如 "{'townCode':PARAMS.adCode.substring(0,6)}",
     * 表示获取传入参数adCode前6个字符作为townCode的查询关键词.
     * @return 配置的参数映射.
     */
    String Params() default "";

    /**
     * 强制标志为List,默认不使用强制, 智能识别List.
     * @return 是否强制为List.
     */
    boolean IsList() default false;

    /**
     * 功能的简单描述.
     * @return 描述.
     */
    String Description() default "";

    /**
     * 关联的逻辑关联, 格式tableName.fieldName = value.
     * @return 逻辑关联.
     */
    String[] About() default {};

    /**
     * 当查询不到数据时填充的对象.
     * @return 固定空值对象.
     */
    String Null() default "";

    /**
     * 主键修改方案的消费者.
     * 关联接口插入操作时返回的主键需要替换本接口的Key, "{'paramKey':'replaceKey'}".
     * paramKey
     * @return 参数名称.
     */
    String ReplaceKey() default "";

    /**
     * 主键修改方案的供应者.
     * 当上传成功后替换本地与之关联的数据. "{'tableName.fieldName':'paramKey',...}"
     * 如果只替换本接口主键 {'fieldName':'paramKey'}
     * @return 需要操作的关联表和字段名.
     */
    String Update() default "";

    /**
     * 下行作用是替换服务器来的数据.
     * 离线上行数据存储成功后返回的数据.
     * @return
     */
    String Return() default "";

    /**
     * 是否为文件提交，
     * @return 文件提交
     */
    boolean PutFile() default false;

    /**
     * 扩展数据,在复制关联关系表获取数据适合填充的固定项目.
     * @return 扩展数据.
     */
    String Extra() default "";

    /**
     * 强行忽略的字段,跳过这些字段建表.
     * @return 强行忽略的字段名,逗号隔开.
     */
    String Igs() default "";

    /**
     * 直接执行查询语句.
     * @return
     */
    String SQL() default "";

    /**
     * 是否为删除数据请求.
     * @return
     */
    boolean IsDelete() default false;

    /**
     * 只取不存.
     * @return
     */
    boolean NoSave() default false;

    /**
     * 拦截规则表达式.
     * @return
     */
    String Intercept() default "";

    /**
     * 自定义数据表达式.
     * @return
     */
    String Data() default "";

    /**
     * 缓存类型
     * @return
     */
    CacheType CacheType() default CacheType.CacheDownLoad;

    /**
     * 只使用缓存
     * @return
     */
    boolean OnlyCache() default false;
}