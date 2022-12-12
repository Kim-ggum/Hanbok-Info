package com.bck.hanbokbck.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing // auditing 기능 활성화
public class JpaAuditingConfig {

}
