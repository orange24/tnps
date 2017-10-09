package th.co.nttdata.tki.blogic.cmm.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.MUser;
import th.co.nttdata.tki.bean.Message;
import th.co.nttdata.tki.blogic.AbstractBaseLogic;
import th.co.nttdata.tki.blogic.cmm.CMM_S02Logic;
import th.co.nttdata.tki.dao.MUserDao;

@Service
public class CMM_S02LogicImpl extends AbstractBaseLogic implements CMM_S02Logic {

	@Autowired
	private MUserDao userDao;

	@Override
	public MUser init() {
		return userDao.getUser();
	}

	@Override
	public MUser save( MUser MUser ) {
		
		MUser user = userDao.getUser();
		boolean isCorrect = new Md5PasswordEncoder().encodePassword(MUser.getOldPswrd(), null).equals(user.getPassword());
		if(isCorrect){
			
			if (MUser.getPassword() != null && !"".equals(MUser.getPassword())) {
				PasswordEncoder encoder = new Md5PasswordEncoder();
				String hashedPass = encoder.encodePassword(MUser.getPassword(), null);
				MUser.setPassword(hashedPass);
			}
			MUser.setOldPswrd(user.getPassword());
			userDao.updatePassword(MUser);	
			// <!-- Set 'Successfully' Message. -->
			MUser.getInfos().add( new Message("inf.cmm.002", new String[] {}) );
			return MUser;
			
		}else{
			MUser.getErrors().add( new Message("err.cmm.010", new String[] {"Old Password"}) );
			return MUser;
		}
	}
}