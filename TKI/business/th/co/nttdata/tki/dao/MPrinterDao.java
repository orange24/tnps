package th.co.nttdata.tki.dao;

import java.util.List;

import th.co.nttdata.tki.bean.MPrinter;

public interface MPrinterDao {

	List<MPrinter> getAllPrinter();

	String getPrinterNameByPrinterId(String string);

}