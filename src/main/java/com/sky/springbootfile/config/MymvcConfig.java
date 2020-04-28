package com.sky.springbootfile.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 扩展SpringMvc
@Configuration
public class MymvcConfig implements WebMvcConfigurer {

    // 注册自定义视图解析器
    @Bean
    public MyViewResolver getMyViewResolver(){
        return new MyViewResolver();
    }

    /**
     *
     *
     * Springboot 自定义localeResolver不生效的问题
     *
     *通过修改配置类, Bean创建函数的@Bean注解的值，将beanName设置成localResolver，果然问题解决了。
     *
     * @Bean("localeResolver")
     * public LocaleResolver getMyLocalResolver(){
     *
     *     return new MyLocalResolver();
     * }
     * 当然也可以像下边这样直接修改方法名，也可以解决问题。因为spring在通过java config对象中的方法创建Bean时，默认会用方法的名字作为容器中该bean的名字（就是spring配置文件中，bean的id的值）。
     *
     * @Bean
     * public MyLocalResolver localeResolver(){
     *
     *     return new MyLocalResolver();
     * }
     *  顺便说一下，WebMvcAutoConfiguration里边自动配置的localeResolver，好像并没有直接装载到容器中。即使我们没有在我们的配置类中定义自己的localResolver，在容器中也不能直接找到加载的localResolver对象。
     *
     * public class WebMvcAutoConfiguration {
     * ...
     * @Bean
     * @ConditionalOnMissingBean
     * @ConditionalOnProperty(prefix = "spring.mvc", name = "locale")
     * public LocaleResolver localeResolver() {
     *    if (this.mvcProperties.getLocaleResolver() == WebMvcProperties.LocaleResolver.FIXED) {
     *       return new FixedLocaleResolver(this.mvcProperties.getLocale());
     *    }
     *    AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
     *    localeResolver.setDefaultLocale(this.mvcProperties.getLocale());
     *    return localeResolver;
     * }
     * 我们可以通过如下代码确认这一点。
     *
     * @SpringBootApplication
     * public class SpringBoot04WebRestfulCrudApplication {
     *
     *     public static void main(String[] args) {
     *         ApplicationContext ac = SpringApplication.run(SpringBoot04WebRestfulCrudApplication.class, args);
     *         //Arrays.stream(ac.getBeanNamesForType(LocaleResolver.class)).forEach(System.out::println);
     *         String[] beanNamesForType = ac.getBeanNamesForType(LocaleResolver.class);
     *         if(beanNamesForType.length > 0){
     *             for (String s: beanNamesForType) {
     *                 System.out.println("================>" + s);
     *             }
     *         }
     *     }
     * }
     *
     *
     *
     *
     * @return
     */
    @Bean
    public MyLocalResolver localeResolver(){
        return new MyLocalResolver();
    }

    // 自定义视图跳转
    // 访问：http://localhost:8080/sky 页面条跳转至： index.html
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("login").setViewName("login");
    }
}
