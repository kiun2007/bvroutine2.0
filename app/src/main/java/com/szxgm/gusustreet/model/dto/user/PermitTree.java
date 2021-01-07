//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.szxgm.gusustreet.model.dto.user;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PermitTree {
    public static final String ADMIN = "admin";
    public static final String AMBIENT_WORKER = "ambient_worker";
    public static final String DEPART_ADMIN = "depart_admin";
    public static final String GRID_ADMIN = "grid_admin";
    public static final String GRID_WORKER = "grid_worker";
    public static final String LEADER_PERSON = "leader_person";
    public static final String MARKET_MANAGER = "market_manager";
    public static final String NON = "____non";
    public static final String STREET_ADMIN = "street_admin";
    public static final String STREET_EVALUATION = "street_evaluation";
    public static final String STREET_RIVER_MANAGE = "street_river_manage";
    public static final String TREATMENT_MANAGER = "treatment_manager";
    public static final String URBAN_WORKER = "urban_worker";
    public static final String WORK_STATION_ADMIN = "work_station_admin";
    private static final Map<String, PermitTree> permitTreeMap = new HashMap();
    private String rolesName;
    List<PermitTree> withPermit;

    static {
        putTree(TREATMENT_MANAGER);
        putTree(MARKET_MANAGER);
        putTree(URBAN_WORKER);
        putTree(AMBIENT_WORKER);
        putTree(STREET_RIVER_MANAGE);
        putTree(GRID_WORKER);
        putTree(STREET_EVALUATION);
        putTree(GRID_ADMIN, GRID_ADMIN);
        putTree(DEPART_ADMIN, GRID_WORKER, STREET_RIVER_MANAGE);
        putTree(WORK_STATION_ADMIN, TREATMENT_MANAGER, MARKET_MANAGER, URBAN_WORKER, AMBIENT_WORKER);
        putTree(STREET_ADMIN, GRID_ADMIN, STREET_EVALUATION, WORK_STATION_ADMIN);
        putTree(LEADER_PERSON, STREET_ADMIN);
        putTree(ADMIN, LEADER_PERSON, DEPART_ADMIN);
    }

    private PermitTree(String var1, String... var2) {
        this.rolesName = var1;
        this.withPermit = new LinkedList();
        int var4 = var2.length;

        for(int var3 = 0; var3 < var4; ++var3) {
            var1 = var2[var3];
            PermitTree var5 = (PermitTree)permitTreeMap.get(var1);
            if (var5 != null) {
                this.withPermit.add(var5);
            }
        }

    }

    public static PermitTree permit(String var0) {
        PermitTree var1 = (PermitTree)permitTreeMap.get(var0);
        PermitTree var2 = var1;
        if (var1 == null) {
            var2 = new PermitTree("____non", new String[0]);
        }

        return var2;
    }

    static void putTree(String var0, String... var1) {
        permitTreeMap.put(var0, new PermitTree(var0, var1));
    }

    public String getRolesName() {
        return this.rolesName;
    }

    public boolean isWithRoles(String var1) {
        if (this.rolesName.equals(var1)) {
            return true;
        } else {
            Iterator var2 = this.withPermit.iterator();

            do {
                if (!var2.hasNext()) {
                    return false;
                }
            } while(!((PermitTree)var2.next()).isWithRoles(var1));

            return true;
        }
    }
}