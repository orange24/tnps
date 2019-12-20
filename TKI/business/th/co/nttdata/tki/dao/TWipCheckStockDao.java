package th.co.nttdata.tki.dao;

import th.co.nttdata.tki.bean.TWipCheckStock;

public interface TWipCheckStockDao {
     
        public TWipCheckStock getWipFg();
        
        public TWipCheckStock getWipFg(TWipCheckStock TWipChekStock);        

}