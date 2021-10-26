package kiun.com.bvroutine.data.verify;

/**
 * 验证中的问题.
 */
public class Problem {

    /**
     * 验证中的问题描述.
     */
    private String desc;

    /**
     * 出错字段.
     */
    private String field;

    /**
     * 是否强制要求.
     */
    private boolean force;

    /**
     * 问题的优先级,越大越会被重视.
     */
    private int level;

    public Problem(String desc, String field, boolean force, int level) {
        this.desc = desc;
        this.field = field;
        this.force = force;
        this.level = level;
    }

    public String getDesc() {
        if (desc == null){
            return "填入错误";
        }
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public boolean isForce() {
        return force;
    }

    public void setForce(boolean force) {
        this.force = force;
    }

    public int getLevel() {
        return level;
    }
}
