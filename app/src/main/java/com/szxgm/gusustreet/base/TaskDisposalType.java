package com.szxgm.gusustreet.base;

import com.szxgm.gusustreet.R;

public enum TaskDisposalType {

    /**
     * 个人任务
     */
    quickTaskPerson(R.layout.layout_disposal_depart),

    //联动工作站节点
    workStation(R.layout.layout_disposal_work_station),

    //街道任务分派中心
    subCenter(R.layout.layout_disposal_sub_center),

    //处室部门
    depart(R.layout.layout_disposal_depart),

    //街道平台核查节点
    subCenterCheck(R.layout.layout_disposal_sub_center_check),

    //街道平台核查节点
    subCenterCheckB(R.layout.layout_disposal_sub_center_check),

    //街道领导节点
    streetLeader(R.layout.layout_disposal_street_leader),

    //联动工作站分配任务节点
    stationLeader(R.layout.layout_disposal_station_leader),

    //协同处置节点
    unionPerson(R.layout.layout_disposal_union_person),

    //协同处置实施
    unionDisposal(R.layout.layout_disposal_union),

    //街道平台节点
    streetStation(R.layout.layout_disposal_street_station);

    /**
     * 处置使用的布局.
     */
    private int layoutId;

    TaskDisposalType(int layoutId) {
        this.layoutId = layoutId;
    }

    public int getLayoutId() {
        return layoutId;
    }
}
