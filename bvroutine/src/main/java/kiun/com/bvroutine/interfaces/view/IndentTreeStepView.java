package kiun.com.bvroutine.interfaces.view;


public interface IndentTreeStepView extends TreeStepView{

    /**
     * 每一级缩进的像素.
     * @return 缩进数值 单位px.
     */
    int indent();
}
