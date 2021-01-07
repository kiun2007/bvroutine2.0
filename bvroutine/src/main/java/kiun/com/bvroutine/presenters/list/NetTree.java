package kiun.com.bvroutine.presenters.list;

import java.util.LinkedList;
import java.util.List;

import kiun.com.bvroutine.interfaces.TreeItem;
import kiun.com.bvroutine.utils.RetrofitUtil;
import retrofit2.Call;

public class NetTree implements TreeItem {

    protected boolean isLoaded = false;

    private Call netCall;

    public NetTree() {}

    public NetTree(Call netCall) {
        this.netCall = netCall;
    }

    @Override
    public int itemLayoutId() {
        return 0;
    }

    @Override
    public boolean withChild(int parentLevel) {
        return false;
    }

    @Override
    public List<?> itemList() throws Exception {
        if (!isLoaded){
            isLoaded = true;
            if (netCall != null){
                return RetrofitUtil.unpackWrap(null, netCall.execute());
            }
        }
        return new LinkedList<>();
    }

    public static NetTree create(Call netCall){
        return new NetTree(netCall);
    }
}
