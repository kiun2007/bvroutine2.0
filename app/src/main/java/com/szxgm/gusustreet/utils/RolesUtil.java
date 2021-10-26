package com.szxgm.gusustreet.utils;

import androidx.annotation.NonNull;

import com.szxgm.gusustreet.model.dto.user.PermitTree;
import com.szxgm.gusustreet.model.dto.user.Roles;
import com.szxgm.gusustreet.model.dto.user.User;

import kiun.com.bvroutine.utils.SharedUtil;

public class RolesUtil {

    /**
     * 指定某角色使用.
     * @param rolesKey 角色Key
     * @return 用户是否拥有使用权限.
     */
    public static boolean assign(@NonNull String rolesKey){
        User user = SharedUtil.getValue(User.TAG, new User());
        if (user.getRoles() != null){
            for (Roles roles : user.getRoles()){
                if (rolesKey.equals(roles.getRoleKey())){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 在权限树中,权力高度最少满足此权限才可以使用.
     * @param rolesKey 角色key.
     * @return 用户是否拥此功能权限使用.
     */
    public static boolean minimum(@NonNull String rolesKey){

        User user = SharedUtil.getValue(User.TAG, new User());
        if (user.getRoles() != null){
            for (Roles roles : user.getRoles()){
                PermitTree permitTree = PermitTree.permit(roles.getRoleKey());
                if (permitTree.isWithRoles(rolesKey)){
                    return true;
                }
            }
        }
        return false;
    }
}
