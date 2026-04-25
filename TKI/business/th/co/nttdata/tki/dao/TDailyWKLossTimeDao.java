package th.co.nttdata.tki.dao;

import java.util.List;

import th.co.nttdata.tki.bean.TDailyWK;
import th.co.nttdata.tki.bean.TDailyWKLossTime;

public interface TDailyWKLossTimeDao {
    /** โหลด lossTimeList ใส่แต่ละ detail ใน TDailyWK */
    void query(TDailyWK tdailyWK);
    /** insert losstime items ทุกรายการจาก detail list */
    void insert(TDailyWK tdailyWK);
    /** โหลด losstime items สำหรับ DAL_R03 report (filter ตาม criteria เดียวกับ selectDAL_R03) */
    List<TDailyWKLossTime> selectDAL_R03LossTime(TDailyWK tdailyWK);
}
