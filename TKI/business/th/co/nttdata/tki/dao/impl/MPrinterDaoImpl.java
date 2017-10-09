package th.co.nttdata.tki.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import th.co.nttdata.tki.bean.MPrinter;
import th.co.nttdata.tki.dao.AbstractBaseDao;
import th.co.nttdata.tki.dao.MPrinterDao;

@Repository
@SuppressWarnings("unchecked")
public class MPrinterDaoImpl extends AbstractBaseDao implements MPrinterDao {

	@Override
	public List<MPrinter> getAllPrinter() {
		return queryForList("m_printer.select_printer");
	}

	@Override
	public String getPrinterNameByPrinterId(String printerId) {
		return (String) queryForObject("m_printer.getPrinterNameByprinterId",
				printerId);
	}

}