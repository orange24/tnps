package th.co.nttdata.tki.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import th.co.nttdata.tki.bean.TDailyWK;
import th.co.nttdata.tki.bean.TDailyWKDetail;
import th.co.nttdata.tki.bean.TDailyWKLossTime;
import th.co.nttdata.tki.dao.AbstractBaseDao;
import th.co.nttdata.tki.dao.TDailyWKLossTimeDao;

@Repository
@SuppressWarnings("unchecked")
public class TDailyWKLossTimeDaoImpl extends AbstractBaseDao implements TDailyWKLossTimeDao {

    @Override
    public void query(TDailyWK tdailyWK) {
        List<TDailyWKLossTime> allItems =
                (List<TDailyWKLossTime>) queryForList("t_dailywk_losstime.queryByDailyWK", tdailyWK);

        // group by dailyWKDetailId → ใส่ใน detail ที่ตรงกัน
        Map<Integer, List<TDailyWKLossTime>> byDetail = new HashMap<Integer, List<TDailyWKLossTime>>();
        for (TDailyWKLossTime item : allItems) {
            Integer did = item.getDailyWKDetailId();
            List<TDailyWKLossTime> list = byDetail.get(did);
            if (list == null) {
                list = new ArrayList<TDailyWKLossTime>();
                byDetail.put(did, list);
            }
            list.add(item);
        }

        if (tdailyWK.getDailyWKDetailList() != null) {
            for (TDailyWKDetail detail : tdailyWK.getDailyWKDetailList()) {
                List<TDailyWKLossTime> items = byDetail.get(detail.getDailyWKDetailId());
                detail.setLossTimeList(items != null ? items : new ArrayList<TDailyWKLossTime>());
            }
        }
    }

    @Override
    public List<TDailyWKLossTime> selectDAL_R03LossTime(TDailyWK tdailyWK) {
        List<TDailyWKLossTime> result = (List<TDailyWKLossTime>) queryForList("t_dailywk.selectDAL_R03LossTime", tdailyWK);
        return result != null ? result : Collections.<TDailyWKLossTime>emptyList();
    }

    @Override
    public void insert(TDailyWK tdailyWK) {
        if (tdailyWK.getDailyWKDetailList() == null) return;
        for (TDailyWKDetail detail : tdailyWK.getDailyWKDetailList()) {
            if (detail.getLossTimeList() == null) continue;
            for (TDailyWKLossTime item : detail.getLossTimeList()) {
                if (item.getLossTimeReasonId() == null || item.getLossTime() == null) continue;
                item.setDailyWKId(detail.getDailyWKId());
                item.setDailyWKDetailId(detail.getDailyWKDetailId());
                insert("t_dailywk_losstime.insert", item);
            }
        }
    }
}
