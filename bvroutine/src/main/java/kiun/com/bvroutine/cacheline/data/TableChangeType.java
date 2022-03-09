package kiun.com.bvroutine.cacheline.data;

/**
 * 表格结构变化类型.
 */
public enum TableChangeType{

    /**
     * 不修改或创建表格.
     */
    ChangeNone,

    /**
     * 修改表结构.
     */
    ChangeEidt,

    /**
     * 创建表.
     */
    ChangeCreate
}