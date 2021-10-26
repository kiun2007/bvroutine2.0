package com.szxgm.gusustreet.model.query;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableField;

import android.text.TextUtils;

import com.alibaba.fastjson.annotation.JSONField;
import com.szxgm.gusustreet.base.QueryType;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import kiun.com.bvroutine.data.PagerBean;
import kiun.com.bvroutine.interfaces.TypeRequest;
import kiun.com.bvroutine.interfaces.callers.GetCaller;
import kiun.com.bvroutine.interfaces.callers.SetCaller;
import kiun.com.bvroutine.utils.ListUtil;

public class GeneralListQuery extends PagerBean<Object, GeneralListQuery> implements Parcelable, TypeRequest {

    private String field;

    private String table;

    private GetCaller<String, Boolean> putCaller;

    private List<Query> search = new LinkedList<>();

    private Class itemClz;

    @JSONField(serialize = false)
    private String[] filterNames;

    public GeneralListQuery(){}

    public void setItemClz(Class itemClz) {
        this.itemClz = itemClz;
    }

    public Class getItemClz() {
        return itemClz;
    }

    public GeneralListQuery(@NonNull Class clz){
        this(clz.getSimpleName(), clz);
    }

    public GeneralListQuery(String table, @NonNull Class itemClz){
        this();
        this.table = table;
        this.itemClz = itemClz;
    }

    protected GeneralListQuery(Parcel in) {
        field = in.readString();
        table = in.readString();
        in.readTypedList(search, Query.CREATOR);
    }

    public GeneralListQuery putEvent(GetCaller<String, Boolean> caller){
        putCaller = caller;
        return this;
    }

    public Query find(String field){
        return find(field, QueryType.Eq);
    }

    public Query find(String field, QueryType queryType){
        for (Query query : search){
            if (query.field.equals(field) && query.type == queryType){
                return query;
            }
        }
        return null;
    }

    public Query create(String field){
        Query query = find(field);
        if (query == null){
            query = new Query(field, QueryType.Eq, null);
            search.add(query);
        }
        return query;
    }

    public String findValue(String field){
        Query query = find(field);
        return query != null ? query.getValue() : null;
    }

    public GeneralListQuery filterFields(String... fields){
        filterNames = fields;
        return this;
    }

    @Override
    public boolean isEmpty() {

        if (filterNames != null){
            for (String fieldName : filterNames){
                if (find(fieldName) != null && find(fieldName).value != null){
                    return false;
                }
            }
        }
        return true;
    }

    public void clearFilter(){
        if (filterNames != null){
            for (String fieldName : filterNames){
                clear(fieldName);
            }
        }
    }

    public void clear(String field){
        clear(field, false);
    }

    public void clear(String field, boolean orderBy){

        for (int i = 0; i < search.size(); i++) {
            Query query = search.get(i);
            if (query.field.equals(field)){
                if (orderBy && !query.isOrderBy()){
                    continue;
                }
                search.remove(query);
                i--;
            }
        }
    }

    public GeneralListQuery orderBy(String field, QueryType type){
        clear(field, true);
        if (type == QueryType.Asc || type == QueryType.Desc){
            search.add(new Query(field, type, null));
        }
        return this;
    }

    public GeneralListQuery put(String field, String value){
        return put(field, QueryType.Eq, value);
    }

    public GeneralListQuery put(String field, QueryType type, String value) {

        try {
            return put(field, type, value, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GeneralListQuery put(String field, QueryType type, String value, boolean isRemove) throws Exception{

        if (type != QueryType.In){
            clear(field);
        }

        if (value != null && type != QueryType.Clear){

            //处理 in 逻辑.
            if (type == QueryType.In){
                Query query = find(field);

                if (query == null) {
                    if (!isRemove){
                        search.add(new Query(field, type, value));
                    }
                    return this;
                }

                if (TextUtils.isEmpty(query.value)){
                    query.value = isRemove ? null : value;
                }else{
                    String[] inValues = query.value.split(",");
                    List<String> listIn = new LinkedList<>();
                    for (String itemValue : inValues){
                        if (!itemValue.equals(value)){
                            listIn.add(itemValue);
                        }
                    }

                    if (!isRemove){
                        listIn.add(value);
                    }

                    StringBuffer itemBuff = new StringBuffer("");
                    ListUtil.map(listIn, item->itemBuff.append(item).append(","));

                    if (itemBuff.length() > 0){
                        query.value = itemBuff.substring(0, itemBuff.length() - 1);
                    }else{
                        clear(field);
                    }
                }
            }else{
                search.add(new Query(field, type, value));
                if (putCaller != null){
                    if (!putCaller.call(field)){
                        throw new Exception("拒绝操作");
                    }
                }
            }
        }
        return this;
    }

    public GeneralListQuery put(String field, QueryType type, long min, long max){
        clear(field);
        if (type == QueryType.Between){
            search.add(new Query(field, type, Integer.class.getSimpleName()){{
                setMin(min);
                setMax(max);
            }});
        }
        return this;
    }

    public GeneralListQuery put(String field, QueryType type, Date begin, Date end){
        clear(field);
        if (type == QueryType.Between){
            search.add(new Query(field, type, Date.class.getSimpleName()){{
                setMin(begin.getTime());
                setMax(end.getTime());
            }});
        }
        return this;
    }

    public boolean isIn(String field, String value){
        Query query = find(field);
        if (query != null && !TextUtils.isEmpty(query.getValue())){
            String[] values = query.getValue().split(",");
            for (String item : values){
                if (item.equals(value)){
                    return true;
                }
            }
        }
        return false;
    }

    @JSONField(serialize = false)
    public Map<String, String> toSimple(){
        Map<String, String> simpleMap = new HashMap<>();
        simpleMap.put("pageNum", String.valueOf(getPageNum()));
        simpleMap.put("pageSize", String.valueOf(getPageSize()));

        for(Query query : search){
            if (query.value != null){
                simpleMap.put(query.field, query.value);
            }
        }

        return simpleMap;
    }

    @Override
    public GeneralListQuery setPageSize(int pageSize) {
        super.setPageSize(pageSize);
        return this;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(field);
        dest.writeString(table);
        dest.writeTypedList(search);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GeneralListQuery> CREATOR = new Creator<GeneralListQuery>() {
        @Override
        public GeneralListQuery createFromParcel(Parcel in) {
            return new GeneralListQuery(in);
        }

        @Override
        public GeneralListQuery[] newArray(int size) {
            return new GeneralListQuery[size];
        }
    };

    public String getField() {
        return field;
    }

    public GeneralListQuery field(String field) {
        this.field = field;
        return this;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public List<Query> getSearch() {
        return search;
    }

    public void setSearch(List<Query> search) {
        this.search = search;
    }

    @Override
    public Class getType() {
        return itemClz;
    }

    public static class Query extends ObservableField<String> implements Parcelable{

        private String field;

        private QueryType type;

        private String value;

        private long min;

        private long max;

        public Query(){
        }

        public Query(String field, QueryType type, String value){
            this.field = field;
            this.type = type;
            this.value = value;
        }

        protected Query(Parcel in) {
            field = in.readString();
            type = QueryType.valueOf(in.readString());
            value = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(field);
            dest.writeString(type.toString());
            dest.writeString(value);
        }

        public static final Creator<Query> CREATOR = new Creator<Query>() {
            @Override
            public Query createFromParcel(Parcel in) {
                return new Query(in);
            }

            @Override
            public Query[] newArray(int size) {
                return new Query[size];
            }
        };

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public QueryType getType() {
            return type;
        }

        public boolean isOrderBy(){
            return type == QueryType.Asc || type == QueryType.Desc;
        }

        public void setType(QueryType type) {
            this.type = type;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public long getMin() {
            return min;
        }

        public void setMin(long min) {
            this.min = min;
        }

        public long getMax() {
            return max;
        }

        public void setMax(long max) {
            this.max = max;
        }

        @Override
        public void set(String value) {
            this.value = value;
            super.set(value);
        }

        @Nullable
        @Override
        public String get() {
            return value;
        }
    }
}
