package com.app.multitenancy.domain;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import io.micrometer.common.util.StringUtils;

@Component
@RequestScope
public class ManagementTenant {

	private Long currencyTenantId;

	public void setCurrencyTenant(String tenantId) {
		if (StringUtils.isEmpty(tenantId)) return;
		
		this.currencyTenantId = Long.valueOf(tenantId);
	}
	
	public Long getCurrencyTenant() {
		return this.currencyTenantId;
	}
}
