package kiun.com.bvroutine.handlers;

import java.util.HashMap;
import java.util.Map;

import kiun.com.bvroutine.base.BaseHandler;
import kiun.com.bvroutine.presenters.list.Refresher;

public class ListHandler<T> extends BaseHandler<T> {

    public final static int LIST_LOADING = 0;
    public final static int LIST_ERROR = 1;
    public final static int LIST_EMPTY = 2;

    protected String errorDesc;
    protected String emptyDesc;
    protected String loadingDesc;

    protected String runError;

    private int type = 0;
    private int errorLayout = 0;

    private boolean initStart = true;

    private Object tag;
    private Map<Integer, Object> tagMaps = new HashMap<>();

    private static Config config = new Config();

    public static class Config{

        private String error = null;
        private String empty = null;
        private String loading = null;
        private int layout = 0;

        public Config empty(String empty) {
            this.empty = empty;
            return this;
        }

        public Config error(String error) {
            this.error = error;
            return this;
        }

        public Config loading(String loading) {
            this.loading = loading;
            return this;
        }

        public Config layout(int layout){
            this.layout = layout;
            return this;
        }

        private String errorNormal(String newValue){
            return newValue != null ? newValue : error;
        }

        private String emptyNormal(String newValue){
            return newValue != null ? newValue : empty;
        }

        private String loadingNormal(String newValue){
            return newValue != null ? newValue : loading;
        }

        private int layoutNormal(int newValue){
            return newValue != 0 ? newValue : this.layout;
        }
    }

    public static Config configNormal(){
        return config;
    }

    public ListHandler(int handlerBR, int errorLayout) {
        this(handlerBR, errorLayout, null,null);
    }

    public ListHandler(int handlerBR, int errorLayout, String errorDesc, String emptyDesc) {
        this(handlerBR, errorLayout, errorDesc, emptyDesc, null);
    }

    public ListHandler(int handlerBR, int errorLayout, String errorDesc, String emptyDesc, String loadingDesc) {
        super(handlerBR);
        this.errorDesc = config.errorNormal(errorDesc);
        this.emptyDesc = config.emptyNormal(emptyDesc);
        this.loadingDesc = config.loadingNormal(loadingDesc);
        this.errorLayout = config.layoutNormal(errorLayout);
    }

    public void setType(int type){
        this.type = type;
    }

    public int getType(){
        return type;
    }

    public int getItemLayout(T item){
        return 0;
    }

    public String getDesc(){
        if (type == LIST_ERROR){
            return errorDesc != null ? errorDesc : runError;
        }else if (type == LIST_EMPTY){
            return emptyDesc;
        }
        return loadingDesc;
    }

    public void setTag(int tag, Object value){
        tagMaps.put(tag, value);
    }

    public Object getTag(int tag){
        return tagMaps.get(tag);
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public Object getTag() {
        return tag;
    }

    public boolean isSelect(T item){
        return false;
    }

    public void setErrorLayout(int errorLayout) {
        this.errorLayout = errorLayout;
    }

    public void setEmptyDesc(String emptyDesc) {
        this.emptyDesc = emptyDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }

    public void setLoadingDesc(String loadingDesc) {
        this.loadingDesc = loadingDesc;
    }

    public String getRunError() {
        return runError;
    }

    public int getErrorLayout() {
        return errorLayout;
    }

    public void error(String error){
        runError = error;
    }

    public boolean isLoading(){
        return type == LIST_LOADING;
    }

    public boolean isError(){
        return type == LIST_ERROR;
    }

    public boolean isEmpty(){
        return type == LIST_EMPTY;
    }

    public boolean itemChose(T item){
        return false;
    }

    public boolean isInitStart() {
        return initStart;
    }

    public void setInitStart(boolean initStart) {
        this.initStart = initStart;
    }

    public void refresh(){
        if (getTag() instanceof Refresher){
            ((Refresher) getTag()).refresh();
        }
    }
}
