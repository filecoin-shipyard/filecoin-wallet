package pro.xjxh.wallet.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * app configuration
 * @author yangjian
 */
@Configuration
public class WebAppConf extends WebMvcConfigurationSupport {

	/**
	 * fix bug for HttpMediaTypeNotAcceptableException
	 * ('Could not find acceptable representation')
	 * @param configurer
	 */
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer)
	{
		configurer.favorPathExtension(false);
	}

	/**
	 * set the static resource dir
	 * @param registry
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry)
	{
		registry.addResourceHandler("static/**").addResourceLocations("classpath:/static/");
	}
}
