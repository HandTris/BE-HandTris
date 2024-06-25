package jungle.HandTris.global.config;

import jungle.HandTris.global.filter.MDCFilter;
import jungle.HandTris.global.filter.ServletWrappingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    private final MDCFilter mdcFilter;

    public FilterConfig(MDCFilter mdcFilter) {
        this.mdcFilter = mdcFilter;
    }

    @Bean
    public FilterRegistrationBean<ServletWrappingFilter> secondFilter() {
        FilterRegistrationBean<ServletWrappingFilter> filterRegistrationBean = new FilterRegistrationBean<>(
                new ServletWrappingFilter());
        filterRegistrationBean.setOrder(-100);
        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean<MDCFilter> thirdFilter() {
        FilterRegistrationBean<MDCFilter> filterRegistrationBean = new FilterRegistrationBean<>(mdcFilter);
        filterRegistrationBean.setOrder(-99);
        return filterRegistrationBean;
    }
}
