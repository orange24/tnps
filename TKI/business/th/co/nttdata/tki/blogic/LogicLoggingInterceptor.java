package th.co.nttdata.tki.blogic;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Component;

@Component("logicLog")
public class LogicLoggingInterceptor {

	private static final ObjectMapper mapper = new ObjectMapper();
	private static final Log logger = LogFactory.getLog("th.co.nttdata.tki.blogic");

	public void before( JoinPoint jp ) {

		// <!-- Checking: No need to serialize data if not debugging. -->
		if( !logger.isDebugEnabled() ) {
			return;
		}

		try {
			Object[] params = jp.getArgs();
			StringBuilder buffer = new StringBuilder();
			if( params != null )
				for( Object param : params ) {
					buffer.append( mapper.writeValueAsString(param) );
				}

			logger.info("***** LOGIC START [" + buffer + "] *****");
		} catch( Exception ex ) {
			afterThrowing(ex);
		}
	}

	public void afterReturning( Object returnValue ) {

		// <!-- Checking: No need to serialize data if not debugging. -->
		if( !logger.isDebugEnabled() ) {
			return;
		}

		try {
			int maxLength = 8192, begin = 0, end = maxLength;
			StringBuilder returnString = new StringBuilder(mapper.writeValueAsString(returnValue));

			logger.info("***** LOGIC END   [");
			while( returnString.length() > maxLength && end < returnString.length() ) {

				logger.info( returnString.substring(begin, end) );
				begin = end;
				end += maxLength;
			}
			logger.info( returnString.substring(begin) + "] *****");
		} catch( Exception ex ) {
			afterThrowing(ex);
		}
	}

	public void afterThrowing( Exception exception ) {
		logger.error("***** LOGIC Exception : ", exception);
	}
}