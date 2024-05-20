package com.asimkilic.courier.common.config.async;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
@Slf4j
@RequiredArgsConstructor
public class AsyncConfiguration implements AsyncConfigurer {

    public static final String DEFAULT_THREAD_EXECUTOR_NAME = "defaultThreadExecutor";

    @Bean(DEFAULT_THREAD_EXECUTOR_NAME)
    public Executor publishTaskExecutor(ThreadExecutorProperties properties) {
        var taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(properties.getCorePoolSize());
        taskExecutor.setMaxPoolSize(properties.getMaxPoolSize());
        taskExecutor.setQueueCapacity(properties.getQueueCapacity());
        taskExecutor.setThreadNamePrefix(properties.getName());
        taskExecutor.initialize();
        return taskExecutor;
    }

    @Bean
    @ConfigurationProperties(prefix = "thread-executor.default")
    public ThreadExecutorProperties defaultThreadExecutorProperties() {
        return new ThreadExecutorProperties();
    }

    @Override
    public Executor getAsyncExecutor() {
        return new SimpleAsyncTaskExecutor();
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new AsyncExceptionHandler();
    }


    public static class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
        @Override
        public void handleUncaughtException(Throwable ex, Method method, Object... params) {
            log.error("Unexpected asynchronous exception at : {} - {} ",
                    method.getDeclaringClass().getName(), method.getName(), ex);
        }
    }
}
