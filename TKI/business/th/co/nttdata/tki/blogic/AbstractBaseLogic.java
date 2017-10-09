package th.co.nttdata.tki.blogic;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractBaseLogic {

	@Autowired
	protected Properties settings;
}