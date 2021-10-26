package kiun.com.bvroutine.data.verify;

/**
 * 问题输出
 */
public interface ProblemExport {

    /**
     * 发现问题.
     * @param problem
     */
    void onProblem(Problem problem);

    /**
     * 清除问题.
     */
    void clear();
}
