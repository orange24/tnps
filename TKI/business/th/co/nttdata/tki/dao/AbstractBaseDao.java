package th.co.nttdata.tki.dao;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.security.core.context.SecurityContextHolder;

import th.co.nttdata.tki.bean.AbstractBaseBean;
import th.co.nttdata.tki.bean.Message;

import com.ibatis.sqlmap.client.SqlMapClient;

public abstract class AbstractBaseDao extends SqlMapClientTemplate {

	@Autowired
	@Qualifier("sqlsvrMapClient")
	@Override
	public void setSqlMapClient(SqlMapClient sqlMapClient) {
		super.setSqlMapClient(sqlMapClient);
	}
	
	protected static final Integer getSkipResult(AbstractBaseBean bean) {
		return getSkipResult(bean.getPageNumber(), bean.getPageCount());
	}
	
	protected static final Integer getSkipResult(Integer pageNumber, Integer pageCount) {
		return (pageNumber-1) * pageCount;
	}
	
	protected final void calPageTotal(String selectCountId, AbstractBaseBean bean) {
		Integer rowTotal = (Integer) queryForObject(selectCountId, bean);
		bean.setPageTotal(calPageTotal(rowTotal, bean.getPageCount()));
		checkItemNotFound(bean);
	}
	
	protected static final Integer calPageTotal(Integer rowTotal, Integer pageCount) {
		BigDecimal pages = new BigDecimal(rowTotal).divide(new BigDecimal(pageCount), 0, RoundingMode.UP);
		return pages.intValue();
	}
	
	protected final void checkItemNotFound(AbstractBaseBean bean) {
		if (bean.getPageTotal() == 0) {
			bean.getInfos().add(new Message("inf.cmm.004", null));
		}
	}
	
	protected static final String getUsername() {
		if (SecurityContextHolder.getContext().getAuthentication() == null) {
			return "TEST";
		}
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}
}